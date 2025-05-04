public class Binary {

    public static void main(String[] args) {
        int[] list = {14, 15, 17, 19, 20};

        
        int key = 17;  // Example: we search for 17

        int result = search.find(key, list);
        search.display(result, key);
    }

    public int find(int key, int[] list) {
        int lowIndex = 0;
        int highIndex = list.length - 1;

        while (lowIndex <= highIndex) {
            int midIndex = (lowIndex + highIndex) / 2;
            if (key == list[midIndex]) {
                return midIndex; // return index, not key value
            } else if (key < list[midIndex]) {
                highIndex = midIndex - 1;
            } else {
                lowIndex = midIndex + 1;
            }
        }
        return -1;
    }

    public void display(int result, int key) {
        if (result == -1) {
            System.out.println("Key " + key + " not found in the list.");
        } else {
            System.out.println("Key " + key + " found at index " + result + ".");
        }
    }
}
