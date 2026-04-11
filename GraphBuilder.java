/*
Anun Luechaphongthip         6713253
Puvit Kitiwongpaisan         6713246
Kanapod Lamthong             6713220
Piyawat Jaroonchaikhanakit   6713240
Sawana Thiputhai             6713249
*/
package Project2_6713220; 

import org.jgrapht.graph.SimpleDirectedGraph;
import java.util.LinkedList;
import java.util.Queue;

public class GraphBuilder {

    // A method for creating a graph, taking an initial state as input.
    public SimpleDirectedGraph<BoardState, MoveEdge> build(BoardState initialState) {
        
        // 1. Create an empty graph.
        SimpleDirectedGraph<BoardState, MoveEdge> graph =
                new SimpleDirectedGraph<>(MoveEdge.class);

        // 2. Queue for tree graph expansion (BFS Generation).
        Queue<BoardState> queue = new LinkedList<>();

        // Add the initial state (Garph Queue)
        graph.addVertex(initialState);
        queue.add(initialState);

        while (!queue.isEmpty()) {
            BoardState currentState = queue.poll();

            // check complete
            if (currentState.isAllOff()) {
                break; 
            }

            int n = currentState.getN();
            
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    //  (nextState)
                    BoardState nextState = currentState.toggle(r, c);

                    // check status of Board  ,add it if not have.
                    if (!graph.containsVertex(nextState)) {
                        graph.addVertex(nextState);
                        queue.add(nextState);
                    }

                    // Draw a line connecting the current board to the new board, and record  pressed button.
                    graph.addEdge(currentState, nextState, new MoveEdge(r, c));
                }
            }
        }
        
        // Complete graph max can use into BFSShortestPath 
        return graph;
    }
}