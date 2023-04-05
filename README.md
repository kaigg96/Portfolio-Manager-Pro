# My Personal Project - Creating a stock portfolio manager
## Phase 0:
### Project overview:

The purpose of this project is to allow users to 
create their own stock portfolio containing shares 
of common stock in publicly traded companies. The 
user's portfolio will be a class containing a cash balance
as well as any shares owned by the user. Companies 
will be defined in a separate class.

This application is designed for **all** users, because
**investing should be for everyone**. This
project aims to simplify the user experience such
that even someone with no investing or stock market
experience can learn to build their own portfolio. 

This project is of interest to me because it bridges
my two primary areas of interest: technology and 
finance. I hope this can be the first step in a 
larger project that I can add to my resume, and 
demonstrate to future employers in the quantitative
finance field.

### User stories:

- As a user, I want to be able to deposit and withdraw cash 
from my cash balance.

- As a user, I want to be able to add shares 
  of a listed company into my portfolio by purchasing them
with my cash.
  - Company and portfolio are different classes.

- As a user, I want to be able to sell shares, removing
  them from my portfolio and adding the proceeds to my 
  cash balance.

- As a user, I want to be able to browse listed companies 
to choose which I want to buy.
  
- As a user, I want to be able to view my portfolio,
holdings and cash balance. 

- As a user, I want to be able to choose if I want to 
save my portfolio.

- As a user, I want to be able to load a saved portfolio.


**Instructions for Grader**

- You can generate the first required action related to adding Xs to a Y by...
1. Run GraphicalUIAppMain
2. Select New Portfolio
3. Select Deposit
4. Input 1000 and press deposit
5. Select Purchase Stocks
6. Input AAPL as the company name and 1 as the shares to purchase
7. Press the Purchase button

- You can generate the second required action related to adding Xs to a Y by...
1. Run GraphicalUIAppMain
2. Select New Portfolio
3. Select View Portfolio (although you probably want to make a deposit and/or 
purchase some shares first!)

- You can locate my visual component by...
1. Run GraphicalUIAppMain
2. Select New Portfolio
3. Select Browse Listed Companies (logos)

- You can save the state of my application by...
1. Run GraphicalUIAppMain
2. Select New Portfolio
3. Select Save Portfolio

- You can reload the state of my application by...
1. Run GraphicalUIAppMain
2. Select Load Portfolio

**Phase 4: Task 2**

Event Log: 
Wed Apr 05 12:11:44 PDT 2023
Deposited 1000.0
Wed Apr 05 12:11:50 PDT 2023
Purchased 1 shares of Microsoft
Wed Apr 05 12:11:54 PDT 2023
Withdrew 100.0
Wed Apr 05 12:11:59 PDT 2023
Deposited 252.75
Wed Apr 05 12:11:59 PDT 2023
Sold 1 shares of Microsoft