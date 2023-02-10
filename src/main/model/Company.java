package model;

// Represents a company with a name, ticker, share price, and market cap
public class Company {
    private String name;
    private String ticker;
    //    String sector;                   <--- may add later
    private double sharePrice;
    private double marketCapitalization;
    private int sharesHeld;

    // EFFECTS: construct a company with a name, ticker, 0 shares held, share price, and market cap
    Company(String name, String ticker, double sharePrice, double marketCap, int sharesHeld) {
        this.name = name;
        this.ticker = ticker;
        this.sharePrice = sharePrice;
        this.marketCapitalization = marketCap;
        this.sharesHeld = sharesHeld;
    }


    //TODO: need any methods for company class?

    // EFFECTS: produce an approximation of the number of shares outstanding of the given company
    //double sharesOutstanding() {
    //    return (marketCapitalization * 1_000_000_000) / sharePrice;
    //}

    // EFFECTS: classify the given company
    //String size() {

    //    if (marketCapitalization >= 100) {
    //        return "Mega";
    //    } else if (marketCapitalization >= 10) {
    //        return "Large";
    //    } else if (marketCapitalization >= 1) {
    //        return "Mid";
    //    } else {
    //        return "Small";
    //    }
    //}

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

    public double getMarketCap() {
        return this.marketCapitalization;
    }

    public void setSharesHeld(int shares) {
        this.sharesHeld = shares;
    }

}

