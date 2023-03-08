package model.tests;

import model.ListedCompanies;
import model.exceptions.CompanyNotFoundException;
import org.junit.jupiter.api.Test;

import static model.ListedCompanies.findInListedCompanies;
import static org.junit.jupiter.api.Assertions.*;

public class ListedCompaniesTest {

    @Test
    void findInListedCompaniesTest() {
        try {
            assertEquals(ListedCompanies.HD, findInListedCompanies("HD"));
        } catch (CompanyNotFoundException e) {
            fail("Threw CompanyNotFoundException");
        }
        try {
            assertEquals(ListedCompanies.HD, findInListedCompanies("Home Depot"));
        } catch (CompanyNotFoundException e) {
            fail("Threw CompanyNotFoundException");
        }
        try {
            findInListedCompanies("LOL");
            fail("Did not throw CompanyNotFoundException");
        } catch (CompanyNotFoundException e) {
            // continue
        }
    }
}
