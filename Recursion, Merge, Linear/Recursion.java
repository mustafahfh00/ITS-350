public class Recursion {
    public static void main(String[] args) {
        //triangleN(3);
        //System.out.println(starString(1));
        //System.out.println(starString(2));
        //System.out.println(starString(2));
        // System.out.println(starString(3));
        printStars(5);
        countDown(2);
        System.out.println(parenthesize("Ahmed", 5));
    }

    public static void countDown(int i) {
        if (i == 0) {
            System.out.println(i);
        } else {
            countDown(i - 1);
            System.out.println(i);
        }
    }
    public static String parenthesize(String name, int i){
        if(i==0){
            return name;
        }
        else{
            return  "(" + parenthesize(name, i-1 ) + ")";
        }
    }

   /* static public int triangleN(int n) {

        System.out.println("Hi");
        if (n == 1)
            return 1;  // Base case
        else
            return n + triangleN(n - 1);  // Recursive case
    }*/

    public static void printStars(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print("\n*");
        }
        System.out.println();
    }
    public static String starString (int n){
        if(n==0){
            return "*";
        }else{
            return starString(n-1) + starString(n-1);
        }
    }
}