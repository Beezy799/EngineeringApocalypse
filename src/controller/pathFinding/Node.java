package src.controller.pathFinding;

public class Node implements  Comparable{

    private int pathCost;
    private int heuristic;
    private int f_cost;
    private int row, col;
    private boolean explored, isSolid;
    private Node parent;


    public Node(int row, int col){
        this.col = col;
        this.row = row;
        explored = false;
    }


    public int getPathCost() {
        return pathCost;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public int getF_cost() {
        return f_cost;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isExplored() {
        return explored;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public void setF_cost(int f_cost) {
        this.f_cost = f_cost;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isSolid(){
        return isSolid;
    }

    public void setSolid(boolean s){
        isSolid = s;
    }

    public int compareTo(Node n) {
//        if(n.getF_cost() < this.f_cost)
//            return 1;
//
//        if(n.getF_cost() > this.f_cost)
//            return -1;
//
//        return 0;

        if(n.getHeuristic() < this.heuristic)
            return 1;

        if(n.getHeuristic() > this.heuristic)
            return -1;

        return 0;
    }

    @Override
    public int compareTo(Object o) {
        System.out.println("uso questo metodo demmerda");
        return 0;
    }
}
