package model;

// Represents a company with a name, ticker, share price, and market cap
public class Company {
    private final String name;
    private final String ticker;
    private final double sharePrice;
    private final double marketCapitalization;
    private int sharesHeld;

    // EFFECTS: construct a company with a name, ticker, shares held, share price, and market cap
    Company(String name, String ticker, double sharePrice, double marketCap, int sharesHeld) {
        this.name = name;
        this.ticker = ticker;
        this.sharePrice = sharePrice;
        this.marketCapitalization = marketCap;
        this.sharesHeld = sharesHeld;
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

    public double getMarketCap() {
        return this.marketCapitalization;
    }

    public void setSharesHeld(int shares) {
        this.sharesHeld = shares;
    }


    //TODO: need any methods for company class?
}

