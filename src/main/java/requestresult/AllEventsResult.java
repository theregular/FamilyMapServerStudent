package requestresult;

import model.Event;
/**
 * Object used in the AllEvents process to transfer result information
 */
public class AllEventsResult extends Result {

    public AllEventsResult(boolean success) {
        this.success = success;
    }
    private Event[] data;

    public Event[] getEvents() {
        return data;
    }

    public void setEvents(Event[] events) {
        this.data = events;
    }
}
