﻿# BankingAPI

*(Use case diagram is at the end of the README)*

This API represents the one used in a banking system. It features its users, its functions and all the logic behind them.

## Components

The API is composed by its users, its accounts and its functions.

### Users

There are three kind of users: Admins, Account Holders and Third Parties.

#### Admins

Admins represent the people behind the curtains making it all work. They could be bank workers, system administrators or automated processes.

As every user, it descends from the User class, making its attributes the next:

1. An ID
2. A username
3. A password
4. A name

Admins don't have any special features but are the only ones who can access some routes to do some special functions.

There's only one admin in the basic configuration of the API, and they can only be created by modifying the code of the API.

#### Account Holders

Account Holders represent the clients of the banking institution. They could be a singular person, or they could represent an entity that needs a banking account for their affairs.

They inherit from the User class, making their attributes the next:

1. An ID
2. A username
3. A password
4. A first name
5. A last name
6. A birthdate
7. A primary address
8. An optional mailing address
9. A phone number
10. An e-Mail
11. A list of accounts where they are the primary owners
12. A list of accounts where they are the secondary owners

As for their attributes, they could have a mailing address that is different from their principal address.
Also, they have two lists, containing the accounts where they are owners, yet being the primary owners or secondary owners.

Using the correct route, everyone could create an Account Holder, introducing the right parameters.

#### Third Parties

Third Parties represent external entities that interact with the banking institution. They could be other banking institutions, 
external processes that the API doesn't take care (like cash ingresses) or petitions make by external entities like automated payments and so.

They have its class with the next parameters:

1. An ID
2. A name
3. A hashed key

Their hashed key it's encrypted by a codifier to add security to the routes that affect the Third Parties behavior.

Third parties can only be created by an Admin using the correct route.

### Accounts

All accounts inherit from an abstract class named Account, because every account have parameters in common and to make an easy way to work
at the same time with every type of Account, grouping them un lists or being used as method arguments without distinction.

#### Checking accounts

Checking accounts are the default type of account used it for everyday transactions.

They have the next parameters:

1. An ID, starting with *"CHE-"*
2. A balance, represented by a Money object
3. A secret key
4. A primary owner
5. A secondary owner
6. A creation date
7. A status
8. A minimum balance
9. A penalty fee
10. A monthly maintenance fee
11. A maintenance date

Checking accounts have a minimum balance established by its parameter. If in any point the balance is lesser than the minimum balance, 
a penalty fee is incurred. Also, Checking accounts have a maintenance fee to cover the expenses that the account suppose to the banking entity. 
This fee is deduced from the balance every month after its creation.

#### Students Checking accounts

Students Checking accounts are similar to the normal Checking accounts but are suited for any user whose age is lesser than 24.

Considering the difficulties that these type of clients face for their income, the Students Checking accounts eliminates all the 
fees that the normal Checking accounts have, making the parameters of these accounts the next:

1. An ID, starting with *"SCH-"*
2. A balance, represented by a Money object
3. A secret key
4. A primary owner
5. A secondary owner
6. A creation date
7. A status

#### Savings accounts

This kind of account have an interest rate that make the deposited money grow annually.
The parameters of these accounts are the next:

1. An ID, starting with *"SAV-"*
2. A balance, represented by a Money object
3. A secret key
4. A primary owner
5. A secondary owner
6. A creation date
7. A status
8. A minimum balance
9. A penalty fee
10. An interest rate
11. An interest date

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

### Use Case Diagram

![Blank diagram](https://user-images.githubusercontent.com/106558181/188310290-e786e991-6f5c-4f4a-9368-19da646ad54c.jpeg)
