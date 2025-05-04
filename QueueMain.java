class Queue {   // Removed 'public'
    int rear;
    int front;
    int[] queue;
    int count;

    public Queue(int size) {
        queue = new int[size];
        rear = front = 0;
        count = 0;
    }

    public void enQueue(int item) {
        if (isFull()) {
            System.out.println("Queue is Full! Cannot add " + item);
            return;
        }
        queue[rear] = item;
        rear = (rear + 1) % queue.length;
        count++;
        System.out.println("Enqueued: " + item);
        maith();
    }

    public int deQueue() {
        if (isEmpty()) {
            System.out.println("Queue is Empty! Cannot dequeue.");
            return -1;
        }
        int data = queue[front];
        queue[front] = -1; 
        front = (front + 1) % queue.length;
        count--;
        System.out.println("Dequeued: " + data);
        maith();
        return data;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {
        return count == queue.length;
    }

    public void maith() {
        System.out.print("Queue State: [ ");
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] != -1) {
                System.out.print(queue[i] + " ");
            } else {
                System.out.print("_ ");
            }
        }
        System.out.println("]");
    }
}

public class QueueMain {  // Only QueueMain is public
    public static void main(String[] args) {
        Queue q = new Queue(3);

        q.enQueue(2);    
        q.enQueue(3); 
        q.deQueue();   
        q.enQueue(12);   
        q.enQueue(19);   

        q.deQueue();     
        q.enQueue(19);   
        q.maith();
    }
}
