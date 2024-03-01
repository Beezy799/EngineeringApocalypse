package src.view.entityView;

import src.view.IView;
import src.view.ViewUtils;
import src.view.main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static src.model.Constants.EntityConstants.*;
import javax.imageio.ImageIO;

public class PlayerView {

    private IView view;
    //campo 0 = tipo(ragazzo-ragazza), primo campo = azione, secondo = direzione, terzo = sprite
    private BufferedImage[][][][] playerAnimation;
    private static int RAGAZZO = 0, RAGAZZA = 1;
    private int gender = RAGAZZO;
    private int animationCounter; //aumenta di 1 ad ogni repaint, si azzera quando raggiunge animationspeed,
    // e in quel momento cambia sprite
    private int animationSpeed = 20;
    private int numSprite;  //indica in quale sprite dell'animazione siamo
    private int currentAction = IDLE;

    private int currenDirection = DOWN;

    public PlayerView(IView v) {
        this.view = v;

        loadImages();
    }

    public void draw(Graphics2D g2) {
        animationCounter++;

        int x = (int)view.getController().getPlayerController().getxPosPlayer();
        int y = (int)view.getController().getPlayerController().getyPosPlayer();

        if (animationCounter > animationSpeed) {
            numSprite++;
            // perchè la prima slide si deve vedere solo una volta
            //setParryAniIndex();

            if (numSprite >= getAnimationLenght() && currentAction != PARRY) {
                numSprite = 0;
                //endAttackAnimation = true;
            }
            animationCounter = 0;
        }

        g2.drawImage(playerAnimation[gender][currentAction][currenDirection][numSprite], x, -y, null );
    }

    public int getAnimationLenght() {
        if (currentAction == IDLE)
            return 4;
        else if (currentAction == MOVE)
            return 6;
        else if (currentAction == ATTACK)
            return 5;
        else if (currentAction == DIE)
            return 9;
        else if (currentAction == PARRY)
            return 2;
        else if (currentAction == THROW)
            return 2;

        return 0;
    }

    public void setCurrenDirection(float xDir, float yDir){

        if(yDir > 0) {
            currenDirection = UP;
        }
        else if(yDir < 0) {
            currenDirection = DOWN;
        }

        if(xDir > 0) {
            currenDirection = RIGHT;
        }
        else if(xDir < 0) {
            currenDirection = LEFT;
        }

    }

    private void loadImages() {
        BufferedImage image = null; //serveper prendere immagine completa di tutte le sprite di una azione
        BufferedImage temp = null; //serve per prendere ciascuna sprite separatamente

        playerAnimation = new BufferedImage[2][][][];            //due avatar (1 campo)
        playerAnimation[RAGAZZO] = new BufferedImage[7][][];    //sette azioni (2 campo)
        playerAnimation[RAGAZZA] = new BufferedImage[7][][];

        loadIdleImages(image, temp);
        loadRunImages(image, temp);
        loadAttackImages(image, temp);
        loadDeathImages(image, temp);
        loadSleepImages(image, temp);
        loadParryImages(image, temp);
        loadThrowImages(image, temp);

    }


