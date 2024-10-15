/**
 * a contact is a place to store the name of a person or company along with their address and phone number
 *
 * @author Hayden Gillen
 * @version v1.0
 */
public class Contact
{
    //Holds the name of the person
    private String Name;
    //holds the phone number of the contact
    private String Phone;
    // holds the persons address
    private String Address;
    
    public Contact(String name, String address, String phone){
        //uses setInfo to initially set the users information
        setInfo(name, address, phone);
    }
    
    /**
     * allows a user to change a contacts information
     */
    public void setInfo(String Name, String Address, String phone)
    {
        this.Name = Name;
        this.Address = Address;
        this.Phone = phoneNumber(phone);
    }
    
    /**
     * prints out all of the users information
     */
    public void output()
    {
        System.out.println(Name + ": ");
        System.out.println("    " + Address);
        System.out.println("    " + Phone);
    }
    
    
    /**
    * formats phone number   
    */
    public String phoneNumber(String number){
        String result = "";
        //if the number is 11 digits then it's a +1 number
        if (number.length() == 11){
            result = "+" + number.substring(0,1) + " " + "(" + number.substring(1,4) + ")-" + number.substring(4,7) + "-" + number.substring(7);
        }
        //format it like (area code)-nums- nums and any extra digits are just kept on the end
        else {
            result = "(" + number.substring(0,3) + ")-" + number.substring(3,6) + "-" + number.substring(6);            
        }
        
        return result;
    }
    
    /**
    * returns name   
    */
    public String name(){
        return Name;
    }
}
