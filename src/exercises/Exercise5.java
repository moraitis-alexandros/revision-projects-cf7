package exercises;
/**
 * A booking seat app for a  theatre. It can make bookings and cancellations and also print a
 * schema of the theatre. 4 choices are available for user: Booking (B), Cancellation (C),
 * Theatre schema Printing (P) and Exiting the app (E). The program also performs integrity checks
 * based on column and row ranges.
 */
import java.util.Scanner;


public class Exercise5 {
    static boolean[][] reservationTable;
    static String seat;
    static char col;
    static int row;
    static char action;

    public static void main(String[] args) {
        initialize();
        printTheatreschema();
        do {
            actionChoices();
            readChoice();
            applyChoices();
            eraseSeatChoice();
        } while (true);//repeats endlessly

    }//main

    /**
     * A help function that reads from a scanner. It performs checks for most input error scenarios
     * using mostly switch inside a do - while loop.
     */
    static void readChoice() {
        do {
            Scanner in = new Scanner(System.in);
            System.out.println("Give seat column and row: ");
        seat = in.nextLine();
        switch (seat.length()) {
            case 2:
                //Validate column choice
                if ((seat.charAt(0) >= 'A') && (seat.charAt(0) <= 'L') ) {
                    col = seat.charAt(0);
//                    System.out.println("Found col "+col);
                } else {
                    System.out.println("You entered invalid column choice");
                    col = '0'; //null
                }

                switch (seat.charAt(1)) {
                    case '1', '2', '3', '4', '5', '6', '7', '8', '9':
                        row = Integer.parseInt(seat.substring(1, 2));
                        break;
                    default:
                        System.out.println("You entered invalid row choice");
                }
                break;

            case 3:
                //Validate column choice
                if ((seat.charAt(0) >= 'A') && (seat.charAt(0) <= 'L') ) {
                    col = seat.charAt(0);
//                    System.out.println("Found col "+col);
                } else {
                    System.out.println("You entered invalid column choice");
                    col = '0'; //null
                }

                switch (seat.charAt(1)) {
                    case '1', '2':
                        switch (seat.charAt(2)) {
                            case '0','1', '2', '3', '4', '5', '6', '7', '8', '9':
                                row = Integer.parseInt(seat.substring(1, 3));
//                                System.out.println("Found ROW "+row);
                                break;
                            default:
                                System.out.println("You entered invalid row choice");
                                row = '0';
                        }
                        break;

                    default:
                        System.out.println("You entered invalid row choice");
                        row = '0';

                    case '3':
                        switch (seat.charAt(2)) {
                            case '0':
                                row = Integer.parseInt(seat.substring(1, 3));
//                                System.out.println("Found ROW "+row);
                                break;
                            default:
                                System.out.println("You entered invalid row choice");
                                row = '0';
                        }
            }
            break;
            default:
                System.out.println("You entered invalid choice");
            }//switch

        } while (!evaluatechoice(convertLetterToInt(col),row));
//        System.out.println("col is: " + convertLetterToInt(col));
//        System.out.println("row is: " + row);
    }//readChoice

    static void initialize() {
        reservationTable = new boolean[30][12];

        for (boolean row[] : reservationTable) {
            for (boolean el : row) {
                el = false;
            }
        }
    }//initialize

    /**
     * It prints theatre schema using ASCII encoding
     */
    static void printTheatreschema() {
        System.out.print("   ");
        for (int j = 65; j <= 76; j++) {
            System.out.print("-  " + (char) j+"  ");
        }
        System.out.print("-");
        System.out.println();
        for (int i = 0; i < 30; i++) {
            System.out.printf("%2d |  ",(i+1));
            for (int j = 0; j < 12; j++) {
                System.out.print(convertchoiceIntoInt(j,i)+ "  |  ");
            }
            System.out.println();
        }
    }

    /**
     * Converts column letter into int
     */
    static int convertLetterToInt(char col) {
        return (Character.valueOf(col) % 'A' + 1); //to the choice in base of A
    }//convertLetterToInt

    /**
     *Checks for integrity of the choice. It must be within the int range provided
     */
    static boolean evaluatechoice(int col, int row) {
        return  ((col >= 1 && col <= 12) && (row >=1 && row <= 30));
    }//evaluateChoice

    static void cancel(char col, int row) {
        if (evaluatechoice(convertLetterToInt(col), row)) {
            if (reservationTable[row - 1][convertLetterToInt(col) - 1]) {
                reservationTable[row - 1][convertLetterToInt(col) - 1] = false;
                System.out.println("Cancellation Successful!");
            } else {
                System.out.println("There is no reservation to cancel on this seat");
            }
        }
    }//book

    /**
     * Applies the user choice to reservation table
     */
    static int convertchoiceIntoInt(int col, int row) {
        return (reservationTable[row][col]) ? 1 : 0;
    }//convertTableIntoInt

    /**
     * The basic action menu
     */
    static void actionChoices() {
        do {
            Scanner in = new Scanner(System.in);
            System.out.println("Please write B for booking or C for canceling P for printing reservation schema and E for exiting the app ");
            seat = in.nextLine();

            switch (seat.length()) {
                case 1:
                    switch (seat.charAt(0)) {
                        case 'B':
                            action = 'B';
                            break;
                        case 'C':
                            action = 'C';
                            break;
                        case 'P':
                            printTheatreschema();
                            break;
                        case 'E':
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Not valid choice.");
                    }
                    break;
                default:
                    System.out.println("Not valid choice.");
            }
        } while (action != 'C' && action != 'B' && action !='E');
    }//actionBookCancel

    static void book(char col, int row) {
        if (!reservationTable[row - 1][convertLetterToInt(col) - 1]) {
            reservationTable[row - 1][convertLetterToInt(col) - 1] = true;
            System.out.println("Booking Successful!");
        } else {
            System.out.println("There is already reservation on this seat");
        }
    }//book

    /**
     * Applies booking or cancelling if possible
     */
    static void applyChoices() {
        if (action == 'B') {
            book(col,row);
        } else {
            cancel(col,row);
        }
    }//applyChoices

    /**
     * Erases the choice made by user after each iteration
     */
    static void eraseSeatChoice() {
        col = '0';
        row = 0;
    }//eraseSeatChoice

}//class
