package exercises;
/**
 * An app that creates lotto numbers. The user defines the txt file's path and also the output path.
 * If the path is not found or it is not in the appropriate format it throws IOException
 * and displays the message to the END USER "An error occurred. Check files and paths."
 * The numbers should be one in each line separately and with no separator. The numbers should also be
 * from 1 to 49. If the data are not in the appropriate format it throws IOException
 * and displays message "The file data are not in the appropriate format" for DEV. Also
 * the message propagates to main with the message to the END USER "An error occurred. Check files and paths." .
 * Then the numbers are sorted and we produce combinations of six depending on
 * some filtering (i.e. isFiltered method). The combinations are written in the path
 * provided using a printstream. We assume that the END user provided valid output path.
 * We used static variables in class in order to be accessible for any method inside it.
 * Also, we did not use ArrayList as the example provided in class rather than a table
 * with two pointers (startIndex, lastIndex)
 */

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Exercise1 {
    static String inPath;
    static String outPath;
    static int[] inputTable;
    static int startIndex;
    static int lastIndex;//show the numbers in the table that correspond to real number and not 0
    static int combinationNumber;
    static int[] row;
    //The paths provided for testing:
    //C:\Users\mypc\Desktop\testData.txt
    //C:\\Users\\mypc\\Desktop\\testDataOutput.txt

    public static void main(String[] args) {
        try  {
            initializeVariables();
            readPath();
            readFile();
            printTable();
            produceSixNumbers();
        } catch (IllegalArgumentException | IOException e) {
            System.out.println("An error occurred. Check files and paths."); //For the end user
        }

    }//main

    /**
     * Reads the in and out path from terminal using a BufferedReader
     * @throws IOException
     */
    private static void readPath() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Give input path for associated file");
            inPath = br.readLine();
            System.out.println("Give output path for file that will be created");
            outPath = br.readLine();
    }//readPath

    /**
     * Reads each number in each line (based on the description assumptions) using a buffered reader
     * If it exceeds 49 digits it stops reading. It also checks for data input format error such as
     * empty line or string instead of int throwing Illegal Argument Exception.
     * Also checks if the path does not exist throwing IOException.
     * It also sorts the table data.
     * @throws IllegalArgumentException
     * @throws IOException
     */
    private static void readFile() throws IllegalArgumentException, IOException {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(inPath));
            int lineCounter = 0; //counts the digits that have been imported into table
            //The line number increases with each digit added. If it exceeds the inputTable.length
            //it stops reading
            String line = "";
            int index = 0;
            while ((line = bf.readLine()) != null && lineCounter <= inputTable.length) {
                int num = Integer.parseInt(line); //reads the first number in string and converts into int
                if (inRange(num)) {
                    inputTable[index++] = num;
                    lineCounter++;
                }
                lastIndex = index;
            }//while

            if (digitsMoreThanSix()) {
                System.out.println("File read and checked. Proceeding...");
                sortInputTable();
                System.out.println("Data are sorted ascending. Proceeding...");
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("The file data are not in the appropriate format");
            throw e;
        } catch (IOException e) {
            System.out.println("Cannot find the paths specified");
            throw e;
        }
    }//readFile

    static void initializeVariables() {
        inputTable = new int[49];
        startIndex = 0;
        combinationNumber = 6;
        row = new int[6];
    }

    static boolean inRange(int num) {

        return num >= 1 && num <=49;
    }

    static void printTable() {
        System.out.println("You given the following digits:");
        System.out.print("[ ");
        for (int i = startIndex; i <= lastIndex; i++ ) {
            System.out.print(inputTable[i] + " ");
        }
        System.out.println("]");
    }//printTable

    /**
     * Checks if digits are more than 6.
     * @return
     */
    static boolean digitsMoreThanSix() {
        int digitCounter = 0;
        for (int i = 0; i <= inputTable.length - 1; i++) {
            if (inputTable[i] != 0) {
                digitCounter++;
            }
        }
        return (digitCounter >= 6);
    }

    static void sortInputTable() {
        Arrays.sort(inputTable);
        System.out.println("Sort Completed. Proceeding...");
        startIndex = 49 - lastIndex;
        lastIndex = 48;
    }

    /**
     * Produce the six numbersIf there is problem with the output path it catches IOException which throws
     * into main
     * @throws IOException
     */
    static void produceSixNumbers() throws IOException {
        //To lastIndex είναι η τελευταία θέση του πίνακα. Το πραγματικό length είναι lastIndex + 1
        //αφού έχει και το 0
            System.out.println("Creating lotto numbers. Please wait...");
            for (int i = startIndex; i <= lastIndex - combinationNumber + 1; i++) {
                for (int j = i + 1; j <= lastIndex - combinationNumber + 2; j++) {
                    for (int k = j + 1; k <= lastIndex - combinationNumber + 3; k++) {
                        for (int m = k + 1; m <= lastIndex - combinationNumber + 4; m++) {
                            for (int n = m + 1; n <= lastIndex - combinationNumber + 5; n++) {
                                for (int o = n + 1; o < lastIndex + 1; o++) {
                                    row[0] = inputTable[i];
                                    row[1] = inputTable[j];
                                    row[2] = inputTable[k];
                                    row[3] = inputTable[m];
                                    row[4] = inputTable[n];
                                    row[5] = inputTable[o];
                                    if (isFiltered()) {
                                        exportRowIntoTextFile();
                                    }
                                }//o
                            }//n
                        }//m

                    }//k
                }//j
            }//i
            System.out.println("Operation Completed");

    }//produce six numbers

    static boolean isFiltered() {
        return (hasMostFourEven() && hasMostThreeWithSameEnding() && hasMostTwoConsecutives()
                && hasMostFourOdds() && hasMostThreeInTheSameTen());
    }//isFiltered

    static boolean hasMostFourEven() {
        int count = 0;
        for (int el : row) {
            if (isEven(el)) {
                count++;
            }
        }
        return count <= 4;
    }

    static boolean hasMostFourOdds() {
        int count = 0;
        for (int el : row) {
            if (!isEven(el)) {
                count++;
            }
        }
        return count <= 4;
    }

    static boolean hasMostTwoConsecutives() {
        boolean flag =false;
        for (int i = 0; i < row.length - 2; i++) {
            if((row[i] == (row[i + 1] - 1)) && (row[i] == (row[i + 2] - 2))) {
                flag = true;
            }
        }
        return !flag;
    }//hasMostTwoConsecutives

    static boolean hasMostThreeSame(int[] tmp) {
        int count;
        boolean flag = false;
        for (int  i = 0; i < tmp.length; i++) {
            count = 0;
            for (int j = i; j < tmp.length; j ++) {
                if (tmp[i] == tmp[j]) {
                    count++;
                }
            }
            if (count >= 4) {
                //Ελέγχω αν το σύνολο των αριθμών είναι μεγαλύτερο ή ίσο του 4. Αν ναι,
                //ενεργοποιείται το flag. Αν το flag παραμείνει false μετά το τέλος όλων των
                //επαναλήψεων σημαίνει ότι δεν βρήκε πάνω από 4 με ίδιους λήγοντες. Συνεπώς
                //ο πίνακας περιέχει στοιχεία με το πολύ 3 ίδιους λήγοντες. Εγώ ωστόσο θέλω να
                //επιστρέψω το αντίθετο flag ως αποτέλεσμα. Δηλ. αν επιστρέφει true για
                //count >= 4 θέλω true για count <= 3. Οπότε διαμορφώνω το return
                //με !flag
                flag = true;
            }
        }
        return !flag;
    }

    static boolean hasMostThreeWithSameEnding() {
        int[] temp = new int[row.length];

        for (int i = 0; i < row.length; i++) {
            temp[i] = row[i] % 10;
        }
        return hasMostThreeSame(temp);
        }

    static boolean isEven(int el) {
        return  (el % 2 == 0);
    }//isEven

    static boolean hasMostThreeInTheSameTen() {
        int count;
        boolean flag = false;
        for (int i = 0; i < row.length; i++) {
            count = 0;
            for (int j = i + 1; j < row.length; j++) {
                if (areInTheSameTenth(row[i], row[j])) {
                    count++;
                }
            }
            if (count >= 3) { //Μπαίνει ίσο γιατί έχει και το στοιχείο που συγκρίνω
                flag = true;
            }

        }
        return  !flag;
    }//hasMoreThanThreeInTheSameTen

    static boolean areInTheSameTenth(int a, int b) {
       return getTenth(a) == getTenth(b);
    }

    static int getTenth(int a) {
        return a / 10;
    }

    /**
     * It prints to textFile using a printstream.
     * We suppose that the user provides a valid path
     */
    static void exportRowIntoTextFile() throws  IOException {
        PrintStream ps = new PrintStream(new FileOutputStream(outPath, true),
                true, StandardCharsets.UTF_8);
        ps.print(row[0] + " " + row[1] + " " +
                row[2] + " " + row[3] + " "
                + row[4] + " " + row[5] + "\n");
    }//exportRowIntoTextFile
}//class

