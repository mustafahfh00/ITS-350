public class LinearSearch1 {

    public static void main(String[] args) {
        int list[] = {2, 4, 5, 20, 4};
        int l = linear(list, 2);  // Searching for the key '2'
        
        if (l < 0) {
            System.out.println("Not found");
        } else {
            System.out.println("The index is " + l);
        }
    }

    public static int linear(int list[], int key) {
        for (int i = 0; i < list.length; i++) {  // Fix the loop condition
            if (list[i] == key) {
                return i;  // Return the index where the key is found
            }
        }
        return -1;  // Return -1 if the key is not found
    }
}
