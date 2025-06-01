class Node {
    int val;
    Node prev;
    Node next;
    
    public Node(int val) {
        this.val = val;
        this.prev = null;
        this.next = null;
    }
}

public class Doubly {
    Node head;
    Node tail;
    int count;
    
    // Constructor to initialize the list
    public Doubly() {
        head = null;
        tail = null;
        count = 0;
    }

    // Insert at the beginning of the list
    public void insertFirst(int val) {
        Node newNode = new Node(val);
        
        if (count == 0) {
            head = tail = newNode;  // List was empty
        } else {
            newNode.next = head;  // Set newNode's next to the current head
            head.prev = newNode;  // Set current head's previous to newNode
            head = newNode;       // Move head to the new node
        }
        count++;
    }

    // Insert at a specific index
    public void insertAt(int index, int val) {
        if (index < 0 || index > count) {
            System.out.println("Index out of bounds");
            return;
        }

        if (index == 0) {
            insertFirst(val);  // Insert at the beginning
            return;
        }

        if (index == count) {
            insertLast(val);  // Insert at the end
            return;
        }

        Node newNode = new Node(val);
        Node current = head;

        // Traverse to the node just before the index
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }

        // Adjust the pointers to insert the new node
        newNode.next = current.next;    // New node's next is current node's next
        current.next.prev = newNode;    // Current node's next node's prev is new node
        current.next = newNode;         // Current node's next is new node
        newNode.prev = current;         // New node's prev is current node

        count++;
    }

    // Insert at the end of the list
    public void insertLast(int val) {
        Node newNode = new Node(val);
        
        if (count == 0) {
            head = tail = newNode;  // List was empty
        } else {
            tail.next = newNode;  // Set current tail's next to newNode
            newNode.prev = tail;  // Set newNode's prev to current tail
            tail = newNode;       // Move tail to newNode
        }
        count++;
    }

    // Print the list
    public void printList() {
        if (head == null) {
            System.out.println("List is empty.");
            return;
        }

        Node current = head;
        while (current != null) {
            System.out.print(current.val + " ");
            current = current.next;
        }
        System.out.println();
    }

    // Get size of the list
    public int size() {
        return count;
    }

    public static void main(String[] args) {
        Doubly list = new Doubly();
        
        // Testing the doubly linked list
        list.insertFirst(10);
        list.insertFirst(20);
        list.insertFirst(30);

        System.out.println("List after inserting first:");
        list.printList();

        list.insertAt(1, 15);  // Insert 15 at index 1

        System.out.println("List after inserting at index 1:");
        list.printList();

        list.insertLast(40);  // Insert at the end
        System.out.println("List after inserting last:");
        list.printList();

        System.out.println("Size of the list: " + list.size());
    }
}
