import java.util.Random;
import java.util.Scanner;

public class Harmony {
    private static long seed = System.currentTimeMillis();
    private static Random random = new Random(seed);
    private static final int EMPTY_CELL = 0;
    private static final int SIZE = 9;
    public int[][] board;
    static double iter ;
    static int [][] list_0 = new int [SIZE][SIZE];
    int r;


    public static void printBoard(int [][] board) {
        System.out.println(" board: ");
        System.out.println("------------------------\n");
        for (int i = 0; i < SIZE; i++) {
            if (i % 3 == 0 && i != 0)
                System.out.println("-------------------------");

            for (int j = 0; j < SIZE; j++) {
                if (j % 3 == 0 && j != 0)
                    System.out.print(" | ");
                if(board[i][j] == 0 || list_0[i][j] == 1)
                    System.out.print("\u001B[31m" + board[i][j] + "\u001B[0m" + " ");
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
        @SuppressWarnings("resource")
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to sudoku Please : choose the number you want of cells to remove from the board : ");;
        r = scan.nextInt();
        System.out.println("Wich iterations  you want see : (chose 100)");;
        iter = scan.nextInt();

        // board = new int[][] {
        //     {5, 3, 8, 0, 7, 0, 0, 0, 0},
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
        printBoard(board);
        removeCells(r);
        printBoard(board);
        for (int i = 0;i < SIZE;i++){
            for (int j = 0;j < SIZE;j++){
                if (this.board[i][j] == 0)
                    list_0[i][j]=1;
            }
        }
    }

    public static int objectiveFunction(int[][] board) {
        int totalViolations = 0;

        // Check row violations
        for (int row = 0; row < SIZE; row++) {
            int[] rowCounts = new int[SIZE + 1];
            for (int col = 0; col < SIZE; col++) {
                int value = board[row][col];
                if (value != 0) {
                    rowCounts[value]++;
                }
            }
            for (int count : rowCounts) {
                if (count > 1) {
                    totalViolations += (count - 1);
                }
            }
        }

        // Check column violations
        for (int col = 0; col < SIZE; col++) {
            int[] colCounts = new int[SIZE + 1];
            for (int row = 0; row < SIZE; row++) {
                int value = board[row][col];
                if (value != 0) {
                    colCounts[value]++;
                }
            }
            for (int count : colCounts) {
                if (count > 1) {
                    totalViolations += (count - 1);
                }
            }
        }

        // Check subgrid violations
        int subgridSize = 3;
        for (int boxRow = 0; boxRow < subgridSize; boxRow++) {
            for (int boxCol = 0; boxCol < subgridSize; boxCol++) {
                int[] subgridCounts = new int[SIZE + 1];
                for (int row = boxRow * subgridSize; row < (boxRow + 1) * subgridSize; row++) {
                    for (int col = boxCol * subgridSize; col < (boxCol + 1) * subgridSize; col++) {
                        int value = board[row][col];
                        if (value != 0) {
                            subgridCounts[value]++;
                        }
                    }
                }
                for (int count : subgridCounts) {
                    if (count > 1) {
                        totalViolations += (count - 1);
                    }
                }
            }
        }

        return totalViolations;
    }

    public static int[][] initializeHarmonyMemory(int[][] board) {
        int[][] grid = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) 
                grid[i][j] = board[i][j];
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0) {
                    grid[i][j] = random.nextInt(9) + 1;
                }
            }
        }
        System.out.println("Initial Harmony Memory:");
        printBoard(grid);
        return grid;
    }

    public static int chooseValueFromHM(int[][] HM, int row, int col) {
        return HM[row][col];
    }

    public static int adjustValue(int value) {
        return value; // Here we keep it simple, no adjustment
    }

    public static int randomValue() {
        
        return random.nextInt(9) + 1;
    }

    public static int[][] harmonySearchSudoku(int[][] board, int maxIterations, double HMCR, double PAR) {
        int[][] HM = initializeHarmonyMemory(board);

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            int[][] newHarmony = new int[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) 
                    newHarmony[i][j] = board[i][j];
            }
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (board[i][j] == 0) {
                        int value;
                        if (random.nextDouble() < HMCR) {
                            value = chooseValueFromHM(HM, i, j);
                            if (random.nextDouble() < PAR) {
                                value = adjustValue(value);
                            }
                        } else {
                            value = randomValue();
                        }
                        newHarmony[i][j] = value;
                    }
                }
            }
            if(iteration % iter == 0){
                System.out.printf("New Harmony (Iteration %d):%n", iteration + 1);
                System.out.println("Objective function is : "+ objectiveFunction(newHarmony)+"\n");
                printBoard(newHarmony);
            }

            if (objectiveFunction(newHarmony) == 0) {
                System.out.printf("Found a valid Sudoku solution: in iteration %d",iteration+1 );
                return newHarmony;
                }

            HM = newHarmony;
        }
        System.out.println("Best solution found Has objective function equal to :"+objectiveFunction(HM) + " is : ");
        return HM;
    }

    public static void main(String[] args) {
        Harmony sudoku = new Harmony();
        int [][] b = new int [SIZE][SIZE];
        sudoku.generateBoard();
        b = sudoku.board;
        printBoard(b);
        int [][] sol = new int [SIZE][SIZE];
        sol = harmonySearchSudoku(b, 1000000, 0.3,0.1);
        printBoard(sol);
    }
}
