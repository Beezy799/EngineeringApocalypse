package src.controller;

import src.model.BulletComplete;
import src.model.entity.EnemyComplete;
import src.view.gameWindow.GamePanel;

public class BulletController {
    private Hitbox hitbox;
    private int damage = 20;
    private float speed = GamePanel.SCALE*1.2f;
    private float xPos, yPos;
    private Vector direction;
    private IController controller;
    private boolean hit = false;
    private Object hittedObject;
    private int yHitboxOffset;
    private int xHitboxOffset;
    private float distanzaPercorsa;
    private BulletComplete bulletComplete;

    //per evitare che il proiettile colpisca chi l'ha lanciato
    private Object owner;


    public BulletController(Hitbox h, float x, float y, Vector dir, IController c, Object o) {
        direction = dir;
        hitbox = h;
        xPos = x;
        yPos = y;
        controller = c;
        owner = o;
        //centriamo la posizione della hitbox
        xHitboxOffset = hitbox.getWidth() / 2;
        yHitboxOffset = hitbox.getHeight() / 2;
        hitbox.setX(xPos - xHitboxOffset);
        hitbox.setY(yPos - yHitboxOffset);
    }

    public void update() {
        checkCollision();
        if(hit)
            controller.getModel().revomeBullet(bulletComplete.getIndexInList());

        if(hittedObject != null) {
            hitEntity();
            controller.getModel().revomeBullet(bulletComplete.getIndexInList());
        }

        updatePosition();

    }

    private void hitEntity() {
        if(hittedObject instanceof PlayerController)
            controller.getPlayerController().hitted(damage, direction);
        else if (hittedObject instanceof EnemyComplete)
            ((EnemyComplete) hittedObject).getEnemyController().hitted(damage);
    }

    private void checkCollision() {
        if (direction.getX() < 0) {
            //prima controlla se si schianta contro una parete
            if (!controller.getCollisionChecker().canGoLeft(hitbox)) {
                hit = true;
            }
        }
        else if (direction.getY() < 0) {
            if (!controller.getCollisionChecker().canGoUp(hitbox)) {
                hit = true;
            }
        }
        else if (direction.getY() > 0) {
            if (!controller.getCollisionChecker().canGoDown(hitbox)) {
                hit = true;
            }
        }
        else if (direction.getX() > 0) {
            if (!controller.getCollisionChecker().canGoRight(hitbox)) {
                hit = true;
            }
        }

        hittedObject = controller.getCollisionChecker().collisionWithEntityAndBullet(hitbox, owner);

    }

    private void updatePosition() {
        //dopo aver scelto di muoversi, vede se può farlo, altrimenti si ferma
        if (direction.getY() < 0) {
            yPos -= speed;
            hitbox.setY(yPos - yHitboxOffset);
        }
        else if (direction.getY() > 0) {
            yPos += speed;
            hitbox.setY(yPos - yHitboxOffset);
        }
        else if (direction.getX() < 0) {
            xPos -= speed;
            hitbox.setX(xPos - xHitboxOffset);
        }
        else if (direction.getX() > 0) {
            xPos += speed;
            hitbox.setX(xPos - xHitboxOffset);
        }

        //il proiettile dopo qualche metro si autodistrugge
        distanzaPercorsa += speed;
        if (distanzaPercorsa >= 5 * GamePanel.TILES_SIZE) {
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