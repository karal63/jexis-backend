# Domain Logic Documentation

## ACCOUNT Module

FILE: CreateAccountDto.java

CreateAccountDto

Data Transfer Object used for account creation requests. It encapsulates the
necessary data required to create a new account, such as the account name and
owner ID.

Author: Leo

---

FILE: EditAccountDto.java

EditAccountDto

Data Transfer Object used for account editing requests. It encapsulates the
necessary data required to edit an existing account, such as the account ID
and updated account details.

Author: Leo

---

FILE: CreateAccountUseCase.java

CreateAccountUseCase

This service class implements the use case for creating a new account.
It contains only the business logic related to account creation, such as
validating input data and interacting with the repository to persist the new
account.

Author: Leo

Handles account creation.

Accepts a {@link CreateAccountDto} payload from controller, looks for
duplicate account name, calls the repository to save the account, and returns
the created {@link Account}.

@param body passed by controller payload containing account creation data
@return the newly created account

---

FILE: DeleteAccountUseCase.java

DeleteAccountUseCase

This service class implements the use case for deleting an existing account.
It contains only the business logic related to account deletion, such as
validating the input ID and interacting with the repository to remove the
account.

Author: Leo

Handles account deletion.

Accepts a {@link UUID} payload from controller, looks for
the account, calls the repository to delete the account, and returns
nothing.

@param body passed by controller payload containing account creation data

---

FILE: EditAccountUseCase.java

EditAccountUseCase

This service class implements the use case for editing an existing account.
It contains the business logic related to updating an account, such as
interacting with the repository to fetch and save the updated data, and
validating if user with given id exists.

Author: Leo

Handles account editing.

Accepts a {@link EditAccountDto} payload and {@link id} from controller,
looks for
the account, calls the repository to update the account, and returns
the updated {@link Account}.

@param body passed by controller payload containing account editing data
@param id the ID of the account to edit
@return the updated account

---

FILE: GetAccountsUseCase.java

GetAccountsUseCase

This service class implements the use case for retrieving all existing
accounts. It contains only the business logic related to fetching accounts,
such as interacting with the repository to fetch the data.

Author: Leo

Handles fetching all accounts.

Calls the repository to fetch all accounts and returns the list of
accounts.

@return list of all accounts

---

FILE: GetAccountUseCase.java

GetAccountUseCase

This service class implements the use case for retrieving a specific account.
It contains the business logic related to fetching an account,
such as interacting with the repository to fetch the data and validating if
user with given id exists

Author: Leo

Handles fetching a specific account.

Calls the repository to fetch the account and returns the account.

@param id the ID of the account to fetch
@return the fetched account

---

FILE: Account.java

Account entity mapped to the persistence layer.

Represents an account record stored in the database and defines
its persistence structure (table mapping, constraints, and identifiers).

This class is managed by JPA and is used to persist and retrieve
account data.

Author: Leo

---

FILE: AccountNotFoundException.java

Returns an error response when account with given id is not found

This exception is thrown when account name was not found in the database. It
extends {@link DomainException} to provide a specific error status, code and
message, which are then handled by the global exception handler to return a
structured error response to the client.

Author: Leo

---

FILE: NameExistsException.java

Returns an error response when an account with the same name already exists.

This exception is thrown when account name was found in the database. It
extends {@link DomainException} to provide a specific error status, code and
message, which are then handled by the global exception handler to return a
structured error response to the client.

Author: Leo

---

FILE: AccountController.java

AccountController

REST controller in the presentation layer responsible for exposing
account-related HTTP endpoints.

It handles request routing, input validation, and response mapping,
delegating all business logic execution to dedicated account use case
services (application layer).

This class does not contain domain logic; its role is limited to
orchestrating request/response flow between the client and the
application layer.

Author: Leo

Returns a list of all accounts.

This endpoint retrieves all existing accounts by delegating to the
getAccountsUseCase, which interacts with the repository to fetch the data.

Endpoint: GET /account/list

@return list of all accounts

Return a specific account.

This endpoint retrieves a specific account by delegating to the
getAccountsUseCase, which interacts with the repository to fetch the data.

Endpoint: GET /account/{id}

@param id the ID of the account to retrieve
@return the account with the specified ID

Handles account creation requests.

