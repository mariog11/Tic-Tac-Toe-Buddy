import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * @author Mario Gomez
 * @date Oct 29, 2018
 * The structure of a tic tac toe game object with flow of game control
 */
public class TicTacToe {
	public static final int PLAYER_X = 1;
	public static final int PLAYER_O = 2;
	
	/**
	 * @param args Command line paramaters
	 * Flow of execution for PvP game of tic tac toe
	 */
	public static void main(String[] args) {
		char[][] board = new char[3][3];
		Scanner keyboard = new Scanner(System.in);
		
		//Prompt for players' full names and get the names
		System.out.print("Enter PLAYER 1 full name: ");
		String P1_name = keyboard.nextLine();
		System.out.print("Enter PLAYER 2 full name: ");
		String P2_name = keyboard.nextLine();
		
		int player = PLAYER_X; //player X (the 1st player) plays first
		boolean done = false;
		
		while (!done) {
			System.out.println();
			printBoard(board);
			//Prompt for current player's turn
			String current_player = (player == PLAYER_X) ? P1_name : P2_name;
			System.out.println(current_player + "'s turn");
			int row = getRow(keyboard);
			int col = getCol(keyboard);
			
			if (!isLegalMove(board, row, col)) {
				System.out.println("Invalid move, try again");
			} else {
				placeMarker(board, row, col, player);
				if (playerWins(board, player)) {
					// Current player wins
					// Show current player's name and winning message
					printBoard(board);
					System.out.println(current_player + " WINS! CONGRATULATIONS");
					done = true;
				} else if (isDraw(board)) {
					// Game is a draw
					printBoard(board);
					System.out.println("It's a draw!");
					done = true;
				} else {
					// Switch player
					player = (player == PLAYER_X) ? PLAYER_O : PLAYER_X;
				}
			}
			
		} 
		
		System.out.println("Thanks for playing!");
	}
	

	/**
	 * @param board A 2d array of characters representing current state of tic tac toe board
	 * Prints the tic tac toe in a visually appealing and readable manner
	 */
	public static void printBoard(char[][] board) {
		System.out.println("  1   2   3");
		for(int row = 0; row < 3; row++) {
			System.out.print(row + 1 + " ");
			for(int col = 0; col < 3; col++){
				if(board[row][col] != 'X' && board[row][col] != 'O') {
					System.out.print(" ");
				} else {
					System.out.print(board[row][col]);
				}
				if(col < 2) 
				{System.out.print(" | ");}
			}
			if(row < 2) 
			{System.out.print("\n  -   -   -\n");}
		}
		System.out.print("\n");
	}
	
	/**
	 * @param keyboard Scanner object for retrieving keyboard input from user
	 * @return the row number the user chooses
	 */
	public static int getRow(Scanner keyboard) {
		boolean invalid_input = false;
		int row_num = -1;
		
		do {
			System.out.print("Enter a row number: ");
			try{
				row_num = keyboard.nextInt();
				invalid_input = false;
			} catch(InputMismatchException ime){
		    	System.out.println("Your input is invalid, please try again");
		    	keyboard.next();
		        invalid_input = true;
			}
		}while(invalid_input);
		
		return row_num;
	}
	
	/**
	 * @param keyboard Scanner object for retrieving keyboard input from user
	 * @return the column number the user chooses
	 */
	public static int getCol(Scanner keyboard) {
		boolean invalid_input = false;
		int col_num = -1;
		
		do {
			System.out.print("Enter a column number: ");
			try{
				col_num = keyboard.nextInt();
				invalid_input = false;
			} catch(InputMismatchException ime){
		    	System.out.println("Your input is invalid, please try again");
		    	keyboard.next();
		        invalid_input = true;
			}
		}while(invalid_input);
		
		return col_num;
	}
	
	/**
	 * @param board A 2d array of characters representing current state of tic tac toe board
	 * @param row the row number the user chose
	 * @param col the column number the user chose
	 * @return whether the move the user chose is legal given the current board state
	 */
	public static boolean isLegalMove(char[][] board, int row, int col){
		return ((row >= 1 && row <= 3 && col >= 1 && col <= 3) && 
				(board[row-1][col-1] != 'X' || board[row-1][col-1] != 'O')
				) ? true : false;
	}
	
	/**
	 * @param board A 2d array of characters representing current state of tic tac toe board
	 * @param row row the row number the user chose
	 * @param col the column number the user chose
	 * @param player number representing the current player moving
	 */
	public static void placeMarker(char[][] board, int row, int col, int player) {
		char marker = (player == PLAYER_X) ? 'X' : 'O';
		board[row - 1][col - 1] = marker;
	}
	
	/**
	 * @param board A 2d array of characters representing current state of tic tac toe board
	 * @param player number representing the current player moving
	 * @return whether the given player won
	 */
	public static boolean playerWins(char[][] board, int player) {
		char marker = (player == PLAYER_X) ? 'X' : 'O';
		
		for(int i = 0; i < 3; i++) {
			//Horizontal check
			if(board[i][0] == marker && board[i][1] == marker && board[i][2] == marker) {
				return true;
			}
			//Vertical check
			if(board[0][i] == marker && board[1][i] == marker && board[2][i] == marker) {
				return true;
			}
		}
		
		//Diagonal check
		return ((board[0][0] == marker && board[1][1] == marker && board[2][2] == marker) 
				|| (board[0][2] == marker && board[1][1] == marker && board[2][0] == marker))
				? true : false;
	}
	
	/**
	 * @param board A 2d array of characters representing current state of tic tac toe board
	 * @return whether the game is a tie or not
	 */
	public static boolean isDraw(char[][] board) {
		boolean player_1_win = playerWins(board, 1), player_2_win = playerWins(board, 2);
		for(int row = 0; row < 3; row++) {
			for(int col = 0; col < 3; col++){
				if(board[row][col] != 'X' && board[row][col] != 'O'){
					return false;
				}
			}
		}
		return (!player_1_win && !player_2_win);
	}
}
