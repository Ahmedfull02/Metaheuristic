import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.random.*;

import javax.sound.midi.Track;

public class App {
    private static final int EMPTY_CELL = 0;
    private static final int SIZE = 9;
    private static final int SUBGRID_SIZE = 3;
    public int[][] board;
    static double iter = 0;

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
        // printBoard();    
        solveLocal();
        if(isComplete()){
            printBoard();
            System.out.println("Sudoku solved using greedy algorithm :) ");
        }
        else
        System.out.println("Greedy algorithm cannot solve that sudoku");
    }

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
    
    private void solveLocal() {
        Random rand = new Random();

        int [] [] currSol = generateSolution();
        int currCountRep = countRep(currSol);// countRep is function to count the repetitive numbers 
                                             // withi a column ,row or subgrid
        for (int iter = 0 ; iter < 100 ; iter++){
            int [] [] neighbor = getNeighbor(currCountRep);// getNeighbor is function to get next neighbor
            int neighborCountRep = countRep(neighbor);

            if(neighborCountRep < currCountRep){
                currCountRep = neighborCountRep;
                currSol = neighbor;
            }
            if(currCountRep == 0){
                System.out.println("Sudoku Solved :) : ");
                System.out.println("---------------------------");
                break;
            }

        }
        insertSolToBoard(currSol);
    }

    private int[][] getNeighbor(int currCountRep) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNeighbor'");
    }

    private int countRep(int[][] currSol) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'countRep'");
    }

    private int [] [] generateSolution(){
        int [] [] sol = new int [SIZE] [SIZE];
        insertSolToBoard(sol);
        Random rand = new Random();
        return sol;


    }

    private void insertSolToBoard(int [][] sol){
        for (int i = 0 ; i < SIZE ; i++){
            for (int j = 0 ; j < SIZE ; i++){
                if(this.board[i][j]==0){
                    board[i][j] = sol[i][j];
                }
            }
        }
    } 

    private int [] acceptedNumbers(int r,int c){
        boolean [] possible = new boolean[SIZE+1];
        int startR = (r / SUBGRID_SIZE * SUBGRID_SIZE);
        int startC = (c / SUBGRID_SIZE * SUBGRID_SIZE);
        int countNum = 0;

        Arrays.fill(possible , true);
        
        //rows 
        for (int i = 0 ; i < SIZE ; i++){
            int n = board[r] [i];
            if (n != 0)
                possible[n] = false; 
        }

        //column
        for (int i = 0 ; i < SIZE ; i++){
            int n = board[i] [c];
            if (n != 0)
                possible[n] = false; 
        }

        //subgrid

        for (int i = startR; i < startR + SUBGRID_SIZE; i++) {
            for (int j = startC; j < startC + SUBGRID_SIZE; j++) {
                int n = board[i][j];
                if (n != 0) 
                    possible[n] = false;
            }
        }
        //count accepted numbers
        for (int i = 1; i <= SIZE; i++) {
            if (possible[i]) 
                countNum++;
        }
        //array of accepted numbers
        int[] accepted = new int[countNum];
        int index = 0;
        for(int i = 1 ; i < SIZE ; i++){
            if (possible[i])
                accepted[index++] = i;
        }
        
        return accepted;
    }


    public static void main(String[] args) {
        App sudoku = new App();
        sudoku.generateBoard();
        System.out.println();
    }
}
