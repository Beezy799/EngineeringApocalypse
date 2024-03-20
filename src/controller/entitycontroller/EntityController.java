package src.controller.entitycontroller;

import src.controller.Vector;
import src.model.EntityStates;
import src.model.Hitbox;
import src.view.main.GamePanel;

public class EntityController {

    protected Hitbox hitbox, interactionHitbox;
    protected int XhitboxOffset, YhitboxOffset;
    protected int xPos, yPos;
    protected EntityStates currentState = EntityStates.IDLE;
    protected Vector movementVector;

    public EntityController(Hitbox h , int x, int y){
        xPos = x * GamePanel.TILES_SIZE;
        yPos = y * GamePanel.TILES_SIZE;

        hitbox = h;
        hitbox.setWidth((int)(hitbox.getWidth()*GamePanel.SCALE));
        hitbox.setHeight((int)(hitbox.getHeight()*GamePanel.SCALE));
        XhitboxOffset = hitbox.getWidth()/2;
        YhitboxOffset = hitbox.getHeight()/2;
        hitbox.setX(xPos - XhitboxOffset);
        hitbox.setY(yPos - YhitboxOffset);

        movementVector = new Vector(2);
        XhitboxOffset = hitbox.getWidth()/2;
        YhitboxOffset = hitbox.getHeight()/2;
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

    public int getXhitboxOffset() {
        return XhitboxOffset;
    }

    public int getYhitboxOffset() {
        return YhitboxOffset;
    }

    public Hitbox getInteractionHitbox(){
        return interactionHitbox;
    }
}
