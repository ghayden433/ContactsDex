import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class RolodexJUnitTest.
 *
 * @author  Hayden Gillen
 * @version v1.0
 */
public class RolodexJUnitTest
{
    /**
     * Default constructor for test class RolodexJUnitTest
     */
    public RolodexJUnitTest()
    {
       
    }
    
    @Test
    public void testAdd(){
        //creates a rolodex, logs in, adds 2 people, and checks to see if they're there
        Rolodex dex= new Rolodex("dog");
        dex.LogIn("dog");  
        //add two contacts
        dex.addContact("John", "1200 park st", "1234567890");
        dex.addContact("Jack", "2500 second st", "0987654321");
        //check if they are there
        assertEquals(dex.FindName("John"), true);
        assertEquals(dex.FindName("Jack"), true);
    }
    
    @Test
    public void testConnection(){
        //new rolodex password dog
        Rolodex dex= new Rolodex("dog");
        dex.LogIn("dog");
        
        //create 10 contacts in dex, the names are numbers
        for (int i = 0; i  < 10; i++){
            dex.addContact(Integer.toString(i), "street", "1234567890");
        }
        
        /* 
        create sequential connections, ie: 1->2->3
        it looks like this as an adjacency list, which is how it is stored
        0: 1 -> 
        1: 0 -> 2 -> 
        2: 1 -> 3 -> 
        3: 2 -> 4 -> 
        4: 3 -> 5 -> 
        5: 4 -> 6 -> 
        6: 5 -> 7 -> 
        7: 6 -> 8 -> 
        8: 7 -> 9 -> 
        9: 8 ->
        
        since is undirected and unweighted it can be drawn like:
        0-1-2-3-4-5-6-7-8-9
        fairly simple
        example: 9 is 9 degrees of seperation from 0
        */
        for (int i = 0; i < 9; i++){
            dex.addConnection(Integer.toString(i), Integer.toString(i + 1));
        }
        
        //now see if the connections holds true
        //using 0 as source each node distance = name, in theory
        int degree;
        for (int i = 0; i < 10; i++){
            degree = dex.degreesOfSep("0", Integer.toString(i));
            assertEquals(degree, i);
        }
    }
    
    @Test
    public void testStack(){
        Stack test = new Stack(10);
        //add 10 contacts to stack
        for (int i = 0; i  < 10; i++){
            Contact temp = new Contact(Integer.toString(i), "street", "1234567890");
            test.push(temp);
        }
        
        //peek should return, but not remove
        assertEquals(test.size(), 10);
        assertEquals(test.peek(1)[0].name(), "9");
        
        //add one more to make sure it stays at size 10, even thought there are already 10 items in test
        test.push(new Contact("10", "street", "1234567890"));
        assertEquals(test.size(), 10);
        
        //should be taken out backwards and it compares the name to the number it was assigned to be the name
        //the size should also match
        for (int i = 10; i  > 0; i--){
            assertEquals(test.size(), i);
            assertEquals(test.pop().name(), Integer.toString(i));
        }
    }
    
    @Test
    public void testRecents(){
        Rolodex dex = new Rolodex("dog");
        dex.LogIn("dog");
        
        for (int i = 0; i  < 10; i++){
            dex.addContact(Integer.toString(i), "street", "1234567890");
        }
        dex.FindName("5");
        
        //this is the order that the contacts should be in
        String[] contactNames = {"5", "9", "8", "7", "6", "5", "4", "3", "2", "1"};
        Contact[] contacts = dex.printRecents();
        
        //see if the names match
        int i = 0;
        for (Contact item: contacts){
            assertEquals(item.name(), contactNames[i]);
            i++;
        }
        
        //clear recents
        dex.clearRecents();
        contacts = dex.printRecents();
        //make sure it is clear
        for (Contact item: contacts){
            assertEquals(item, null);
        }
    }
    
}
