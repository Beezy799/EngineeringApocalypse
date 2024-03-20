package src.controller.pathFinding;

import src.controller.IController;
import src.model.mapModel.Rooms;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class PathFinder {

    private Node[][] graph;
    private PriorityQueue<Node> frontier;
    private IController controller;

    public PathFinder(IController c){
        controller = c;
        //createGraph();
    }

    public void createGraph(){
        int roomRow = Rooms.actualRoom.getMap().getFirstLayer().length;
        int roomCol = Rooms.actualRoom.getMap().getFirstLayer()[0].length;
        graph = new Node[roomRow][roomCol];

        //crea solo i nodi necessari, quelli della stanza
        for(int i = 8; i < roomRow - 9; i++){
            for(int j = 11; j < roomCol - 11; j++){
                graph[i][j] = new Node(i, j);
            }
        }
    }

    private void resetNodes(){
        int roomRow = Rooms.actualRoom.getMap().getFirstLayer().length;
        int roomCol = Rooms.actualRoom.getMap().getFirstLayer()[0].length;
        for(int i = 8; i < roomRow - 9; i++){
            for(int j = 11; j < roomCol - 11; j++){
                graph[i][j].setExplored(false);
                graph[i][j].setF_cost(0);
                graph[i][j].setPathCost(0);
                graph[i][j].setParent(null);
                graph[i][j].setSolid(false);
            }
        }
    }

    private void setNodes(Node startNode, Node goalNode){
        int roomRow = Rooms.actualRoom.getMap().getFirstLayer().length;
        int roomCol = Rooms.actualRoom.getMap().getFirstLayer()[0].length;
        for(int i = 8; i < roomRow - 9; i++){
            for(int j = 11; j < roomCol - 11; j++){
                if(Rooms.actualRoom.getMap().getTthirdLayer()[i][j] != 0) {
                    setCostOfThisNode(graph[i][j], startNode, goalNode);
                }
                else{
                   graph[i][j].setSolid(true);
               }
            }
        }
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
        resetNodes();
        setNodes(start, goal);

        Node currentNode = graph[start.getRow()][start.getCol()];
        if(currentNode.getHeuristic() == 0) {
            System.out.println("start = goal");
            return true;
        }

        //frontier.add(currentNode);

//        int roomRow = Rooms.actualRoom.getMap().getFirstLayer().length;
//        int roomCol = Rooms.actualRoom.getMap().getFirstLayer()[0].length;
//        for(int i = 8; i < roomRow - 9; i++) {
//            for (int j = 11; j < roomCol - 11; j++) {
//                if (!graph[i][j].isSolid()) {
//                    frontier.add(graph[i][j]);
//                }
//            }
//        }

//        Node n = frontier.poll();
//        System.out.println(n.getHeuristic());

//        while (!frontier.isEmpty()){
//
//            Node n = frontier.poll();
//            if(n.getHeuristic() == 0)
//                return true;
//
//            n.setExplored(true);
//            expandNode();

//        }



        return false;
    }

    private void expandNode() {
    }

}
//        int roomRow = Rooms.actualRoom.getMap().getFirstLayer().length;
//        int roomCol = Rooms.actualRoom.getMap().getFirstLayer()[0].length;
//        for(int i = 8; i < roomRow - 9; i++) {
//            for (int j = 11; j < roomCol - 11; j++) {
//                Node n = graph[i][j];
//                if(n.isSolid())
//                    System.out. print(" s ");
//                else
//                    System.out.print(n.getHeuristic() + " ");
//            }
//            System.out.println();
//        }