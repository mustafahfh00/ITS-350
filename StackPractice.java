class Stack
{
    private int capacity;
    private int top;
    private int [] StackArray;

    public Stack(int capacity)
    {
        this.capacity = capacity;
        this.top = -1;
        this.StackArray = new int[capacity];
    }
    
    public void push(int data) {
        if (top == capacity - 1) { // Check if stack is full
            System.out.println("Stack Overflow");
        } else {
            StackArray[++top] = data; // Increment top and add data to the stack
        }
    }
    public int pop()
    {
        if(isEmpty())
        {
             System.out.println("Stack Underflow");
            return -1;
        }
        else{
            return StackArray[top--];

        }
    }
    public boolean isEmpty() {
        return top == -1;  // Stack is empty if top is -1
    }
    
    public void print() {
        if (isEmpty()) {
            System.out.println("Stack is empty.");
        } else {
            for (int i = top; i >= 0; i--) {
                System.out.print(StackArray[i] + " ");
            }
            System.out.println();
        }
    }
}
public class StackPractice 
{
   public static void main(String[] args) {
    Stack stack  = new Stack(5);
    stack.push(2);
    stack.push(4);
     stack.pop();
     stack.print();
   }
   
}
