import java.util.Scanner;

/**
 * Library Book Reservation System - ITS350 Assignment 2
 * 
 * Implements all requirements from the assignment specification:
 * 1. Book reservation with user tracking (Req. 1)
 * 2. Cancellation of reservations (Req. 2)
 * 3. Available books count (Req. 3)
 * 4. Display of all reservations grouped by user (Req. 4)
 * 
 * Data Structures Used (as per assignment constraints):
 * - Binary Tree (primary focus) for user management
 * - Hash Table (array implementation) for O(1) book access
 * - Doubly-Linked List to maintain reservation order
 */
// reserveBook()	            Time Complexity O(log n)	Space Complexity O(1)
// cancelReservation()	        Time Complexity O(1)	    Space Complexity O(1)
// getAvailableBooksCount()	    Time Complexity O(1)	    Space Complexity O(1)
// displayReservations()	    Time Complexity O(n)	    Space Complexity O(1)
// run()	                    Time Complexity O(n) 	    Space Complexity O(1) per loop
// User Management (BST)	    Time ComplexityO(log n)     Space Complexity O(1)

class LibrarySystem {
    // Constants matching assignment requirements
    private static final int TOTAL_BOOKS = 200;       // Book IDs 001-200 (Req. 1)
    private static final int MAX_BOOKS_PER_USER = 5;  // Max 5 books per user (Req. 1)

    // Requirement 1: Track book availability (Array as simple hash table)
    private final String[] bookReservations = new String[TOTAL_BOOKS + 1]; // index 1-200
    
    // Requirement 1 & 4: Binary tree for user tracking (primary data structure focus)
    private static class UserNode {
        String name;                // User name
        int reservationCount;       // Tracking books per user (max 5)
        UserNode left, right;       // Binary tree structure
        
        UserNode(String name) {
            this.name = name;
            this.reservationCount = 1;  // Initialize with first reservation
        }
    }
    private UserNode userTree;  // Root of user binary tree
    
    // Requirement 4: Maintain reservation order (doubly-linked list)
    private static class ReservationNode {
        final int bookId;       // Book ID (001-200)
        final String name;       // User who reserved it
        ReservationNode next;    // Next reservation
        ReservationNode prev;    // Previous reservation (for O(1) removal)
        
        ReservationNode(int bookId, String name) {
            this.bookId = bookId;
            this.name = name;
        }
    }
    private ReservationNode reservationHead, reservationTail;  // List head/tail
    
    // Requirement 2: O(1) cancellation support (hash table mapping)
    private final ReservationNode[] bookToNodeMap = new ReservationNode[TOTAL_BOOKS + 1];
    private int totalReserved = 0;  // For Requirement 3 (available books count)

    // ================ MAIN OPERATIONS ================ //

    /**
     * Reserve a book - Implements Requirement 1
     * @param bookId (001-200)
     * @param name User making reservation
     * Checks:
     * - Valid book ID (1-200)
     * - Book availability
     * - User reservation limit (5 books)
     */
    public void reserveBook(int bookId, String name) {
        // Validate book ID range (Req. 1 note about 1-200 range)
        if (!isValidBookId(bookId)) {
            System.out.println("Error: Invalid book ID");
            return;
        }
        
        // Check book availability (Req. 1: "The book is not available")
        if (bookReservations[bookId] != null) {
            System.out.println("Error: Book " + bookId + " already reserved by " + bookReservations[bookId]);
            return;
        }
        
        // Check 5-book limit per user (Req. 1)
        int userCount = getUserReservationCount(name);
        if (userCount >= MAX_BOOKS_PER_USER) {
            System.out.println("Error: " + name + " has reached the 5-book limit");
            return;
        }
        
        // Update all data structures
        bookReservations[bookId] = name;          // Mark book as reserved
        userTree = updateUserCount(name, 1);      // Update user count in binary tree
        addReservationNode(bookId, name);         // Add to ordered list
        totalReserved++;                          // Update available count
        
        System.out.println("Success: Book " + bookId + " reserved for " + name);
    }

    /**
     * Cancel reservation - Implements Requirement 2
     * @param bookId ID of book to cancel
     * Example from assignment:
     * Before: 015, 087, 120, 155, 190
     * After cancelling 155: 015, 087, 120, 190
     */
    public void cancelReservation(int bookId) {
        // Validate input (Req. 2: "delete a book reservation by providing its book ID")
        if (!isValidBookId(bookId) || bookReservations[bookId] == null) {
            System.out.println("Error: Invalid book ID or not reserved");
            return;
        }
        
        String name = bookReservations[bookId];
        bookReservations[bookId] = null;      // Free the book
        userTree = updateUserCount(name, -1); // Update user count
        removeReservationNode(bookId);        // Remove from ordered list (O(1))
        totalReserved--;                      // Update available count
        
        System.out.println("Success: Reservation for book " + bookId + " cancelled");
    }

