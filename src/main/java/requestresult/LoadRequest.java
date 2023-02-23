package requestresult;

import model.User;
import model.Event;
import model.Person;
/**
 * Object used in the Load process to transfer request information
 */
public class LoadRequest {
    private User[] users;
    private Event[] events;
    private Person[] persons;

    /**
     * Constructor that takes parameters necessary to build the object
     * @param users User[]
     * @param events Event[]
     * @param persons Person[]
     */

    public LoadRequest(User[] users, Event[] events, Person[] persons) {
        this.users = users;
        this.events = events;
        this.persons = persons;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }
}
