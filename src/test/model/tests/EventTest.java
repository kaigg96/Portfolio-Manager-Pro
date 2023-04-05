package model.tests;

import model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e;
    private Date d;

    @BeforeEach
    public void runBefore() {
        e = new Event("Money money money");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("Money money money", e.getDescription());
        assertTrue((d.getYear() == e.getDate().getYear()) && (d.getMonth() == e.getDate().getMonth())
                && (d.getDay() == e.getDate().getDay()) && (d.getHours() == e.getDate().getHours())
                && (d.getMinutes() == e.getDate().getMinutes()) && (d.getSeconds() == e.getDate().getSeconds()));
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Money money money", e.toString());
    }
}

