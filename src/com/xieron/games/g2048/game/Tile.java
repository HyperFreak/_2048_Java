package com.xieron.games.g2048.game;

import com.xieron.games.g2048.ui.Colors;
import com.xieron.games.g2048.ui.Fonts;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

public class Tile {

    private int num;
    private int value;

    private int xPos, yPos;
    private int posX, posY;
    private GameField grid;

    private int lX, lY;

    private RoundRectangle2D rect;
    private Fonts fontManager;

    private boolean merged;
    private boolean merging;

    private boolean moving;
    private int nX;
    private int nY;

    private int moveSpeed = 35;

    private Game game;

    public Tile(int x, int y, int width, int height, Fonts fontManager, int posX, int posY, GameField grid, Game game) {
        this.game = game;
        this.value = rand2_4();
        this.num = value == 4 ? 2 : 1;

        this.rect = new RoundRectangle2D.Float(x, y, width, height, 10, 10);
        this.fontManager = fontManager;

        this.posX = posX;
        this.posY = posY;
        this.grid = grid;
    }

    public void update(){
        xPos = (int)rect.getX();
        yPos = (int)rect.getY();
        nX = grid.getTileXPos(posX);
        nY = grid.getTileYPos(posY);
        if(moving){
            if(nX > xPos){
                xPos += moveSpeed;
                if(xPos > nX){
                    xPos = nX;
                }

            }else if(nX < xPos) {
                xPos -= moveSpeed;
                if(xPos < nX){
                    xPos = nX;
                }
            }else if(nY > yPos) {
                yPos += moveSpeed;
                if(yPos > nY){
                    yPos = nY;
                }
            }else if(nY < yPos) {
                yPos -= moveSpeed;
                if(yPos < nY){
                    yPos = nY;
                }
            }else{


                game.stopMoving();
                if(merging){
                    mergewith(lX, lY, grid.getTile(posX, posY));
                }

                moving = false;
            }



            rect.setRoundRect(xPos, yPos, rect.getWidth(), rect.getHeight(), rect.getArcWidth(), rect.getArcHeight());

        }
    }

    public void render(Graphics2D g){
        update();

        if(num >= Colors.TILE.length){
            g.setColor(Colors.TILE[Colors.TILE.length - 1]);
        }else {
            g.setColor(Colors.TILE[num]);
        }
        g.fill(rect);
        if(num != 0){
            //draw number as string
            if(num == 1) {
                g.setColor(Color.GRAY);
            }else{
                g.setColor(Color.WHITE);
            }

            g.setFont(fontManager.getFont(value));
            g.drawString(Integer.toString(value), (int)rect.getX() + (int)rect.getWidth() / 2 - g.getFontMetrics().stringWidth(Integer.toString(value)) / 2, (int)rect.getY() + (int)rect.getHeight() / 2 + (int)((float)g.getFontMetrics().getHeight() / 3.3F));
        }
    }

    public int getValue(){
        return this.value;
    }

    private int rand2_4() {
        if((int)(Math.random() * 4) == 2) {
            return 4;
        }
        return 2;
    }


