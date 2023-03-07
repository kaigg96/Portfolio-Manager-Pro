package persistence;

import model.Portfolio;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonWriterTest {

    @Test
    void testWriterNoCashEmptyStocks() {
        Portfolio p = new Portfolio(0);
        JsonWriter writer = new JsonWriter("./data/testNoCashEmptyStocks.json");
        try {
            writer.openWriter();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        writer.write(p);
        writer.closeWriter();

        JsonReader reader = new JsonReader("./data/testNoCashEmptyStocks.json");
        try {
            p = reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(0, p.getCashBalance());
        assertEquals(0, p.getStocks().size());
    }

    @Test
    void testWriterWithCashAndStocks() {
        Portfolio p = new Portfolio(1000);
        p.addCompanyToStocks("Apple", "AAPL", 150, 2100, 1);
        p.addCompanyToStocks("Microsoft", "MSFT", 252.32, 1937, 2);
        JsonWriter writer = new JsonWriter("./data/testCashAndStocks.json");
        try {
            writer.openWriter();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        writer.write(p);
        writer.closeWriter();

        JsonReader reader = new JsonReader("./data/testCashAndStocks.json");
        try {
            p = reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(1000, p.getCashBalance());
        assertEquals(2, p.getStocks().size());
        assertEquals("Apple", p.getStocks().get(0).getName());
        assertEquals(2, p.getStocks().get(1).getSharesHeld());
    }
}
