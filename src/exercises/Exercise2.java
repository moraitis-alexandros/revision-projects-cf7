package exercises;

/**
 * Finds the maximum sum subarray. We used recursion. With on traverse through the table arr
 * we find the global maximum. As a result the complexity is O(n).
 * We also use pointers startIndex & endIndex to show the subarray
 */

public class Exercise2 {
    static int[] arr;
    static int globalMaximum;
    static int ls;
    static int startIndex;
    static int endIndex;
    static int counter;

    public static void main(String[] args) {
        initialize();
        localMaxima(arr.length-1);
        printResults();
    }//main

    static int localMaxima(int i) {
        if (i != 0) {
            ls = localMaxima(i-1) + arr[i];
            int a = Math.max(ls, arr[i]);
            if (a > globalMaximum) {
                globalMaximum = a;
                endIndex = i;
                counter++;
            }

            if (ls < arr[i]) {
                startIndex = i;
            }
            return a ;

//            System.out.printf("Maximum between loc + arr: %d and arr[i]: %d is %d \n", ls,arr[i],a);
            //Used for testing
        } else {
//            System.out.printf("Reached zero %d \n",arr[0]); //Used for testing
            return arr[0];
        }

    }

    static  void initialize() {
        startIndex = 0;
        globalMaximum = Integer.MIN_VALUE; //A very small price
                arr = new int[]{4, -3, -2, -1, -2, -1, 5, 4, -5}; //A usual case
//        arr = new int[]{4, -3, -2, -1, -2, -1, 2, 4, -5}; //In case of two subarrays with same local maxima
//        arr = new int[]{4}; //In case of a single digit
//        arr = new int[]{-2, 1,-3, 4, -1, -2, -1, -5, 4};
//        If local maxima found in more than one subarrays (single digit or more),
//        the program does not display the value, since it does not know which array to choose.
//         However it correctly prints the Global Maximum
    }//initialize

    static  void printResults() {
        System.out.printf("Global Maximum is: %d \n",globalMaximum);
        System.out.print("The sub array is: [ ");
        for (int i = startIndex; i <= endIndex; i++) {
            System.out.print(arr[i]);
            if (i < endIndex) {
                System.out.print(", ");
            }
        }
        System.out.print("] \n");
        System.out.println("Start Index: "+startIndex);
        System.out.println("End Index: "+endIndex);
        System.out.println("Counter: "+counter);
    }//printResults

}//class
