-- Apply before deploying enforcement if schema updates are managed manually.
-- Numeric entitlement values are business configuration, not migration defaults.
BEGIN;
ALTER TABLE cards ADD COLUMN IF NOT EXISTS replacement_pending boolean NOT NULL DEFAULT false;
ALTER TABLE cards ADD COLUMN IF NOT EXISTS replacement_reason varchar(255);
ALTER TABLE cards ADD COLUMN IF NOT EXISTS replacement_card_id uuid REFERENCES cards(id);
CREATE UNIQUE INDEX IF NOT EXISTS cards_replacement_card_unique ON cards (replacement_card_id);
COMMIT;
