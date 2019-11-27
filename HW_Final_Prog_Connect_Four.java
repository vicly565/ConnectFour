import java.util.Scanner;
/**
 * This program is a connect four game. 
 * It creates a 2D char array called gameBoard with the size of 6x7 and all of the elements initialized to ' '
 * It allows two users to input their names, so they can know who's turn it is to go
 * and it shows what piece belongs to what player. 
 * Each player takes turns entering a integer between 1-7 of what column they want to place their piece in
 * and inbetween each turn it checks to see if the most recent piece played managed to connect four either
 * vertically, horizonally, diagonally forward, or diagonally backwards.
 * If the player managed to connect four in a row, the program will let the two players know which player won
 * and will ask them if they would like to play again.
 * In the scenario that it is a tie, it will let both users know the game ended in a tie
 * and will ask them if they want to play again.
 * This program assumes that the players will input which column they want their pieces in by entering a integer
 *
 * @author (Victor Ly)
 * @version (1.0)
 */
public class HW_Final_Prog_Connect_Four
{
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        char keepPlaying;
        //allows the user to play once, and will continue to reset the game as the user wants to keep playing
        do {
            //initializations and declarations
            char[][] gameBoard = createBoard();
            boolean pieceOnBoard = false;
            int place; //place is a column given by the user to determine where to place their piece.
            int placedRow; //the row the piece was placed into
            char pieceOne = 'X';
            char pieceTwo = 'O';
            boolean playerOneWin = false;
            boolean playerTwoWin = false;

            //give initial rules and get player names
            System.out.print("How to play: ");
            System.out.println("Connect four pieces in a row vertically, horizontally, or diagonally to win");
            System.out.println("Enter a name for Player One");
            String nameOne = keyboard.nextLine();
            System.out.println("Enter a name for Player Two");
            String nameTwo = keyboard.nextLine();

            //clears the board to prepare for play
            System.out.print('\u000C'); //code to clear the board in BlueJ
            printRules(pieceOne, nameOne, pieceTwo, nameTwo);
            printBoard(gameBoard);

            //while both players are not winners
            while(playerOneWin == false && playerTwoWin == false) {
                //start of player one's turn
                System.out.println(nameOne + "'s turn");
                //allows player one to place pieces in columns until they select a valid column
                do {
                    System.out.println("Enter the column you would like to drop your piece in");
                    place = keyboard.nextInt() - 1; //take the input minus one since the array's first element is at 0, and the last is 6
                    pieceOnBoard = placePiece(place, gameBoard, pieceOne);
                } while (pieceOnBoard == false);
                placedRow = rowFinder(gameBoard, pieceOne, place);

                //checking if player1 wins
                playerOneWin = checkIfWinning(gameBoard, pieceOne, placedRow, place);
                if (playerOneWin) {
                    System.out.print('\u000C');
                    System.out.println(nameOne + " wins!");
                    break;
                }

                //prepares for player two's turn
                pieceOnBoard = false; //Resets piece on board so player two can go
                System.out.print('\u000C');
                printRules(pieceOne, nameOne, pieceTwo, nameTwo);
                printBoard(gameBoard);
                //testIfPiecePlacedAndFound(gameBoard, placedRow, place, pieceOne); TEST METHOD@@@@@@@@@@@@@

                //player two's turn
                System.out.println(nameTwo + "'s turn");
                //allows player two to place pieces in columns until they select a valid column
                do {
                    System.out.println("Enter the column you would like to drop your piece in");
                    place = keyboard.nextInt() - 1; //take the input minus one since the array's first element is at 0, and the last is 6
                    pieceOnBoard = placePiece(place, gameBoard, pieceTwo);
                } while (pieceOnBoard == false);
                placedRow = rowFinder(gameBoard, pieceTwo, place);

                //checks to see if player two has won
                playerTwoWin = checkIfWinning(gameBoard, pieceTwo, placedRow, place);
                if (playerTwoWin) {
                    System.out.print('\u000C');
                    System.out.println(nameTwo + " wins!");
                    break;
                }

                //checks to see if the gameboard is full and at a tie
                if (checkForTie(gameBoard)) {
                    System.out.print('\u000C');
                    System.out.println("The game ends in a draw!");
                    break;
                }

                //prepares the game for player one to go
                pieceOnBoard = false; //resets piece on board so player one can go
                System.out.print('\u000C');
                printRules(pieceOne, nameOne, pieceTwo, nameTwo);
                printBoard(gameBoard);
                //testIfPiecePlacedAndFound(gameBoard, placedRow, place, pieceTwo); TEST METHOD@@@@@@@@@@@@@
            }

            printBoard(gameBoard); //shows the final board
            System.out.println("Play again? (y/n)");
            keyboard.nextLine(); //eats up when the player hits enter to place a piece
            keepPlaying = keyboard.nextLine().toLowerCase().charAt(0); //For if the user give something other than y or n

        } while (keepPlaying == 'y');
    }

    /**
     * This method creates a 6x7 gameboard for connect four, and initializes all of the spaces to ' '
     * 
     * @return gameBoard        a 6x7 gameboard with blank spaces. char[][]
     * 
     */
    public static char[][] createBoard() {
        char[][] gameBoard = new char[6][7]; //makes a 6x7 gameboard to play connect four on
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[row].length; col++) {
                gameBoard[row][col] = ' '; //initializes every piece to a blank
            }
        }
        return gameBoard;
    }

    /**
     * This method prints out to the console
     * how to play the game, the player names, and what the pieces of the two players look like
     * 
     * @param pieceOne           The piece for player one. char
     * @param nameOne            The name for player one. String
     * @param pieceTwo           The piece for player two. char
     * @param nameTwo            The name for player two. String
     */
    public static void printRules(char pieceOne, String nameOne, char pieceTwo, String nameTwo) { 
        System.out.print("How to play: ");
        System.out.println("Connect four pieces in a row vertically, horizontally, or diagonally to win");
        System.out.println("Pick your column by entering the numbers 1, 2, 3, 4, 5, 6, or 7");
        System.out.println("Player One Name: " + nameOne);
        System.out.println("Player One Piece: " + pieceOne);
        System.out.println("Player Two Name: " + nameTwo);
        System.out.println("Player Two Piece: " + pieceTwo);
        System.out.println("\n"); //prints a line for spacing
    }

    /**
     * This method prints out the gameboard to the console
     * 
     * @param gameBoard         The connect four gameboard. char[][]
     * 
     * Example:
     * If a char 2d array named board, was declared and initialized as a 6x7 2d array with all blank values then
     * printBoard(board);
     * 
     * will print to the console
     * " 
     * |   |   |   |   |   |   |   |
     * -----------------------------
     * |   |   |   |   |   |   |   |
     * -----------------------------
     * |   |   |   |   |   |   |   |
     * -----------------------------
     * |   |   |   |   |   |   |   |
     * -----------------------------
     * |   |   |   |   |   |   |   |
     * -----------------------------
     * |   |   |   |   |   |   |   |
     * -----------------------------
     * "
     */
    public static void printBoard(char[][] gameBoard) {
        //prints out every element in the two dimensional array
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[row].length; col++) {
                System.out.print("| " + gameBoard[row][col]+ " ");
            }
            System.out.println("|");
            //prints a line to make the board look like a grid
            for (int i = 0; i < 29; i++) {
                System.out.print("-");
            }
            System.out.println();
        }
    }

    /**
     * This method places the player piece at the next spot that isn't a player piece
     * This method does this by taking in a user inputted integer called place
     * then check's the char 2d array called gameBoard at the column place
     * and if it runs into the player piece 'X' or 'O' it will go back one row and place
     * the char player there.
     * If the user inputs an invalid column, or a full column, it will exit this method.
     * 
     * @param place            The user inputted column that the array will check. int
     * @param gameBoard        The connect four gameboard. char[][]
     * @param player           The player piece that will be placed. char
     * @return pieceOnBoard    This is if the piece was successfully placed on the board or not. 
     * 
     * Example:
     * If a char 2d array named board, was declared and initialized as a 6x7 2d array with all blank values then
     * placePiece(3, gameBoard, 'X');
     * 
     * would return
     * true
     * 
     * and
     * gameBoard[5][3] = 'X'
     * 
     */
    public static boolean placePiece(int place, char[][] gameBoard, char player) { 
        boolean pieceOnBoard = false;

        //if the user tries to input a spot on the board less than zero or more than the length
        if(place < 0 || place > gameBoard.length) {
            System.out.println("The column you picked doesn't exist. Try again!");
            return pieceOnBoard;
        }

        for(int row = 0; row < gameBoard.length + 1; row++) {
            //if it reaches the length of the gameboard, then it will place the piece on the bottom row
            if (row == gameBoard.length) {
                gameBoard[row - 1][place] = player;
                pieceOnBoard = true;
            }
            //if it runs into a player piece while going down the rows
            else if (gameBoard[row][place] == 'X' || gameBoard[row][place] == 'O'){
                //if the row isn't the top of the board, it will go down a row (up direction) and place the piece
                if (row != 0) {
                    gameBoard[row - 1][place] = player;
                    pieceOnBoard = true;
                    break;
                } else {
                    System.out.println("This column is full! Try again!");
                    pieceOnBoard = false;
                    break;
                }

            }
        }
        return pieceOnBoard; //returns if it was able to place the piece or not
    }

    /**
     * This method finds the row of the most recently placed piece.
     * This method does this by going through every row of the 2d char array gameboard
     * looking only in the column of integer piece, which is a user inputted integer
     * and if the gameboard at the row and the column piece are the same as
     * the char player, it will return the row that piece was in.
     * This method assumes that it will find a row to return.
     * 
     * @param gameBoard         The connect four gameboard. char[][]
     * @param player            The player piece. char
     * @param piece             The user inputted column. int
     * @return rowOfPiece       The row of the most recent piece dropped. int
     * 
     * Example:
     * If a char 2d array named board, was declared and initialized as a 6x7 2d array with all blank values except
     * gameBoard[5][3] = 'X'
     * 
     * then
     * int row = findRow(gameBoard, 'X', 3);
     * 
     * would return
     * row = 5;
     */
    public static int rowFinder(char[][] gameBoard, char player, int piece) {
        int rowOfPiece = 999; //initializing a return value
        //find what row the placed piece is in by finding the first occurance of the player piece in the column
        for(int row = 0; row < gameBoard.length; row++) {
            if (gameBoard[row][piece] == player){
                rowOfPiece = row;
                break;
            }
        }
        return rowOfPiece;
    }

    /**
     * This method prints and tracks where the most recent piece was placed, and what the player piece looks like
     * This is used to check that a connect four was properly found.
     * 
     * @param gameBoard         The connect four gameboard. char[][]
     * @param row               The expected row of the placed piece. int
     * @param column            The expected column of the placed piece. int
     * @param player            The player piece of the last placed piece. char
     */
    public static void testIfPiecePlacedAndFound(char[][] gameBoard, int row, int column, char player) {
        System.out.println("\n\nPRINTING TEST INFORMATION");
        printBoard(gameBoard);
        System.out.println("The latest piece was placed in: ");
        System.out.print("Row: " + row);
        System.out.print(" Column: " + column);
        System.out.println(" Player Piece: " + player);
        System.out.println("ENDING TEST INFORMATION\n\n");
    }

    /**
     * This method checks if the player has won the game by connecting four pieces horizontally.
     * This does this by looking through every piece in the board looking through every column of a row before increasing the row,
     * to see if the piece at the specified row and column is identical to the char player. 
     * If it finds a player piece it will increase the counter by one.
     * If it isn't the same it resets the counter back to zero, and continues to go
     * and look for player pieces.
     * If it finds four in a row it would return true. Otherwise it returns false.
     * 
     * @param gameBoard         The connect four gameboard. char[][]
     * @param player            The player piece. char
     * @return fourConnected    Whether or not there are four pieces connected in a row. boolean
     * 
     * Example:
     * 
     * char[][] board = {
     *     {' ', ' ', ' ', ' ', ' ', ' ', ' '}, 
     *     {' ', ' ', ' ', ' ', ' ', ' ', ' '},
     *     {' ', ' ', ' ', ' ', ' ', ' ', ' '},
     *     {' ', ' ', ' ', ' ', ' ', ' ', ' '},
     *     {' ', ' ', ' ', ' ', ' ', ' ', ' '},
     *     {'X', 'X', 'X', 'X', ' ', ' ', ' '}};
     * checkHorizontalWin(gameBoard, 'X');
     * 
     * would return true
     */
    public static boolean checkHorizontalWin(char[][] gameBoard, char player) {
        int amntConnected = 0; //the amount of pieces connnected in a row
        boolean fourConnected = false; //if four is connected in a row, this becomes true otherwise it's false.

        //checks every row in the board to see if 4 is connected horizontally
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[row].length; col++) {
                if (gameBoard[row][col] == player) {
                    amntConnected = amntConnected + 1;
                } else {
                    amntConnected = 0; //resets if it runs into a piece of the other player, or a blank spot
                }
                if (amntConnected == 4) {
                    fourConnected = true;
                    return fourConnected;
                }
            }
        }

        return fourConnected;
    }

    /**
     * This method checks if the player has won the game by connecting four pieces vertically.
     * This does this by looking through every piece in the board looking through every row of a column before increasing the column,
     * to see if the piece at the specified row and column is identical to the char player. 
     * If it finds a player piece it will increase the counter by one.
     * If it isn't the same it resets the counter back to zero, and continues to go
     * and look for player pieces.
     * If it finds four in a row it would return true. Otherwise it returns false.
     * 
     * @param gameBoard         The connect four gameboard. char[][]
     * @param player            The player piece. char
     * @return fourConnected    Whether or not there are four pieces connected in a row. boolean
     * 
     * Example:
     * 
     * char[][] board = {
     *     {' ', ' ', ' ', ' ', ' ', ' ', ' '}, 
     *     {' ', ' ', ' ', ' ', ' ', ' ', ' '},
     *     {'X', ' ', ' ', ' ', ' ', ' ', ' '},
     *     {'X', ' ', ' ', ' ', ' ', ' ', ' '},
     *     {'X', ' ', ' ', ' ', ' ', ' ', ' '},
     *     {'X', ' ', ' ', ' ', ' ', ' ', ' '}};
     * checkVerticalWin(gameBoard, 'X');
     * 
     * would return true
     */
    public static boolean checkVerticalWin(char[][] gameBoard, char player) {
        int amntConnected = 0; //the amount of pieces connnected in a row
        boolean fourConnected = false; //if four is connected in a row, this becomes true otherwise it's false.

        //checks every column in the board to see if 4 is connected vertically
        for (int col = 0; col < gameBoard[0].length; col++) {
            for (int row = 0; row < gameBoard.length; row++) {
                if (gameBoard[row][col] == player) {
                    amntConnected = amntConnected + 1;
                } else {
                    amntConnected = 0; //resets if it runs into a piece of the other player, or a blank spot
                }
                if (amntConnected == 4) {
                    fourConnected = true;
                    return fourConnected;
                }
            }
        }

        return fourConnected;
    }

    /**
     * This method checks if the player has won the game by connecting four pieces diagonally in a forward direction (/).
     * This does this by taking the spot of the recently placed piece,
     * and increases row by one, while decreasing column by one, three times
     * to see if any of the pieces in the rows beneath it diagonally forwards are the same as the char player.
     * If it finds a player piece it will increase the counter by one.
     * If it isn't the same it breaks the loop and then
     * it goes back to the initial position of the placed piece and
     * it decreases row by one, and increases column by one three times
     * to see if any of the pieces in the rows above it diagonally are the same as the player piece,
     * but it keeps the counter from below it diagonally for the scenarios where a player has the same piece on both diagonal ends.
     * If it finds a player piece it will increase the counter by one.
     * If it isn't the same it breaks the loop.
     * If it finds four in a row it would return true. Otherwise it returns false.
     * 
     * @param gameBoard         The connect four gameboard. char[][]
     * @param player            The player piece. char
     * @param row               The row of the most recent placed piece. int
     * @param col               The column of the most recent placed piece. int
     * @return fourConnected    Whether or not there are four pieces connected in a row. boolean
     * 
     * Example:
     * 
     * char[][] board = {
     *     {' ', ' ', ' ', ' ', ' ', ' ', ' '}, 
     *     {' ', ' ', ' ', ' ', ' ', ' ', ' '},
     *     {' ', ' ', ' ', 'X', ' ', ' ', ' '},
     *     {' ', ' ', 'X', 'O', ' ', ' ', ' '},
     *     {'X', 'X', 'O', 'X', ' ', ' ', ' '},
     *     {'X', 'O', 'O', 'O', ' ', ' ', ' '}};
     * checkFwdDiagonalWin(gameBoard, 'X');
     * 
     * would return true
     */
    public static boolean checkFwdDiagonalWin(char[][] gameBoard, char player, int row, int col) {
        //check forward diagonal (/)
        int fourConnected = 1; //freebie point for where the piece is

        //run this loop 3 times to check the 3 diagonal pieces below and left of the placed piece
        for (int i = 1; i < 4; i++) {
            //if statement checks to make sure there are 0 out of bounds errors
            if (row + i < 6 && col - i >= 0) {
                //checks if the board down one left one is the same as the player piece
                if (gameBoard[row+i][col-i] == player) {
                    fourConnected = fourConnected + 1;
                } else {
                    //if it runs into a piece that isn't the player piece it breaks this loop
                    break;
                }
                if (fourConnected == 4) {
                    return true;
                }
            }
        }

        //runs this loop 3 times to check the 3 diagonal pieces above and right of the placed piece
        for (int j = 1; j < 4; j++) {
            if (row - j >= 0 && col + j < 7) {
                //checks the board up one right one is the same piece
                if (gameBoard[row-j][col+j] == player) {
                    fourConnected = fourConnected + 1;
                } else {
                    //if it runs into a piece that isn't the player piece then break this loop
                    break;
                }
                if (fourConnected == 4) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method checks if the player has won the game by connecting four pieces diagonally in a backward direction (\).
     * This does this by taking the spot of the recently placed piece,
     * and increases row and column by one, three times
     * to see if any of the pieces in the rows beneath it diagonally backwards are the same as the char player.
     * If it finds a player piece it will increase the counter by one.
     * If it isn't the same it breaks the loop and then
     * it goes back to the initial position of the placed piece and
     * it decreases row and column by one, three times
     * to see if any of the pieces in the rows above it diagonally backwards are the same as the player piece,
     * but it keeps the counter from below it diagonally for the scenarios where a player has the same piece on both diagonal ends.
     * If it finds a player piece it will increase the counter by one.
     * If it isn't the same it breaks the loop.
     * If it finds four in a row it would return true. Otherwise it returns false.
     * 
     * @param gameBoard         The connect four gameboard. char[][]
     * @param player            The player piece. char
     * @param row               The row of the most recent placed piece. int
     * @param col               The column of the most recent placed piece. int
     * @return fourConnected    Whether or not there are four pieces connected in a row. boolean
     * 
     * Example:
     * 
     * char[][] board = {
     *     {' ', ' ', ' ', ' ', ' ', ' ', ' '}, 
     *     {' ', ' ', ' ', ' ', ' ', ' ', ' '},
     *     {' ', ' ', ' ', 'X', ' ', ' ', ' '},
     *     {' ', ' ', ' ', 'O', 'X', ' ', ' '},
     *     {' ', ' ', ' ', 'X', 'O', 'X', 'X'},
     *     {' ', ' ', ' ', 'O', 'O', 'O', 'X'}};
     * checkBckwdDiagonalWin(gameBoard, 'X');
     * 
     * would return true
     */
    public static boolean checkBckwdDiagonalWin(char[][] gameBoard, char player, int row, int col) {
        //check backward diagonal (\)
        int fourConnected = 1; //freebie point for where the piece is

        //run this loop 3 times to check the 3 diagonal pieces below and right of the placed piece
        for (int i = 1; i < 4; i++) {
            //if statement checks to make sure there are 0 out of bounds errors
            if (row + i < 6 && col + i < 7) {
                //checks if the board down one right one is the same as the player piece
                if (gameBoard[row+i][col+i] == player) {
                    fourConnected = fourConnected + 1;
                } else {
                    //if it runs into a piece that isn't the player piece it breaks this loop
                    break;
                }
                if (fourConnected == 4) {
                    return true;
                }
            }
        }

        //runs this loop 3 times to check the 3 diagonal pieces above and left of the placed piece
        for (int j = 1; j < 4; j++) {
            if (row - j >= 0 && col - j >= 0) {
                //checks the board up one left one is the same piece
                if (gameBoard[row-j][col-j] == player) {
                    fourConnected = fourConnected + 1;
                } else {
                    //if it runs into a piece that isn't the player piece then break this loop
                    break;
                }
                if (fourConnected == 4) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method calls all four methods that check if the piece the player has played is a winning move
     * and if any of the methods return true, then it will return the variable winning set to true. Otherwise
     * winning is returned as false.
     * 
     * @param gameBoard         The connect four gameboard. char[][]
     * @param player            The player piece it's checking for. char
     * @param placedRow         The row the most recent piece was placed in. int
     * @param place             The user inputted column that the piece is placed into. int
     * @return winning          Whether or not any of the methods was a winning check. boolean
     * 
     * Example:
     * 
     * char[][] board = {
     *     {' ', ' ', ' ', ' ', ' ', ' ', ' '}, 
     *     {' ', ' ', ' ', ' ', ' ', ' ', ' '},
     *     {' ', ' ', ' ', ' ', ' ', ' ', ' '},
     *     {' ', ' ', ' ', ' ', ' ', ' ', ' '},
     *     {' ', ' ', ' ', ' ', ' ', ' ', ' '},
     *     {'X', 'X', 'X', 'X', ' ', ' ', ' '}};
     * checkIfWinning(gameBoard, 'X', 5, 3);
     * 
     * would return true
     */
    public static boolean checkIfWinning(char[][] gameBoard, char player, int placedRow, int place) {
        boolean winning = false;
        //if any of these win conditions are met, then then return the player wins.
        if (checkHorizontalWin(gameBoard, player) || checkVerticalWin(gameBoard, player) ||
        checkFwdDiagonalWin(gameBoard, player, placedRow, place) || checkBckwdDiagonalWin(gameBoard, player, placedRow, place)){
            winning = true;
            return winning;
        }
        return winning;
    }

    /**
     * This method checks the top row of the gameboard to see if it is no longer filled with ' '
     * If it is full, then it would return true, meaning that the game is tied.
     * Otherwise it returns false because the board isn't full
     * 
     * @param gameBoard         The connect four gameboard. char[][]
     * @return tie              Whether or not the game is tied. boolean
     * 
     * Example: 
     * 
     * char[][] board = {
     *     {'X', 'O', 'X', 'O', 'X', 'O', 'X'}, 
     *     {'X', 'O', 'X', 'O', 'X', 'O', 'X'},
     *     {'O', 'X', 'O', 'X', 'O', 'X', 'O'},
     *     {'O', 'X', 'O', 'X', 'O', 'X', 'O'},
     *     {'X', 'O', 'X', 'O', 'X', 'O', 'X'},
     *     {'X', 'O', 'X', 'O', 'X', 'O', 'X'}};
     * checkForTie(gameBoard);
     * 
     * would return true
     */
    public static boolean checkForTie(char[][] gameBoard) {
        boolean tie = true;
        //checks to make sure there are 0 blanks in the top row. If there is then it returns false because it's not a tie
        for (int col = 0; col < gameBoard[0].length; col++) {
            if (gameBoard[0][col] == ' ') {
                tie = false;
                return tie;
            }
        }
        return tie;
    }
}
