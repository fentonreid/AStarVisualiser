import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class AStar {
    Node[][] searchSpace;
    Grid grid;
    Node startNode;
    Node endNode;
    List<Node> foundPath;


    public AStar(Grid g) {
        /*
          AStar constructor

          @param grid: A 2d array of point objects
         */
        grid = g;
        searchSpace = new Node[grid.row][grid.column];

        createSearchSpace();
        foundPath = shortestPath();
    }

    public float manhattanDistance(Node neighbour, Node endNode) {
        /*
            Calculates the distance from the currentPoint to the endNode

            @param neighbour: is a point on the grid, endNode: is the point at which aStar should terminate
         */
        return abs(endNode.r - neighbour.r) + abs(endNode.c - neighbour.c);
    }

    public void createSearchSpace() {
        /*
            This function adds rows and columns and traversability to the pre-existing grid object
         */
        // generate all nodes, with an x,y:
        for (int i = 0; i < searchSpace.length; i++) {
            for (int j = 0; j < searchSpace[i].length; j++) {
                searchSpace[i][j] = new Node(i, j);
                searchSpace[i][j].traversable = grid.gameBoard[i][j].traversable();
                searchSpace[i][j].r = grid.gameBoard[i][j].getRow();
                searchSpace[i][j].c = grid.gameBoard[i][j].getColumn();
            }
        }

        // set start and end nodes and add them to the searchSpace
        startNode = searchSpace[grid.startNode.getRow()][grid.startNode.getColumn()];
        endNode = searchSpace[grid.endNode.getRow()][grid.endNode.getColumn()];
    }

    public void getNeighbours(int i, int j) {
        /*
        Gets all the neighbours of the current node

        @params i: row in the grid, j: column in the grid
         */
        // get all 8 possible neighbour nodes
        if (searchSpace[i][j].traversable) {
            // add all 8 possible neighbours
            List<Node> neighbourList = new ArrayList<>();

            if (i - 1 >= 0 && j - 1 >= 0) {
                if (searchSpace[i - 1][j - 1].traversable) {
                    neighbourList.add(searchSpace[i - 1][j - 1]);
                }
            }

            if (i - 1 >= 0) {
                if (searchSpace[i - 1][j].traversable) {
                    neighbourList.add(searchSpace[i - 1][j]);
                }
            }

            if (i - 1 >= 0 && j + 1 < searchSpace[i].length) {
                if (searchSpace[i - 1][j + 1].traversable) {
                    neighbourList.add(searchSpace[i - 1][j + 1]);
                }
            }

            if (j - 1 > 0) {
                if (searchSpace[i][j - 1].traversable) {
                    neighbourList.add(searchSpace[i][j - 1]);
                }
            }

            if (j + 1 < searchSpace[i].length) {
                if (searchSpace[i][j + 1].traversable) {
                    neighbourList.add(searchSpace[i][j + 1]);
                }
            }

            if (i + 1 < searchSpace.length && j - 1 >= 0) {
                if (searchSpace[i + 1][j - 1].traversable) {
                    neighbourList.add(searchSpace[i + 1][j - 1]);
                }
            }

            if (i + 1 < searchSpace.length) {
                if (searchSpace[i + 1][j].traversable) {
                    neighbourList.add(searchSpace[i + 1][j]);
                }
            }

            if (i + 1 < searchSpace.length && j + 1 < searchSpace[i].length) {
                if (searchSpace[i + 1][j + 1].traversable) {
                    neighbourList.add(searchSpace[i + 1][j + 1]);
                }
            }

            searchSpace[i][j].setNeighbours(neighbourList);
        }
    }

    public List<Node> shortestPath() {
        /*
        Gets the shortest path from the start to end node.

        @return: A list of points from the startNode to the endNode
         */
        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();
        Node currentNode;
        openList.add(startNode);

        while (!openList.isEmpty()) {
            // get f value of first node in the open list
            float lowestFCost = openList.get(0).f;
            int position = 0;
            // get lowest f cost of the nodes in open list
            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).f < lowestFCost) {
                    lowestFCost = openList.get(i).f;
                    position = i;
                }
            }

            currentNode = openList.get(position);
            openList.remove(position);
            closedList.add(currentNode);

            if (currentNode == endNode) {
                foundPath = new ArrayList<>();
                currentNode = currentNode.parent;

                while (currentNode.parent != null || currentNode != startNode) {
                    grid.gameBoard[currentNode.r][currentNode.c].setPath();
                    grid.updateGrid();
                    foundPath.add(searchSpace[currentNode.r][currentNode.c]);
                    currentNode = currentNode.parent;
                }

                return foundPath;
            }

            getNeighbours(currentNode.r, currentNode.c);

            for (Node neighbour : currentNode.neighbours) {
                if (closedList.contains(neighbour)) {
                    continue;
                }

                neighbour.g = currentNode.g + 1;
                neighbour.h = manhattanDistance(neighbour, endNode);
                neighbour.f = neighbour.g + neighbour.h;

                if (!openList.contains(neighbour)) {
                    neighbour.f = (currentNode.g + 1) + (manhattanDistance(neighbour, endNode));
                    neighbour.parent = currentNode;

                    if (!openList.contains(neighbour)) {
                        openList.add(neighbour);
                    }
                }

                if (grid.gameBoard[neighbour.r][neighbour.c] != grid.startNode && grid.gameBoard[neighbour.r][neighbour.c] != grid.endNode) {
                    grid.gameBoard[neighbour.r][neighbour.c].setVisited();
                    grid.updateGrid();
                }
            }
        }

        return null;
    }
}