Accepts a {@link CreateAccountDto} payload, delegates execution to the
createAccountUseCase, and returns the created {@link Account}.

Endpoint: POST /account/create

@param body request payload containing account creation data
@return the newly created account

Handles account deletion requests.

Accepts a {@link id} in the path, delegates execution to the
deleteAccountUseCase, and returns the deleted {@link Account}.

Endpoint: DELETE /account/delete/{id}

@param id the ID of the account to delete
@return message confirming deletion of the account with the specified ID

Handles account editing requests.

Accepts a {@link id} in the path, delegates execution to the
editAccountUseCase, and returns the updated {@link Account}.

Endpoint: PATCH /account/edit/{id}

@param id the ID of the account to edit
@param body payload with updated values for the account
@return retuers updated account

---

## AUTH Module

FILE: AuthUser.java

AuthUser

Data Transfer Object used for authentication requests. It encapsulates the
necessary data required for user authentication, such as the user ID, name,
email, and activation status.

Author: Leo

---

FILE: LoginDto.java

LoginDto

Data Transfer Object used for login requests. It encapsulates the
necessary data required for user login, such as the user's email and
password.

Author: Leo

---

FILE: LoginResult.java

LoginResult

Data Transfer Object used for login results. It encapsulates the
necessary data required to return the result of a login request, such as
the authenticated user and their tokens.

Author: Leo

---

FILE: SignupDto.java

SignupDto

Data Transfer Object used for signup requests. It encapsulates the
necessary data required to create a new user account, such as the user's
name, email, and password.

Author: Leo

---

FILE: SignupResult.java

SignupResult

Data Transfer Object used for signup results. It encapsulates the
necessary data required to return the result of a signup request, such as
the newly created user and their tokens.

Author: Leo

---

FILE: TokenPair.java

TokenPair

Data Transfer Object used for representing a pair of access and refresh
tokens.

Author: Leo

---

FILE: LoginUseCase.java

LoginUseCase

This service class implements the use case for logging in a user.
It contains only the business logic related to user authentication, such as
validating input data and interacting with the repository to verify user
credentials.

Author: Leo

---

FILE: RefreshTokensUseCase.java

RefreshTokensUseCase

This service class implements the use case for refreshing authentication
tokens.
It contains only the business logic related to token refresh, such as
validating the refresh token and generating new access and refresh tokens.

Author: Leo

---

FILE: SignupUseCase.java

SignupUseCase

This service class implements the use case for signing up a new user.
It contains only the business logic related to user registration, such as
validating input data and interacting with the repository to persist the new
user.

Author: Leo

---

FILE: InvalidRtException.java

InvalidRtException

This exception is thrown when the provided refresh token is invalid. It
extends {@link DomainException} to provide a specific error status, code and
message, which are then handled by the global exception handler to return a
structured error response to the client.

Author: Leo

---

FILE: AuthController.java

AuthController

REST controller in the presentation layer responsible for exposing
authentication-related HTTP endpoints.

It handles request routing, input validation, and response mapping,
delegating all business logic execution to dedicated authentication use case
services (application layer).

This class does not contain domain logic; its role is limited to
orchestrating request/response flow between the client and the
application layer.

Author: Leo

Endpoint to create a new account (signup).

This endpoint accepts a JSON body with the necessary account details (e.g.,
email, password). It deligates the account creation logic to the
SignupUseCase, which handles all the necessary validations, password hashing,
and persistence.

Endpoint: POST /auth/signup

@param body the request body containing the account details
@return the created account and tokens

Endpoint to log out from existing account.

This endpoint clears the authentication cookies by setting them with empty
values and immediate expiration

Endpoint: GET /auth/logout

@return a response with cleared cookies

Endpoint to log in an existing account.

This endpoint accepts a JSON body with the login credentials (e.g., email and
password). It deligates the authentication logic to the LoginUseCase

Endpoint: POST /auth/login

@param body the request body containing the login credentials
@return account and tokens

Endpoint to refresh authentication tokens.

This endpoint accepts a refresh token. It deligates the token refresh logic
to the refreshTokensUseCase, which handles all the necessary validations,
password
hashing, and persistence.

Endpoint: POST /auth/refresh

@param cookie refresh_token the request body containing the account details
@return account and tokens

