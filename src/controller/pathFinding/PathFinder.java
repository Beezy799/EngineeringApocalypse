package src.controller.pathFinding;

import src.controller.IController;
import src.model.mapModel.Rooms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class PathFinder {

    private Node[][] graph;
    private ArrayList<Node> frontier, path;
    private IController controller;

    private int counter;

    public PathFinder(IController c){
        controller = c;
        createGraph();
        frontier = new ArrayList<>();
        path = new ArrayList<>();

        resetNodes();
    }

    public void createGraph(){
        int roomRow = Rooms.actualRoom.getMap().getFirstLayer().length;
        int roomCol = Rooms.actualRoom.getMap().getFirstLayer()[0].length;
        graph = new Node[roomRow][roomCol];

        //crea solo i nodi necessari, quelli della stanza
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
                graph[i][j].setF_cost(0);
                graph[i][j].setPathCost(0);
                graph[i][j].setParent(null);
                graph[i][j].setSolid(false);
                graph[i][j].inFrontier = false;
            }
        }
    }

    public void setNodes(Node startNode, Node goalNode){

        resetNodes();

        int roomRow = Rooms.actualRoom.getMap().getFirstLayer().length;
        int roomCol = Rooms.actualRoom.getMap().getFirstLayer()[0].length;
        for(int i = 0; i < roomRow; i++){
            for(int j = 0; j < roomCol; j++){
                if(Rooms.actualRoom.getMap().getTthirdLayer()[i][j] != 0) {
                    graph[i][j].setSolid(true);
                    graph[i][j].setF_cost(999);
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

    public boolean existPath(Node start, Node goal){
        setNodes(start, goal);

        Node currentNode = graph[start.getRow()][start.getCol()];
        if(currentNode.getHeuristic() == 0) {
            return true;
        }

        frontier.add(currentNode);

        while (!frontier.isEmpty() && counter < 100){
            counter++;

            //il nodo esce dalla frontiera
            Node nodeToExplore = frontier.get(0);
            frontier.remove(0);

            //controlla se il nodo è il goal
            if(nodeToExplore.getHeuristic() == 0) {
                trackThePath(start, nodeToExplore);
                counter = 0;
                frontier.clear();
                resetNodes();
                return true;
            }

            nodeToExplore.setExplored(true);
            expandNode(nodeToExplore.getRow(), nodeToExplore.getCol(), nodeToExplore);
            Collections.sort(frontier);

        }

        System.out.println("no path, counter = " + counter + " frontier " + frontier.size());
        counter = 0;
        frontier.clear();
        resetNodes();
        return false;
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

    public void drawGraph(){

        System.out.println("//////////////////////////////////////////////////////////");

        int roomRow = Rooms.actualRoom.getMap().getFirstLayer().length;
        int roomCol = Rooms.actualRoom.getMap().getFirstLayer()[0].length;
        for(int i = 0; i < roomRow; i++) {
            for (int j = 0; j < roomCol; j++) {
                Node n = graph[i][j];
                if(n.isSolid())
                    System.out. print(" s ");
                else
                    System.out.print(n.getPathCost() + " ");
            }
            System.out.println();
        }
    }

    private void trackThePath(Node startNode, Node solution) {
        Node current = solution;
        path.clear();
        while(current.getParent() != null) {
            path.add(0, current);
            current = current.getParent();
        }
        for (int i = 0; i < path.size(); i++)
            System.out.println(i +" " + path.get(i).getRow() + ", " + path.get(i).getCol());
    }

    //per debugging, per vedere se settava bene i vaolori
    public void isSolid(Node start) {
        System.out.println(graph[start.getRow()][start.getCol()].isSolid);
    }
}

