class NodeList {
    int data;
    NodeList next;
    NodeList prev;

    public NodeList(int data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}

public class LinkedList {
    NodeList head = null;
    NodeList tail = null;
    int count = 0;

    // Add data at the end of the list
    public void add(int data) {
        NodeList newNode = new NodeList(data);
        if (head == null) {
            head = tail = newNode; // If list is empty, newNode becomes both head and tail
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        count++;
    }

    // Add data at the beginning
    public void addFirst(int data) {
        NodeList newNode = new NodeList(data);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        count++;
    }

    // Delete a node with the given data
    public void delete(int data) {
        NodeList current = head;
        while (current != null && current.data != data) {
            current = current.next;
        }
        if (current == null) {
            System.out.println("Node not found");
            return;
        }
        if (current == head) { // If it's the head node
            head = current.next;
            if (head != null) {
                head.prev = null;
            }
        } else if (current == tail) { // If it's the tail node
            tail = current.prev;
            if (tail != null) {
                tail.next = null;
            }
        } else { // If it's a middle node
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        count--;
    }

    // Insert a new node at a specific index
    public void insertAt(int data, int index) {
        if (index < 0 || index > count) {
            throw new IndexOutOfBoundsException("Index out of range.");
        }
        NodeList newNode = new NodeList(data);
        if (index == 0) {
            addFirst(data); // Special case for inserting at the beginning
        } else {
            NodeList current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            newNode.next = current.next;
            if (current.next != null) {
                current.next.prev = newNode;
            } else {
                tail = newNode;
            }
            current.next = newNode;
            newNode.prev = current;
            count++;
        }
    }

    // Remove a node at a specific index
    public void removeAt(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Index out of range.");
        }
        if (index == 0) {
            delete(head.data); // Special case for removing the head node
        } else {
            NodeList current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            delete(current.data); // Delete the node at the specific index
        }
    }

    // Remove the last node
    public void removeLast() {
        if (tail != null) {
            delete(tail.data); // Remove the last node
        }
    }

    // Display the list from the beginning
    public void display() {
        NodeList current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    // Display the list from the end
    public void reverseDisplay() {
        NodeList current = tail;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.prev;
        }
        System.out.println();
    }

    // Get the size of the list
    public int size() {
        return count;
    }
}

