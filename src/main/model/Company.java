package model;

import java.util.ArrayList;
import java.util.List;

// Represents a company with a name, ticker, share price, and market cap
public class Company {
    String name;
    String ticker;
    int sharesHeld;
    //    String sector;                   <--- may add later
    double sharePrice;
    double marketCapitalization;

    // EFFECTS: construct a company with a name, ticker, 0 shares held, share price, and market cap
    Company(String name, String ticker, double sharePrice, double marketCap) {
        this.name = name;
        this.ticker = ticker;
        this.sharesHeld = 0;
        this.sharePrice = sharePrice;
        this.marketCapitalization = marketCap;
    }

    //ArrayList<Company> listedCompanies = new ArrayList<>();
    //listedCompanies.add(new Company("Apple", "AAPL", 0, 135, 2140));
    //listedCompanies.add(new Company("Microsoft", "MSFT", 0, 145, 2150));

    // EFFECTS: produce an approximation of the number of shares outstanding of the given company
    double sharesOutstanding() {
        return (marketCapitalization * 1_000_000_000) / sharePrice;
    }

    // EFFECTS: classify the given company
    String size() {

        if (marketCapitalization >= 100) {
            return "Mega";
        } else if (marketCapitalization >= 10) {
            return "Large";
        } else if (marketCapitalization >= 1) {
            return "Mid";
        } else {
            return "Small";
        }
    }

    public String getName() {
        return this.name;
    }
    public String getTicker() {
        return this.ticker;
    }

    public int getSharesHeld() {
        return this.sharesHeld;
    }

    public double getSharePrice() {
        return this.sharePrice;
    }

    public double getMarketCapitalization() {
        return this.marketCapitalization;
    }


}