---

## WALLET Module

FILE: CreateWalletDto.java

CreateWalletDto

Data Transfer Object used for wallet creation requests. It encapsulates the
necessary data required to create a new wallet, such as the wallet name and
owner ID.

Author: Leo

---

FILE: EditWalletDto.java

EditWalletDto

Data Transfer Object used for wallet editing requests. It encapsulates the
necessary data required to edit an existing wallet, such as the wallet name
and
owner ID.

Author: Leo

---

FILE: CreateWalletUseCase.java

CreateWalletUseCase

This service class implements the use case for creating a new wallet.
It contains only the business logic related to wallet creation, such as
interacting with the repository to persist the new wallet.

Author: Leo

---

FILE: DeleteWalletUseCase.java

DeleteWalletUseCase

This service class implements the use case for deleting a wallet.
It contains only the business logic related to wallet deletion, such as
validating input data and interacting with the repository to update the
wallet.

Author: Leo

---

FILE: EditWalletUseCase.java

EditWalletUseCase

This service class implements the use case for editing a wallet.
It contains only the business logic related to wallet modification, such as
validating input data and interacting with the repository to update the
wallet.

Author: Leo

---

FILE: GetAllWalletsUseCase.java

GetAllWalletsUseCase

This service class implements the use case for retrieving all wallets.
It contains only the business logic related to fetching wallet data, such as
interacting with the repository to retrieve the list of wallets.

Author: Leo

---

FILE: GetWalletUseCase.java

GetWalletUseCase

This service class implements the use case for retrieving a single wallet.
It contains only the business logic related to fetching wallet data, such as
interacting with the repository to retrieve a specific wallet.

Author: Leo

---

FILE: Wallet.java

Wallet entity mapped to the persistence layer.

Represents a wallet record stored in the database and defines
its persistence structure (table mapping, constraints, and identifiers).

This class is managed by JPA and is used to persist and retrieve
wallet data.

Author: Leo

---

FILE: WalletNotFoundException.java

WalletNotFoundException

This exception is thrown when the requested wallet is not found. It
extends {@link DomainException} to provide a specific error status, code and
message, which are then handled by the global exception handler to return a
structured error response to the client.

Author: Leo

---

FILE: ForbiddenException.java

ForbiddenException

This exception is thrown when the user is not allowed to perform the
requested action.

Author: Leo

---

FILE: WalletController.java

WalletController

REST controller in the presentation layer responsible for exposing
wallet-related HTTP endpoints.

It handles request routing, input validation, and response mapping,
delegating all business logic execution to dedicated wallet use case
services (application layer).

This class does not contain domain logic; its role is limited to
orchestrating request/response flow between the client and the
application layer.

Author: Leo

Retrieves all wallets available in the system.

Endpoint: GET /wallet/list

@return a list of all wallet entities

Retrieves a single wallet by its identifier.

Endpoint: GET /wallet/list/{id}

@param id the unique identifier of the wallet to retrieve
@return the matching wallet entity

Creates a new wallet for the specified account.

Endpoint: POST /wallet/create

@param body the request payload containing the account identifier
@return the newly created wallet entity

Updates an existing wallet with the provided changes.

Endpoint: PATCH /wallet/edit/{id}

@param id the unique identifier of the wallet to update
@param body the wallet update payload
@return the updated wallet entity

Deletes a wallet owned by the authenticated user.

Endpoint: POST /wallet/delete/{id}

@param user the authenticated user making the request
@param id the unique identifier of the wallet to delete

---

## CARD Module

FILE: CreateCardDto.java

CreateCardDto

Data Transfer Object used for card creation requests. It encapsulates the
necessary data required to create a new card, such as the card details and
user ID.

Author: Leo

---

FILE: EditCardDto.java

EditCardDto

Data Transfer Object used for card editing requests. It encapsulates the
necessary data required to edit an existing card, such as the card details.

Author: Leo

---

FILE: CreateCardUseCase.java

CreateCardUseCase

This service class implements the use case for creating a new card.
It contains only the business logic related to card creation, such as
validating input data and interacting with the repository to persist the new
card.

Author: Leo

---

FILE: DeleteCardUseCase.java

DeleteCardUseCase

