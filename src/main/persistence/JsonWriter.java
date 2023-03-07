package persistence;

import model.Portfolio;
import org.json.JSONObject;

import java.io.*;

// Represents a writer that converts a portfolio into json data stored in a file
// This code is adapted from that found in the JsonSterilizationDemo file
public class JsonWriter {
    private String destination;
    private PrintWriter writer;

    // EFFECTS: construct a writer to write to a destination file
    public JsonWriter(String file) {
        this.destination = file;
    }

    // MODIFIES: this
    // EFFECTS: opens the writer
    public void openWriter() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes a JSON representation of the given portfolio to a file
    public void write(Portfolio portfolio) {
        JSONObject json = portfolio.toJson();
        saveToFile(json.toString());
    }

    // MODIFIES: this
    // EFFECTS: write the given string to file
    private void saveToFile(String string) {
        writer.print(string);
    }

    // MODIFIES: this
    // EFFECTS: close the writer
    public void closeWriter() {
        writer.close();
    }
}
