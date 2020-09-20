import javax.swing.*;
import java.awt.*;

public class Grid extends JPanel {
    // attributes
    int width;
    int height;
    int row;
    int column;
    int rectangleSize = 60;
    Point[][] gameBoard;
    Point startNode = null;
    Point endNode = null;

    // point constructor
    public Grid(int w, int h) {
        /*
        Grid constructor

        @params: w: width of the canvas, h: height of the canvas
         */
        width = w;
        height = h;
        row = height / rectangleSize;
        column = width / rectangleSize;
        gameBoard = new Point[row][column];

        createGridPoints();
    }

    public void createGridPoints() {
        /*
           Create a 2d array of points
         */
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                gameBoard[i][j] = new Point(i, j);
            }
        }
    }

    public void clearGrid() {
        /*
            Clear all from the grid
         */
        for (Point[] points : gameBoard) {
            for (int j = 0; j < points.length; j++) {
                if (points[j] != startNode && points[j] != endNode && points[j].traversable()) {
                    points[j].setEmpty();
                    updateGrid();
                }
            }
        }
    }
    public void addStartNode(Point sNode) {
        /*
        Set startNode

        @param: sNode: startNode
         */
        startNode = sNode;
    }

    public void addEndNode(Point eNode) {
         /*
        Set endNode

        @param: eNode: endNode
         */
        endNode = eNode;
    }

    public Point getStartNode() {
        return startNode;
    }

    public Point getEndNode() {
        return endNode;
    }

    public void updateGrid() {
        /*
        Redraw the canvas
         */
        repaint();
    }
    // overridden from jPanel, draws game grid
    public void paintComponent(Graphics g) {
        /*
        Override the default jPanel paintComponent method. To draw the gameBoard to the canvas
         */
       super.paintComponent(g);

       for(int i=0; i<gameBoard.length; i++) {
           for (int j=0; j<gameBoard[i].length; j++) {
               // set borders to black
               g.setColor(gameBoard[i][j].getColour());

               // set fill colour
               g.fillRect(i*rectangleSize, j*rectangleSize, rectangleSize, rectangleSize);

               // draw rectangle
               g.setColor(Color.BLACK);
               g.drawRect(i*rectangleSize, j*rectangleSize, rectangleSize, rectangleSize);
           }
       }
    }
}