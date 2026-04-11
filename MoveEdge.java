/*
Anun Luechaphongthip         6713253
Puvit Kitiwongpaisan         6713246
Kanapod Lamthong             6713220
Piyawat Jaroonchaikhanakit   6713240
Sawana Thiputhai             6713249
*/

package Project2_6713220;

import org.jgrapht.graph.DefaultEdge;

public class MoveEdge extends DefaultEdge {
    private int row;
    private int col;

    // Constructor Store the coordinates of the switch press.
    public MoveEdge(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "(row " + row + ", col " + col + ")";
    }
}