# BankingAPI

*(Use case diagram is at the end of the README)*

This API represents the one used in a banking system. It features its users, its functions and all the logic behind them.

## Components

The API is composed by its users, its accounts and its functions.

### Users

There are three kind of users: Admins, Account Holders and Third Parties.

#### Admins

Admins represent the people behind the curtains making it all work. They could be bank workers, system administrators or automated processes.

As every user, it descends from the User class, making its attributes the next:

1. An ID.
2. A username.
3. A password.
4. A name.

Admins don't have any special features but are the only ones who can access some routes to do some special functions.

There's only one admin in the basic configuration of the API, and they can only be created by modifying the code of the API.

#### Account Holders

Account Holders represent the clients of the banking institution. They could be a singular person, or they could represent an entity that needs a banking account for their affairs.

They inherit from the User class, making their attributes the next:

1. An ID.
2. A username.
3. A password.
4. A first name.
5. A last name.
6. A birthdate.
7. A primary address.
8. An optional mailing address.
9. A phone number.
10. An e-Mail.
11. A list of accounts where they are the primary owners.
12. A list of accounts where they are the secondary owners.

As for their attributes, they could have a mailing address that is different from their principal address.
Also, they have two lists, containing the accounts where they are owners, yet being the primary owners or secondary owners.

Using the correct route, everyone could create an Account Holder, introducing the right parameters.

#### Third Parties

Third Parties represent external entities that interact with the banking institution. They could be other banking institutions, 
external processes that the API doesn't take care (like cash ingresses) or petitions make by external entities like automated payments and so.

They have its class with the next parameters:

1. An ID.
2. A name.
3. A hashed key.

Their hashed key it's encrypted by a codifier to add security to the routes that affect the Third Parties behavior.

Third parties can only be created by an Admin using the correct route.

### Accounts

All accounts inherit from an abstract class named Account, because every account have parameters in common and to make an easy way to work
at the same time with every type of Account, grouping them un lists or being used as method arguments without distinction.

#### Checking accounts

Checking accounts are the default type of account used it for everyday transactions.

They have the next parameters:

1. An ID, starting with *"CHE-"*.
2. A balance, represented by a Money object.
3. A secret key.
4. A primary owner.
5. A secondary owner.
6. A creation date.
7. A status.
8. A minimum balance.
9. A penalty fee.
10. A monthly maintenance fee.
11. A maintenance date.

Checking accounts have a minimum balance established by its parameter. If in any point the balance is lesser than the minimum balance, 
a penalty fee is incurred. Also, Checking accounts have a maintenance fee to cover the expenses that the account suppose to the banking entity. 
This fee is deduced from the balance every month after its creation.

#### Students Checking accounts

Students Checking accounts are similar to the normal Checking accounts but are suited for any user whose age is lesser than 24.

Considering the difficulties that these type of clients face for their income, the Students Checking accounts eliminates all the 
fees that the normal Checking accounts have, making the parameters of these accounts the next:

1. An ID, starting with *"SCH-"*.
2. A balance, represented by a Money object.
3. A secret key.
4. A primary owner.
5. A secondary owner.
6. A creation date.
7. A status.

#### Savings accounts

This kind of account have an interest rate that make the deposited money grow annually.
The parameters of these accounts are the next:

1. An ID, starting with *"SAV-"*.
2. A balance, represented by a Money object.
3. A secret key.
4. A primary owner.
5. A secondary owner.
6. A creation date.
7. A status.
8. A minimum balance.
9. A penalty fee.
10. An interest rate.
11. An interest date.

As the Checking account, the minimum balance and the penalty fee works the same. 

In addition, Savings accounts have an interest rate *(default: 0,0025 and limited to a maximum of 0,5)* that is added to the deposited funds annually.
This interest rate can be changed in the moment of the creation of the account only.

#### Credit Card accounts

These accounts represent the mechanism behind the functioning of a Credit Card. 
Their parameters are the next:

