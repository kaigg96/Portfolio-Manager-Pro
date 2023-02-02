package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PortfolioTest {
    Portfolio p1;
    Portfolio p2;

    @BeforeEach
    void runBefore() {
        p1 = new Portfolio(0);
        p2 = new Portfolio(1000.);
    }

    @Test
    void addSubBalanceTestNoProbTest() {
        assertEquals(0, p1.getCashBalance());
        p1.addToBalance(1000.);
        assertEquals(1000, p1.getCashBalance());
        p1.subFromBalance(1000.);
        assertEquals(0, p1.getCashBalance());
    }

    @Test
    void subInsufficientFundsTest() {
        assertEquals(0, p1.getCashBalance());
        p1.addToBalance(500.);
        assertEquals(500, p1.getCashBalance());
        p1.subFromBalance(500.1);
        assertEquals(500, p1.getCashBalance());
    }

}