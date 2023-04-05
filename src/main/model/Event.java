package model;

import java.util.Calendar;
import java.util.Date;


// Represents a portfolio event
public class Event {

    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;


     //EFFECTS: Creates an event with the given description and the current date/time stamp.
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    public Date getDate() {
        return dateLogged;
    }

    public String getDescription() {
        return description;
    }

    //EFFECTS: Overrides equals to make sure the date and descriptions are equal instead of the objects themselves
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                && this.description.equals(otherEvent.description));
    }

    //EFFECTS: Overrides hashCode to work with new equals
    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    //EFFECTS: Overrides toString to combine date and description of event
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}