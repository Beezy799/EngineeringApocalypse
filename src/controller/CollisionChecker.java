package src.controller;

import src.controller.entitycontroller.EntityController;
import src.controller.entitycontroller.enemy.EnemyController;
import src.model.Rooms;
import src.model.entity.EnemyComplete;
import src.view.gameWindow.GamePanel;

public class CollisionChecker {

    private IController control;

    public CollisionChecker(IController c) {
        control = c;
    }

    public boolean canGoLeft(Hitbox tempHitbox) {
        int hitboxRow = (int)tempHitbox.getY() / GamePanel.TILES_SIZE;
        int hitboxCol = (int)tempHitbox.getX() / GamePanel.TILES_SIZE;

        //la hitbox sta tutta sullo stesso tile oppure sfora su quello sotto?
        if(tempHitbox.getY() + tempHitbox.getHeight() < (hitboxRow + 1) * GamePanel.TILES_SIZE){
            int tileNumber = Rooms.actualRoom.getMap().getTthirdLayer()[hitboxRow][hitboxCol];
            if(tileNumber > 0) {		//se il tile è solido

                Hitbox hitboxTile = getHitboxOfTile(tileNumber, hitboxRow, hitboxCol);

                if(tempHitbox.intersects(hitboxTile))
                    return false;
            }
        }
        //se si trova a cavallo tra due righe, deve controllare anche il tile sotto
        else{
            int tileNumberUp = Rooms.actualRoom.getMap().getTthirdLayer()[hitboxRow][hitboxCol];
            int tileNumberDown = Rooms.actualRoom.getMap().getTthirdLayer()[hitboxRow + 1][hitboxCol];
            if(tileNumberUp > 0 || tileNumberDown > 0) {		//se almeno uno dei due tile è solido

                Hitbox hitboxTileUp = getHitboxOfTile(tileNumberUp, hitboxRow, hitboxCol);
                Hitbox hitboxTileDown = getHitboxOfTile(tileNumberDown, hitboxRow + 1, hitboxCol);

                if(tempHitbox.intersects(hitboxTileUp) || tempHitbox.intersects(hitboxTileDown))
                    return false;
            }
        }
        return true;
    }

    public boolean canGoUp(Hitbox tempHitbox) {
        int hitboxPlayerRow = (int)tempHitbox.getY() / GamePanel.TILES_SIZE;
        int hitboxPlayerCol = (int)tempHitbox.getX() / GamePanel.TILES_SIZE;

        //la hitbox sta tutta sullo stesso tile oppure sfora su quello a destra?
        if(tempHitbox.getX() + tempHitbox.getWidth() < (hitboxPlayerCol + 1) * GamePanel.TILES_SIZE){
            int tileNumber = Rooms.actualRoom.getMap().getTthirdLayer()[hitboxPlayerRow][hitboxPlayerCol];
            if(tileNumber > 0) {		//se il tile è solido

                Hitbox hitboxTile = getHitboxOfTile(tileNumber, hitboxPlayerRow, hitboxPlayerCol);

                if(tempHitbox.intersects(hitboxTile))
                    return false;
            }
        }
        //se si trova a cavallo tra due colonne, deve controllare anche il tile sotto
        else{
            int tileNumberLeft = Rooms.actualRoom.getMap().getTthirdLayer()[hitboxPlayerRow][hitboxPlayerCol];
            int tileNumberRight = Rooms.actualRoom.getMap().getTthirdLayer()[hitboxPlayerRow][hitboxPlayerCol + 1];
            if(tileNumberLeft > 0 || tileNumberRight > 0) {		//se almeno uno dei due tile è solido

                Hitbox hitboxTileLeft = getHitboxOfTile(tileNumberLeft, hitboxPlayerRow, hitboxPlayerCol);
                Hitbox hitboxTileRight = getHitboxOfTile(tileNumberRight, hitboxPlayerRow, hitboxPlayerCol + 1);

                if(tempHitbox.intersects(hitboxTileLeft) || tempHitbox.intersects(hitboxTileRight))
                    return false;
            }
        }
        return true;
    }

    public boolean canGoDown(Hitbox tempHitbox) {
        int hitboxPlayerRow = ((int)tempHitbox.getY() + tempHitbox.getHeight())/ GamePanel.TILES_SIZE;
        int hitboxPlayerCol = (int)tempHitbox.getX() / GamePanel.TILES_SIZE;

        //la hitbox sta tutta sullo stesso tile oppure sfora su quello a destra?
        if(tempHitbox.getX() + tempHitbox.getWidth() < (hitboxPlayerCol + 1) * GamePanel.TILES_SIZE){
            int tileNumber = Rooms.actualRoom.getMap().getTthirdLayer()[hitboxPlayerRow][hitboxPlayerCol];
            if(tileNumber > 0) {		//se il tile è solido

                Hitbox hitboxTile = getHitboxOfTile(tileNumber, hitboxPlayerRow, hitboxPlayerCol);

                if(tempHitbox.intersects(hitboxTile))
                    return false;
            }
        }
        //se si trova a cavallo tra due colonne, deve controllare anche il tile sotto
        else{
            int tileNumberLeft = Rooms.actualRoom.getMap().getTthirdLayer()[hitboxPlayerRow][hitboxPlayerCol];
            int tileNumberRight = Rooms.actualRoom.getMap().getTthirdLayer()[hitboxPlayerRow][hitboxPlayerCol + 1];
            if(tileNumberLeft > 0 || tileNumberRight > 0) {		//se almeno uno dei due tile è solido

                Hitbox hitboxTileLeft = getHitboxOfTile(tileNumberLeft, hitboxPlayerRow, hitboxPlayerCol);
                Hitbox hitboxTileRight = getHitboxOfTile(tileNumberRight, hitboxPlayerRow, hitboxPlayerCol + 1);

                if(tempHitbox.intersects(hitboxTileLeft) || tempHitbox.intersects(hitboxTileRight))
                    return false;
            }
        }
        return true;
    }