    /*
    *

DDDDDDDDDDDDD             OOOOOOOOO     EEEEEEEEEEEEEEEEEEEEEE   SSSSSSSSSSSSSSS      NNNNNNNN        NNNNNNNN     OOOOOOOOO     TTTTTTTTTTTTTTTTTTTTTTT     WWWWWWWW                           WWWWWWWW     OOOOOOOOO     RRRRRRRRRRRRRRRRR   KKKKKKKKK    KKKKKKK
D::::::::::::DDD        OO:::::::::OO   E::::::::::::::::::::E SS:::::::::::::::S     N:::::::N       N::::::N   OO:::::::::OO   T:::::::::::::::::::::T     W::::::W                           W::::::W   OO:::::::::OO   R::::::::::::::::R  K:::::::K    K:::::K
D:::::::::::::::DD    OO:::::::::::::OO E::::::::::::::::::::ES:::::SSSSSS::::::S     N::::::::N      N::::::N OO:::::::::::::OO T:::::::::::::::::::::T     W::::::W                           W::::::W OO:::::::::::::OO R::::::RRRRRR:::::R K:::::::K    K:::::K
DDD:::::DDDDD:::::D  O:::::::OOO:::::::OEE::::::EEEEEEEEE::::ES:::::S     SSSSSSS     N:::::::::N     N::::::NO:::::::OOO:::::::OT:::::TT:::::::TT:::::T     W::::::W                           W::::::WO:::::::OOO:::::::ORR:::::R     R:::::RK:::::::K   K::::::K
  D:::::D    D:::::D O::::::O   O::::::O  E:::::E       EEEEEES:::::S                 N::::::::::N    N::::::NO::::::O   O::::::OTTTTTT  T:::::T  TTTTTT      W:::::W           WWWWW           W:::::W O::::::O   O::::::O  R::::R     R:::::RKK::::::K  K:::::KKK
  D:::::D     D:::::DO:::::O     O:::::O  E:::::E             S:::::S                 N:::::::::::N   N::::::NO:::::O     O:::::O        T:::::T               W:::::W         W:::::W         W:::::W  O:::::O     O:::::O  R::::R     R:::::R  K:::::K K:::::K
  D:::::D     D:::::DO:::::O     O:::::O  E::::::EEEEEEEEEE    S::::SSSS              N:::::::N::::N  N::::::NO:::::O     O:::::O        T:::::T                W:::::W       W:::::::W       W:::::W   O:::::O     O:::::O  R::::RRRRRR:::::R   K::::::K:::::K
  D:::::D     D:::::DO:::::O     O:::::O  E:::::::::::::::E     SS::::::SSSSS         N::::::N N::::N N::::::NO:::::O     O:::::O        T:::::T                 W:::::W     W:::::::::W     W:::::W    O:::::O     O:::::O  R:::::::::::::RR    K:::::::::::K
  D:::::D     D:::::DO:::::O     O:::::O  E:::::::::::::::E       SSS::::::::SS       N::::::N  N::::N:::::::NO:::::O     O:::::O        T:::::T                  W:::::W   W:::::W:::::W   W:::::W     O:::::O     O:::::O  R::::RRRRRR:::::R   K:::::::::::K
  D:::::D     D:::::DO:::::O     O:::::O  E::::::EEEEEEEEEE          SSSSSS::::S      N::::::N   N:::::::::::NO:::::O     O:::::O        T:::::T                   W:::::W W:::::W W:::::W W:::::W      O:::::O     O:::::O  R::::R     R:::::R  K::::::K:::::K
  D:::::D     D:::::DO:::::O     O:::::O  E:::::E                         S:::::S     N::::::N    N::::::::::NO:::::O     O:::::O        T:::::T                    W:::::W:::::W   W:::::W:::::W       O:::::O     O:::::O  R::::R     R:::::R  K:::::K K:::::K
  D:::::D    D:::::D O::::::O   O::::::O  E:::::E       EEEEEE            S:::::S     N::::::N     N:::::::::NO::::::O   O::::::O        T:::::T                     W:::::::::W     W:::::::::W        O::::::O   O::::::O  R::::R     R:::::RKK::::::K  K:::::KKK
DDD:::::DDDDD:::::D  O:::::::OOO:::::::OEE::::::EEEEEEEE:::::ESSSSSSS     S:::::S     N::::::N      N::::::::NO:::::::OOO:::::::O      TT:::::::TT                    W:::::::W       W:::::::W         O:::::::OOO:::::::ORR:::::R     R:::::RK:::::::K   K::::::K
D:::::::::::::::DD    OO:::::::::::::OO E::::::::::::::::::::ES::::::SSSSSS:::::S     N::::::N       N:::::::N OO:::::::::::::OO       T:::::::::T                     W:::::W         W:::::W           OO:::::::::::::OO R::::::R     R:::::RK:::::::K    K:::::K
D::::::::::::DDD        OO:::::::::OO   E::::::::::::::::::::ES:::::::::::::::SS      N::::::N        N::::::N   OO:::::::::OO         T:::::::::T                      W:::W           W:::W              OO:::::::::OO   R::::::R     R:::::RK:::::::K    K:::::K
DDDDDDDDDDDDD             OOOOOOOOO     EEEEEEEEEEEEEEEEEEEEEE SSSSSSSSSSSSSSS        NNNNNNNN         NNNNNNN     OOOOOOOOO           TTTTTTTTTTT                       WWW             WWW                 OOOOOOOOO     RRRRRRRR     RRRRRRRKKKKKKKKK    KKKKKKK




               at least not completely


    *
    * */


