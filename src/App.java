import java.util.Random;
import java.util.Scanner;


public class App {
    private static final int EMPTY_CELL = 0;
    private static final int SIZE = 9;
    private static final int SUBGRID_SIZE = 3;
    public static int[][] board;
    public static int[][] list_0;
    public static final int iter = 10;
    public static Random random;
    public static int r;
    public static long seed = System.currentTimeMillis()+1;

    private static void generateBoard() {
        @SuppressWarnings("resource")
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to sudoku Please : choose the number you want of cells to remove from board : ");;
        r = scan.nextInt();
        // System.out.println("chose maximal Number of iteration : ");
        // iter = scan.nextInt();
        
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
        // printBoard(board);
        removeCells(r);
        // printBoard(board);
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
        random = new Random(seed);
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
        
        for (int boxRow = 0; boxRow < SUBGRID_SIZE; boxRow++) {
            for (int boxCol = 0; boxCol < SUBGRID_SIZE; boxCol++) {
                int[] subgridCounts = new int[SIZE + 1];
                for (int row = boxRow * SUBGRID_SIZE; row < (boxRow + 1) * SUBGRID_SIZE; row++) {
                    for (int col = boxCol * SUBGRID_SIZE; col < (boxCol + 1) * SUBGRID_SIZE; col++) {
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
    
    private static int [] [] generateSolutionLocal(){
        random = new Random(seed);
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

    private static int[][] getNeighbor(int[][] sol) {
        int [][] news = new int[SIZE][SIZE];
        random = new Random(seed);
        int row1, col1,row2, col2; 
        do  {
            row1 = random.nextInt(9);
            col1 = random.nextInt(9);
            row2 = random.nextInt(9);
            col2 = random.nextInt(9);
        }while(sol[row1][col1] == 0 && sol[row2][col2] == 0 || (row1 == row2 && col1 ==col2 ));
        for(int i = 0;i<SIZE ; i++){
            for(int j=0 ;j < SIZE ;j++){
                if(i==row1 && j == col1){
                    news[i][j]=sol[row2][col2];
                }else{
                    if(i==row2 && j == col2){
                        news[i][j]=sol[row1][col1];
                    }
                    else{
                        news[i][j]=sol[i][j];
                    }
                }  
            }
        }
        
        return news;
    }

    private static void solveLocal(int [][]b) {
        int i=0;
        int [][] currboard , newboard, newSol, currSol ;
        
        currSol = generateSolutionLocal();
        currboard = insertSolToBoard(currSol);
        
        System.out.println("current BOARD is :");
        printBoard(currboard);
        int newob , ob = objectiveFunction(currboard);
        
        System.out.println("Objective function of the 1st solution is : " +ob+"\n\n");
        
        do{
            newSol = getNeighbor(currSol);
            
            newboard = insertSolToBoard(newSol);
            System.out.println("new BOARD is :");
            printBoard(newboard);
            newob = objectiveFunction(newboard);
            System.out.println("Objective function of the neghbour solution is : " +newob+"\n\n");
            i++;
            if (newob < ob) {
                for (int x = 0; x < SIZE; x++) {
                    for (int y = 0; y < SIZE; y++) {
                        currboard[x][y] = newboard[x][y];
                    }
                }
                for (int x = 0; x < SIZE; x++) {
                    for (int y = 0; y < SIZE; y++) {
                        currSol[x][y] = newSol[x][y];
                    }
                }
                
                ob = newob;
            }
            }while (i<iter && ob>newob);
            
        
        System.out.println("Objective function for the iteration  last iteration is equal to " + ob);
        System.out.println("last board is");
        printBoard(currboard);
        
    }


    public static void main(String[] args) {
        App sudoku = new App();
        generateBoard();
        // int [][] t =new int[SIZE][SIZE];
        
        // System.out.println("SOlution initial");
        // t = generateSolutionLocal();
        // System.out.println("initial SOlution");
        // printBoard(t);
        // System.out.println("OF init : "+objectiveFunction(t));
        // System.out.println("neighbour Solution ");
        // t = getNeighbor(t);
        // System.out.println("neighbour OF : "+objectiveFunction(t));

        // printBoard(t);
        solveLocal(board);
    }
}
