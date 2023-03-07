package model.tests;

import model.ListedCompanies;
import org.junit.jupiter.api.Test;

import static model.ListedCompanies.findInListedCompanies;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ListedCompaniesTest {

    @Test
    void findInListedCompaniesTest() {
        assertEquals(ListedCompanies.HD, findInListedCompanies("HD"));
        assertEquals(ListedCompanies.HD, findInListedCompanies("Home Depot"));
        assertNull(findInListedCompanies("LOL"));
    }
}
