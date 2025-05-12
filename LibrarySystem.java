import java.util.Scanner;

/**
 * Optimized Library Book Reservation System
 * - O(1) cancellation using hash table
 * - Binary tree for user tracking
 * - Maintains reservation order with doubly-linked list
 */
class LibrarySystem {
    private static final int TOTAL_BOOKS = 200;
    private static final int MAX_BOOKS_PER_USER = 5;

    // O(1) book availability tracking
    private final String[] bookReservations = new String[TOTAL_BOOKS + 1]; // index 1-200
    
    // Binary tree for user reservation counts
    private static class UserNode {
        String name;
        int reservationCount;
        UserNode left, right;
        
        UserNode(String name) {
            this.name = name;
            this.reservationCount = 1;
        }
    }
    private UserNode userTree;
    
    // Doubly-linked list for maintaining reservation order with O(1) removal
    private static class ReservationNode {
        final int bookId;
        final String name;
        ReservationNode next;
        ReservationNode prev;
        
        ReservationNode(int bookId, String name) {
            this.bookId = bookId;
            this.name = name;
        }
    }
    private ReservationNode reservationHead, reservationTail;
    
    // Hash table for O(1) cancellation (maps bookId -> ReservationNode)
    private final ReservationNode[] bookToNodeMap = new ReservationNode[TOTAL_BOOKS + 1];
    private int totalReserved = 0;

    // Main operations ==========================================

    /**
     * Reserve a book (O(log n) for user count check + O(1) for book availability)
     */
    public void reserveBook(int bookId, String name) {
        if (!isValidBookId(bookId)) {
            System.out.println("Error: Invalid book ID");
            return;
        }
        
        if (bookReservations[bookId] != null) {
            System.out.println("Error: Book " + bookId + " already reserved by " + bookReservations[bookId]);
            return;
        }
        
        int userCount = getUserReservationCount(name);
        if (userCount >= MAX_BOOKS_PER_USER) {
            System.out.println("Error: " + name + " has reached the 5-book limit");
            return;
        }
        
        bookReservations[bookId] = name;
        userTree = updateUserCount(name, 1);
        addReservationNode(bookId, name);
        totalReserved++;
        
        System.out.println("Success: Book " + bookId + " reserved for " + name);
    }

    /**
     * Cancel a reservation (now O(1) with hash table)
     */
    public void cancelReservation(int bookId) {
        if (!isValidBookId(bookId) || bookReservations[bookId] == null) {
            System.out.println("Error: Invalid book ID or not reserved");
            return;
        }
        
        String name = bookReservations[bookId];
        bookReservations[bookId] = null;
        userTree = updateUserCount(name, -1);
        removeReservationNode(bookId);
        totalReserved--;
        
        System.out.println("Success: Reservation for book " + bookId + " cancelled");
    }

    public int getAvailableBooksCount() {
        return TOTAL_BOOKS - totalReserved;
    }

    public void displayReservations() {
        if (reservationHead == null) {
            System.out.println("No books currently reserved");
            return;
        }
        
        System.out.println("\nCurrent Reservations (Book ID : Name)");
        System.out.println("----------------------------------");
        
        ReservationNode current = reservationHead;
        while (current != null) {
            System.out.print(current.bookId);
            
            // Group consecutive reservations by same user
            ReservationNode next = current.next;
            while (next != null && next.name.equals(current.name)) {
                System.out.print(", " + next.bookId);
                next = next.next;
            }
            
            System.out.println(" : " + current.name);
            current = next;
        }
    }

    // Optimized Helper Methods =================================

    private boolean isValidBookId(int bookId) {
        return bookId >= 1 && bookId <= TOTAL_BOOKS;
    }

    private void addReservationNode(int bookId, String name) {
        ReservationNode node = new ReservationNode(bookId, name);
        
        // Add to doubly-linked list
        if (reservationTail == null) {
            reservationHead = reservationTail = node;
        } else {
            node.prev = reservationTail;
            reservationTail.next = node;
            reservationTail = node;
        }
        
        // Add to hash table
        bookToNodeMap[bookId] = node;
    }

    private void removeReservationNode(int bookId) {
        ReservationNode node = bookToNodeMap[bookId];
        if (node == null) return;
        
        // Update neighbors
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            reservationHead = node.next;
        }
        
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            reservationTail = node.prev;
        }
        
        // Clear from hash table
        bookToNodeMap[bookId] = null;
    }

    // Binary Tree Operations (unchanged) =======================

    private int getUserReservationCount(String name) {
        UserNode node = findUserNode(name);
        return node != null ? node.reservationCount : 0;
    }

    private UserNode findUserNode(String name) {
        UserNode current = userTree;
        while (current != null) {
            int cmp = name.compareTo(current.name);
            if (cmp == 0) return current;
            current = cmp < 0 ? current.left : current.right;
        }
        return null;
    }

    private UserNode updateUserCount(String name, int delta) {
        if (userTree == null) {
            return new UserNode(name);
        }
        
        UserNode parent = null, current = userTree;
        while (current != null) {
            int cmp = name.compareTo(current.name);
            if (cmp == 0) {
                current.reservationCount += delta;
                if (current.reservationCount <= 0) {
                    return deleteUserNode(userTree, name);
                }
                return userTree;
            }
            
            parent = current;
            current = cmp < 0 ? current.left : current.right;
        }
        
        // Add new user if not found
        UserNode newNode = new UserNode(name);
        if (parent != null) {
            if (name.compareTo(parent.name) < 0) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
        }
        return userTree;
    }

    private UserNode deleteUserNode(UserNode root, String name) {
        if (root == null) return null;
        
        int cmp = name.compareTo(root.name);
        if (cmp < 0) {
            root.left = deleteUserNode(root.left, name);
        } else if (cmp > 0) {
            root.right = deleteUserNode(root.right, name);
        } else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            
            UserNode min = findMin(root.right);
            root.name = min.name;
            root.reservationCount = min.reservationCount;
            root.right = deleteUserNode(root.right, min.name);
        }
        return root;
    }

    private UserNode findMin(UserNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // User Interface (unchanged) ===============================

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nLibrary Reservation System");
            System.out.println("1. Reserve a book");
            System.out.println("2. Cancel reservation");
            System.out.println("3. View available books");
            System.out.println("4. View all reservations");
            System.out.println("5. Exit");
            System.out.print("Select option: ");
            
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter book ID (1-200): ");
                    int bookId = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    reserveBook(bookId, name);
                    break;
                case 2:
                    System.out.print("Enter book ID to cancel: ");
                    cancelReservation(scanner.nextInt());
                    break;
                case 3:
                    System.out.println("Available books: " + getAvailableBooksCount());
                    break;
                case 4:
                    displayReservations();
                    break;
                case 5:
                    System.out.println("Exiting system...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    public static void main(String[] args) {
        new LibrarySystem().run();
    }
}