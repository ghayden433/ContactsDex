
/**
 * ListNode is nodes for a singly linked list
 * these nodes store contacts
 * 
 * I've decided not to store the whole list as an object and just keep the head avaliable
 * the list will be used in network and that will have the methods for changing and accessing
 *
 * @author Hayden Gillen
 * @version v1.0
 */
public class ListNode
{
    // stores the value in this node
    public Contact val;
    // stores the pointer (I think it's technically a reference in java) to the next node
    public ListNode next;

    /**
     * Constructor for objects of class ListNode
     */
    public ListNode(Contact contact)
    {
        // initialise next to null becuase these will get appended to the previous node
        next = null;
        //put the contact in the node's value
        val = contact;
    }
}
