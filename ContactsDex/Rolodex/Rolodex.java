import java.util.ArrayList; // import the ArrayList class
import java.util.Random; // import the Random class
import java.util.HashMap; // Import the HashSet class
/**
 * Works just like a rolodex, create a contact, stored in a hashmap for O(1) access time
 * it is password protected and you can search within the rolodex, or get a list of all the contacts in it
 *
 * @author Hayden Gillen
 * @version v1.0
 */


public class Rolodex
{
    //New Instance Variable to hold every person in the contact list
    public HashMap<String, Contact> AllContacts;
    // creates a variable to store the password for the users information
    private String Password;
    // boolean for whether the user is logged in or not
    private boolean LoggedIn;
    //creates a variable for the graph for the network
    private Network connections;
    //recently added and accessed are put into a stack
    private Stack recents;
    //amount to keep in recents
    private final int RECENTAMT = 10;
    
    /**
     * Constructor for objects of class Rolodex
     */
    public Rolodex(String password)
    {
        // create a hashmap in which all contacts will be stored
        AllContacts = new HashMap<String, Contact>();
        //take the password and store its hashed value
        Password = hash(password);
        //initialize graph for connections
        connections = new Network();
        //initialize recents to hold the 10 most recent contacts
        recents = new Stack(RECENTAMT);
        //set the log in status to false
        LoggedIn = false;
    }

    /**
    *   hash by concatenating each chars ascii value then cube
    *   It's not great and can easily be cracked, but the principle is there
    *   specifically not storing plain text passwords
    */
    private String hash(String password){
        //string builder to store the concatenated value of asciis from each char
        StringBuilder asciiNumber = new StringBuilder();
        
        //for each char append the ascii value to the result string
        for (int i = 0; i < password.length(); i++) {
            int asciiValue = password.charAt(i);
            asciiNumber.append(asciiValue);
        }

        // Convert the ASCII series string to a number
        long result = Long.parseLong(asciiNumber.toString());
        
        //cube the result
        //theres definitely an opportunity for overflow here but I don't think it matters in this case
        result = (result * result * result);
        
        //return number as string
        return Long.toString(result);
    }
    
    /**
     * adds contact to the list for this person
     */
    public boolean addContact(String name, String address, String PhonePlainNoSymbol)
    {
        if (!LoggedIn){
            System.out.println("Not logged in!");
            return false;
        }
        //creates a new contact to be added
        Contact newContact = new Contact(name, address, PhonePlainNoSymbol);
        //if they are already here, don't add them and return false
        if (AllContacts.get(newContact.name()) != null){
            System.out.println(newContact.name() + " is already a contact.");  
            return false;
        }
        //otherwise do add them an return true
        AllContacts.put(newContact.name(), newContact);
        //add them to recents
        recents.push(newContact);
        return true;
    }
    
    /**
     * allows the user to log in if they have the corect password
     */
    public void LogIn(String password)
    {
        //checks to see if the password is correct and lets the user know whether they logged in or not
        //hashes the input and compares it to the hash on file
        LoggedIn = hash(password).equals(Password)? true : false;
        if (LoggedIn) {
            System.out.println("Log In Successful!");
        }
        else {
            System.out.println("Invalid Password");
        }
    }
    
    /***
     * logs out the user
     */
    public void LogOut()
    {
        LoggedIn = false;
        System.out.println("Log Out Successful");
    }
    
    /**
     * searches for a specific person in the ArrayList and provides their information
     */
    public boolean FindName(String SearchName)
    {
        if (!LoggedIn){
            System.out.println("Not logged in!");
            return false;
        }
        //if it is a contact
        if (AllContacts.get(SearchName) != null){
            //uses a method in the Contact class to output the info to the console
            AllContacts.get(SearchName).output();
            recents.push(AllContacts.get(SearchName));
            return true;
        }
        //if the name is not found it lets the user know
        else {
            System.out.println("No Contact found for " + SearchName);
            return false;
        }
    }
    
    /**
    * adds a connection between two people in the Network graph (variable connections)
    *
    */
    public void addConnection(String name1, String name2){
        Contact contact1;
        Contact contact2;
        if (!LoggedIn){
            System.out.println("Not logged in!");
            return;
        }
        //if both contacts can be found
        if (AllContacts.get(name1) != null && AllContacts.get(name2) != null){
            //store them and pass them to the network to create a connection
            contact1 = AllContacts.get(name1);
            contact2 = AllContacts.get(name2);
            connections.addConnection(contact1, contact2);
        }
        //output for which name cannot be found
        else if (AllContacts.get(name1) != null){
            System.out.println(name1+ " cannot be found");
            return;
        }
        else if (AllContacts.get(name2) != null){
            System.out.println(name2+ " cannot be found");
            return;
        }
    }
    
    /**
    * uses Dijkstra's algorithm to determine the degrees of separation between two contacts connections
    */
    public Integer degreesOfSep(String source, String destination){
        if (!LoggedIn){
            System.out.println("Not logged in!");
            return null;
        }
        //get the contacts of the names
        Contact start = AllContacts.get(source);
        Contact end = AllContacts.get(destination);
        //find the path to all nodes, and use .get(end) to get the distance to desired destination 
        Integer degrees = connections.dijkstras(start).get(end);
        //return the degrees of separation that destination is from source
        return degrees;
    }
    
    /**
    * prints the recent contacts   
    */
    public Contact[] printRecents(){
        Contact[] recentArr = recents.peek(RECENTAMT);
        int i = 1;
        for (Contact item: recentArr){
            System.out.println(i + ".");
            if (item != null) item.output();
            i++;
        }
        return recentArr;
    }
    
    /**
    *  clears the recents stack   
    */
    public void clearRecents(){
        recents.clear();
    }
}
