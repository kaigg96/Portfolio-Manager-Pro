package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {

    Company c1;

    @BeforeEach
    void runBefore () {
        c1 = new Company ("Apple", "AAPL",
                135.2, 2140, 101);
    }

    @Test

    void gettersAndSetters() {
        assertEquals("Apple", c1.getName());
        assertEquals("AAPL", c1.getTicker());
        assertEquals(135.2, c1.getSharePrice());
        assertEquals(2140, c1.getMarketCap());
        assertEquals(101, c1.getSharesHeld());
    }

    //@Test
    //void testSize() {
    //    assertEquals("Mega", c1.size());
    //    assertEquals("Mid", c2.size());
    //}


}