    public void moveRight(){
        int cX = posX, cY = posY;

        moving = true;

        if(posX != 3){
            if(grid.getTile(posX + 1, posY) == null){
                posX++;
                moveRight();
            }else{
                if(grid.getTile(posX + 1, posY).getValue() == this.value && !grid.getTile(posX + 1, posY).hasMerged()){
                    //MERGE
                    merging = true;
                    posX++;
                    grid.getTile(posX, posY).setMerging(true);
                    this.lX = cX;
                    this.lY = cY;
                    //mergewith(cX, cY, grid.getTile(posX + 1, posY));
                }
                nX = grid.getTileXPos(posX);
            }

            if(merging)
                return;

            if(cX != posX)
                grid.updateTilePos(cX, cY, posX, posY, this);
        }
    }

    public void moveLeft(){
        int cX = posX, cY = posY;

        moving = true;

        if(posX != 0){
            if(grid.getTile(posX - 1, posY) == null){
                posX--;
                moveLeft();
            }else{
                if(grid.getTile(posX - 1, posY).getValue() == this.value && !grid.getTile(posX - 1, posY).hasMerged()){
                    //MERGE
                    merging = true;
                    posX--;
                    grid.getTile(posX, posY).setMerging(true);
                    this.lX = cX;
                    this.lY = cY;
                    //mergewith(cX, cY, grid.getTile(posX - 1, posY));
                }
                nX = grid.getTileXPos(posX);
            }

            if(merging)
                return;

            if(cX != posX)
                grid.updateTilePos(cX, cY, posX, posY, this);
        }
    }

    public void moveUp(){
        int cX = posX, cY = posY;

        moving = true;

        if(posY != 0){
            if(grid.getTile(posX, posY - 1) == null){
                posY--;
                moveUp();
            }else{
                if(grid.getTile(posX, posY - 1).getValue() == this.value && !grid.getTile(posX, posY - 1).hasMerged()){
                    //MERGE
                    merging = true;
                    this.lX = cX;
                    this.lY = cY;
                    posY--;
                    grid.getTile(posX, posY).setMerging(true);
                }
                nY = grid.getTileYPos(posY);
            }

            if(merging)
                return;

            if(cY != posY)
                grid.updateTilePos(cX, cY, posX, posY, this);
        }
    }

    public void moveDown(){
        int cX = posX, cY = posY;

        moving = true;

        if(posY != 3){
            if(grid.getTile(posX, posY + 1) == null){
                posY++;
                moveDown();
            }else{
                if(grid.getTile(posX, posY + 1).getValue() == this.value && !grid.getTile(posX, posY + 1).hasMerged()){
                    //MERGE
                    merging = true;
                    posY++;
                    grid.getTile(posX, posY).setMerging(true);
                    this.lX = cX;
                    this.lY = cY;
                }
                nY = grid.getTileYPos(posY);
            }

            if(merging)
                return;

            if(cY != posY)
                grid.updateTilePos(cX, cY, posX, posY, this);
        }
    }

    private void mergewith(int oX, int oY, Tile other){
        other.merge();
        grid.removeTile(oX, oY);
    }

    public void merge(){
        this.value *= 2;
        this.num++;
        this.merged = true;
    }

    public void setMerging(boolean b){
        this.merged = b;
    }

    public boolean hasMerged(){
        return this.merged;
    }


}