This service class implements the use case for deleting an existing card.
It contains only the business logic related to card deletion, such as
validating the user's permission and interacting with the repository to
remove the card.

Author: Leo

---

FILE: EditCardUseCase.java

EditCardUseCase

This service class implements the use case for editing an existing card.
It contains only the business logic related to card editing, such as
validating input data and interacting with the repository to update the card.

Author: Leo

---

FILE: GetAllCardsUseCase.java

GetAllCardsUseCase

This service class implements the use case for retrieving all cards.
It contains only the business logic related to fetching cards, such as
interacting with the repository to fetch all persisted cards.

Author: Leo

---

FILE: GetCardUseCase.java

GetCardUseCase

This service class implements the use case for retrieving a single card.
It contains only the business logic related to fetching a card, such as
interacting with the repository to fetch the requested card.

Author: Leo

---

FILE: Card.java

Card entity mapped to the persistence layer.

Represents a card record stored in the database and defines
its persistence structure (table mapping, constraints, and identifiers).

This class is managed by JPA and is used to persist and retrieve
card data.

Author: Leo

---

FILE: CardNotFoundException.java

CardNotFoundException

This exception is thrown when the provided card is not found. It
extends {@link DomainException} to provide a specific error status, code and
message, which are then handled by the global exception handler to return a
structured error response to the client.

Author: Leo

---

FILE: ForbiddenException.java

ForbiddenException

This exception is thrown when the user is not allowed to perform the
requested action. It
extends {@link DomainException} to provide a specific error status, code and
message, which are then handled by the global exception handler to return a
structured error response to the client.

Author: Leo

---

FILE: CardController.java

CardController

REST controller in the presentation layer responsible for exposing
card-related HTTP endpoints.

It handles request routing, input validation, and response mapping,
delegating all business logic execution to dedicated card use case
services (application layer).

This class does not contain domain logic; its role is limited to
orchestrating request/response flow between the client and the
application layer.

Author: Leo

Retrieves all cards available in the system.

Endpoint: GET /card/list

@return a list of all card entities

Retrieves a single card by its identifier.

Endpoint: GET /card/list/{id}

@param id the unique identifier of the card to retrieve
@return the matching card entity

Creates a new card for the specified user.

Endpoint: POST /card/create

@param body the request payload containing card details and user information
@return the newly created card entity

Updates an existing card with the provided changes.

Endpoint: PATCH /card/edit/{id}

@param id the unique identifier of the card to update
@param body the card update payload
@return the updated card entity

Deletes a card owned by the authenticated user.

Endpoint: POST /card/delete/{id}

@param user the authenticated user making the request
@param id the unique identifier of the card to delete

---

## USER Module

FILE: CreateDto.java

CreateDto

Data Transfer Object used for user creation requests. It encapsulates the
necessary data required to create a new user, such as the user name, email,
and password.

Author: Leo

---

FILE: EditDto.java

EditDto

Data Transfer Object used for user editing requests. It encapsulates the
necessary data required to edit an existing user, such as the user name,
email,
and password.

Author: Leo

---

FILE: CreateUserUseCase.java

CreateUserUseCase

This service class implements the use case for creating a new user.
It contains only the business logic related to user creation, such as
validating input data and interacting with the repository to persist the new
user.

Author: Leo

---

FILE: GetUsersUseCase.java

GetUsersUseCase

This service class implements the use case for retrieving all existing
users. It contains only the business logic related to fetching users,
such as interacting with the repository to fetch the data.

Author: Leo

Handles fetching all users.

Calls the repository to fetch all users and returns the list of
users.

@return list of all users

---

FILE: GetUserUseCase.java

GetUsersUseCase

This service class implements the use case for retrieving all existing
users. It contains only the business logic related to fetching users,
such as interacting with the repository to fetch the data.

Author: Leo

Handles fetching all users.

Calls the repository to fetch all users and returns the list of
users.

@return found user

---

FILE: User.java

User entity mapped to the persistence layer.

Represents a user record stored in the database and defines
its persistence structure (table mapping, constraints, and identifiers).

This class is managed by JPA and is used to persist and retrieve
user data.

Author: Leo

---

FILE: EmailExistsException.java

EmailExistsException

This exception is thrown when the provided email already exists. It
extends {@link DomainException} to provide a specific error status, code and
message, which are then handled by the global exception handler to return a
structured error response to the client.

