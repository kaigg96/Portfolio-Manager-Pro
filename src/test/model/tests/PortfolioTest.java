package model.tests;

import model.Portfolio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


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
        assertEquals(0, p1.getCashBalance()); //init bal = 0
        p1.addToBalance(1000.); //add 1000
        assertEquals(1000, p1.getCashBalance()); //confirm 1000 is added
        p1.subFromBalance(1000.); //sub 1000
        assertEquals(0, p1.getCashBalance()); //confirm bal is back to 0
    }

    //**now required to have sufficient funds to use method so no need for this test
    //@Test
    //void subInsufficientFundsTest() {
    //    assertEquals(0, p1.getCashBalance());
    //    p1.addToBalance(500.); //add 500
    //    assertEquals(500, p1.getCashBalance());
    //    p1.subFromBalance(500.1); //try to remove 500.1 (more than was added)
    //    assertEquals(500, p1.getCashBalance()); //make sure cash stays at 500
    //}

    @Test
    void purchaseSharesTest() {
        p2.purchaseShares("AAPL", 1); //Buy a share using ticker
        assertEquals("Apple", p2.getStocks().get(0).getName()); //confirm share is added to stocks by checking name
        assertEquals(1, p2.getStocks().get(0).getSharesHeld()); //confirm one share was bought

        p2.purchaseShares("Microsoft", 1); //Buy a share using name
        assertEquals("MSFT", p2.getStocks().get(1).getTicker()); //confirm share is added to stocks by checking ticker

        p2.purchaseShares("AAPL", 1); //purchase another share
        assertEquals("Apple", p2.getStocks().get(0).getName()); //confirm company is still at same index
        assertEquals(2, p2.getStocks().get(0).getSharesHeld()); //confirm two shares are owned now
    }

    // **Now purchaseShares requires sufficient funds so no need to test this
    //@Test
    //void purchaseSharesInsuffFundsTest() {
    //    p2.purchaseShares("AAPL", 7); //fail to buy shares of company not already in stocks
    //    assertEquals(0, p2.getStocks().size());
    //    p2.purchaseShares("AAPL", 1); //buy one share so the company is now in the array
    //    p2.purchaseShares("AAPL", 6); //fail to buy shares of company already in stocks
    //    p2.addToBalance(50); //top up balance to sufficient amount
    //    p2.purchaseShares("AAPL", 6); //purchase successfully
    //}

    @Test
    void sellSharesTest() {
        p2.purchaseShares("AAPL", 6); //add 6 shares to portfolio
        assertEquals(100, p2.getCashBalance()); //
        p2.addToBalance(203);
        p2.purchaseShares("Alphabet", 2); //
        p2.sellShares("GOOG", 1);
        p2.sellShares("Alphabet", 1);
        p2.subFromBalance(203);
        p2.sellShares("AAPL", 1); //sell 1 share by ticker
        assertEquals(250, p2.getCashBalance()); //make sure funds are added to cash bal
        p2.sellShares("Apple", 5); //sell remaining shares by name
        assertEquals(1000, p2.getCashBalance()); //make sure funds are added to cash bal
        p2.sellShares("APPL", 1); //make sure you can't sell shares of company not in portfolio
        assertEquals(1000, p2.getCashBalance()); //make sure no change to cash balance


    }

    @Test
    void findCompanyInStocksTest () {
        p2.purchaseShares("AAPL", 1);
        p2.purchaseShares("Microsoft", 2);
        assertNull(p2.findCompanyInStocks("BRK"));
        assertEquals(p2.getStocks().get(0), p2.findCompanyInStocks("Apple"));
        assertEquals(p2.getStocks().get(1), p2.findCompanyInStocks("MSFT"));
    }

    @Test
    void findIndexOfCompanyInStocksTest () {
        p2.purchaseShares("Home Depot", 1);
        p2.purchaseShares("MSFT", 2);
        assertNull(p2.findCompanyInStocks("BRK"));
        assertEquals(0, p2.findIndexOfCompanyInStocks("HD"));
        assertEquals(1, p2.findIndexOfCompanyInStocks("Microsoft"));
    }
}
