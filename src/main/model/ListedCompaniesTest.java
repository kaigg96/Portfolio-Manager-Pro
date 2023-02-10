package model;

import org.junit.Test;

import static model.ListedCompanies.findInListedCompanies;
import static org.junit.Assert.assertEquals;

public class ListedCompaniesTest {

    @Test
    public void findInListedCompaniesTest() {
        assertEquals(ListedCompanies.APPLE, findInListedCompanies("AAPL"));
    }
}
