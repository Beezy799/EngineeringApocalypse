package src.controller;

import src.model.Hitbox;
import src.model.mapModel.Rooms;
import src.view.main.GamePanel;

public class CollisionChecker {

    private IController control;

    public CollisionChecker(IController c) {
        control = c;
    }


    public boolean canGoLeft(Hitbox tempHitbox) {
        int hitboxPlayerRow = tempHitbox.getY() / GamePanel.TILES_SIZE;
        int hitboxPlayerCol = tempHitbox.getX() / GamePanel.TILES_SIZE;

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


    public boolean canGoUp(Hitbox tempHitbox) {
        int hitboxPlayerRow = tempHitbox.getY() / GamePanel.TILES_SIZE;
        int hitboxPlayerCol = tempHitbox.getX() / GamePanel.TILES_SIZE;

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
        int hitboxPlayerRow = (tempHitbox.getY() + tempHitbox.getHeight())/ GamePanel.TILES_SIZE;
        int hitboxPlayerCol = tempHitbox.getX() / GamePanel.TILES_SIZE;

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



    //prende la hitbox corrispondente al numero del tile e la trasla nella posizione dove si trova il tile
    private Hitbox getHitboxOfTile(int tileIndex, int playerRow, int playerCol) {
        //all'interno del generico tile, la hitbox può essere traslata rispetto al punto in alto a sinistra, in questo caso x, y != 0
        int x = control.getModel().getTileset().getTile(tileIndex).getTileModel().getHitbox().getX();
        x += playerCol*GamePanel.TILES_SIZE;

        int y = control.getModel().getTileset().getTile(tileIndex).getTileModel().getHitbox().getY();
        y += playerRow*GamePanel.TILES_SIZE;

        int width = control.getModel().getTileset().getTile(tileIndex).getTileModel().getHitbox().getWidth();
        int height = control.getModel().getTileset().getTile(tileIndex).getTileModel().getHitbox().getHeight();

        Hitbox hitboxTile = new Hitbox(x, y, width, height);
        return hitboxTile;
    }

    public boolean canGoRight(Hitbox tempHitbox) {
        int hitboxPlayerRow = tempHitbox.getY() / GamePanel.TILES_SIZE;
        int hitboxPlayerCol = (tempHitbox.getX() + tempHitbox.getWidth()) / GamePanel.TILES_SIZE;

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
}