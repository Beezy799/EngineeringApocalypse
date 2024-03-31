package src.controller;

public class Vector {

    private float x, y, module;

    public Vector (){
        x = 0;
        y = 0;
        module = 1;
    }

    public Vector (float m){
        x = 0;
        y = 0;
        module = m;
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
        return x*module;
    }

    public float getY() {
        return y*module;
    }

    public float getNomalizedX(){
        return x;
    }

    public float getNormalizedY(){
        return y;
    }

    public float getModule() {
        return module;
    }

    public void resetDirections(){
        setX(0);
        setY(0);
    }
}

//ciao