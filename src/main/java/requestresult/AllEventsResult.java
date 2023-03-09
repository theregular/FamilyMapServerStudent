package requestresult;

import model.Event;
/**
 * Object used in the AllEvents process to transfer result information
 */
public class AllEventsResult extends Result {

    public AllEventsResult(boolean success) {
        this.success = success;
    }
    private Event[] events;

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
