
/**
 * its a stack with a limited size that is built on ListNode<Contact>
 *
 * @author Hayden Gillen
 * @version v1.0
 */
public class Stack
{
    // for the size limit of the stack
    private final int SIZE_LIMIT;
    // for how many items are in the stack
    private int size;
    //top of stack pointer
    ListNode top;
    
    /**
     * Constructor for objects of class Stack
     */
    public Stack(int size)
    {
        SIZE_LIMIT = size;
        size = 0;
        top = null;
    }

    /**
     * push new contact onto the stack
     */
    public void push(Contact source)
    {
        ListNode pushNode = new ListNode(source);
        //new nodes points to rest of the stack
        pushNode.next = top;
        //adjust the top pointer
        top = pushNode;
        //add to size to match the size of the stack
        size++;
        //if it makes size > SIZE_LIMIT then remove last element
        if (SIZE_LIMIT == 1){
            top.next = null;
            return;
        }
        if (size > SIZE_LIMIT){
            ListNode head = top;
            //traverse to the second to last node and point it towards null
            while (head.next.next != null){
                head = head.next;
            }
            head.next = null;
            //readjust size
            size--;
        }
    }
    
    /**
     * returns the top num items in the stack without removing
     * if num > size then it returns all items
     */
    public Contact[] peek(int num) {
        //result to store num items
        Contact[] result = new Contact[num];
        //starting node
        ListNode curr = top;
        //grab top num items, if reaches null then return
        for (int i = 0; i < num; i++){
            if (curr == null) return result;
            result[i] = curr.val;
            curr = curr.next;
        }
        return result;
    }
    
    /**
     * push new contact onto the stack
     */
    public Contact pop()
    {
        //hold the head of the list
        ListNode temp = top;
        //move the top pointer to the next item
        top = top.next;
        //adjust size
        size--;
        //return the temp item
        return temp.val;
    }
    
    public void clear(){
        top = null;
    }
    
    public int size(){
        return size;
    }
}
