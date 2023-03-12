package service;

import com.google.gson.Gson;
import dataAccess.*;
import handlers.Server;
import jsonData.*;
import model.Event;
import model.Person;
import model.User;
import requestresult.FillResult;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.Random;

/**
 * Processes Fill requests and results
 */
public class FillService {
    private Connection conn;
    PersonDao pDao;
    UserDao uDao;
    EventDao eDao;
    Gson gson = new Gson();
    private FemaleNamesData fData;
    private MaleNamesData mData;
    private SurnamesData sData;
    private LocationData lData;

    private String[] fnames;
    private String[] mnames;
    private String[] snames;
    private Location[] locations;
    private int numPersons = 0;
    private int numEvents = 0;

    int persons = 0;
    int events = 0;

    /** Constructor
     * gets json data to generate with
     */
    public FillService() throws FileNotFoundException {
        try {
            FileReader fileReader = new FileReader("json\\fnames.json");
            fData = gson.fromJson(fileReader, FemaleNamesData.class);

            fileReader = new FileReader("json\\mnames.json");
            mData = gson.fromJson(fileReader, MaleNamesData.class);

            fileReader = new FileReader("json\\snames.json");
            sData = gson.fromJson(fileReader, SurnamesData.class);

            fileReader = new FileReader("json\\locations.json");
            lData = gson.fromJson(fileReader, LocationData.class);

            fnames = fData.getData();
            mnames = mData.getData();
            snames = sData.getData();
            locations = lData.getData();

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /** Fill Service
     * @return FillResult Object
     */

    //TODO: fix marriage year sometimes being too young

    public FillResult fill(String username, int generations) {
            FillResult result = new FillResult(false);
            Database db = new Database();
            Person[] persons;
            try {
                    conn = db.getConnection();

                    //establish connections
                    uDao = new UserDao(conn);
                    pDao = new PersonDao(conn);
                    eDao = new EventDao(conn);

                    //get all persons associated with username from DB
                    persons = pDao.getPersonsForUser(username);
                    if (persons.length != 0) {
                        //delete all events associated with each person
                        for (Person person : persons) {
                            eDao.delete(person);
                        }
                        //delete all persons associated with username
                        pDao.delete(username);
                    }

                    generate(username, generations); //method to add persons to DB by username and # of generations

                    db.closeConnection(true);
                    result.setSuccess(true);
                    result.setMessage("Successfully added " + numPersons + " persons and " + numEvents + " events to the database.");

            } catch (DataAccessException e) {
                db.closeConnection(false);
                e.printStackTrace();
                result.setMessage("Error: " + e.getMessage());

            }

            return result;
    }

    /**
     * Generates family history data
     */
    public void generate(String username, int generations) throws DataAccessException{

        //check if generations is valid
        if (generations >= 0 && generations <= 10) {
            User user = uDao.find(username); //find user in database
            if (user != null) {
                //generate algorithm
                //make Person for User
                Person userPerson = new Person(user.getPersonID(), user.getUsername(), user.getFirstName(),
                        user.getLastName(), user.getGender());
                numPersons++;

                //generate Birth Event for User Person
                int birthYear = 2023 - randomNumber(80); //get random year for user to be born
                Event userBirthEvent = generateBirthEvent(userPerson, birthYear);
                numEvents++;

                if (generations != 0) {
                    //generate parents for User

                    /*
                    Person father = generatePerson(userPerson.getAssociatedUsername(), generations - 1, "m",
                            userPerson.getLastName());
                    Person mother = generatePerson(userPerson.getAssociatedUsername(), generations - 1, "f",
                            userPerson.getLastName());
*/



                    //weird idea test
                    /*
                    ArrayList<Object> fatherStuff = generatePerson(userPerson.getAssociatedUsername(), generations - 1, "m",
                            userPerson.getLastName(), birthYear);
                    Person father = (Person)fatherStuff.get(1);
                    ArrayList<Object> motherStuff = generatePerson(userPerson.getAssociatedUsername(), generations - 1, "f",
                            userPerson.getLastName(), birthYear);
                    Person mother = (Person)motherStuff.get(1);
                     */

                    //another weird idea test
                    HashMap<String, Object> fatherStuff = generatePerson(userPerson.getAssociatedUsername(), generations - 1, "m",
                            userPerson.getLastName(), birthYear);
                    Person father = (Person)fatherStuff.get("person"); //gets father Person from map

                    HashMap<String, Object> motherStuff = generatePerson(userPerson.getAssociatedUsername(), generations - 1, "f",
                            userPerson.getLastName(), birthYear);
                    Person mother = (Person)motherStuff.get("person"); //gets mother Person from map


                    //add birth events for father and mother
                    /*
                    int fatherBirthYear = generateBirthYear(userBirthEvent.getYear());
                    Event fatherBirthEvent = generateBirthEvent(father, fatherBirthYear);

                    int motherBirthYear = generateBirthYear(userBirthEvent.getYear());
                    Event motherBirthEvent = generateBirthEvent(mother, motherBirthYear);
                    */

                    //weird idea

                    Event fatherBirthEvent = (Event)fatherStuff.get("birth");
                    Event motherBirthEvent = (Event)motherStuff.get("birth");


                    //set father and mother as spouses
                    father.setSpouseID(mother.getPersonID());
                    mother.setSpouseID(father.getPersonID());

                    //add marriage events for father and mother
                    Location marriageLocation = locations[randomNumber(locations.length - 1)];
                    int marriageYear = generateMarriageYear(fatherBirthEvent.getYear(), motherBirthEvent.getYear());
                    Event fatherMarriageEvent = generateMarriageEvent(father, marriageLocation, marriageYear);
                    Event motherMarriageEvent = generateMarriageEvent(mother, marriageLocation, marriageYear);
                    numEvents++;
                    numEvents++;


                    //add death events for father and mother
                    int oldestEventYearF = eDao.findOldestEventYearForPerson(father.getPersonID());
                    int deathYearF = generateDeathYear(fatherBirthEvent.getYear(), oldestEventYearF);



                    int oldestEventYearM = eDao.findOldestEventYearForPerson(father.getPersonID());
                    int deathYearM = generateDeathYear(motherBirthEvent.getYear(), oldestEventYearM);



                    Event fatherDeathEvent = generateDeathEvent(father, deathYearF);
                    Event motherDeathEvent = generateDeathEvent(mother, deathYearM);
                    numEvents++;
                    numEvents++;

                    //add father and mother ID to userPerson
                    userPerson.setFatherID(father.getPersonID());
                    userPerson.setMotherID(mother.getPersonID());


                    //add father and mother to database
                    pDao.insert(father);
                    pDao.insert(mother);

                    //output testing
                    System.out.println("Generation: " + (generations - 1));
                    System.out.println("Father:" + "\n" + father);
                    System.out.println(fatherBirthEvent);
                    System.out.println(fatherMarriageEvent);
                    System.out.println(fatherDeathEvent);
                    System.out.println("Mother:" + "\n" + mother);
                    System.out.println(motherBirthEvent);
                    System.out.println(motherMarriageEvent);
                    System.out.println(motherDeathEvent);

                }


                System.out.println("Generation: " + generations + "\n" + userPerson);
                System.out.println(userBirthEvent);
                pDao.insert(userPerson); //userPerson complete, insert into database (closes database in parent function)

            }
            else {
                throw new DataAccessException("Invalid user/Please register user before using fill command!");
            }
        }
        else {
            throw new DataAccessException("Please enter a valid number of generations");
        }
    }


    public HashMap<String, Object> generatePerson(String username, int generations, String gender, String prevlastName, int childBirthYear) throws DataAccessException {
        //ArrayList<Object> returnStuff = new ArrayList<>(); //weird idea
        HashMap<String, Object> returnStuff = new HashMap<>(); //weird idea 2
        String newPersonID = UUID.randomUUID().toString();
        numPersons++;
        String firstName = null;
        String lastName;

        if (gender.equals("m")) {
            //use boy names
            firstName = mnames[randomNumber(mnames.length - 1)];
        }
        if (gender.equals("f")) {
            //use girl names
            firstName = fnames[randomNumber(fnames.length - 1)];
        }
        //use last name of child (new Person gave child the last name)
        lastName = prevlastName;

        //make new Person w/ info
        Person newPerson = new Person(newPersonID, username, firstName, lastName, gender);

        //make birth event
        int birthYear = generateBirthYear(childBirthYear);
        Event newPersonBirthEvent = generateBirthEvent(newPerson, birthYear);
        numEvents++;
        //weird idea
        //returnStuff.add(newPersonBirthEvent); //birth event is first item in ArrayList
        returnStuff.put("birth", newPersonBirthEvent); //add to map of return stuff

        //generate parents for new Person
        if (generations != 0) {
            Person father = null;
            Person mother = null;
            HashMap<String, Object> fatherStuff = new HashMap<>();
            HashMap<String, Object> motherStuff = new HashMap<>();

            //same last name if male's parents
            if (newPerson.getGender().equals("m")) {
/*
                father = generatePerson(newPerson.getAssociatedUsername(), generations - 1, "m",
                        newPerson.getLastName());
                mother = generatePerson(newPerson.getAssociatedUsername(), generations - 1, "f",
                        newPerson.getLastName());
*/
                //weird idea stuff
                fatherStuff = generatePerson(newPerson.getAssociatedUsername(), generations - 1, "m",
                        newPerson.getLastName(), childBirthYear);
                father = (Person)fatherStuff.get("person"); //gets father Person from map

                motherStuff = generatePerson(newPerson.getAssociatedUsername(), generations - 1, "f",
                        newPerson.getLastName(), childBirthYear);
                mother = (Person)motherStuff.get("person"); //gets mother Person from map



            }

            //different last name if female's parents
            String newLastName = snames[randomNumber(snames.length - 1)];
            if (newPerson.getGender().equals("f")) {
/*
                father = generatePerson(newPerson.getAssociatedUsername(), generations - 1, "m",
                        newLastName);
                mother = generatePerson(newPerson.getAssociatedUsername(), generations - 1, "f",
                        newLastName);
*/

                //weird idea stuff

                fatherStuff = generatePerson(newPerson.getAssociatedUsername(), generations - 1, "m",
                        newLastName, childBirthYear);
                father = (Person)fatherStuff.get("person"); //gets father Person from map

                motherStuff = generatePerson(newPerson.getAssociatedUsername(), generations - 1, "f",
                        newLastName, childBirthYear);
                mother = (Person)motherStuff.get("person"); //gets mother Person from map


            }

            Event fatherBirthEvent = (Event)fatherStuff.get("birth");
            Event motherBirthEvent = (Event)motherStuff.get("birth");

            //set father and mother as spouses
            father.setSpouseID(mother.getPersonID());
            mother.setSpouseID(father.getPersonID());

            //add marriage events for father and mother
            Location marriageLocation = locations[randomNumber(locations.length - 1)];
            int marriageYear = generateMarriageYear(fatherBirthEvent.getYear(), motherBirthEvent.getYear());
            Event fatherMarriageEvent = generateMarriageEvent(father, marriageLocation, marriageYear);
            Event motherMarriageEvent = generateMarriageEvent(mother, marriageLocation, marriageYear);
            numEvents++;
            numEvents++;

            //add death events for father and mother
            int oldestEventYearF = eDao.findOldestEventYearForPerson(father.getPersonID());
            int deathYearF = generateDeathYear(fatherBirthEvent.getYear(), oldestEventYearF);

            int oldestEventYearM = eDao.findOldestEventYearForPerson(father.getPersonID());
            int deathYearM = generateDeathYear(motherBirthEvent.getYear(), oldestEventYearM);

            Event fatherDeathEvent = generateDeathEvent(father, deathYearF);
            Event motherDeathEvent = generateDeathEvent(mother, deathYearM);
            numEvents++;
            numEvents++;

            //add father and mother ID to newPerson
            newPerson.setFatherID(father.getPersonID());
            newPerson.setMotherID(mother.getPersonID());
            //returnStuff.add(newPerson); //person is second item in ArrayList
            //returnStuff.put("person", newPerson);

            //add completed father and mother to database
            pDao.insert(father);
            pDao.insert(mother);

            System.out.println("Generation: " + (generations - 1));
            System.out.println("Father:" + "\n" + father);
            System.out.println(fatherBirthEvent);
            System.out.println(fatherMarriageEvent);
            System.out.println(fatherDeathEvent);
            System.out.println("Mother:" + "\n" + mother);
            System.out.println(motherBirthEvent);
            System.out.println(motherMarriageEvent);
            System.out.println(motherDeathEvent);
        }
        returnStuff.put("person", newPerson);

        //return newPerson
        return returnStuff;
    }

    public Event generateEvent(Person person, String eventType) {
        eventType = eventType.toLowerCase();
        return null;
    }

    public Event generateBirthEvent(Person person, int year) throws DataAccessException {
        String newEventID = UUID.randomUUID().toString(); //generate eventID
        Location birthLocation = locations[randomNumber(locations.length - 1)]; //get random location


        Event birthEvent = new Event(newEventID, person.getAssociatedUsername(), person.getPersonID(), birthLocation.getLatitude(),
                birthLocation.getLongitude(), birthLocation.getCountry(), birthLocation.getCity(), "birth", year);

        eDao.insert(birthEvent); //add to DB

        return birthEvent;
    }

    public Event generateMarriageEvent(Person person, Location location, int year) throws DataAccessException {
        String newEventID = UUID.randomUUID().toString(); //generate eventID
        Event marriageEvent = new Event(newEventID, person.getAssociatedUsername(), person.getPersonID(), location.getLatitude(),
                location.getLongitude(), location.getCountry(), location.getCity(), "marriage", year);

        eDao.insert(marriageEvent); //add to DB

        return marriageEvent;
    }

    public Event generateDeathEvent(Person person, int year) throws DataAccessException {
        String newEventID = UUID.randomUUID().toString(); //generate eventID
        Location deathLocation = locations[randomNumber(locations.length - 1)]; //get random location

        Event deathEvent = new Event(newEventID, person.getAssociatedUsername(), person.getPersonID(), deathLocation.getLatitude(),
                deathLocation.getLongitude(), deathLocation.getCountry(), deathLocation.getCity(), "death", year);

        eDao.insert(deathEvent); //add to DB

        return deathEvent;
    }

    public int randomNumber(int range){
        Random rand = new Random();
        return rand.nextInt(range);
    }

    public int generateBirthYear(int childBirthYear) {
        int year = randomNumber(50);
        while (year < 13) {
            year += randomNumber(7);
        }
        return childBirthYear - year;
    }

    public int generateMarriageYear(int husbandBirthYear, int wifeBirthYear) {

        int year = randomNumber(50);
        while (year < 13) {
            year += randomNumber(7);
        }
        if (husbandBirthYear < wifeBirthYear) {
            return year + husbandBirthYear;
        }
        else {
            return year + wifeBirthYear;
        }
    }

    public int generateDeathYear(int birthYear, int oldestEventYear) {
        if ((oldestEventYear - birthYear) > 120) {
            return 0;
        }
        int deathYear = birthYear + randomNumber(120);
        while (deathYear < oldestEventYear) {
            deathYear += randomNumber(31);
            while ((deathYear - birthYear) > 120) {
                deathYear -= randomNumber(13);;
            }
        }
        return deathYear;
    }


/*
    public static void main(String[] args) {
        try {
            FillService service = new FillService();
            int deathYear = service.generateDeathYear( 2000, 2090);
            System.out.println(deathYear);
            System.out.println("died at: " + (deathYear - 2000));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
*/

}
