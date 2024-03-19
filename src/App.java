import java.util.Random;
import java.util.Scanner;

public class App {
    private static final int EMPTY_CELL = 0;
    private static final int SIZE = 9;
    private static  int cellsToRemove=(9*9)/2;
    public int[][] board;
     
    private void generateBoard() {
        board = new int[SIZE][SIZE]; // Initialize the board with zeros (empty cells)
            // You can also manually specify an initial Sudoku puzzle here if you want
            // For example:
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
            
        //Random random = new Random();
        //solve(0, 0, random.nextInt(9) + 1);
    }

    private boolean solve(int row, int col, int num) {
        if (row == SIZE - 1 && col == SIZE)
            return true;

        if (col == SIZE) {
            row++;
            col = 0;
        }

        if (board[row][col] != 0)
            return solve(row, col + 1, num);

        for (int i = 1; i <= SIZE; i++) {
            if (isValidMove(row, col, i)) {
                board[row][col] = i;
                if (solve(row, col + 1, i))
                    return true;
                board[row][col] = 0;
            }
        }
        return false;
    }

    private boolean isValidMove(int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num)
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

    public void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            if (i % 3 == 0 && i != 0)
                System.out.println("---------------------");

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

    public boolean isValid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] != 0 && !isValidMove(i, j, board[i][j]))
                    return false;
            }
        }
        return true;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        boolean test = false; 
        while (!test||!isValid()) {
            System.out.println("Current Sudoku Board:");
            printBoard();

            System.out.print("Enter row (1-9): ");
            int row = scanner.nextInt() - 1;
            System.out.print("Enter column (1-9): ");
            int col = scanner.nextInt() - 1;
            System.out.print("Enter value (1-9): ");
            int num = scanner.nextInt();

            if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || num < 1 || num > 9) {
                System.out.println("Invalid input. Please try again.");
                continue;
            }

            if (!isValidMove(row, col, num)) {
                System.out.println("Invalid move. Please try again.");
                continue;
            }

            board[row][col] = num;
            if(this.isComplete()==true)
                break;
        }
    }
        public void play_G() {
            Scanner scanner = new Scanner(System.in);
            boolean test = false; 
            while (!test||!isValid()) {
                System.out.println("Current Sudoku Board:");
                printBoard();
                solve();
                test=isComplete();
                if (test) {
                    break;
                }
            }
        System.out.println("Congratulations! You solved the Sudoku puzzle.");
        scanner.close();
    }
    private void removeCells() {
        Random random = new Random();
        //int cellsToRemove = 1; // Adjust the number of cells to remove

        while (cellsToRemove > 0) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            if (board[row][col] != EMPTY_CELL) {
                board[row][col] = EMPTY_CELL;
                cellsToRemove--;
            }
        }
    }

    private boolean solve() {
        // Greedy approach - fill each cell with a valid number
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == EMPTY_CELL) {
                    // If the current cell is empty, try filling it with a valid number
                    for (int num = 1; num <= SIZE; num++) {
                        // Iterate through numbers from 1 to 9
                        if (isValidMove(row, col, num)) {
                            // Check if the current number is valid for the cell
                            board[row][col] = num;
                            // If the number is valid, set it in the cell
                            if (solve()) {
                                // Recursively call solve to continue solving the puzzle
                                return true;
                            }
                            // If the recursive call returns true, puzzle is solved, return true
                            // Otherwise, backtrack by resetting the cell value and try the next number
                            board[row][col] = EMPTY_CELL; // Undo the placement
                        }
                    }
                    // If no valid number found for this cell, return false
                    return false;
                }
            }
        }
        // If all cells are filled successfully, return true
        return true;
    }
    

    public static void main(String[] args) {
        App sudoku = new App();
        sudoku.generateBoard();
        sudoku.printBoard();
        System.out.println();
        sudoku.removeCells();
        sudoku.printBoard();
        System.out.println();
        sudoku.play_G();
        sudoku.printBoard();
        
    }
}
