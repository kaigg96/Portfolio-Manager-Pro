package model;

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

    // Requires: amount >= 0
    // Modifies: this
    // Effects: Adds the given amount of cash to the portfolio's cash balance
    public void addToBalance(double amount) {
        this.cashBalance += amount;
    }

    // Requires: cashBalance >= amount >= 0
    // Modifies: this
    // Effects: subtract amount from cashBalance
    public void subFromBalance(double amount) {
        this.cashBalance -= amount;
    }

    // Requires: cashBalance >= shareNumber * c.getSharePrice()
    //           c is a member of ListedCompanies
    // Modifies: this
    // Effects: subtract shareNumber * sharePrice from cashBalance
    //          add given number of shares of the stated company to the portfolio
    //            if shares are already held, add to sharesHeld
    //            else add holding to portfolio
    public void purchaseShares(String nameOrTicker, int shareNumber) {
        ListedCompanies c = findInListedCompanies(nameOrTicker);
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

    // EFFECTS: return the index of the Company in stocks matching the given name or ticker
    //          if none match, returns null
    public Integer findIndexOfCompanyInStocks(String nameOrTicker) {
        for (int i = 0; i < stocks.size(); i++) {
            Company company = stocks.get(i);
            if (company.getName().equals(nameOrTicker) || company.getTicker().equals(nameOrTicker)) {
                return i;
            }
        }
        return null;
    }

    // Requires: given nameOrTicker corresponds to a company that is currently in the portfolio
    // Modifies: this
    // Effects: remove given number of shares of the stated company from the portfolio, add cash from sale to
    //          cash balance
    public void sellShares(String nameOrTicker, int shareNumber) {
        for (int i = 0; i < stocks.size(); i++) {
            Company c = stocks.get(i);
            if ((c.getName().equals(nameOrTicker)) || (c.getTicker().equals(nameOrTicker))) {
                addToBalance(c.getSharePrice() * shareNumber);
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

    // EFFECTS: Create a JSON object for the portfolio
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Cash Balance", cashBalance);
        jsonObject.put("Stocks", stocksToJson());
        return jsonObject;
    }

    private JSONArray stocksToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Company company : stocks) {
            jsonArray.put(company.toJson());
        }
        return jsonArray;
    }

}






