public class Stack {
    int top;
    int stack[];
    
    public Stack(int length){
        stack = new int[length];
        top=-1;
    }
    public void push(int val){
        if(!isFull()){
            top++;
            stack[top] = val;
        }
    }
    public int pop(){
        if(!isEmpty()){
            return stack[top--];
        }
        return -1;
    }
    public int peek(){ // peek at top of stack
        return stack[top]; 
    }
    public boolean isEmpty(){
        return top==-1;
    }
    public boolean isFull(){
        return top==stack.length-1;
    }
    void display(){
        
    }
    public static void main(String args[]){
        Stack st = new Stack(3);
        st.push(3);
        st.push(10);
        st.pop();
        st.pop();
        st.pop();
    }
}