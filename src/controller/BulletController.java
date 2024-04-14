package src.controller;

import src.model.BulletComplete;
import src.view.gameWindow.GamePanel;

import static src.model.EntityStates.IDLE;

public class BulletController {
    private Hitbox hitbox;
    private int damage;
    private float speed = 0.5f;
    private float xPos, yPos;
    private Vector direction;
    private IController controller;
    private boolean hit = false;
    private int yHitboxOffset;
    private int xHitboxOffset;
    private int distanzaPercorsa;

    private BulletComplete bulletComplete;

    //riferimento a chi l'ha lanciato


    public BulletController(Hitbox h, float x, float y, Vector dir, IController c) {
        direction = dir;
        hitbox = h;
        xPos = x;
        yPos = y;
        controller = c;
        //centriamo la posizione della hitbox
        xHitboxOffset = hitbox.getWidth() / 2;
        yHitboxOffset = hitbox.getHeight() / 2;
        hitbox.setX(xPos - xHitboxOffset);
        hitbox.setY(yPos - yHitboxOffset);
    }

    public void update() {
        //checkCollision();
        if(hit){
            controller.getModel().revomeBullet(bulletComplete.getIndexInList());
            System.out.println("distrutto");
        }
        //checkHit();
        updatePosition();

    }

    private void checkHit() {

    }

    private void checkCollision() {
        if (direction.getX() < 0) {
            //prima controlla se si schianta contro una parete
            if (controller.getPlayStateController().getCollisionChecker().canGoLeft(hitbox)) {
                hit = true;
            } else if (controller.getPlayStateController().getCollisionChecker().collisionWithEntityAndBullet(hitbox)) {
                hit = true;
            }
        } else if (direction.getY() < 0) {
            if (controller.getPlayStateController().getCollisionChecker().canGoUp(hitbox)) {
                hit = true;
            } else if (controller.getPlayStateController().getCollisionChecker().collisionWithEntityAndBullet(hitbox)) {
                hit = true;
            }
        } else if (direction.getY() > 0) {
            if (controller.getPlayStateController().getCollisionChecker().canGoDown(hitbox)) {
                hit = true;
            } else if (controller.getPlayStateController().getCollisionChecker().collisionWithEntityAndBullet(hitbox)) {
                hit = true;
            }
        } else if (direction.getX() > 0) {
            if (controller.getPlayStateController().getCollisionChecker().canGoRight(hitbox)) {
                hit = true;
            } else if (controller.getPlayStateController().getCollisionChecker().collisionWithEntityAndBullet(hitbox)) {
                hit = true;
            }
        }

    }


    private void updatePosition() {
        //dopo aver scelto di muoversi, vede se può farlo, altrimenti si ferma
        if (direction.getY() < 0) {
            yPos -= speed;
            hitbox.setY(yPos - yHitboxOffset);
        } else if (direction.getY() > 0) {
            yPos += speed;
            hitbox.setY(yPos - yHitboxOffset);
        } else if (direction.getX() < 0) {
            xPos -= speed;
            hitbox.setX(xPos - xHitboxOffset);
        } else if (direction.getX() > 0) {
            xPos += speed;
            hitbox.setX(xPos - xHitboxOffset);
        }

        //il proiettile dopo qualche metro si autodistrugge
        distanzaPercorsa += speed;
        if (distanzaPercorsa >= 3 * GamePanel.TILES_SIZE) {
            controller.getModel().revomeBullet(bulletComplete.getIndexInList());
        }

    }

    public float getxPos(){
        return xPos;
    }

    public float getyPos(){
        return yPos;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setBulletComplete(BulletComplete b) {
        bulletComplete = b;
    }
}