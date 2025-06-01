public class Queue {
    int items[];
    int front, rear, count;
    
    public Queue(int size){
        items = new int[size];
    }
    boolean isFull(){
        return (count == items.length);
    }
    boolean isEmpty(){
        return (count == 0);
    }
    void enQueue(int val){
        if(!isFull()){
            items[rear] = val;
            rear = (rear+1)%items.length;
            count++;
            return;
        }
        System.out.println("is Full");
        
    }
    int deQueue(){
        if(count>0){
            int item = items[front];
            items[front] = 0;
            front = (front+1)%items.length;
            count--;
            return item;
        }
        return -1;
    }
    void display(){
        for(int i=0; i<items.length; i++){
            System.out.print(items[i] + "  ");
        }
        System.out.println();
    }
    public static void main(String args[]){
        Queue queue = new Queue(5);
        queue.enQueue(10);
        queue.enQueue(12);
        queue.enQueue(14);
        queue.enQueue(16);
        queue.enQueue(18);
        System.out.println(queue.deQueue()+ " is deleted");
        System.out.println(queue.deQueue()+ " is deleted");
        System.out.println(queue.deQueue()+ " is deleted");
        queue.enQueue(20);
        System.out.println(queue.deQueue()+ " is deleted");
        queue.display();
    }
}