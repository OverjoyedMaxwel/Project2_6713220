/*
Anun Luechaphongthip     6713253
Puvit Kitiwongpaisan     6713246
Kanapod Lamthong         6713220
Piyawat Jaroonchaikhanakit   6713240
Sawana Thiputhai             6713249
*/
package Project2_6713220;



import java.util.List;
import java.util.Scanner;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.SimpleDirectedGraph;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Loop the program to allow continuous play without restarting
        while (true) {
            try {
                System.out.println("--------------------------------------------------");
                System.out.print("Enter number of rows for square grid = ");
                int n = Integer.parseInt(scanner.nextLine().trim());
                
                if (n < 2) {
                    System.out.println("N must be at least 2. Please try again.");
                    continue;
                }

                System.out.print("Enter initial states (" + (n * n) + " bits, left to right, line by line) = ");
                String bits = scanner.nextLine().trim();
                
                // Handle errors if the user inputs incorrect bit length or non-binary characters
                if (bits.length() != n * n || !bits.matches("[01]+")) {
                    System.out.println("Invalid input. Please enter exactly " + (n * n) + " bits (0 or 1).");
                    continue;
                }

                // Convert String to 2D Array
                int[][] initialGrid = new int[n][n];
                int index = 0;
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        initialGrid[i][j] = bits.charAt(index++) - '0';
                    }
                }

                // Print the initial state before asking for the broken light
                // Create a temporary state with no broken light (-1, -1) just for printing
                BoardState tempInitialState = new BoardState(n, initialGrid, -1, -1);
                System.out.println();
                printBoard(tempInitialState, bits);

                System.out.print("Set broken light (Y/N)? [Type 'Y' for Yes, any other key for No] :");
                String brokenStr = scanner.nextLine().trim().toLowerCase();
                int brokenRow = -1;
                int brokenCol = -1;
                
                if (brokenStr.equals("y")) {
                    System.out.print("Enter row of broken light (0-" + (n - 1) + ") = ");
                    brokenRow = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter col of broken light (0-" + (n - 1) + ") = ");
                    brokenCol = Integer.parseInt(scanner.nextLine().trim());
                    
                    // Handle out-of-bounds broken light coordinates
                    if (brokenRow < 0 || brokenRow >= n || brokenCol < 0 || brokenCol >= n) {
                        System.out.println("Invalid broken light coordinates. Please try again.");
                        continue;
                    }
                }

                // Start the graph building process with the actual broken light coordinates
                BoardState initialState = new BoardState(n, initialGrid, brokenRow, brokenCol);
                
                // Send to GraphBuilder to create the graph
                GraphBuilder builder = new GraphBuilder();
                SimpleDirectedGraph<BoardState, MoveEdge> graph = builder.build(initialState);

                // Send the graph to GameSolver to find the shortest path
                GameSolver solver = new GameSolver();
                GraphPath<BoardState, MoveEdge> path = solver.solve(graph, initialState);

                // Check the result
                if (path == null) {
                    System.out.println("\nNo solution !!\n");
                } else {
                    List<BoardState> states = path.getVertexList();
                    List<MoveEdge> edges = path.getEdgeList();
                    
                    System.out.println("\n" + edges.size() + " moves to turn off all lights\n");
                    
                    // Trace the nodes and edges to print each step
                    for (int i = 0; i < edges.size(); i++) {
                        MoveEdge edge = edges.get(i);
                        BoardState prevState = states.get(i);
                        BoardState currState = states.get(i + 1);
                        
                        int r = edge.getRow();
                        int c = edge.getCol();
                        
                        // Check the previous state of the pressed light to print the correct action
                        String action = (prevState.getGrid()[r][c] == 1) ? "turn off" : "turn on";
                        
                        System.out.println(">>> Move " + (i + 1) + " : " + action + " row " + r + ", col " + c);
                        printBoard(currState, getBitsString(currState));
                    }
                }

                System.out.print("Continue playing? (Y/N) [Type 'Y' for Yes, any other key to Exit] :");
                if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    System.out.println("Exiting game...");
                    break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid format. Please enter numbers only.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }
    
    // Helper Method 1: Convert the board back to a bit string
    private static String getBitsString(BoardState state) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : state.getGrid()) {
            for (int val : row) {
                sb.append(val);
            }
        }
        return sb.toString();
    }

    // Helper Method 2: Format the board to match the Demo output
    private static void printBoard(BoardState state, String bits) {
        int n = state.getN();
        int[][] grid = state.getGrid();
        int bRow = state.getBrokenRow();
        int bCol = state.getBrokenCol();

        System.out.println("States in bits = " + bits);
        
        // Print column headers
        System.out.print("      ");
        for (int c = 0; c < n; c++) {
            System.out.printf("| col %d ", c);
        }
        System.out.println();
        
        // Print each row with light values
        for (int r = 0; r < n; r++) {
            System.out.printf("row %d ", r);
            for (int c = 0; c < n; c++) {
                // Check if it is a broken light position. If yes, add 'x'
                String brokenMarker = (r == bRow && c == bCol) ? "x" : " ";
                System.out.printf("|   %d%s  ", grid[r][c], brokenMarker);
            }
            System.out.println();
        }
        System.out.println();
    }
}
