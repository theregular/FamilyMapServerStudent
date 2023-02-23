package requestresult;

import model.Event;
/**
 * Object used in the AllEvents process to transfer result information
 */
public class AllEventsResult {
    private boolean success;
    private String message;
    private Event[] events;
}
