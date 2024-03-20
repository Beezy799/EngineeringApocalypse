package src.controller.entitycontroller;

import src.controller.Vector;
import src.model.EntityStates;
import src.model.Hitbox;

public class EntityController {
    protected Hitbox hitbox;
    protected int xPos, yPos;
    protected EntityStates currentState = EntityStates.IDLE;
    protected Vector movementVector;

    public EntityController(Hitbox h , int x, int y){
        xPos = x;
        yPos = y;
        hitbox = h;
        movementVector = new Vector(2);
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public Hitbox getHitbox(){
        return  hitbox;
    }

    public EntityStates getCurrentState(){
        return currentState;
    }

    public Vector getMovementVector(){
        return movementVector;
    }
}