1. An ID, starting with *"CRE-"*
2. A balance, represented by a Money object
3. A secret key
4. A primary owner
5. A secondary owner
6. A creation date
7. A status
8. An interest rate
9. An interest date
10. A credit limit

Their interest system is equal to the one of the Savings account but is added monthly to the credit limit instead.

### Routes and Methods

#### *"/new-user"*

This route allow anyone to create a new Account Holder, introducing the data need it to do so.
If the next data is not correct, the API could throw the next exceptions:

1. 403 FORBIDDEN. If the age of the new user is lesser than 18 years old.

#### *"/create-account"*

ADMIN only access. This route allow to create a new account binding it to an Account Holder entered as a parameter for the request. 
The API could throw the next exceptions:

1. 404 NOT FOUND. If the ID of the Account Holder introduced is not found on the database.
2. 400 BAD REQUEST. If the kind of account you want to create is not entered correctly *(checking, savings, creditcard all in lowercase)*.

#### *"/myaccount"*

USER only access. This route allow the Account Holder to check the balance in all the accounts he is owner of. Also, it will apply the interest
and maintenance fees need it since the last time they should be applied *(Because we work asynchronously, it's the only way it could be made)*.
This method uses the authentication data to show the correct accounts to its owners.

It could throw the next exceptions:

1. 404 NOT FOUND. If the username introduced doesn't have any accounts bound to it.

#### *"/all-accounts"*

ADMIN only access. This route will show a list of every account in the database and its owner.

#### *"/check-balance"*

ADMIN only access. This route will show the balance of all the accounts of the Account Holder username introduced.
This method could throw the next exception:

1. 404 NOT FOUND. If there aren't any Account Holder with that username in the database.

#### *"/modify-balance"*

ADMIN only access. This route allows an Admin to manually change the balance of the account introduced as a parameter.
It could throw the next exception:

1. 404 NOT FOUND. If there aren't any account with that ID in the database.

#### *"/delete/{accountId}"*

ADMIN only access. This route allows an Admin to completely delete the account associated with the account ID entered as a parameter for the request.
It could throw the next exception:

1. 404 NOT FOUND. If there aren't any account with that ID in the database.

#### *"/myaccount/transfer"*

USER only access. This route allow the Account Holder to send money form one of his accounts to another account in the same 
banking entity. It could throw the next exceptions:

1. 404 NOT FOUND. If the username of the Account Holder can not be found on the database.
2. 400 BAD REQUEST. If the origin account ID is not found on the database.
3. 403 FORBIDDEN. If the origin account is not bound to the Account Holder.
4. 404 NOT FOUND. If the destiny account ID is not found on the database.
5. 400 BAD REQUEST. If the destiny account Account Holder first name is wrong.
6. 400 BAD REQUEST. If the destiny account Account Holder last name is wrong.
7. 412 PRECONDITION FAILED. If the origin account doesn't have enough balance to fulfill the operation.

Also, if the balance of the account falls below the minimum balance, the penalty fee will be applied to the account.

#### */myaccount/send/{hashedKey}*

USER only access. This route allow the account holder to send money to an external entity by entering the entity hashed key as a path variable.
The method could throw the next exceptions:

1. 400 BAD REQUEST. If the hashed key introduced is not found on the database.
2. 404 NOT FOUND. If the origin account ID introduced is not found on the database.
3. 400 BAD REQUEST. If the origin account doesn't belong to the user.
4. 404 NOT FOUND. If the secret key is wrong.

#### */new-third-party*

Admin only access. This route allow an Admin to create a new Third Party in the database.

#### */send/{hashedKey}*

This route allow a Third Party to send money to an account in the banking entity. It could throw the next exceptions:

1. 400 BAD REQUEST. If the hashed key is not found on the database.
2. 404 NOT FOUND. If the destiny account is not found on the database.
3. 404 NOT FOUND. If the secret key is wrong.

### Use Case Diagram

This is the Use Case Diagram of the Banking API:

![Blank diagram](https://user-images.githubusercontent.com/106558181/188310290-e786e991-6f5c-4f4a-9368-19da646ad54c.jpeg)

Made by Guillermo P. Corbella.