    public boolean canGoRight(Hitbox tempHitbox) {
        int hitboxPlayerRow = (int)tempHitbox.getY() / GamePanel.TILES_SIZE;
        int hitboxPlayerCol = ((int)tempHitbox.getX() + tempHitbox.getWidth()) / GamePanel.TILES_SIZE;

        //la hitbox sta tutta sullo stesso tile oppure sfora su quello sotto?
        if(tempHitbox.getY() + tempHitbox.getHeight() < (hitboxPlayerRow + 1) * GamePanel.TILES_SIZE){
            int tileNumber = Rooms.actualRoom.getMap().getTthirdLayer()[hitboxPlayerRow][hitboxPlayerCol];
            if(tileNumber > 0) {		//se il tile è solido

                Hitbox hitboxTile = getHitboxOfTile(tileNumber, hitboxPlayerRow, hitboxPlayerCol);

                if(tempHitbox.intersects(hitboxTile))
                    return false;
            }
        }
        //se si trova a cavallo tra due righe, deve controllare anche il tile sotto
        else{
            int tileNumberUp = Rooms.actualRoom.getMap().getTthirdLayer()[hitboxPlayerRow][hitboxPlayerCol];
            int tileNumberDown = Rooms.actualRoom.getMap().getTthirdLayer()[hitboxPlayerRow + 1][hitboxPlayerCol];
            if(tileNumberUp > 0 || tileNumberDown > 0) {		//se almeno uno dei due tile è solido

                Hitbox hitboxTileUp = getHitboxOfTile(tileNumberUp, hitboxPlayerRow, hitboxPlayerCol);
                Hitbox hitboxTileDown = getHitboxOfTile(tileNumberDown, hitboxPlayerRow + 1, hitboxPlayerCol);

                if(tempHitbox.intersects(hitboxTileUp) || tempHitbox.intersects(hitboxTileDown))
                    return false;
            }
        }
        return true;
    }

    //prende la hitbox corrispondente al numero del tile e la trasla nella posizione dove si trova il tile
    private Hitbox getHitboxOfTile(int tileIndex, int playerRow, int playerCol) {
        //all'interno del generico tile, la hitbox può essere traslata rispetto al punto in alto a sinistra, in questo caso x, y != 0
        int x = (int)control.getModel().getTileset().getTile(tileIndex).getTileModel().getHitbox().getX();
        x += playerCol*GamePanel.TILES_SIZE;

        int y = (int)control.getModel().getTileset().getTile(tileIndex).getTileModel().getHitbox().getY();
        y += playerRow*GamePanel.TILES_SIZE;

        int width = control.getModel().getTileset().getTile(tileIndex).getTileModel().getHitbox().getWidth();
        int height = control.getModel().getTileset().getTile(tileIndex).getTileModel().getHitbox().getHeight();

        Hitbox hitboxTile = new Hitbox(x, y, width, height);
        return hitboxTile;
    }

    public boolean isNotCollisionWithOtherEntities(EntityController entity){
        boolean notcollisionNpc = true;
        boolean notcollisionEnemy = true;

        for(int i = 0; i < Rooms.actualRoom.getNpc().size(); i++){
            EntityController other = Rooms.actualRoom.getNpc().get(i).getEntityController();
            //per controllare che non guardi l'intersezione con la sua stessa hitbox quando è un npc
            boolean isThesameEntity = (entity.getEntityIndex() == other.getEntityIndex() && !(entity instanceof EnemyController));
            if(entity.getTempHitbox().intersects(other.getHitbox()) && !isThesameEntity) {
                notcollisionNpc = false;
            }
        }

        for(int i = 0; i < Rooms.actualRoom.getEnemy().size(); i++){
            EnemyComplete other = Rooms.actualRoom.getEnemy().get(i);
            if(other.isAlive()) {
                //per controllare che non guardi l'intersezione con la sua stessa hitbox quando è un nemico
                boolean isThesameEntity = (entity.getEntityIndex() == other.getEntityController().getEntityIndex() && (entity instanceof EnemyController));
                if(entity.getTempHitbox().intersects(other.getEntityController().getHitbox()) && !isThesameEntity) {
                    notcollisionEnemy = false;
                }
            }

        }

        //deve controllare anche il giocatore
        boolean collisionPlayer = control.getPlayerController().getHitbox().intersects(entity.getTempHitbox());

        return notcollisionNpc && !collisionPlayer && notcollisionEnemy;

    }

    public Object collisionWithEntityAndBullet(Hitbox bulletHitbox, Object owner){

        for(int i = 0; i < Rooms.actualRoom.getNpc().size(); i++){
            EntityController other = Rooms.actualRoom.getNpc().get(i).getEntityController();
            //per controllare che non guardi l'intersezione con la sua stessa hitbox quando è un npc
            if(bulletHitbox.intersects(other.getHitbox()) && other != owner) {
                return other;
            }
        }

        for(int i = 0; i < Rooms.actualRoom.getEnemy().size(); i++){
            EnemyComplete other = Rooms.actualRoom.getEnemy().get(i);
            if(other.isAlive()) {
                if(bulletHitbox.intersects(other.getEntityController().getHitbox()) && other != owner) {
                    return other;
                }
            }

        }

        //deve controllare anche il giocatore
        if(control.getPlayerController().getHitbox().intersects(bulletHitbox) && control.getPlayerController() != owner)
            return control.getPlayerController();

        return null;

    }

}