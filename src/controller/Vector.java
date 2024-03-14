package src.controller;

public class Vector {

    private int x, y, module;

    public Vector (){
        x = 0;
        y = 0;
        module = 1;
    }

    public Vector (int m){
        x = 0;
        y = 0;
        module = m;
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
        return x*module;
    }

    public int getY() {
        return y*module;
    }

    public int getNomalizedX(){
        return x;
    }

    public int getNormalizedY(){
        return y;
    }

    public int getModule() {
        return module;
    }
}

//ciao