import java.util.List;

public class Node {
    // attributes
    float f, g, h;
    int r, c;
    Node parent;
    List<Node> neighbours;
    boolean traversable;

    public Node(int xPoint, int yPoint) {
        /*
        Node constructor

        @params: xPoint: current row, yPoint: current column
         */
        f = 0;
        g = 0;
        h = 0;
        r = xPoint;
        c = yPoint;
    }

    public void setNeighbours(List<Node> neighbourList) {
        /*
        Set the neighbour list

        @params: neighbourList a list of nodes
         */
        neighbours = neighbourList;
    }
}