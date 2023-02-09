package model;

import java.util.ArrayList;

// Represents a user's portfolio of cash and a list of stocks held
public class Portfolio {

    private double cashBalance;
    private ArrayList<Company> stocks;

    //Effects: Constructs a portfolio with 0 cash
    public Portfolio(double cash) {
        cashBalance = cash;
        stocks = new ArrayList<>();
    }


    // Requires: amount > 0
    // Modifies: this
    // Effects: Adds the given amount of cash to the portfolio's cash balance
    public void addToBalance(double amount) {
        this.cashBalance += amount;
    }

    //TODO: change to have a required section to get rid of any print sections

    // Requires: cashBalance >= amount > 0
    // Modifies: this
    // Effects: If cashBalance > given amount, subtract amount from cashBalance.
    //          else tell the customer this withdrawal cannot be completed
    public void subFromBalance(double amount) {
        if (this.cashBalance >= amount) {
            this.cashBalance -= amount;
            System.out.println("Withdrawal successful! Your remaining balance is: " + this.cashBalance);
        } else {
            System.out.println("Insufficient funds! You cannot withdraw " + amount);
            System.out.println("Your current balance is: " + this.cashBalance);
        }
    }

    // requires: given nameOrTicker belongs to a member of ListedCompanies
    //           cashBalance >= shareNumber * sharePrice
    // modifies: this
    // effects: subtract shareNumber * sharePrice from cashBalance
    //          add given number of shares of the stated company to the portfolio
    //            if shares are already held, add to sharesHeld
    //            else add holding to portfolio
    public void purchaseShares(String nameOrTicker, int shareNumber, ListedCompanies c) {
        cashBalance -= shareNumber * c.getSharePrice();
        if (findIndexOfCompanyInStocks(nameOrTicker) != null) {
            Integer i = findIndexOfCompanyInStocks(nameOrTicker);
            stocks.get(i).setSharesHeld(stocks.get(i).getSharesHeld() + shareNumber);
        } else {
            stocks.add(new Company(
                    c.getName(),
                    c.getTicker(),
                    c.getSharePrice(),
                    c.getMktcap(),
                    shareNumber));
        }
    }

    public Integer findIndexOfCompanyInStocks(String nameOrTicker) {
        for (int i = 0; i < stocks.size(); i++) {
            Company company = stocks.get(i);
            if (company.getName().equals(nameOrTicker) || company.getTicker().equals(nameOrTicker)) {
                return i;
            }
        }
        return null;
    }

    //TODO: change to have a required section to get rid of any print sections

    // requires: sharesHeld in the given company >= given shareNumber to sell
    //           company is currently held in portfolio
    // modifies: this
    // effects: if found, remove given number of shares of the stated company from the portfolio, add cash from sale to
    //          cash balance
    //          else give error
    public void sellShares(String nameOrTicker, int shareNumber) {
        for (int i = 0; i < stocks.size(); i++) {
            Company c = stocks.get(i);
            if ((c.getName().equals(nameOrTicker)) || (c.getTicker().equals(nameOrTicker))) {
                addToBalance(c.getSharePrice() * shareNumber);
                if (c.getSharesHeld() > shareNumber) {
                    c.setSharesHeld(c.getSharesHeld() - shareNumber);
                    break;
                } else {
                    stocks.remove(i);
                    break;
                }
            }
        }
    }
        // search for given String in list of companies in the portfolio (name and ticker)
        //  if found, check if shares held > shareNumber trying to sell
        //      if shares held > shareNumber, remove shareNumber from shares held and add to cash balance
        //      if not, produce error
        //  if company not found, produce error

    //public Company findCompanyInStocks(String nameOrTicker) {

    public Company findCompanyInStocks(String nameOrTicker) {
        for (Company company : stocks) {
            if (company.getName().equals(nameOrTicker) || company.getTicker().equals(nameOrTicker)) {
                return company;
            }
        }
        return null;
    }

    public double getCashBalance() {
        return this.cashBalance;
    }

    public ArrayList<Company> getStocks() {
        return this.stocks;
    }
}






