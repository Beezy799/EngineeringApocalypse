package src.controller;

public class Vector {

    private float x, y, module;

    public Vector (){
        x = 0;
        y = 0;
        module = 1;
    }



    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setModule(float module) {
        this.module = module;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getModule() {
        return module;
    }
}
