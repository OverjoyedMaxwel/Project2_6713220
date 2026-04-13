/*
Anun Luechaphongthip     6713253
Puvit Kitiwongpaisan     6713246
Kanapod Lamthong         6713220
Piyawat Jaroonchaikhanakit   6713240
Sawana Thiputhai             6713249
*/

package Project2_6713220;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.graph.SimpleDirectedGraph;

public class GameSolver {
    
    public GraphPath<BoardState, MoveEdge> solve(SimpleDirectedGraph<BoardState, MoveEdge> graph, BoardState initialState) {
        
        BoardState targetState = null;
        
        // 1. Find the target node (all lights off) in the graph
        for (BoardState state : graph.vertexSet()) {
            if (state.isAllOff()) {
                targetState = state;
                break;
            }
        }
        
        // 2. If the target node is not found, there is no solution
        if (targetState == null) {
            return null; 
        }
        
        // 3. Use BFS to find the shortest path (Minimum moves)
        BFSShortestPath<BoardState, MoveEdge> bfs = new BFSShortestPath<>(graph);
        return bfs.getPath(initialState, targetState);
    }
}
