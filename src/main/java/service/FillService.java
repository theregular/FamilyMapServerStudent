package service;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.PersonDao;
import dataAccess.UserDao;
import handlers.Server;
import jsonData.*;
import model.Event;
import model.Person;
import model.User;
import requestresult.FillResult;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.util.UUID;
import java.util.Random;

/**
 * Processes Fill requests and results
 */
public class FillService {
    private Connection conn;
    PersonDao pDao;
    Gson gson = new Gson();
    private FemaleNamesData fData;
    private MaleNamesData mData;
    private SurnamesData sData;
    private LocationData lData;

    private String[] fnames;
    private String[] mnames;
    private String[] snames;
    private Location[] locations;

    /** Constructor
     * gets json data to generate with
     */
    public FillService() throws FileNotFoundException{
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
    public FillResult fill(String username, int generations) {
            FillResult result = new FillResult(false);
            Database db = new Database();
            try {
                    conn = db.getConnection();

                    generate(username, generations); //method to add person to DB by username and # of generations

                    db.closeConnection(true);
                    result.setSuccess(true);
                    result.setMessage("Successfully added \"X\" persons and \"Y\" events to the database.");

            } catch (DataAccessException e) {
                db.closeConnection(false);
                e.printStackTrace();
                result.setMessage("Error: " + e.getMessage());

            }

            return result;
    }

    public void generate(String username, int generations) throws DataAccessException{
        UserDao uDao = new UserDao(conn);
        pDao = new PersonDao(conn);
        if (generations >= 0 && generations <= 10) {
            User user = uDao.find(username); //find user in database
            if (user != null) {
                //generate algorithm

                //make Person for User
                Person userPerson = new Person(user.getPersonID(), user.getUsername(), user.getFirstName(),
                        user.getLastName(), user.getGender());

                if (generations != 0) {
                    //generate parents for User //TODO: make sure gender is not caps sensitive, need to add parents to datbase?
                    Person father = generatePerson(userPerson.getAssociatedUsername(), generations - 1, "m",
                            userPerson.getLastName());
                    Person mother = generatePerson(userPerson.getAssociatedUsername(), generations - 1, "f",
                            userPerson.getLastName());

                    //set father and mother as spouses
                    father.setSpouseID(mother.getPersonID());
                    mother.setSpouseID(father.getPersonID());

                    //add father and mother ID to userPerson
                    userPerson.setFatherID(father.getPersonID());
                    userPerson.setMotherID(mother.getPersonID());

                    //add father and mother to database
                    //pDao.insert(father);
                    //pDao.insert(mother);
                    System.out.println("Generation: " + (generations - 1));
                    System.out.println("Father:" + "\n" + father);
                    System.out.println("Mother:" + "\n" + mother);

                }

                System.out.println("Generation: " + generations + "\n" + userPerson);
                //pDao.insert(userPerson); //userPerson complete, insert into database (closes database in parent function)

            }
            else {
                throw new DataAccessException("Invalid user/Please register user before using fill command!");
            }
        }
        else {
            throw new DataAccessException("Please enter a valid number of generations");
        }
    }


    public Person generatePerson(String username, int generations, String gender, String prevlastName) throws DataAccessException {
        String newPersonID = UUID.randomUUID().toString();
        String firstName = null;
        String lastName = null;

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

        //generate parents for new Person
        if (generations != 0) {
            Person father = null;
            Person mother = null;

            //same last name if male's parents
            if (newPerson.getGender().equals("m")) {
                father = generatePerson(newPerson.getAssociatedUsername(), generations - 1, "m",
                        newPerson.getLastName());
                mother = generatePerson(newPerson.getAssociatedUsername(), generations - 1, "f",
                        newPerson.getLastName());
            }

            //different last name if female's parents
            String newLastName = snames[randomNumber(snames.length - 1)];
            if (newPerson.getGender().equals("f")) {
                father = generatePerson(newPerson.getAssociatedUsername(), generations - 1, "m",
                        newLastName);
                mother = generatePerson(newPerson.getAssociatedUsername(), generations - 1, "f",
                        newLastName);
            }

            //set father and mother as spouses
            father.setSpouseID(mother.getPersonID());
            mother.setSpouseID(father.getPersonID());

            //add father and mother ID to newPerson
            newPerson.setFatherID(father.getPersonID());
            newPerson.setMotherID(mother.getPersonID());

            //add father and mother to database
            //pDao.insert(father);
            //pDao.insert(mother);
            System.out.println("Generation: " + (generations - 1));
            System.out.println("Father:" + "\n" + father);
            System.out.println("Mother:" + "\n" + mother);
        }

        //pDao.insert(newPerson); //insert completed new Person into DB TODO: Not necessary probably
        //System.out.println("Generation: " + generations + "\n" + newPerson); //output testing
        return newPerson;
    }

    public Event generateBirthEvent() {
        return null;
    }

    public Event generateMarriageEvent() {
        return null;
    }

    public Event generateDeathEvent() {
        return null;
    }

    public int randomNumber(int range){
        Random rand = new Random();
        return rand.nextInt(range);
    }

    /*
    public static void main(String[] args) {
        try {
            FillService service = new FillService();
            service.
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

     */
}
