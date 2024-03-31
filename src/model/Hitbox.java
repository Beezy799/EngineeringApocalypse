package src.model;

public class Hitbox {

    private float x, y;
    private int width, height;


    public Hitbox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    //costruttore dove i campi sono di default, in modo da non dover
    // mettere tutti zero come parametro quando richiamo il costruttore
    public Hitbox() {
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
    }

    public boolean intersects(Hitbox r)  {
        boolean schianto = r.width > 0 && r.height > 0 && width > 0 && height > 0
                && r.x < x + width && r.x + r.width > x
                && r.y < y + height && r.y + r.height > y;

        return schianto;
    }

}