/*
 * Project: Meeting Scheduler
 * Team Members:
 * - Mustafa Haitham, Section 1
 * Date: 17-04-2025
 */
import java.util.Scanner;

class TimeNode
{
    int time;
    TimeNode next;
    public TimeNode(int time)
    {
        this.time = time;
        this.next = null;
    }
}
public class MeetingSchedualer
{
    public static void main(String[] args)
    {
        MeetingRoom room1 = new MeetingRoom("Room1");
        MeetingRoom room2 = new MeetingRoom("Room2");
        Scanner scanner = new Scanner(System.in);
       
      
        while (true) {
            System.out.println("\nMeeting Scheduler Menu:");
            System.out.println("1. Schedule a new meeting");
            System.out.println("2. Un-schedule a meeting");
            System.out.println("3. Show first and last meeting in Room1");
            System.out.println("4. Show number of meetings in each room");
            System.out.println("5. Display all scheduled meetings in a room");
            System.out.println("6. Exit");
            

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice)
             {
                case 1:
                    System.out.print("Enter room number (1 or 2): ");
                     int roomNum = scanner.nextInt();
                     if (roomNum != 1 && roomNum != 2) {
                     System.out.println("Invalid room number! Please enter 1 or 2.");
                     break;
                     }
                    System.out.print("Enter meeting start time (HH:MM): ");
                    String time = scanner.next();
                    if (roomNum == 1) room1.scheduleMeeting(time);
                    else room2.scheduleMeeting(time);
                    break;
                case 2:
                    System.out.print("Enter room number (1 or 2): ");
                    roomNum = scanner.nextInt();
                    System.out.print("Enter meeting start time to remove (HH:MM): ");
                    time = scanner.next();
                    if (roomNum == 1) room1.unscheduleMeeting(time);
                    else room2.unscheduleMeeting(time);
                    break;
                case 3: 
                        System.out.print("Enter room number (1 or 2): ");
                        roomNum = scanner.nextInt();
                    if (roomNum != 1 && roomNum != 2)
                    {
                        System.out.println("Invalid room number! Please enter 1 or 2.");
                        break;
                    }
                        if (roomNum == 1) room1.printFirstAndLastMeetings();
                        else room2.printFirstAndLastMeetings();
                        break;
                    
                case 4:  System.out.println("Room1 meetings count: " + room1.countMeetings());
                    System.out.println("Room2 meetings count: " + room2.countMeetings());
                    break;
                case 5:
                    System.out.print("Enter room number (1 or 2): ");
                    roomNum = scanner.nextInt();
                    if (roomNum == 1) room1.displayMeetings();
                    else if (roomNum == 2) room2.displayMeetings();
                    else System.out.println("Invalid room number.");
                    break;
                case 6:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");;
            }
    }
}
}
// for all methods, space complexity is O(1)
class MeetingRoom {
    private String name;
    private TimeNode head;
    private TimeNode tail;

    public MeetingRoom(String name) {
        this.name = name;
        this.head = null;
        this.tail = null;
    }

    // Time Complexity: O(n) – traverses the list to find a valid slot and insert in order
    public void scheduleMeeting(String timeStr) {
        int time = parseTime(timeStr); 
        if (time < 420 || time > 1080) {
            System.out.println("Time is outside allowable booking hours.");
            return;
        }

        TimeNode newNode = new TimeNode(time); 

        if (head == null) {
            head = newNode;
            tail = newNode; 
            System.out.println("Meeting scheduled at " + timeStr);
            return;
        }

        TimeNode current = head;
        TimeNode prev = null;

        while (current != null) {
            int gapBefore = (prev != null) ? time - prev.time : Integer.MAX_VALUE; 
            int gapAfter = current.time - time;

            if (Math.abs(gapAfter) < 70 || (prev != null && gapBefore < 70)) {
                System.out.println("You cannot schedule a meeting at that time;");
                return;
            }

            if (time < current.time) break;

            prev = current;
            current = current.next;
        }

        if (prev == null) {
            newNode.next = head;
            head = newNode;
        } else {
            newNode.next = prev.next;
            prev.next = newNode;
        }

        
        if (newNode.next == null) tail = newNode;

        System.out.println("Meeting scheduled at " + timeStr);
    }

    // Time Complexity: O(n) – needs to find the matching node to delete
    public void unscheduleMeeting(String timeStr) {
        int time = parseTime(timeStr);
        TimeNode current = head;
        TimeNode prev = null;

        while (current != null) {
            if (current.time == time) {
                if (prev == null) head = current.next;
                else prev.next = current.next;

               
                if (current.next == null) tail = prev;

                System.out.println("Meeting at " + timeStr + " has been removed.");
                return;
            }
            prev = current;
            current = current.next;
        }
        System.out.println("No such scheduled meeting.");
    }

    // Time Complexity: O(n) – traverses all meetings to find min and max
    public void printFirstAndLastMeetings() {
        if (head == null) {
            System.out.println("No meetings scheduled in " + name); 
            return;
        }

        TimeNode first = head, last = tail;
        TimeNode current = head.next;

        while (current != null) {
            if (current.time < first.time) first = current;
            if (current.time > last.time) last = current;
            current = current.next;
        }

        System.out.println("The first meeting in " + name + " starts at: " + formatTime(first.time));
        System.out.println("The last meeting in " + name + " starts at: " + formatTime(last.time));
    }

    // Time Complexity: O(n) – counts all nodes
    public int countMeetings() {
        int count = 0;
        TimeNode current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    // Time Complexity: O(1) – string split and parse
    private int parseTime(String timeStr) {
        String[] parts = timeStr.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }

    // Time Complexity: O(1) – constant time math and formatting
    public String formatTime(int time) {
        int hour = time / 60;
        int minute = time % 60;
        return String.format("%02d:%02d", hour, minute); 
    }
    // this is an extra method
    // Time Complexity: O(n) – visits each node once
    public void displayMeetings() {
    if (head == null) {
        System.out.println("No meetings scheduled in " + name);
        return;
    }

    System.out.println("Meetings scheduled in " + name + ":");
    TimeNode current = head;
    while (current != null) {
        System.out.println("- " + formatTime(current.time));
        current = current.next;
    }
}

}


