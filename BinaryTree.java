class Node {
    int data;
    Node leftChild;
    Node rightChild;

    public Node(int data) {
        this.data = data;
    }
}

public class BinaryTree {
    Node root;

    public void insert(int data) {
        Node newNode = new Node(data); // Use the correct data
        if (root == null) {
            root = newNode;
            return;
        }

        Node current = root;
        while (true) {
            Node parent = current;
            if (data > current.data) {
                current = current.rightChild;
                if (current == null) {
                    parent.rightChild = newNode;
                    return;
                }
            } else {
                current = current.leftChild;
                if (current == null) {
                    parent.leftChild = newNode;
                    return;
                }
            }
        }
    }

    public boolean find(int key) { // Change to boolean
        Node current = root;
        if (root == null) {
            return false;
        }

        while (current != null) {
            if (current.data == key) {
                return true;
            } else if (key > current.data) {
                current = current.rightChild;
            } else {
                current = current.leftChild;
            }
        }
        return false;
    }

    public void inOrder(Node node) {
        if (node != null) {
            inOrder(node.leftChild);
            System.out.print(node.data + ", ");
            inOrder(node.rightChild);
        }
    }

    public void preOrder(Node node) {
        if (node != null) {
            System.out.print(node.data + ", ");
            preOrder(node.leftChild);
            preOrder(node.rightChild);
        }
    }

    public void postOrder(Node node) {
        if (node != null) {
            postOrder(node.leftChild);
            postOrder(node.rightChild);
            System.out.print(node.data + ", ");
        }
    }

    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree();

        bt.insert(5);
        bt.insert(3);
        bt.insert(4);
        bt.insert(2);
        bt.insert(8);
        bt.insert(1);
        bt.insert(9);

        System.out.println("In-order Traversal:");
        bt.inOrder(bt.root);
        System.out.println("\n");

        System.out.println("Pre-order Traversal:");
        bt.preOrder(bt.root);
        System.out.println("\n");

        System.out.println("Post-order Traversal:");
        bt.postOrder(bt.root);
        System.out.println("\n");

        // Testing find method
        System.out.println("Find 4: " + bt.find(4)); // Should print true
        System.out.println("Find 10: " + bt.find(10)); // Should print false
    }
}
