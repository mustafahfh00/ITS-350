public class BinaryRecursion {

    public static void main(String[] args) {
        int[] list = {14, 15, 17, 19, 20};  // The sorted list for binary search

         // Create an instance of BinaryRecursion
        Binary1 B = new Binary1();
        // Choose a key to search for (change this key to test different cases)
        int key = 17;

        // Call the iterative find method
        int resultIterative = B.find(key, list);
        B.display(resultIterative, key, "Iterative");

        // Call the recursive binarySR method
        int resultRecursive = B.binarySR(key, list, 0, list.length - 1);
        B.display(resultRecursive, key, "Recursive");
    }

   
}
class Binary1
{
     // Iterative binary search method
    public int find(int key, int[] list) {
        int lowIndex = 0;
        int highIndex = list.length - 1;

        while (lowIndex <= highIndex) {
            int midIndex = (lowIndex + highIndex) / 2;
            if (key == list[midIndex]) {
                return midIndex;  // Return the index of the key if found
            } else if (key < list[midIndex]) {
                highIndex = midIndex - 1;
            } else {
                lowIndex = midIndex + 1;
            }
        }
        return -1;  // Return -1 if the key is not found
    }

    // Recursive binary search method
    public int binarySR(int key, int[] list, int lowIndex, int highIndex) {
        if (lowIndex <= highIndex) {
            int midIndex = (lowIndex + highIndex) / 2;
            if (key == list[midIndex])
                return midIndex;  // Return the index if found
            if (key < list[midIndex])
                return binarySR(key, list, lowIndex, midIndex - 1);  // Search in the left half
            return binarySR(key, list, midIndex + 1, highIndex);  // Search in the right half
        }
        return -1;  // Return -1 if the key is not found
    }

    // Display method to print the result
    public void display(int result, int key, String method) {
        if (result == -1) {
            System.out.println(method + " Search: Key " + key + " not found in the list.");
        } else {
            System.out.println(method + " Search: Key " + key + " found at index " + result + ".");
        }
    }
}
