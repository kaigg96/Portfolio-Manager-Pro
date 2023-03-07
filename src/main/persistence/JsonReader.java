package persistence;

import model.Portfolio;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads a portfolio from json data stored in a file
// This code is adapted from that found in the JsonSterilizationDemo file
public class JsonReader {

    private String source;

    // EFFECTS:
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads a portfolio from file and returns it
    public Portfolio read() throws IOException {
        String jsonData = readFile(source);
        JSONObject object = new JSONObject(jsonData);
        return parsePortfolio(object);
    }

    // REQUIRES: given source contains code file
    // EFFECTS: reads a source file and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses portfolio from JSONObject and returns it
    private Portfolio parsePortfolio(JSONObject object) {
        double cash = object.getDouble("Cash Balance");
        Portfolio p = new Portfolio(cash);
        addCompanies(p, object);
        return p;
    }

    // MODIFIES: p
    // EFFECTS: parses all companies from JSON object and adds them to the portfolio
    private void addCompanies(Portfolio p, JSONObject object) {
        JSONArray stocks = object.getJSONArray("Stocks");
        for (Object company : stocks) {
            JSONObject nextCompany = (JSONObject) company;
            addCompany(p, nextCompany);
        }
    }

    // MODIFIES: p
    // EFFECTS: parse company name and shares from JSON object and add it to the given portfolio
    private void addCompany(Portfolio p, JSONObject object) {
        String name = object.getString("Name");
        String ticker = object.getString("Ticker");
        double sharePrice = object.getDouble("Share Price");
        double marketCap = object.getDouble("Market Cap");
        int sharesHeld = object.getInt("Shares Held");
        p.addCompanyToStocks(name, ticker, sharePrice, marketCap, sharesHeld);
    }



}
