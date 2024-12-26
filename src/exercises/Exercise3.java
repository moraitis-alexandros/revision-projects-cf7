package exercises;
/**
 * Reads a path. It traverses a txt document and counts characters. It stores them in a 2d array
 * Then it provides frequency statistics
 * We used a FileReader to read each character in ASCII decimal. As a result the first
 * column of the table is the int that corresponds to character found (ASCII Format - decimal)
 * The second columns calculates the times the character was inside the document
 * The program does not count whitespaces/ delete, change lines exc, as a result
 * we work with ASCII from decimal 33 to 126.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Exercise3 {
    static String inPath;
    static int[][] array;
    static double[] mappedArrayFrequency;
    static int totalTextCharacters;

    public static void main(String[] args) {
        try {
            readPath();
            initialize();
            readFile();
            createDeepCopy(); //In order to calculate frequencies
            sortArray();
            printTable();
        } catch (IOException ex) {
            System.out.println("The path specified does not exist"); //for end user
        }
    }//main

//For testing
//C:\\Users\\mypc\\Desktop\\testDataExercise3.txt

    /**
     * Reads the in and out path from terminal using a BufferedReader
     */
    private static void readPath() throws IOException {
        try (
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Give input path for associated file");
            inPath = br.readLine();
        }
    }//readPath

    /**
     * Reads with file reader one character at a time and stores its int value.
     * If the input path is not specified it throws IO Exception error.
     */
    private static void readFile() throws  IOException {
        try {
            FileReader fr = new FileReader(inPath);
           int b=0;
           String ch;
           int noASCII;
            while ((b = fr.read()) != -1 ) {
                if (b>=33 && b <=127) { //We do not take into account whitespaces
                    array[b][1]++;
                    totalTextCharacters++;
                }
            }//while

        } catch (IOException e) {
            System.out.println("Cannot find the path specified"); //for dev
            throw e;
        }
    }//readFile


    static void initialize() {
        array = new int[128][2];
        mappedArrayFrequency = new double[128];
        totalTextCharacters = 0;

        for (int i =0; i<=127; i++) {
            array[i][0] = i;
            array[i][1] = 0;
        }
    }//initialize

    static void printTable() {
        for (int i = 33; i <= 126; i++) {
            //We do not want whitespaces, tabs exc, so in ascii table
            // we must begin from number 33 (decimal) and also exclude 127 (del)
            String ch = Character.toString(array[i][0]);
            System.out.printf("Character: %s appears %d times with frequency: %.2f \n",ch,array[i][1],mappedArrayFrequency[i]);
        }
    }//printTable

    static void sortArray() {
        for (int i = 33; i <= 126; i++) {
            for (int j = i; j <= 126; j++) {
                if (array[j][1] > array[i][1]) {
                    //We could also sort with the frequency. It is the same.
                    swapInArray(array[j][1] ,array[i][1]);
                    swapInArray(j,i);
                }
            }
        }
    }

    /**
     * Swaps the elements of the array and mappedArrayFrequency tables
     */
    static void swapInArray(int a, int b) {
        int tempA;
        int tempB;
        double tempC;
        tempA = array[a][0];
        array[a][0] = array[b][0];
        array[b][0] = tempA;

        tempB = array[a][1];
        array[a][1] = array[b][1];
        array[b][1] = tempB;

        tempC = mappedArrayFrequency[a];
        mappedArrayFrequency[a] = mappedArrayFrequency[b];
        mappedArrayFrequency[b] = tempC;
    }

    /**
     * Creates a deep copy to calculate frequencies
     */
    static void createDeepCopy() {
        for (int i = 0; i <= 127; i++) {
            mappedArrayFrequency[i] = array[i][1] / (double)totalTextCharacters;
        }
    }

}//class
