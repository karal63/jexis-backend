# Account resource limits

The backend checks the account's current ACTIVE or TRIALING subscription and merges plan entitlements with subscription overrides. The period start is inclusive and the end is exclusive. Scheduled cancellation keeps access through the current period. No eligible subscription, multiple eligible subscriptions, or a missing/invalid limit denies additions.

## Configuration and deployment

Before deploying enforcement:

1. Create entitlement definitions with keys `max_cards` and `max_members` through `POST /admin/entitlements`. The type is a business category (for example, `limit`), not a variable type; the checker validates the numeric value independently of that category. Reuse existing definitions with those keys.
2. Set each plan's actual business limits through `POST /admin/plans/{id}/entitlements`. Preserve the full existing list: this API replaces assignments omitted from its payload. Values must be strings of nonnegative decimal integers within Java's signed 64-bit range. Zero means none; unlimited is unsupported.
3. Where necessary, configure subscription overrides through `POST /admin/subscriptions/{id}/entitlements`, also preserving existing assignments.
4. Apply `docs/sql/account-resource-limits.sql` to PostgreSQL before deploying the new application, or use the repository's existing Hibernate schema-update mechanism. The SQL only adds replacement-tracking columns; it does not choose or overwrite business limits.
5. Deploy all backend instances together (or drain old instances first). Older instances do not enforce limits or acquire the account lock.

No production data or plan values are automatically modified by this change. Existing resources remain after a downgrade, but additions are denied until capacity is available. Account onboarding alone can initialize its first owner without a subscription.

## API

`GET /accounts/{accountId}/limits/cards?additionalQuantity=2`

Use `members` for the member limit. Quantity defaults to 1; 0 asks whether current usage fits. Account-view authorization is required. The endpoint is advisory: creation repeats the check under a database lock.

Example response:

```json
{
  "allowed": true,
  "used": 8,
  "limit": 10,
  "remaining": 2,
  "additionalQuantity": 2,
  "reason": null
}
```

Denials return HTTP 200 with `allowed: false` and a reason: `LIMIT_EXCEEDED`, `NO_ELIGIBLE_SUBSCRIPTION`, `AMBIGUOUS_SUBSCRIPTION`, `LIMIT_NOT_CONFIGURED`, or `INVALID_LIMIT_CONFIGURATION`. Unknown/unusable limits return `limit: null` and `remaining: 0`. Invalid resource/quantity returns 400. Creation guards return 403 with the corresponding domain error code. Existing role authorization remains required.

Internally, use `CheckAccountResourceLimitUseCase.execute(accountId, AccountResource.CARDS, quantity)` for a decision, or `requireCapacity(...)` for enforcement. Call enforcement inside a write transaction after `LockAccountUseCase.execute(accountId)`. New resources must define an explicit entitlement key and a database usage counter.

## Counting and replacements

Cards count across all account wallets through the cardholder's account: active and inactive cards count; deleted and canceled cards do not. All members count, including owners. Card creation validates both the wallet's and cardholder's account.

Pending replacements reserve one slot, which is included in `used`. Preparation cancels the original in Stripe and commits the canceled status, original reason, and reservation. Issuance then runs in a separate transaction under the same account lock. Failure keeps the reservation; retry the same replacement endpoint with the same reason.

The Stripe request uses `replace-card-{originalCardId}` as its idempotency key. Before issuance it also searches the cardholder's Stripe cards for an existing replacement, recovering issuance whose local commit failed. A successful replacement is linked to the original in the database; subsequent retries return that card. Multiple Stripe replacements require operator reconciliation rather than issuing another card.

A pending replacement cannot be edited or deleted. Completed replacements may proceed even if a downgrade left usage over the limit, provided the subscription is eligible. Canceled/deleted cards cannot be reactivated. Stripe and database operations are not a distributed transaction: monitor pending replacements and reconcile external failures. The account lock spans Stripe calls, so long Stripe latency delays other mutations for that account.

## Verification

`./gradlew test` covers API method authorization, limits and overrides, status/date boundaries, bootstrap owner creation, account isolation, blocked issuance, replacement failures/retries, and concurrent card/member creation. Database concurrency tests use isolated H2 in PostgreSQL compatibility mode; repeat deployment smoke tests against PostgreSQL before release.
