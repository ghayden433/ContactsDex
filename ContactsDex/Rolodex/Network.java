import java.util.HashSet; //import the HashSet class
import java.util.HashMap; //import the HashMap class
/**
 * this network is an unweighted undirected graph that stores the connection between people in a rolodex
 * stored as an adjacency list 
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Network
{
    // HashSet to check if the contact is already in the graph
    private HashSet<Contact> inGraph;
    //
    private GraphNode head;

    /**
     * Constructor for objects of class Network
     */
    public Network()
    {
        //initialize instance variables
        inGraph = new HashSet<Contact>();
        head = null;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void addConnection(Contact contact1, Contact contact2)
    {
        //if the graph is empty
        if (head == null){
            //put the first item in the head node
            //create listNodes for each contact
            ListNode tempList1 = new ListNode(contact1);
            ListNode tempList2 = new ListNode(contact2);

            //put the heads of the connection lists into its spot int he graph list
            head = new GraphNode(tempList1);
            head.next = new GraphNode(tempList2);
            
            //needs to be two new objects to prevent looping throughout the graph
            tempList1 = new ListNode(contact1);
            tempList2 = new ListNode(contact2);
            //create the connection
            head.val.next = tempList2;
            head.next.val.next = tempList1;
            
            //add the new contacts to the discovered set
            inGraph.add(contact1);
            inGraph.add(contact2);
        }
        
        //if the graph is not empty, start here
        else{
            GraphNode curr = head;
            //if contact1 is not already int he graph then add a new graphNode for it
            if (!inGraph.contains(contact1)){
                //traverse to the end of the linked list to add a new item
                while (curr.next != null){
                    curr = curr.next; 
                }
                //append a graph node that holds a list beginning with the contact to be added
                curr.next = new GraphNode(new ListNode(contact1));
                inGraph.add(contact1);
            }
            
            //if contact2 is not already int he graph then add a new graphNode for it
            if (!inGraph.contains(contact2)){
                //traverse to the end of the linked list to add a new item
                while (curr.next != null){
                    curr = curr.next;
                }
                //append a graph node that holds a list beginning with the contact to be added
                curr.next = new GraphNode(new ListNode(contact2));
                inGraph.add(contact2);
            }
            
            //reset curr to head
            curr = head;
            //to store the linked list node when its found
            ListNode listCurr;
            //traverse the graphNode list until you find the list of the first contact
            while (curr.val.val != contact1){
                curr = curr.next;
            }
            //once the list for the first contact is found, traverse to the end of the nested linked list
            listCurr = curr.val;
            while (listCurr.next != null){
                listCurr = listCurr.next;
            }
            listCurr.next = new ListNode(contact2);
            
            
            //same thing again but connect contact1->contact2 (vice versa)
            //reset curr to head
            curr = head;
            //traverse the graphNode list until you find the list of the first contact
            while (curr.val.val != contact2){
                curr = curr.next;
            }
            //once the list for the first contact is found, traverse to the end of the nested linked list
            listCurr = curr.val;
            while (listCurr.next != null){
                listCurr = listCurr.next;
            }
            listCurr.next = new ListNode(contact1);
        } 
    }
    
    /**
    *  Print the graph   
    */
    public void printGraph(){
        System.out.println("*************************************************");
        String line;
        GraphNode currList = head;
        ListNode currNode;
        // for each linked list int he graph, concatenate the contents and print
        while(currList != null){
            line = "";
            currNode = currList.val;
            line = line + currNode.val.name() + ": ";
            currNode = currNode.next;
            while (currNode != null){
                line = line + currNode.val.name() + " -> ";
                currNode = currNode.next;
            }
            currList = currList.next;
            System.out.println(line);
        }
    }
    
    /**
    * performs Dijkstras single source shortest path algorithm   
    */
    public HashMap<Contact, Integer> dijkstras(Contact source){
        Integer leastDist;
        HashMap<Contact, Integer> result = new HashMap<Contact, Integer>();
        //clone inGraph
        HashSet<Contact> notDiscovered = new HashSet();
        notDiscovered = (HashSet)inGraph.clone();
        Integer distance = 1;
        //initialize a hashmap to store each contacts distance from source
        for (Contact element : inGraph) {
            result.put(element, Integer.MAX_VALUE); 
        }
        // dist to source set to zero becuase it is itself and remove from notDiscovered set
        result.put(source, 0);
        notDiscovered.remove(source);
        
        //find the node that holds the contact
        //store the first node of the adjacency list to iterate through
        ListNode listCurr = findGraphNode(source).val;
        while (notDiscovered.size() > 1){
            //remove the contact being visited from the notDiscovered set
            notDiscovered.remove(listCurr.val);
            leastDist = Integer.MAX_VALUE;
            while (listCurr != null){
                //if this path is closer put the new value for distance
                if (distance < result.get(listCurr.val)){
                    result.put(listCurr.val, distance);
                }
                //increment LinkedList pointer
                listCurr = listCurr.next;
            }
            
            //find node with least distance not yet visited
            for (Contact item: inGraph){
                //if the node hasnt been visited and the distance is the least distance set least
                if (notDiscovered.contains(item) && (result.get(item) < leastDist)){
                    leastDist = result.get(item);
                    //set the next node to be visited
                    listCurr = findGraphNode(item).val;
                }
            }
            //the distance on the next iteration is the visiting nodes distance from source, + 1
            distance = result.get(listCurr.val) + 1;
        }
        return result;
    }
    
    /**
    *  iterate through the graphnodes until desired contact is found 
    */
    private GraphNode findGraphNode(Contact contact){
        GraphNode curr = head;
        //for every node, check if it holds the contact
        while (curr != null){
            if (curr.val.val == contact) return curr;
            curr = curr.next;
        }
        //if nothing is found return null
        return null;
    }
}