    /**
     * Get available books count - Implements Requirement 3
     * @return Number of available books (200 - reserved)
     * Example from assignment:
     * Reserved: 015, 087, 120, 155, 190 → Available: 195
     */
    public int getAvailableBooksCount() {
        return TOTAL_BOOKS - totalReserved;  // Simple calculation (Req. 3)
    }

    /**
     * Display all reservations - Implements Requirement 4
     * Output format matches assignment example:
     * Book ID : Name
     * 015, 101 : Alice
     * 087 : Bob
     * 120, 122, 125 : Charlie
     * ...
     * Maintains reservation order as required
     */
    public void displayReservations() {
        if (reservationHead == null) {
            System.out.println("No books currently reserved");
            return;
        }
        
        System.out.println("\nCurrent Reservations (Book ID : Name)");
        System.out.println("----------------------------------");
        
        // Traverse in reservation order (Req. 4: maintain order)
        ReservationNode current = reservationHead;
        while (current != null) {
            System.out.print(current.bookId);
            
            // Group consecutive reservations by same user (Req. 4 format)
            ReservationNode next = current.next;
            while (next != null && next.name.equals(current.name)) {
                System.out.print(", " + next.bookId);
                next = next.next;
            }
            
            System.out.println(" : " + current.name);
            current = next;
        }
    }

    // ================ HELPER METHODS ================ //

    /**
     * Validate book ID is in 1-200 range (Req. 1 constraint)
     */
    private boolean isValidBookId(int bookId) {
        return bookId >= 1 && bookId <= TOTAL_BOOKS;
    }

    /**
     * Add reservation to both linked list and hash table
     * Maintains order for Requirement 4 display
     */
    private void addReservationNode(int bookId, String name) {
        ReservationNode node = new ReservationNode(bookId, name);
        
        // Add to doubly-linked list (maintain order)
        if (reservationTail == null) {
            reservationHead = reservationTail = node;
        } else {
            node.prev = reservationTail;
            reservationTail.next = node;
            reservationTail = node;
        }
        
        // Add to hash table for O(1) access (Req. 2 optimization)
        bookToNodeMap[bookId] = node;
    }

    /**
     * Remove reservation from data structures (O(1) operation)
     * Uses hash table for direct access + doubly-linked list for order maintenance
     */
    private void removeReservationNode(int bookId) {
        ReservationNode node = bookToNodeMap[bookId];
        if (node == null) return;
        
        // Update linked list neighbors
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
        
        bookToNodeMap[bookId] = null;  // Clear from hash table
    }

    // ================ BINARY TREE OPERATIONS ================ //
    // (Primary data structure focus per assignment requirements)

    /**
     * Get user's current reservation count
     * Uses binary search tree for O(log n) access
     */
    private int getUserReservationCount(String name) {
        UserNode node = findUserNode(name);
        return node != null ? node.reservationCount : 0;
    }

    /**
     * Binary tree search for user node
     */
    private UserNode findUserNode(String name) {
        UserNode current = userTree;
        while (current != null) {
            int cmp = name.compareTo(current.name);
            if (cmp == 0) return current;
            current = cmp < 0 ? current.left : current.right;
        }
        return null;
    }

    /**
     * Update user's reservation count in binary tree
     * Handles both increments and decrements
     */
    private UserNode updateUserCount(String name, int delta) {
        if (userTree == null) {
            return new UserNode(name);
        }
        
        // Binary tree traversal
        UserNode parent = null, current = userTree;
        while (current != null) {
            int cmp = name.compareTo(current.name);
            if (cmp == 0) {
                current.reservationCount += delta;
                if (current.reservationCount <= 0) {
                    return deleteUserNode(userTree, name);  // Remove user if no reservations
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

    /**
     * Delete user node from binary tree
     * Uses recursive approach as allowed by constraints
     */
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

    /**
     * Find minimum node in binary subtree
     */
    private UserNode findMin(UserNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // ================ USER INTERFACE ================ //

    /**
     * Main menu system providing options for all requirements
     * 1. Reserve book (Req. 1)
     * 2. Cancel reservation (Req. 2)
     * 3. View available books (Req. 3)
     * 4. View all reservations (Req. 4)
     * 5. Exit
     */
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
    
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear bad input
                continue;
            }
    
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
    
            switch (choice) {
                case 1:
                    System.out.print("Enter book ID (1-200): ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid book ID. Please enter a number between 1 and 200.");
                        scanner.nextLine();
                        break;
                    }
                    int bookId = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    reserveBook(bookId, name);
                    break;
                case 2:
                    System.out.print("Enter book ID to cancel: ");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid book ID. Please enter a valid number.");
                        scanner.nextLine();
                        break;
                    }
                    int cancelId = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    cancelReservation(cancelId);
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