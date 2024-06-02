package src.controller.pathFinding;

import src.model.Rooms;

import java.util.ArrayList;
import java.util.Collections;

public class PathFinder {

    private Node[][] graph;
    private ArrayList<Node> frontier;

    private int counter;

    public PathFinder(){
        createGraph();
        frontier = new ArrayList<>();
        resetNodes();
    }

    public void createGraph(){
        int roomRow = Rooms.actualRoom.getMap().getFirstLayer().length;
        int roomCol = Rooms.actualRoom.getMap().getFirstLayer()[0].length;
        graph = new Node[roomRow][roomCol];
        for(int i = 0; i < roomRow; i++){
            for(int j = 0; j < roomCol; j++){
                graph[i][j] = new Node(i, j);
            }
        }

    }

    private void resetNodes(){
        int roomRow = Rooms.actualRoom.getMap().getFirstLayer().length;
        int roomCol = Rooms.actualRoom.getMap().getFirstLayer()[0].length;
        for(int i = 0; i < roomRow; i++){
            for(int j = 0; j < roomCol; j++){
                graph[i][j].setExplored(false);
                graph[i][j].setF_cost(999);
                graph[i][j].setPathCost(0);
                graph[i][j].setParent(null);
                graph[i][j].setSolid(true);
                graph[i][j].inFrontier = false;
            }
        }
    }

    public void setNodes(Node startNode, Node goalNode){

        resetNodes();

        int roomRow = Rooms.actualRoom.getMap().getFirstLayer().length;
        int roomCol = Rooms.actualRoom.getMap().getFirstLayer()[0].length;
        for(int i = 7; i <= roomRow - 7; i++){
            for(int j = 11; j <= roomCol - 11; j++){
                if(Rooms.actualRoom.getMap().getTthirdLayer()[i][j] != 0) {
                    graph[i][j].setSolid(true);
                }
                else{
                   setCostOfThisNode(graph[i][j], startNode, goalNode);
                   graph[i][j].setSolid(false);
               }
            }
        }
        setCostOfThisNode(graph[startNode.getRow()][startNode.getCol()], startNode, goalNode);
        setCostOfThisNode(graph[goalNode.getRow()][goalNode.getCol()], startNode, goalNode);
        graph[goalNode.getRow()][goalNode.getCol()].isSolid = false;
    }

    private void setCostOfThisNode(Node node, Node startNode, Node goalNode) {
        int distanceFromStartX = Math.abs(node.getCol() - startNode.getCol());
        int distanceFromStartY = Math.abs(node.getRow() - startNode.getRow());
        int gCost = distanceFromStartX + distanceFromStartY;
        node.setPathCost(gCost);

        int distanceForGoalX = Math.abs(node.getCol() - goalNode.getCol());
        int distanceForGoalY = Math.abs(node.getRow() - goalNode.getRow());
        int hCost = distanceForGoalX + distanceForGoalY;
        node.setHeuristic(hCost);

        node.setF_cost(gCost + hCost);
    }

    public ArrayList<Node> findPath(Node start, Node goal){

        setNodes(start, goal);
        ArrayList<Node> path = new ArrayList<>();

        //goal test sul primo nodo
        Node currentNode = graph[start.getRow()][start.getCol()];
        if(currentNode.getHeuristic() == 0) {
            path.add(currentNode);
            return path;
        }

        //se il primo nodo non è il goal, lo aggiunge in frontiera e parte il ciclo
        frontier.add(currentNode);
        while (!frontier.isEmpty() && counter < 100){
            counter++;

            //il nodo esce dalla frontiera
            Node nodeToExplore = frontier.get(0);
            frontier.remove(0);

            //controlla se il nodo è il goal (heuristic = 0), se sì, restituisce il percorso e si resetta tutto
            if(nodeToExplore.getHeuristic() == 0) {
                trackThePath(nodeToExplore, path);
                counter = 0;
                frontier.clear();
                resetNodes();
                return path;
            }

            //se il nodo non è il goal, esplora quelli vicini
            nodeToExplore.setExplored(true);
            expandNode(nodeToExplore.getRow(), nodeToExplore.getCol(), nodeToExplore);
            Collections.sort(frontier);

        }

        //se alla fine la frontiera è vuota e non ha trovato il goal, non c'è un percorso e restituisce null
        //System.out.println("no path, counter = " + counter + " frontier " + frontier.size());
        counter = 0;
        frontier.clear();
        resetNodes();
        return null;
    }

    private void expandNode(int row, int col, Node parent) {
        //open the up Node
        if(row - 1 > 0)
            addNodeToFrontier(graph[row -1][col], parent);

        //open the down Node
        if(row + 1 < graph.length)
            addNodeToFrontier(graph[row + 1][col], parent);

        //open the left Node
        if(col - 1 > 0)
            addNodeToFrontier(graph[row][col - 1], parent);

        //open the right Node
        if(col + 1 < graph[0].length)
            addNodeToFrontier(graph[row][col + 1], parent);

    }

    private void addNodeToFrontier(Node node, Node parent) {
        if(!node.inFrontier && !node.isExplored() && !node.isSolid()) {
            frontier.add(node);
            node.inFrontier = true;
            node.setParent(parent);
        }
    }

    //restituisce la lista di genitori del nodo soluzione, e quindi il percorso
    private void trackThePath(Node solution, ArrayList<Node> path) {
        Node current = solution;
        while(current.getParent() != null) {
            path.add(0, current);
            current = current.getParent();
        }
    }

}