    private void loadThrowImages(BufferedImage image, BufferedImage temp) {
        playerAnimation[RAGAZZO][THROW] = new BufferedImage[4][2];        //ci sono 4 direzioni, ogni direzione ha 2 immagini
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/SpearBoy.png"));

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 25, 0, 25, 38);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][THROW][DOWN][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 32, 38, 32, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][THROW][RIGHT][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 32, 38 + 34, 32, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][THROW][LEFT][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 26, 38 + 34 + 34, 26, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][THROW][UP][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        playerAnimation[RAGAZZA][THROW] = new BufferedImage[4][2];
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/SpellGirl.png"));

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 28, 0, 28, 30);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][THROW][DOWN][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 25, 30, 25, 29);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][THROW][RIGHT][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 25, 30 + 29, 25, 29);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][THROW][LEFT][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 24, 30 + 29 + 29, 24, 32);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][THROW][UP][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void loadParryImages(BufferedImage image, BufferedImage temp) {
        playerAnimation[RAGAZZO][PARRY] = new BufferedImage[4][2];        //ci sono 1 direzioni, ogni direzione ha 6 immagini
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/BowBoypc.png"));

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 25, 0, 25, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][PARRY][DOWN][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 29, 34, 29, 33);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][PARRY][RIGHT][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 28, 34 + 33, 28, 33);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][PARRY][LEFT][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 32, 34 + 33 + 33, 32, 32);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][PARRY][UP][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        playerAnimation[RAGAZZA][PARRY] = new BufferedImage[4][2];
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/BowGirlpc.png"));

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 26, 0, 26, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][PARRY][DOWN][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 32, 34, 32, 33);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][PARRY][RIGHT][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 32, 34 + 33, 32, 33);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][PARRY][LEFT][i] = temp;
            }

            for (int i = 0; i < 2; i++) {
                temp = image.getSubimage(i * 31, 34 + 33 + 33, 31, 32);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][PARRY][UP][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadSleepImages(BufferedImage image, BufferedImage temp) {
        playerAnimation[RAGAZZO][SLEEP] = new BufferedImage[1][6];        //ci sono 1 direzioni, ogni direzione ha 6 immagini
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/SleepingBoy.png"));

            for (int i = 0; i < 6; i++) {
                temp = image.getSubimage(i * 31, 0, 31, 37);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][SLEEP][DOWN][i] = temp;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        playerAnimation[RAGAZZA][SLEEP] = new BufferedImage[1][6];        //ci sono 1 direzioni, ogni direzione ha 6 immagini
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/SleepingGirl.png"));

            for (int i = 0; i < 6; i++) {
                temp = image.getSubimage(i * 28, 0, 28, 36);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][SLEEP][DOWN][i] = temp;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadDeathImages(BufferedImage image, BufferedImage temp) {
        playerAnimation[RAGAZZO][DIE] = new BufferedImage[2][9];        //ci sono 4 direzioni, ogni direzione ha 5 immagini
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/deathBoyCorr.png"));

            for (int i = 0; i < 9; i++) {
                temp = image.getSubimage(i * 36, 0, 36, 37);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][DIE][0][i] = temp;
            }

            for (int i = 0; i < 9; i++) {
                temp = image.getSubimage(i * 36, 37, 36, 37);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][DIE][1][i] = temp;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        playerAnimation[RAGAZZA][DIE] = new BufferedImage[2][9];
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/deadGirl.png"));

            for (int i = 0; i < 9; i++) {
                temp = image.getSubimage(i * 43, 0, 43, 38);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][DIE][0][i] = temp;
            }

            for (int i = 0; i < 9; i++) {
                temp = image.getSubimage(i * 43, 38, 43, 38);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][DIE][1][i] = temp;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void loadAttackImages(BufferedImage image, BufferedImage temp) {
        playerAnimation[RAGAZZO][ATTACK] = new BufferedImage[4][5];        //ci sono 4 direzioni, ogni direzione ha 5 immagini
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/attaccoRagazzo.png"));

            for (int i = 0; i < 5; i++) {
                temp = image.getSubimage(i * 26, 0, 26, 42);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][ATTACK][DOWN][i] = temp;
            }

            for (int i = 0; i < 5; i++) {
                temp = image.getSubimage(i * 35, 42, 35, 36);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][ATTACK][RIGHT][i] = temp;
            }

            for (int i = 0; i < 5; i++) {
                temp = image.getSubimage(i * 35, 80, 35, 36);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][ATTACK][LEFT][i] = temp;
            }

            for (int i = 0; i < 5; i++) {
                temp = image.getSubimage(i * 24, 119, 24, 38);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][ATTACK][UP][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        playerAnimation[RAGAZZA][ATTACK] = new BufferedImage[4][5];
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/attaccoRagazza.png"));

            for (int i = 0; i < 5; i++) {
                temp = image.getSubimage(i * 25, 0, 25, 43);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][ATTACK][DOWN][i] = temp;
            }

            for (int i = 0; i < 5; i++) {
                temp = image.getSubimage(i * 37, 44, 37, 36);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][ATTACK][RIGHT][i] = temp;
            }

            for (int i = 0; i < 5; i++) {
                temp = image.getSubimage(i * 37, 83, 37, 35);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][ATTACK][LEFT][i] = temp;
            }

            for (int i = 0; i < 5; i++) {
                temp = image.getSubimage(i * 29, 118, 29, 37);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][ATTACK][UP][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadIdleImages(BufferedImage image, BufferedImage temp) {
        playerAnimation[RAGAZZO][IDLE] = new BufferedImage[4][4];        //ci sono 4 direzioni, ogni direzione ha 4 immagini
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/idleSpriteBoy.png"));

            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 24, 0, 24, 33);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][IDLE][DOWN][i] = temp;
            }

            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 21, 33, 21, 32);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][IDLE][RIGHT][i] = temp;
            }

            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 21, 33 + 32, 21, 32);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][IDLE][LEFT][i] = temp;
            }

            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 22, 33 + 32 + 32, 22, 32);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][IDLE][UP][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        playerAnimation[RAGAZZA][IDLE] = new BufferedImage[4][4];
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/IdleGirl.png"));

            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 23, 0, 23, 32);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][IDLE][DOWN][i] = temp;
            }

            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 22, 32, 22, 31);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][IDLE][RIGHT][i] = temp;
            }

            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 22, 32 + 31, 22, 31);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][IDLE][LEFT][i] = temp;
            }

            for (int i = 0; i < 4; i++) {
                temp = image.getSubimage(i * 22, 32 + 31 + 31, 22, 32);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][IDLE][UP][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadRunImages(BufferedImage image, BufferedImage temp) {
        playerAnimation[RAGAZZO][MOVE] = new BufferedImage[4][6];        //ci sono 4 direzioni, ogni direzione ha 6 immagini

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/walkingSpritesBoyCorr.png"));

            for (int i = 0; i < 6; i++) {
                temp = image.getSubimage(i * 23, 0, 23, 35);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][MOVE][DOWN][i] = temp;
            }

            for (int i = 0; i < 6; i++) {
                temp = image.getSubimage(i * 24, 35, 24, 33);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][MOVE][RIGHT][i] = temp;
            }

            for (int i = 0; i < 6; i++) {
                temp = image.getSubimage(i * 24, 35 + 33, 24, 33);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][MOVE][LEFT][i] = temp;
            }

            for (int i = 0; i < 6; i++) {
                temp = image.getSubimage(i * 26, 35 + 33 + 33, 26, 36);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZO][MOVE][UP][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        playerAnimation[RAGAZZA][MOVE] = new BufferedImage[4][6];        //ci sono 4 direzioni, ogni direzione ha 6 immagini
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/player/MoveGirl.png"));

            for (int i = 0; i < 6; i++) {
                temp = image.getSubimage(i * 26, 0, 26, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][MOVE][DOWN][i] = temp;
            }

            for (int i = 0; i < 6; i++) {
                temp = image.getSubimage(i * 24, 34, 24, 33);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][MOVE][RIGHT][i] = temp;
            }

            for (int i = 0; i < 6; i++) {
                temp = image.getSubimage(i * 24, 34 + 33, 24, 33);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][MOVE][LEFT][i] = temp;
            }

            for (int i = 0; i < 6; i++) {
                temp = image.getSubimage(i * 31, 34 + 33 + 33, 31, 34);
                temp = ViewUtils.scaleImage(temp, temp.getWidth() * 1.2f * GamePanel.SCALE, temp.getHeight() * 1.2f * GamePanel.SCALE);
                playerAnimation[RAGAZZA][MOVE][UP][i] = temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGender(int i){
        gender = i;
    }

} //end class



