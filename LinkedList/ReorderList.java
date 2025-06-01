class Node {
    int data;
    Node next;
    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class ReorderList {
    public void reorderList(Node head) {
        if (head == null || head.next == null) {
            return;
        }
        
        // Find the middle of the list
        Node slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // Reverse the second half of the list
        Node second = slow.next;
        slow.next = null;
        Node prev = null;
        while (second != null) {
            Node tmp = second.next;
            second.next = prev;
            prev = second;
            second = tmp;
        }
        
        // Merge the two halves
        Node first = head;
        second = prev;
        while (second != null) {
            Node tmp1 = first.next, tmp2 = second.next;
            first.next = second;
            second.next = tmp1;
            first = tmp1;
            second = tmp2;
        }
    }
}