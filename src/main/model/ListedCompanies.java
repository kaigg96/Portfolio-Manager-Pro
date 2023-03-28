package model;

import model.exceptions.CompanyNotFoundException;

// An enumeration of all the listed companies, with their name, ticker, share price and market cap
public enum ListedCompanies {
    APPLE("Apple", "AAPL", 150, 2100),
    ARITZIA("Aritzia", "ATZ", 45.5, 5.18),
    MICROSOFT("Microsoft", "MSFT", 252.75, 1927),
    ALPHABET("Alphabet", "GOOG", 101.5, 1305),
    AMAZON("Amazon", "AMZN", 105.15, 1072),
    BERKSHIRE("Berkshire Hathaway", "BRK", 310.57, 684.8),
        TESLA("Tesla", "TSLA", 181.41, 572.84),
    NVIDIA("NVIDIA", "NVDA", 209.43, 521.89),
    TSM("Taiwan Semiconductor", "TSM", 94.74, 491.32),
    VISA("Visa", "V", 230.9, 485.3),
    TENCENT("Tencent", "TCEHY", 49.3, 475.53),
    EXXON("Exxon Mobil", "XOM", 114.74, 472.53),
    UNH("United Health", "UNH", 497.00, 464.37),
    JNJ("Johnson & Johnson", "JNJ", 164.92, 431.18),
    JPM("JP Morgan Chase", "JPM", 139.59, 409.59),
    META("Meta Platforms", "META", 153.12, 401.51),
    WALMART("Walmart", "WMT", 144.67, 390.14),
    MASTERCARD("Mastercard", "MA", 374.08, 357.62),
    PG("Proctor & Gamble", "PG", 143.19, 337.80),
    HD("Home Depot", "HD", 328.09, 334.38);

    private final String name;
    private final String ticker;
    private final double sharePrice;
    private final double mktcap;

    // REQUIRES: no two ListedCompanies have the same name or ticker
    // EFFECTS: construct a ListedCompanies with a name, ticker, share price, and market cap
    ListedCompanies(String name, String ticker, double sharePrice, double mktcap) {
        this.name = name;
        this.ticker = ticker;
        this.sharePrice = sharePrice;
        this.mktcap = mktcap;
    }

    public String getName() {
        return this.name;
    }

    public String getTicker() {
        return this.ticker;
    }

    public double getSharePrice() {
        return this.sharePrice;
    }

    public double getMktcap() {
        return this.mktcap;
    }

    // EFFECTS: return the ListedCompanies object matching the given name or ticker
    //          throws CompanyNotFoundException if nameOrTicker is not in ListedCompanies
    public static ListedCompanies findInListedCompanies(String nameOrTicker) throws CompanyNotFoundException {
        for (ListedCompanies c : ListedCompanies.values()) {
            if (c.getName().equals(nameOrTicker) || c.getTicker().equals(nameOrTicker)) {
                return c;
            }
        }
        throw new CompanyNotFoundException("Company not found");
    }
}
