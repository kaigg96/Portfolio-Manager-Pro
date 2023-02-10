package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static model.ListedCompanies.findInListedCompanies;

public class ListedCompaniesTest {

    @Test
    void findInListedCompaniesTest() {
        assertEquals(ListedCompanies.APPLE, findInListedCompanies("AAPL"));
    }
}
