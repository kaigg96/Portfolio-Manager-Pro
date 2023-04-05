package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

//Represents a log of portfolio events.
//Uses Singleton Design Pattern to ensure that there is only one EventLog in the system and that the system has
//   global access to the single instance of the EventLog.

public class EventLog implements Iterable<Event> {

    private static EventLog theLog;
    private Collection<Event> events;

    //EFFECTS: Constructs the event log
    private EventLog() {
        events = new ArrayList<Event>();
    }

    //MODIFIES: this
    //EFFECTS: Gets instance of EventLog - creates it if it doesn't already exist. (Singleton Design Pattern)
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    //EFFECTS: Prints the description for all Events in the EventLog to console
    public void printEvents() {
        System.out.println("Event Log: ");
        for (Event e : events) {
            System.out.println(e.toString());
        }
    }

    //EFFECTS: Adds an event to the event log.
    public void logEvent(Event e) {
        events.add(e);
    }

    //EFFECTS: Clears the event log and logs the event.
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    //MODIFIES: this
    //EFFECTS: Assigns the ArrayList iterator to the EventLog
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
