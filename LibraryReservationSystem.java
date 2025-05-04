import java.util.Scanner;

public class LibraryReservationSystem {
    // Custom Node class for linked list implementation
    static class BookNode {
        int bookId;
        BookNode next;
        
        public BookNode(int bookId) {
            this.bookId = bookId;
            this.next = null;
        }
    }
    
    // Custom User class to store user information and reservations
    static class User {
        String name;
        BookNode reservationsHead;
        BookNode reservationsTail;
        int reservationCount;
        
        public User(String name) {
            this.name = name;
            this.reservationsHead = null;
            this.reservationsTail = null;
            this.reservationCount = 0;
        }
        
        // Add a book reservation (O(n) for duplicate check)
        public boolean addReservation(int bookId) {
            if (reservationCount >= 5) {
                System.out.println("Error: " + name + " has reached the maximum of 5 reservations.");
                return false;
            }
            
            // Check for duplicate reservation (linear search)
            if (containsBook(bookId)) {
                System.out.println("Error: " + name + " has already reserved book " + bookId);
                return false;
            }
            
            BookNode newNode = new BookNode(bookId);
            if (reservationsHead == null) {
                reservationsHead = newNode;
                reservationsTail = newNode;
            } else {
                reservationsTail.next = newNode;
                reservationsTail = newNode;
            }
            reservationCount++;
            return true;
        }
        
        // Check if book is already reserved by this user
        private boolean containsBook(int bookId) {
            BookNode current = reservationsHead;
            while (current != null) {
                if (current.bookId == bookId) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }
        
        // Remove a book reservation
        public boolean removeReservation(int bookId) {
            if (reservationsHead == null) return false;
            
            // Check if head needs to be removed
            if (reservationsHead.bookId == bookId) {
                reservationsHead = reservationsHead.next;
                if (reservationsHead == null) {
                    reservationsTail = null;
                }
                reservationCount--;
                return true;
            }
            
            // Find the node to remove
            BookNode current = reservationsHead;
            while (current.next != null) {
                if (current.next.bookId == bookId) {
                    current.next = current.next.next;
                    if (current.next == null) {
                        reservationsTail = current;
                    }
                    reservationCount--;
                    return true;
                }
                current = current.next;
            }
            return false;