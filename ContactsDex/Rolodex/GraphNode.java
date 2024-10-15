
/**
 * GraphNode are nodes for the graph that theres one for each item in the graph.
 * they need to store ListNode objects, which is the head of a list of its connections
 *
 * @author Hayden Gillen
 * @version v1.0
 */
public class GraphNode
{
    // stores the value in this node
    public ListNode val;
    // stores the pointer (I think it's technically a reference in java) to the next node
    public GraphNode next;

    /**
     * Constructor for objects of class ListNode
     */
    public GraphNode(ListNode listNode)
    {
        // initialise next to null becuase these will get appended to the previous node
        next = null;
        //put the contact in the node's value
        val = listNode;
        
    }
}
