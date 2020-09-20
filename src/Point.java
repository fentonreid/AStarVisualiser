import java.awt.Color;

public class Point {
    // attributes:
    int row;
    int column;
    Color colour;


    public Point(int r, int c) {
        /*
        Point constructor
         */
        row = r;
        column = c;
        colour = new Color(255,255,255);
    }

    // getters and setters
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Color getColour() {
        return colour;
    }

    public void setVisited() {
        this.colour = new Color(0, 255, 255);
    }

    public void setStart() { // start node will be blue
        this.colour = new Color(0, 0, 225);
    }

    public void setEnd() { // end node will be red
        this.colour = new Color(255, 0, 0);
    }

    public void setWall() {
        this.colour = new Color(0,0,0);
    }

    public void setPath() {
        this.colour = new Color(0, 255,0);
    }

    public void setEmpty() {
        this.colour = new Color(255, 255,255);
    }

    public boolean traversable() {
        return (!colour.equals(new Color(0,0,0)));
    }
}