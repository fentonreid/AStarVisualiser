import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class main {

    private static Grid grid = new Grid(600, 600);
    private static int clickCounter = 0;
    private static boolean running = false;

    public static void main(String[] args) {
        JFrame frame = new JFrame("A* Visualisation Algorithm");
        frame.setSize(617, 640);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(grid);
        grid.addMouseListener(new mouseListener());
        frame.addKeyListener(new keyListener());
    }

    static class mouseListener extends MouseAdapter {
        // if left mouse is clicked
        public void mouseClicked(MouseEvent e) {
            /*
            If the mouse is clicked get the location of the mouse
             */
            int row = e.getX() / grid.rectangleSize;
            int column = e.getY() / grid.rectangleSize;

            // if the user left clicked and it's the first time they have clicked then set the start to be at the location chosen
            if (e.getButton() == 1 && clickCounter == 0) {
                grid.gameBoard[row][column].setStart();
                grid.addStartNode(grid.gameBoard[row][column]);
                grid.updateGrid();
                clickCounter++;

            // if the user left clicked and it's the second  time they have clicked then set the end to be at the location chosen
            } else if (e.getButton() == 1 && clickCounter == 1) {
                // if the colour has already been set, prevent it from being chosen
                if (grid.gameBoard[row][column].getColour().equals(new Color(255, 255, 255))) {
                    grid.gameBoard[row][column].setEnd();
                    grid.addEndNode(grid.gameBoard[row][column]);
                    grid.updateGrid();
                    clickCounter++;
                }

            // if the user left clicked and it's the 3rd time or more they have clicked then set the wall
            } else if (e.getButton() == 1 && clickCounter > 1) {
                if ((row != grid.getStartNode().getRow() || column != grid.getStartNode().getColumn()) && (row != grid.getEndNode().getRow() || column != grid.getEndNode().getColumn())) {
                    grid.gameBoard[row][column].setWall();
                    grid.updateGrid();
                    clickCounter++;
                }
            } else if (e.getButton() == 3 && clickCounter > 1) {
                if ((row != grid.getStartNode().getRow() || column != grid.getStartNode().getColumn()) && (row != grid.getEndNode().getRow() || column != grid.getEndNode().getColumn())) {
                    grid.gameBoard[row][column].setEmpty();
                    grid.updateGrid();
                    clickCounter++;
                }
            }
        }
    }

    static class keyListener implements KeyListener {
        AStar path;
        int counter = 0;

        @Override
        public void keyPressed(KeyEvent e) {
            /*
                Set a timer displaying the found path to the board

                @params: e: event created in the keyListener method
             */

            // if space is clicked run aStar pathfinder algorithm
            if (e.getKeyCode() == 32 && !running && grid.startNode != null && grid.endNode != null) {
                running = true;
                path = new AStar(grid);

            } else if (e.getKeyCode() == 32 && running) {
                if (path.foundPath != null) {
                    counter = path.foundPath.size() - 1;
                    grid.clearGrid();

                    ActionListener playback = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            if (counter >= 0) {
                                grid.gameBoard[path.foundPath.get(counter).r][path.foundPath.get(counter).c].setPath();
                                grid.updateGrid();
                                counter--;
                            }
                        }
                    };

                    Timer timer = new Timer(500, playback);
                    timer.start();
                }

            }
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}