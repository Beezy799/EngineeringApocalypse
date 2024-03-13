package src.controller;

public class Vector {

    private int x, y, module;

    public Vector (){
        x = 0;
        y = 0;
        module = 1;
    }



    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setModule(int module) {
        this.module = module;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getModule() {
        return module;
    }
}

//ciao