class Stack {
    private int[] stackArray;
    private int top;
    private int capacity;

    // Constructor to initialize the stack with a given capacity
    public Stack(int capacity) {
        this.capacity = capacity;
        stackArray = new int[capacity];  // Creating an array to hold the stack elements
        top = -1;  // Stack is initially empty
    }

    // Push method to add an element to the stack
    public void push(int data) {
        if (top == capacity - 1) {  // Check if stack is full
            System.out.println("Stack Overflow");
        } else {
            stackArray[++top] = data;  // Increment top and add data to the stack
        }
    }

    // Pop method to remove the top element from the stack
    public int pop() {
        if (isEmpty()) {
            System.out.println("Stack Underflow");
            return -1;  // Return -1 if stack is empty
        } else {
            return stackArray[top--];  // Return the top element and decrement top
        }
    }

    // Peek method to view the top element without removing it
    public int peek() {
        if (isEmpty()) {
            System.out.println("Stack is empty");
            return -1;  // Return -1 if stack is empty
        } else {
            return stackArray[top];  // Return the top element
        }
    }

    // Method to check if the stack is empty
    public boolean isEmpty() {
        return top == -1;  // Stack is empty if top is -1
    }

    // Method to get the size of the stack
    public int size() {
        return top + 1;  // Size is top index + 1 (since top starts from -1)
    }

    // Method to clear the stack
    public void clear() {
        top = -1;  // Reset top to -1 to empty the stack
    }

    // Method to print the elements of the stack
    public void print() {
        if (isEmpty()) {
            System.out.println("Stack is empty.");
        } else {
            for (int i = top; i >= 0; i--) {
                System.out.print(stackArray[i] + " ");
            }
            System.out.println();
        }
    }
}

public class StackDemo {

    public static void main(String[] args) {
        Stack stack = new Stack(5);  // Create a stack with a capacity of 5

        // Pushing elements to the stack
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);

        // Print the stack
        System.out.println("Stack after pushing elements:");
        stack.print();

        // Peek at the top element
        System.out.println("Top element (peek): " + stack.peek());

        // Pop an element from the stack
        System.out.println("Popped element: " + stack.pop());

        // Print the stack after popping
        System.out.println("Stack after popping an element:");
        stack.print();

        // Check if stack is empty
        System.out.println("Is the stack empty? " + stack.isEmpty());

        // Get the size of the stack
        System.out.println("Size of the stack: " + stack.size());

        // Clear the stack
        stack.clear();
        System.out.println("Stack after clearing:");
        stack.print();
    }
}
