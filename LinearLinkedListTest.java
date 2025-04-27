class Node {
    public int data;
    public Node next;

    public Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class LinearLinkedList {

    private Node head;  // Head of the linked list
    private Node tail;  // Tail of the linked list

    // Constructor to create an empty linked list
    public LinearLinkedList() {
        head = null;
        tail = null;
    }

    // Method to add data to the linked list
    public void add(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    // Search method to find a key in the linked list
    public Node search(int key) {
        Node temp = head;
        while (temp != null) {
            if (temp.data == key)  // Directly access the data field
                return temp;
            temp = temp.next;
        }
        return null;  // Return null if not found
    }

    // Method to print the linked list
    public void printList() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();
    }
}

public class LinearLinkedListTest {

    public static void main(String[] args) {
        LinearLinkedList list = new LinearLinkedList();

        // Adding elements to the list
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);

        // Printing the list
        System.out.println("Linked List: ");
        list.printList();

        // Searching for a key in the list
        int searchKey = 20;
        Node result = list.search(searchKey);

        if (result != null) {
            System.out.println("Node with data " + searchKey + " found.");
        } else {
            System.out.println("Node with data " + searchKey + " not found.");
        }
    }
}