Author: Leo

---

FILE: UserNotFoundException.java

UserNotFoundException

This exception is thrown when the provided user is not found. It
extends {@link DomainException} to provide a specific error status, code and
message, which are then handled by the global exception handler to return a
structured error response to the client.

Author: Leo

---

FILE: UserController.java

UserController

REST controller in the presentation layer responsible for exposing
user-related HTTP endpoints.

It handles request routing, input validation, and response mapping,
delegating all business logic execution to dedicated user use case
services (application layer).

This class does not contain domain logic; its role is limited to
orchestrating request/response flow between the client and the
application layer.

Author: Leo

Returns a list of all users.

This endpoint retrieves all existing users by delegating to the
getUsersUseCase, which interacts with the repository to fetch the data.

Endpoint: GET /user/list

@return list of all accounts

Retrieves a single user by their identifier.

Endpoint: GET /user/list/{id}

@param id the unique identifier of the user to retrieve
@return the matching user entity

Creates a new user account.

Endpoint: POST /user/create

@param createDto the request payload containing user creation details
@return the newly created user entity

Deletes a user by their identifier.

Endpoint: DELETE /user/delete/{id}

@param id the unique identifier of the user to delete
@return a confirmation message after successful deletion

Updates an existing user with the provided changes.

Endpoint: PATCH /user/edit/{id}

@param editDto the user update payload
@param id the unique identifier of the user to update
@return an optional updated user entity

---

## STRIPE Module

FILE: ConnectResponse.java

ConnectResponse

Data Transfer Object used for Connect requests. It encapsulates the
necessary data required to create a new Connect account, such as the
account ID.

Author: Leo

---

FILE: CreateCardHolderDto.java

CreateCardHolderDto

Data Transfer Object used for card holder creation requests. It encapsulates
the
necessary data required to create a new card holder, such as the connected
account ID.

Author: Leo

---

FILE: CreateConnectDto.java

CreateConnectDto

Data Transfer Object used for Connect creation requests. It encapsulates the
necessary data required to create a new Connect account, such as the email.

Author: Leo

---

FILE: CreateLinkDto.java

CreateLinkDto

Data Transfer Object used for link creation requests. It encapsulates the
necessary data required to create a new link, such as the account ID.

Author: Leo

---

FILE: LinkResult.java

LinkResult

Data Transfer Object used for link creation requests. It encapsulates the
necessary data required to create a new link, such as the URL.

Author: Leo

---

FILE: CreateCardHolderUseCase.java

CreateCardHolderUseCase

This service class implements the use case for creating a new cardholder.
It contains only the business logic related to cardholder creation, such as
validating input data and interacting with the repository to persist the new
cardholder.

Author: Leo

---

FILE: CreateConnectUseCase.java

CreateConnectUseCase

This service class implements the use case for creating a new connect
account.
It contains only the business logic related to account creation

Author: Leo

---

FILE: CreateLinkUseCase.java

CreateLinkUseCase

This service class implements the use case for creating a new account link.
It contains only the business logic related to account link creation

Author: Leo

---

FILE: StripeController.java

StripeController

REST controller in the presentation layer responsible for exposing
stripe-related HTTP endpoints.

It handles request routing, input validation, and response mapping,
delegating all business logic execution to dedicated stripe use case
services (application layer).

This class does not contain domain logic; its role is limited to
orchestrating request/response flow between the client and the
application layer.

Author: Leo

Creates a Stripe connect account for the provided user email.

Endpoint: POST /create-connect-account

@param body the request payload containing the user email
@return a response with the created account identifier
@throws StripeException if the Stripe API call fails

Creates a Stripe account link for the specified connected account.

Endpoint: POST /create-account-link

@param body the request payload containing the account identifier
@return a response with the generated account link URL
@throws StripeException if the Stripe API call fails

Creates a Stripe card holder for the specified connected account.

Endpoint: POST /create-card-holder

@param body the request payload containing the connected account identifier
@return a response with the created card holder identifier
@throws StripeException if the Stripe API call fails

Retrieves the dashboard type information for a Stripe account.

Endpoint: GET /get-type

@return a response with the Stripe account dashboard details
@throws StripeException if the Stripe API call fails

---
