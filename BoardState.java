/*
Anun Luechaphongthip         6713253
Puvit Kitiwongpaisan         6713246
Kanapod Lamthong             6713220
Piyawat Jaroonchaikhanakit   6713240
Sawana Thiputhai             6713249
*/
package Project2_6713220; 

import java.util.Arrays;

public class BoardState {
    private int n;
    private int[][] grid;
    private int brokenRow;
    private int brokenCol;

    // Constructor To create an initial state.
    public BoardState(int n, int[][] initialGrid, int brokenRow, int brokenCol) {
        this.n = n;
        this.brokenRow = brokenRow;
        this.brokenCol = brokenCol;
        this.grid = new int[n][n];
        
        // Deep copying ( necessary to prevent data corruption due to array linking)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.grid[i][j] = initialGrid[i][j];
            }
        }
    }

    // Method to simulate a button press (always returns a new BoardState to prevent graph corruption)
    public BoardState toggle(int r, int c) {
        BoardState newState = new BoardState(this.n, this.grid, this.brokenRow, this.brokenCol);

        // 1.toggle grid[r][c]
        newState.grid[r][c] = 1 - newState.grid[r][c];

        // 2. toggle neighbors
        if (r == brokenRow && c == brokenCol) {
            // Rule 1.2: If a light is faulty, switch the lights diagonally (if available).
            if (r - 1 >= 0 && c - 1 >= 0) newState.grid[r - 1][c - 1] = 1 - newState.grid[r - 1][c - 1]; // Top left
            if (r - 1 >= 0 && c + 1 < n)  newState.grid[r - 1][c + 1] = 1 - newState.grid[r - 1][c + 1]; // Top right
            if (r + 1 < n && c - 1 >= 0)  newState.grid[r + 1][c - 1] = 1 - newState.grid[r + 1][c - 1]; // Bottom left
            if (r + 1 < n && c + 1 < n)   newState.grid[r + 1][c + 1] = 1 - newState.grid[r + 1][c + 1]; // Bottom right
        } else {
            // Rule 1.1: If pressing the regular bulb, switch the "vertical and horizontal" light (if available).
            if (r - 1 >= 0) newState.grid[r - 1][c] = 1 - newState.grid[r - 1][c]; // Top
            if (r + 1 < n)  newState.grid[r + 1][c] = 1 - newState.grid[r + 1][c]; // Bottom
            if (c - 1 >= 0) newState.grid[r][c - 1] = 1 - newState.grid[r][c - 1]; // left
            if (c + 1 < n)  newState.grid[r][c + 1] = 1 - newState.grid[r][c + 1]; // Right
        }

        return newState;
    }

    // This method checks if the lights are completely off (Target state).
    public boolean isAllOff() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) return false;
            }
        }
        return true;
    }

    // check for duplicate nodes.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BoardState that = (BoardState) obj;
        return Arrays.deepEquals(this.grid, that.grid);
    }

    @Override
    public int hashCode() {
        
        return Arrays.deepHashCode(this.grid);
    }

    // Getters for max use Print
    public int[][] getGrid() { return grid; }
    public int getN() { return n; }
}