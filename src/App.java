import java.util.Random;
import java.util.Scanner;
// import java.util.random.*;


public class App {
    private static final int EMPTY_CELL = 0;
    private static final int SIZE = 9;
    private static final int SUBGRID_SIZE = 3;
    public static int[][] board;
    public static int[][] list_0;
    public static int iter;
    // public static Random random;
    public static int r;
    public static long seed = System.currentTimeMillis();

    private static void generateBoard() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to sudoku Please : choose the number you want of cells to remove from board : ");;
        r = scan.nextInt();
        System.out.println("chose maximal Number of iteration : ");
        iter = scan.nextInt();
        
        list_0 = new int[SIZE][SIZE]; 

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
        printBoard(board);
        removeCells(r);
        printBoard(board);
        for (int i = 0;i < SIZE;i++){
            for (int j = 0;j < SIZE;j++){
                if (board[i][j] == 0)
                    list_0[i][j]=1;
            }
        }
        // printBoard();            
    }

    public static void printBoard(int [][] board) {
        System.out.println(" board: ");
        System.out.println("------------------------\n");
        for (int i = 0; i < SIZE; i++) {
            if (i % 3 == 0 && i != 0)
                System.out.println("-------------------------");

            for (int j = 0; j < SIZE; j++) {
                if (j % 3 == 0 && j != 0)
                    System.out.print(" | ");
                if(board[i][j]==0 || list_0[i][j] == 1)
                    System.out.print("\u001B[31m" + board[i][j] + "\u001B[0m" + " ");
                
                else
                    System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println('\n');
    }

    private static void removeCells(int r) {
        Random random = new Random(seed);
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

    
    public boolean isComplete() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0)
                    return false;
            }
        }
        return true;
    }
    
    private static int [][] swap(int [][] board){
        int [][] s = new int[SIZE][SIZE];
        Random random = new Random(seed);
        int row1 = random.nextInt(9);
        int col1 = random.nextInt(9);
        int row2 = random.nextInt(9);
        int col2 = random.nextInt(9);
        while (list_0[row1][col1] == 0 && list_0[row2][col2] == 0 && (row1 == row2 || col1 ==col2)) {
            row1 = random.nextInt(9);
            col1 = random.nextInt(9);
            row2 = random.nextInt(9);
            col2 = random.nextInt(9);
        }
        int t = list_0[row1][col1];
        list_0[row1][col1] = list_0[row2][col2];
        list_0[row2][col2] = t;
        return board;
    }

    private static void solveLocal() {
        int i=0;
        int [] [] currSol = new int[SIZE][SIZE];
        currSol = generateSolutionLocal();
        printBoard(currSol);
        currSol = insertSolToBoard(currSol);
        printBoard(currSol);
        System.out.println("Objective function of this solution is : " +objectiveFunction(board));
        int OB = objectiveFunction(board);
        while (i<iter && OB!=0) {
            currSol=swap(list_0);
            System.err.println("Objective function is equal to ");
            printBoard(board);
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

    private int[][] getNeighbor(int currCountRep) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNeighbor'");
    }

    private int countRep(int[][] currSol) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'countRep'");
    }

    private static int [] [] generateSolutionLocal(){
        Random random = new Random();
        int [][] sol = new int[SIZE][SIZE];

        for(int i = 0 ; i < SIZE ; i++){
            for(int j = 0 ; j < SIZE ; j++){
                if(list_0[i][j]==1)
                    sol[i][j] = random.nextInt(9)+1;            
            }
        }
        return sol;
    }

    private static int [][] insertSolToBoard(int [][] sol){
        int [][] s= new int[SIZE][SIZE];
        for (int i = 0 ; i < SIZE ; i++){
            for (int j = 0 ; j < SIZE ; j++){
                if(board[i][j]==0){
                    s[i][j] = sol[i][j];
                }
                else 
                    s[i][j] = board[i][j];
            }
        }
        return s;
    } 


    public static void main(String[] args) {
        App sudoku = new App();
        generateBoard();
        int [][] t =new int[SIZE][SIZE];
        
        System.out.println("SOlution initial");
        t = generateSolutionLocal();
        System.out.println("initial SOlution");
        printBoard(t);
        System.out.println("swapped Solution ");
        t = swap(t);
        printBoard(t);
    }
}
