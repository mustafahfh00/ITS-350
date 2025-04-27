public class Stack {

    String Stack[];
    int top = -1;
    int capasity;
    int size;

    public Stack(int capasity) {
        this.capasity = capasity;

        this.Stack = new String[capasity];

    }

    public boolean isempty() {
        if (top == -1) {

            return true;

        } else {
            return false;
        }
    }

    public void push(String value) {
        if (isempty()) {
            top++;
            Stack[top] = value;

            size++;

        } else if (size == capasity) {
            System.out.println("Stack is full");
            return;

        } else {
            top++;
            Stack[top] = value;
            size++;
        }

    }

    public void pop() {
        if (isempty()) {
            System.out.println("is empty");
            return;

        } else {
            top--;
            size--;
        }
    }

    public void peak() {
        if (isempty()) {
            System.out.println("is empty");
            return;

        } else {
            System.out.println(Stack[top]);
        }
    }

    public void display() {
        if (isempty()) {
            System.out.println("is empty");
            return;

        } else {
            for (int i = 0; i < size; i++) {
                System.out.println(Stack[i]);
            }
        }
    }
}
