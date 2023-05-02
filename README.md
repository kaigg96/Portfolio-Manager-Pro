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



This code could definitely be helped by refactoring. I think I could
make use of inheritance to increase the specificity of each class 
via the single responsibility principle. Currently, the code has very
low coupling (there are no subtyping relationships) but as a result
lacks cohesion. This is especially pronounced in the UI package 
(the ConsoleUIApp and GraphicalUIApp classes), but could also 
be applied to the Portfolio class by splitting the handling of cash
and shares into separate classes. 

Specifically for the GraphicalUIApp, I would extract each menu
(ie. opening menu, main menu...) into their own classes, and each
could extend the GraphicalUIApp class for shared functionality
(ie. shared fields, and some methods like the createTitle() method).
