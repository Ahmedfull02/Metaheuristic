import java.util.Arrays;
import java.util.Random;

public class SudokuHarmonySearch {
    private static final Random random = new Random();

    public static void printGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("-".repeat(21));
            }
            for (int j = 0; j < grid[i].length; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print("| ");
                }
                System.out.print(grid[i][j] != 0 ? grid[i][j] + " " : ". ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static int objectiveFunction(int[][] grid) {
        int n = 9;
        int totalViolations = 0;

        // Check row violations
        for (int row = 0; row < n; row++) {
            int[] rowCounts = new int[n + 1];
            for (int col = 0; col < n; col++) {
                int value = grid[row][col];
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
        for (int col = 0; col < n; col++) {
            int[] colCounts = new int[n + 1];
            for (int row = 0; row < n; row++) {
                int value = grid[row][col];
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
                int[] subgridCounts = new int[n + 1];
                for (int row = boxRow * subgridSize; row < (boxRow + 1) * subgridSize; row++) {
                    for (int col = boxCol * subgridSize; col < (boxCol + 1) * subgridSize; col++) {
                        int value = grid[row][col];
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

    public static int[][] initializeHarmonyMemory(int[][] sudokuGrid) {
        int[][] grid = new int[9][9];
        for (int i = 0; i < 9; i++) {
            grid[i] = Arrays.copyOf(sudokuGrid[i], 9);
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0) {
                    grid[i][j] = random.nextInt(9) + 1;
                }
            }
        }
        System.out.println("Initial Harmony Memory:");
        printGrid(grid);
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

    public static int[][] harmonySearchSudoku(int[][] sudokuGrid, int maxIterations, double HMCR, double PAR) {
        int[][] HM = initializeHarmonyMemory(sudokuGrid);

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            int[][] newHarmony = new int[9][9];
            for (int i = 0; i < 9; i++) {
                newHarmony[i] = Arrays.copyOf(sudokuGrid[i], 9);
            }
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (sudokuGrid[i][j] == 0) {
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

            System.out.printf("New Harmony (Iteration %d):%n", iteration + 1);
            printGrid(newHarmony);

            if (objectiveFunction(newHarmony) == 0) {
                System.out.println("Found a valid Sudoku solution:");
                return newHarmony;
            }

            HM = newHarmony;
        }

        System.out.println("Best solution found:");
        return HM;
    }

    public static void main(String[] args) {
        int[][] initialSudokuGrid = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        int[][] solvedSudoku = harmonySearchSudoku(initialSudokuGrid, 1000, 0.9, 0.3);

        System.out.println("Solved Sudoku:");
        printGrid(solvedSudoku);
    }
}
