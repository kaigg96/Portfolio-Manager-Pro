package model.tests;

import model.Portfolio;
import model.exceptions.CompanyNotFoundException;
import model.exceptions.InsufficientFundsException;
import model.exceptions.NegativeAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class PortfolioTest {
    Portfolio p1;
    Portfolio p2;

    @BeforeEach
    void runBefore() {
        p1 = new Portfolio(0);
        p2 = new Portfolio(1000.);
    }

    @Test
    void addBalanceSuccessTest() {
        assertEquals(0, p1.getCashBalance()); //init bal = 0
        try {
            p1.addToBalance(1000.); //add 1000
        } catch (NegativeAmountException e) {
            fail("Threw NegativeAmountException");
        }
        assertEquals(1000, p1.getCashBalance()); //confirm 1000 is added
    }

    @Test
    void subBalanceSuccessTest() {
        assertEquals(1000, p2.getCashBalance()); //init bal = 1000
        try {
            p2.subFromBalance(1000.);
        } catch (NegativeAmountException e) {
            fail("Threw NegativeAmountException");
        } catch (InsufficientFundsException e) {
            fail("Threw InsufficientFundsException");
        }
        assertEquals(0, p2.getCashBalance());
    }

    @Test
    void addNegFundsTest() {
        assertEquals(0, p1.getCashBalance());
        try {
            p1.addToBalance(-1);
            fail("Did not throw NegativeAmountException");
        } catch (NegativeAmountException e) {
            // continue
        }
        assertEquals(0, p1.getCashBalance()); //confirm amount still 0
    }

    @Test
    void subNegFundsTest() {
        assertEquals(1000, p2.getCashBalance());
        try {
            p2.subFromBalance(-1);
            fail("Did not throw NegativeAmountException");
        } catch (NegativeAmountException e) {
            // continue
        } catch (InsufficientFundsException e) {
            fail("Threw InsufficientFundsException");
        }
        assertEquals(1000, p2.getCashBalance()); //confirm amount still 0
    }



    @Test
    void subInsufficientFundsTest() {
        try {
            p2.subFromBalance(1001);
            fail("Did not throw InsufficientFundsException");
        } catch (NegativeAmountException e) {
            fail("Threw NegativeAmountException");
        } catch (InsufficientFundsException e) {
            // continue
        }
        assertEquals(1000, p2.getCashBalance());
    }

    @Test
    void purchaseSharesSuccessTest() {
        try {
            p2.purchaseShares("AAPL", 1); //Buy a share using ticker
        } catch (InsufficientFundsException | CompanyNotFoundException e) {
            fail("Threw Exception");
        }
        assertEquals("Apple", p2.getStocks().get(0).getName()); //confirm share is added to stocks by checking name
        assertEquals(1, p2.getStocks().get(0).getSharesHeld()); //confirm one share was bought

        try {
            p2.purchaseShares("Microsoft", 1); //Buy a share using name
        } catch (InsufficientFundsException | CompanyNotFoundException e) {
            fail("Threw Exception");
        }
        assertEquals("MSFT", p2.getStocks().get(1).getTicker()); //confirm share is added to stocks by checking ticker

        try {
            p2.purchaseShares("AAPL", 1); //purchase another share
        } catch (InsufficientFundsException | CompanyNotFoundException e) {
            fail("Threw Exception");
        }
        assertEquals("Apple", p2.getStocks().get(0).getName()); //confirm company is still at same index
        assertEquals(2, p2.getStocks().get(0).getSharesHeld()); //confirm two shares are owned now
    }

    @Test
    void purchaseSharesInsuffFundsTest() {
        try {
            p2.purchaseShares("AAPL", 7); //fail to buy shares of company not already in stocks
            fail("Did not throw InsufficientFundsException");
        } catch (InsufficientFundsException e) {
            // continue
        } catch (CompanyNotFoundException e) {
            fail("Threw CompanyNotFoundException");
        }
        assertEquals(0, p2.getStocks().size());
        try {
            p2.purchaseShares("AAPL", 1); //buy one share so the company is now in the array
        } catch (InsufficientFundsException | CompanyNotFoundException e) {
            fail("Threw Exception");
        }
        try {
            p2.purchaseShares("AAPL", 6); //fail to buy shares of company already in stocks
            fail("Did not throw InsufficientFundsException");
        } catch (InsufficientFundsException e) {
            // continue
        } catch (CompanyNotFoundException e) {
            fail("Threw CompanyNotFoundException");
        }
    }

    @Test
    void purchaseSharesCompanyNotFoundTest() {
        try {
            p2.purchaseShares("LOL", 1);
            fail("Did not throw CompanyNotFoundException");
        } catch (InsufficientFundsException e) {
            fail("Threw InsufficientFundsException");
        } catch (CompanyNotFoundException e) {
            //continue
        }
    }

    @Test
    void sellSharesSuccessTest() {
        try {
            p2.purchaseShares("AAPL", 2); //add 2 shares to portfolio
        } catch (InsufficientFundsException | CompanyNotFoundException e) {
            fail("Threw Exception");
        }
        try {
            p2.purchaseShares("Alphabet", 1); //
        } catch (InsufficientFundsException | CompanyNotFoundException e) {
            fail("Threw Exception");
        }
        assertEquals((1000-150*2-101.5), p2.getCashBalance());
        try {
            p2.sellShares("Apple", 1); //partial exit position
        } catch (NegativeAmountException e) {
            fail("Threw NegativeAmountException");
        }
        try {
            p2.sellShares("GOOG", 1); //complete exit position
        } catch (NegativeAmountException e) {
            fail("Threw NegativeAmountException");
        }
        assertEquals(850, p2.getCashBalance()); //make sure funds are added to cash bal
    }

    @Test
    void sellSharesFailureTest() {
        try {
            p2.purchaseShares("AAPL", 2); //add 2 shares to portfolio
        } catch (InsufficientFundsException | CompanyNotFoundException e) {
            fail("Threw Exception");
        }
        try {
            p2.sellShares("AAPL", -1);
            fail("Did not throw NegativeAmountException");
        } catch (NegativeAmountException e) {
            //continue
        }
        try {
            p2.sellShares("AAPL", 3);
            fail("Did not throw NegativeAmountException");
        } catch (NegativeAmountException e) {
            //continue
        }
    }

    @Test
    void findCompanyInStocksTest () {
        try {
            p2.purchaseShares("AAPL", 1);
        } catch (InsufficientFundsException | CompanyNotFoundException e) {
            fail("Threw Exception");
        }
        try {
            p2.purchaseShares("Microsoft", 2);
        } catch (InsufficientFundsException | CompanyNotFoundException e) {
            fail("Threw Exception");
        }
        assertNull(p2.findCompanyInStocks("BRK"));
        assertEquals(p2.getStocks().get(0), p2.findCompanyInStocks("Apple"));
        assertEquals(p2.getStocks().get(1), p2.findCompanyInStocks("MSFT"));
    }

    @Test
    void findIndexOfCompanyInStocksTest () {
        try {
            p2.purchaseShares("Home Depot", 1);
        } catch (InsufficientFundsException | CompanyNotFoundException e) {
            fail("Threw Exception");
        }
        try {
            p2.purchaseShares("MSFT", 2);
        } catch (InsufficientFundsException | CompanyNotFoundException e) {
            fail("Threw Exception");
        }
        assertNull(p2.findCompanyInStocks("BRK"));
        try {
            assertEquals(0, p2.findIndexOfCompanyInStocks("HD"));
        } catch (CompanyNotFoundException e) {
            fail("Threw CompanyNotFoundException");
        }
        try {
            assertEquals(1, p2.findIndexOfCompanyInStocks("Microsoft"));
        } catch (CompanyNotFoundException e) {
            fail("Threw CompanyNotFoundException");
        }
    }
}

