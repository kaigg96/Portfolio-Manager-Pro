package model;

import org.json.JSONObject;

// Represents a company with a name, ticker, share price, and market cap
public class Company {
    private final String name;
    private final String ticker;
    private final double sharePrice;
    private final double marketCapitalization;
    private int sharesHeld;

    // EFFECTS: construct a company with a name, ticker, shares held, share price, and market cap
    public Company(String name, String ticker, double sharePrice, double marketCap, int sharesHeld) {
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

    // EFFECTS: Create a JSON object for the company
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", name);
        jsonObject.put("Ticker", ticker);
        jsonObject.put("Share Price", sharePrice);
        jsonObject.put("Market Cap", marketCapitalization);
        jsonObject.put("Shares Held", sharesHeld);
        return jsonObject;
    }
}

