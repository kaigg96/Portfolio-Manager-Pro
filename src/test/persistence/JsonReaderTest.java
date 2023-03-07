package persistence;

import model.Portfolio;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonReaderTest {

    @Test
    void testReaderNoCashEmptyStocks() {
        JsonReader reader = new JsonReader("./data/testReaderNoCashEmptyStocks.json");
        try {
            Portfolio p = reader.read();
            assertEquals(0, p.getCashBalance());
            assertEquals(0, p.getStocks().size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testReaderCashAndStocks() {
        JsonReader reader = new JsonReader("./data/testReaderCashAndStocks.json");
        try {
            Portfolio p = reader.read();
            assertEquals(1000, p.getCashBalance());
            assertEquals(2, p.getStocks().size());
            assertEquals("Apple", p.getStocks().get(0).getName());
            assertEquals(2, p.getStocks().get(1).getSharesHeld());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
