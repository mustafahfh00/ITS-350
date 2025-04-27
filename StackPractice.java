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
    public void push(int data)
    {
        if (top == capacity -1)
        {System.out.println("the stack overloaded");}
        
        else{StackArray[++top]= data;}
       
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
}
public class StackPractice 
{
   public static void main(String[] args) {
    Stack stack  = new Stack(5);
     stack.pop();
   }
}
