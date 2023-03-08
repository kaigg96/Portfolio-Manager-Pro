package model;

import model.exceptions.CompanyNotFoundException;
import model.exceptions.InsufficientFundsException;
import model.exceptions.NegativeAmountException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static model.ListedCompanies.findInListedCompanies;

// Represents a user's portfolio of cash and a list of stocks held
public class Portfolio {

    private double cashBalance;
    private ArrayList<Company> stocks;

    //Effects: Constructs a portfolio with given cash and empty stocks
    public Portfolio(double cash) {
        cashBalance = cash;
        stocks = new ArrayList<>();
    }

    // Modifies: this
    // Effects: Adds the given amount of cash to the portfolio's cash balance
    public void addToBalance(double amount) throws NegativeAmountException {
        if (amount >= 0) {
            this.cashBalance += amount;
        } else {
            throw new NegativeAmountException("Cannot add a negative amount to balance.");
        }
    }

    // Modifies: this
    // Effects: subtract amount from cashBalance
    public void subFromBalance(double amount) throws NegativeAmountException, InsufficientFundsException {
        if (amount < 0) {
            throw new NegativeAmountException("Cannot subtract a negative amount to balance.");
        } else if (this.cashBalance < amount) {
            throw new InsufficientFundsException("Insufficient funds to subtract amount from balance");
        } else {
            this.cashBalance -= amount;
        }
    }

    // Modifies: this
    // Effects: subtract shareNumber * sharePrice from cashBalance
    //          add given number of shares of the stated company to the portfolio
    //            if shares are already held, add to sharesHeld
    //            else add holding to portfolio
    public void purchaseShares(String nameOrTicker, int shareNumber) throws InsufficientFundsException {
        ListedCompanies c = null;
        try {
            c = findInListedCompanies(nameOrTicker);
        } catch (CompanyNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (cashBalance < (shareNumber * c.getSharePrice())) {
            throw new InsufficientFundsException("Insufficient funds to purchase that many shares");
        } else {
            try {
                Integer i = findIndexOfCompanyInStocks(nameOrTicker);
                stocks.get(i).setSharesHeld(stocks.get(i).getSharesHeld() + shareNumber);
            } catch (CompanyNotFoundException e) {
                cashBalance -= shareNumber * c.getSharePrice();
                stocks.add(new Company(
                        c.getName(),
                        c.getTicker(),
                        c.getSharePrice(),
                        c.getMktcap(),
                        shareNumber));
            }
        }
    }

    // EFFECTS: return the index of the Company in stocks matching the given name or ticker
    //          if none match, returns null
    public Integer findIndexOfCompanyInStocks(String nameOrTicker) throws CompanyNotFoundException {
        for (int i = 0; i < stocks.size(); i++) {
            Company company = stocks.get(i);
            if (company.getName().equals(nameOrTicker) || company.getTicker().equals(nameOrTicker)) {
                return i;
            }
        }
        throw new CompanyNotFoundException("Company not found in stocks");
    }

    // Requires: given nameOrTicker corresponds to a company that is currently in the portfolio
    // Modifies: this
    // Effects: remove given number of shares of the stated company from the portfolio, add cash from sale to
    //          cash balance
    public void sellShares(String nameOrTicker, int shareNumber) {
        for (int i = 0; i < stocks.size(); i++) {
            Company c = stocks.get(i);
            if ((c.getName().equals(nameOrTicker)) || (c.getTicker().equals(nameOrTicker))) {
                try {
                    addToBalance(c.getSharePrice() * shareNumber);
                } catch (NegativeAmountException e) {
                    throw new RuntimeException(e);
                }
                if (c.getSharesHeld() > shareNumber) {
                    c.setSharesHeld(c.getSharesHeld() - shareNumber);
                } else {
                    stocks.remove(i);
                }
            }
        }
    }

    // EFFECTS: return the Company in stocks matching the given name or ticker
    //          if none match, returns null
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

    // MODIFIES: this
    // EFFECTS: constructs a company with the given arguments and adds it to stocks
    public void addCompanyToStocks(String name, String ticker, double sharePrice,
                                   double marketCap, int sharesHeld) {
        stocks.add(new Company(name, ticker, sharePrice, marketCap, sharesHeld));
    }

    // EFFECTS: Create a JSON object for the portfolio
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Cash Balance", cashBalance);
        jsonObject.put("Stocks", stocksToJson());
        return jsonObject;
    }

    // EFFECTS: convert stocks to JSON data
    private JSONArray stocksToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Company company : stocks) {
            jsonArray.put(company.toJson());
        }
        return jsonArray;
    }

}






