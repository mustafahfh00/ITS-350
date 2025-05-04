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
        }
        
        // Get all reservations as a string (comma-separated)
        public String getReservationsString() {
            StringBuilder sb = new StringBuilder();
            BookNode current = reservationsHead;
            while (current != null) {
                sb.append(String.format("%03d", current.bookId));
                if (current.next != null) {
                    sb.append(", ");
                }
                current = current.next;
            }
            return sb.toString();
        }
    }
    
    // Custom Map-like structure for book to user mapping
    static class BookReservationMap {
        private static final int SIZE = 200;
        private String[] bookToUserMap; // Index represents book ID (1-200)
        
        public BookReservationMap() {
            bookToUserMap = new String[SIZE + 1]; // Using 1-based indexing
        }
        
        public boolean isBookReserved(int bookId) {
            return bookToUserMap[bookId] != null;
        }
        
        public String getReservingUser(int bookId) {
            return bookToUserMap[bookId];
        }
        
        public boolean reserveBook(int bookId, String userName) {
            if (isBookReserved(bookId)) {
                return false;
            }
            bookToUserMap[bookId] = userName;
            return true;
        }
        
        public boolean cancelReservation(int bookId) {
            if (!isBookReserved(bookId)) {
                return false;
            }
            bookToUserMap[bookId] = null;
            return true;
        }
        
        public int countReservedBooks() {
            int count = 0;
            for (int i = 1; i <= SIZE; i++) {
                if (bookToUserMap[i] != null) {
                    count++;
                }
            }
            return count;
        }
    }
    
    // Custom Map-like structure for user management
    static class UserMap {
        private static final int INITIAL_CAPACITY = 16;
        private User[] users;
        private int size;
        
        public UserMap() {
            users = new User[INITIAL_CAPACITY];
            size = 0;
        }
        
        private int hash(String key) {
            return Math.abs(key.hashCode()) % users.length;
        }
        
        public User get(String userName) {
            int index = hash(userName);
            while (users[index] != null) {
                if (users[index].name.equals(userName)) {
                    return users[index];
                }
                index = (index + 1) % users.length;
            }
            return null;
        }
        
        public void put(String userName, User user) {
            if (size >= users.length * 0.75) {
                resize();
            }
            
            int index = hash(userName);
            while (users[index] != null && !users[index].name.equals(userName)) {
                index = (index + 1) % users.length;
            }
            
            if (users[index] == null) {
                size++;
            }
            users[index] = user;
        }
        
        private void resize() {
            User[] oldUsers = users;
            users = new User[oldUsers.length * 2];
            size = 0;
            
            for (User user : oldUsers) {
                if (user != null) {
                    put(user.name, user);
                }
            }
        }
    }
    
    private BookReservationMap bookReservations;
    private UserMap users;
    private final int TOTAL_BOOKS = 200;
    
    public LibraryReservationSystem() {
        bookReservations = new BookReservationMap();
        users = new UserMap();
    }
    
    // Reserve a book
    public void reserveBook(String userName, int bookId) {
        // Validate book ID range
        if (bookId < 1 || bookId > TOTAL_BOOKS) {
            System.out.println("Error: Invalid book ID. Must be between 001 and 200.");
            return;
        }
        
        // Check if book is already reserved
        if (bookReservations.isBookReserved(bookId)) {
            System.out.println("Error: The book " + String.format("%03d", bookId) + " is not available.");
            return;
        }
        
        // Get or create user
        User user = users.get(userName);
        if (user == null) {
            user = new User(userName);
            users.put(userName, user);
        }
        
        // Add reservation
        if (user.addReservation(bookId)) {
            bookReservations.reserveBook(bookId, userName);
            System.out.println("Book " + String.format("%03d", bookId) + " reserved successfully for " + userName);
        }
    }
    
    // Cancel a book reservation
    public void cancelReservation(int bookId) {
        // Validate book ID range
        if (bookId < 1 || bookId > TOTAL_BOOKS) {
            System.out.println("Error: Invalid book ID. Must be between 001 and 200.");
            return;
        }
        
        if (!bookReservations.isBookReserved(bookId)) {
            System.out.println("Error: Book " + String.format("%03d", bookId) + " is not reserved.");
            return;
        }
        
        String userName = bookReservations.getReservingUser(bookId);
        User user = users.get(userName);
        
        if (user != null && user.removeReservation(bookId)) {
            bookReservations.cancelReservation(bookId);
            System.out.println("Reservation for book " + String.format("%03d", bookId) + " cancelled successfully.");
        } else {
            System.out.println("Error: Failed to cancel reservation for book " + String.format("%03d", bookId));
        }
    }
    
    // Count available books
    public void countAvailableBooks() {
        int reserved = bookReservations.countReservedBooks();
        int available = TOTAL_BOOKS - reserved;
        System.out.println("Total available books: " + available);
    }
    
    // Display all reservations
    public void displayAllReservations() {
        System.out.println("\nCurrent Reservations:");
        System.out.println("Book ID : Name");
        
        // We need to iterate through all books to maintain order
        boolean foundAny = false;
        for (int bookId = 1; bookId <= TOTAL_BOOKS; bookId++) {
            if (bookReservations.isBookReserved(bookId)) {
                foundAny = true;
                String userName = bookReservations.getReservingUser(bookId);
                User user = users.get(userName);
                if (user != null) {
                    System.out.println(String.format("%03d", bookId) + " : " + userName);
                }
            }
        }
        
        if (!foundAny) {
            System.out.println("No books are currently reserved.");
        }
    }
    
    // Display reservations grouped by user
    public void displayReservationsByUser() {
        System.out.println("\nReservations by User:");
        System.out.println("Book IDs : Name");
        
        // This is simplified - in a real custom implementation without built-in collections,
        // we would need a way to track all users, which would require additional custom data structures
        // For this example, we'll iterate through all possible books to find users
        
        boolean[] processedUsers = new boolean[201]; // Simple flag array for demo
        boolean foundAny = false;
        
        for (int bookId = 1; bookId <= TOTAL_BOOKS; bookId++) {
            if (bookReservations.isBookReserved(bookId)) {
                String userName = bookReservations.getReservingUser(bookId);
                int hash = Math.abs(userName.hashCode()) % 200;
                
                if (!processedUsers[hash]) {
                    processedUsers[hash] = true;
                    User user = users.get(userName);
                    if (user != null) {
                        foundAny = true;
                        System.out.println(user.getReservationsString() + " : " + userName);
                    }
                }
            }
        }
        
        if (!foundAny) {
            System.out.println("No books are currently reserved.");
        }
    }
    
    // Main menu
    public static void main(String[] args) {
        LibraryReservationSystem system = new LibraryReservationSystem();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\nLibrary Book Reservation System");
            System.out.println("1. Reserve a book");
            System.out.println("2. Cancel a reservation");
            System.out.println("3. Count available books");
            System.out.println("4. Display all reservations");
            System.out.println("5. Display reservations by user");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
                continue;
            }
            
            switch (choice) {
                case 1:
                    System.out.print("Enter user name: ");
                    String userName = scanner.nextLine();
                    System.out.print("Enter book ID (001-200): ");
                    try {
                        int bookId = scanner.nextInt();
                        system.reserveBook(userName, bookId);
                    } catch (Exception e) {
                        System.out.println("Invalid book ID. Please enter a number.");
                        scanner.nextLine();
                    }
                    break;
                case 2:
                    System.out.print("Enter book ID to cancel (001-200): ");
                    try {
                        int bookId = scanner.nextInt();
                        system.cancelReservation(bookId);
                    } catch (Exception e) {
                        System.out.println("Invalid book ID. Please enter a number.");
                        scanner.nextLine();
                    }
                    break;
                case 3:
                    system.countAvailableBooks();
                    break;
                case 4:
                    system.displayAllReservations();
                    break;
                case 5:
                    system.displayReservationsByUser();
                    break;
                case 6:
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}