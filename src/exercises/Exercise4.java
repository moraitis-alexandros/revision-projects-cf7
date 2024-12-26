package exercises;
/**
 * Tic - Tac - Toe Game. Includes checks for choice overlapping, and invalid choices. Also displays
 * the game table after each choice.The player enters a VALID INTEGER (from 1 to 9) that is not already
 * occupied and then the program place player's choice in game table.
 */

import java.util.Scanner;

public class Exercise4 {
    static char[][] gameTable;
    static boolean playerOneTurn; //if false is player 2 turn
    static boolean endGame;
    static char playerOneMark;
    static char playerTwoMark;
    static int totalMoves;
    static boolean winFlag;

    public static void main(String[] args) {
        initialize();
        printGameTable();
        while (!endGame && totalMoves <= 8 ) {
            System.out.println(printPlayerTurn() + "makes move.");
               makeMove();
               printGameTable();
               if (evaluateMove()) {
                   System.out.println(printPlayerTurn() + "WINS");
                   winFlag = true;
                   break;
               }
               playerOneTurn = changeTurn();
               totalMoves++;
           }//while

        if (totalMoves > 8 && !winFlag) {
            System.out.println("Nobody Wins");
        }

    }//main

    /**
     * Initializes variables and populates gameTable array
     */
    static void initialize() {
        gameTable = new char[3][3];
        playerOneTurn = true;
        endGame = false;
        playerOneMark = 'o';
        playerTwoMark = 'x';
        totalMoves = 0;
        winFlag = false;
        int counter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameTable[i][j] = (char) (49 + counter);
                counter++;
            }
        }
    }//initialize


    static void printGameTable() {
        System.out.printf(" %c | %c | %c \n", gameTable[0][0], gameTable[0][1], gameTable[0][2]);
        System.out.print("---|---|--- \n");
        System.out.printf(" %c | %c | %c \n", gameTable[1][0], gameTable[1][1], gameTable[1][2]);
        System.out.print("---|---|--- \n");
        System.out.printf(" %c | %c | %c \n", gameTable[2][0], gameTable[2][1], gameTable[2][2]);
    }

    /**
     * Reads move using scanner. Performs checks if the entered integer is between 1 and 9 with function verifyMove.
     * Also checks if the integer place is already taken from previous choice with function isOverlapping.
     * With hasNextInt, it checks if the choice is not an integer. It returns the valid choice otherwise it prints
     * the appropriate error message.
     */
    static int readMove() {
        Scanner in = new Scanner(System.in);
        int move = -1;
        boolean valid = false;

        while (!valid) {
            System.out.print("Enter your move: ");
            if (in.hasNextInt() ) {
                move = in.nextInt();
                if (verifyMove(move)) {
                   if (!isOverlapping(move)) {
                       valid = true;
                   } else {
                       System.out.println("You entered a choice in a position that already a choice exists!");
                   }

                } else {
                    System.out.println("Invalid input. Please enter an integer.");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                in.next(); // clear
            }
        }
        return move;
    }//read move

    /**
     * A help function that prints player turn.
     */
    static String printPlayerTurn() {
        String player;
        if (playerOneTurn) {
            player = "Player 1 (o) ";
        } else {
            player = "Player 2 (x) ";
        }
        return player;
    }//printPlayerTurn

    static boolean verifyMove(int a) {
        return  (a >= 1 && a<=9);
    }

    /**
     * It reads player move as int. Then it transforms the int into an array format using the transformIntoRow &
     * transformIntoColumn. Then it associates the move with the corresponding player mark.
     */
    static void makeMove() {
        int move = readMove();
        int row = transformIntoRow(move);
        int column = transformIntoColumn(move);
        if (playerOneTurn) {
            gameTable[row][column] =  playerOneMark;
        } else {
            gameTable[row][column] =  playerTwoMark;
        }
    }//makeMove

    static int transformIntoRow(int a) {
        if (a <= 3) {
            return 0;
        } else if (a <= 6) {
            return 1;
        } else {
            return 2;
        }
    }//transformIntoRow

    static int transformIntoColumn(int a) {
        return switch (a) {
            case 1, 4, 7 -> 0;
            case 2, 5, 8 -> 1;
            default -> 2;
        };
    }//transformIntoColumn

    /**
     * It changer the player turn flag
     */
    static boolean changeTurn() {
        return !playerOneTurn;
    }
    /**
     * Checks if it makes three in row, diagonal, column using the appropriate help functions
     */
    static boolean evaluateMove() {
        return checkDiagonial()
                || checkHorizonal() || checkVertical();
    }

    static boolean checkDiagonial() {
        return
                (gameTable[0][0] == gameTable[1][1] && gameTable[0][0] == gameTable[2][2])
                || (gameTable[0][2] == gameTable[1][1] && gameTable[0][2] == gameTable[2][0]);
    }//checkDiagonial

    static boolean checkHorizonal() {
        return
                (gameTable[0][0] == gameTable[0][1] && gameTable[0][0] == gameTable[0][2])
                        || (gameTable[1][0] == gameTable[1][1] && gameTable[1][0] == gameTable[1][2])
                        ||  (gameTable[2][0] == gameTable[2][1] && gameTable[2][0] == gameTable[2][2]);
    }//checkHorizonal

    static boolean checkVertical() {
        return
                (gameTable[0][0] == gameTable[1][0] && gameTable[0][0] == gameTable[2][0])
                        || (gameTable[0][1] == gameTable[1][1] && gameTable[0][1] == gameTable[2][1])
                        ||  (gameTable[0][2] == gameTable[1][2] && gameTable[0][2] == gameTable[2][2]);
    }//checkVertical

    static boolean isOverlapping(int move) {
        int row = transformIntoRow(move);
        int col = transformIntoColumn(move);
        return ((gameTable[row][col] == 'x') || (gameTable[row][col] == 'o'));
    }
}//class
