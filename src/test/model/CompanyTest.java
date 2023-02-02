package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CompanyTest {

    Company c1;
    Company c2;
    Company c3;
    Company c4;
    Company c5;
    Company c6;
    Company c7;
    Company c8;
    Company c9;
    Company c10;
    Company c11;
    Company c12;
    Company c13;
    Company c14;
    Company c15;
    Company c16;
    Company c17;
    Company c18;
    Company c19;
    Company c20;


    @BeforeEach
    void runBefore () {
        c1 = new Company ("Apple", "AAPL", 135, 2140);
        c2 = new Company ("Aritzia", "ATZ", 45.5, 5.18);
        c3 = new Company ("Microsoft", "MSFT", 252.75, 1927);
        c4 = new Company ("Alphabet", "GOOG", 101.5, 1305);
        c5 = new Company ("Amazon", "AMZN", 105.15, 1072);
        c6 = new Company ("Berkshire Hathaway", "BRK", 310.57, 684.8);
        c7 = new Company ("Tesla", "TSLA", 181.41, 572.84);
        c8 = new Company ("NVIDIA", "NVDA", 209.43, 521.89);
        c9 = new Company ("Taiwan Semiconductor", "TSM", 94.74, 491.32);
        c10 = new Company ("Visa", "V", 230.9, 485.3);
        c11 = new Company ("Tencent", "TCEHY", 49.3, 475.53);
        c12 = new Company ("Exxon Mobil", "XOM", 114.74, 472.53);
        c13 = new Company ("United Health", "UNH", 497.00, 464.37);
        c14 = new Company ("Johnson & Johnson", "JNJ", 164.92, 431.18);
        c15 = new Company ("JP Morgan Chase", "JPM", 139.59, 409.59);
        c16 = new Company ("Meta Platforms", "META", 153.12, 401.51);
        c17 = new Company ("Walmart", "WMT", 144.67, 390.14);
        c18 = new Company ("Mastercard", "MA", 374.08, 357.62);
        c19 = new Company ("Proctor & Gamble", "PG", 143.19, 337.80);
        c20 = new Company ("Home Depot", "HD", 328.09, 334.38);
    }

    @Test

    void testSharesOutstanding() {
        assertEquals((double)2140 * 1_000_000_000 / 135, c1.sharesOutstanding(), 1.0E-8);
        assertEquals(5.18 * 1_000_000_000 / 45.5, c2.sharesOutstanding(), 1.0E-8);
    }

    @Test
    void testSize() {
        assertEquals("Mega", c1.size());
        assertEquals("Mid", c2.size());
    }


}

