package src.model.mapModel;

import src.model.Hitbox;

public class TileModel {

    private String tipo;
    private boolean solid;

    private Hitbox hitbox;

    @Override
    public String toString() {
        return "tile " + hitbox.getX() + " " + hitbox.getY() + " " + hitbox.getWidth() + " " + hitbox.getHeight();
    }

    public TileModel(String s, boolean b, Hitbox h){
        tipo = s;
        solid = b;
        hitbox = h;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isSolid() {
        return solid;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }
}
