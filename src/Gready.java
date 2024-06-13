import java.util.Random;
import java.util.Scanner;

public class Gready {
    private static final int EMPTY_CELL = 0;
    private static final int SIZE = 9;
    public int[][] board;
    static double iter = 0;

    public void printBoard() {
        System.out.println(" board: ");
        System.out.println("------------------------\n");
        for (int i = 0; i < SIZE; i++) {
            if (i % 3 == 0 && i != 0)
                System.out.println("-------------------------");

            for (int j = 0; j < SIZE; j++) {
                if (j % 3 == 0 && j != 0)
                    System.out.print(" | ");
                if(board[i][j]==0)
                    System.out.print("\u001B[36m" + board[i][j] + "\u001B[0m" + " ");
                
                else
                    System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println('\n');
    }

    private void removeCells(int r) {
        Random random = new Random();
        int cellsToRemove = r; // Adjust the number of cells to remove

        while (cellsToRemove > 0) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            if (board[row][col] != EMPTY_CELL) {
                board[row][col] = EMPTY_CELL;
                cellsToRemove--;
            }
        }
    }

    private void generateBoard() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to sudoku Please : choose the number you want of cells to remove from board : ");;
        int r = scan.nextInt();
        
        board = new int[SIZE][SIZE]; 

        // board = new int[][] {
        //     {5, 3, 0, 0, 7, 0, 0, 0, 0},
        //     {6, 0, 0, 1, 9, 5, 0, 0, 0},
        //     {0, 9, 8, 0, 0, 0, 0, 6, 0},
        //     {8, 0, 0, 0, 6, 0, 0, 0, 3},
        //     {4, 0, 0, 8, 0, 3, 0, 0, 1},
        //     {7, 0, 0, 0, 2, 0, 0, 0, 6},
        //     {0, 6, 0, 0, 0, 0, 2, 8, 0},
        //     {0, 0, 0, 4, 1, 9, 0, 0, 5},
        //     {0, 0, 0, 0, 8, 0, 0, 7, 9}
        // };
        board = new int[][] {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };
        printBoard();
        removeCells(r);
        printBoard();    
        solve_greedy();
        if(isComplete()){
            printBoard();
            System.out.println("Sudoku solved using greedy algorithm :) ");
        }
        else
        System.out.println("Greedy algorithm cannot solve that sudoku");
    }

    private boolean isValidMove(int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num)
                return false;
        }

        for (int i = 0; i < SIZE; i++) {
            if (board[i][col] == num)
                return false;
        }
        int boxRow = row - row % 3;
        int boxCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[boxRow + i][boxCol + j] == num)
                    return false;
            }
        }
        return true;
    }

    public boolean isComplete() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0)
                    return false;
            }
        }
        return true;
    }
    
    private boolean solve_greedy() {
        
        // Greedy approach - fill each cell with a valid number
        for (int row = 0; row < SIZE; row++) {

            for (int col = 0; col < SIZE; col++) {

                if (board[row][col] == EMPTY_CELL) {
                    
                    iter++;
                    // If the current cell is empty, try filling it with a valid number
                    for (int num = 1; num <= SIZE; num++) {
                        // Iterate through numbers from 1 to 9
                        if (isValidMove(row, col, num)) {
                            // Check if the current number is valid for the cell
                            board[row][col] = num;
                            // If the number is valid, set it in the cell
                            
                            //print Iterations of completing board

                                System.out.print("Iteration : ");
                                System.out.println(iter);
                                printBoard();
                                
                            if (solve_greedy()) {
                                // Recursively call solve to continue solving the puzzle
                                return true;
                            }
                        }
                    }
                }
            }
        }
        // If all cells that could filled successfully, return true
        return true;
    }

    public static void main(String[] args) {
        Gready sudoku = new Gready();
        sudoku.generateBoard();
        System.out.println();
    }
}
