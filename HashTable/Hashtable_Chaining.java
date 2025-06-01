class Node {
    Entry data;
    Node next;

    Node(Entry data) {
        this.data = data;
        this.next = null;
    }
}
public class Hashtable_Chaining {

    Node[] hashtable;

    public Hashtable_Chaining(int size) {
        hashtable = new Node[size];
    }

    public void put(int key, int val) {
        int index = hashFunction(key);
        Node current = hashtable[index];

        while (current != null) {
            if (current.data.key == key) {
                current.data.val = val; // update
                return;
            }
            current = current.next;
        }

        // Insert at beginning of chain
        Node newNode = new Node(new Entry(key, val));
        newNode.next = hashtable[index];
        hashtable[index] = newNode;
    }

    public boolean delete(int key) {
        int index = hashFunction(key);
        Node current = hashtable[index];
        Node prev = null;

        while (current != null) {
            if (current.data.key == key) {
                if (prev == null) {
                    hashtable[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                return true;
            }
            prev = current;
            current = current.next;
        }

        return false;
    }

    public void display() {
        for (int i = 0; i < hashtable.length; i++) {
            System.out.print("Index " + i + ": ");
            Node current = hashtable[i];
            if (current == null) {
                System.out.println("Empty");
            } else {
                while (current != null) {
                    System.out.print("[Key = " + current.data.key + ", Value = " + current.data.val + "] -> ");
                    current = current.next;
                }
                System.out.println("null");
            }
        }
    }

    public int hashFunction(int key) {
        return key % hashtable.length;
    }

    public static void main(String[] args) {
        Hashtable_Chaining ht = new Hashtable_Chaining(5);
        ht.put(1, 100);
        ht.put(6, 200);
        ht.put(11, 300);
        ht.display();

        System.out.println("Deleting key 6: " + ht.delete(6));
        ht.display();
    }
}
// I have to either use a built-in Linked List package or make mulitple methods inside the chaining file like addLast, addFirst, addInBetween an so on...
// as well in delete method I can use getEntry method frist then when i find the key I will delete it using the delete method.