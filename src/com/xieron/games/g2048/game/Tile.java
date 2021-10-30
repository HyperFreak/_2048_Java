package com.xieron.games.g2048.game;

import com.xieron.games.g2048.input.InputManager;
import com.xieron.games.g2048.ui.Colors;
import com.xieron.games.g2048.ui.Fonts;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

public class Tile {

    private int value;

    private int pX, pY; //pixel position
    private int gX, gY; //grid position
    private final GameField grid;

    private int lX, lY;

    private final RoundRectangle2D rect;
    private final Fonts fontManager;

    private boolean merged;
    private boolean merging;

    private boolean moving;
    private int nX;
    private int nY;

    private int lastXPosition;
    private int lastYPosition;

    private int moveSpeed = 35;

    private final Game game;

    public Tile(int x, int y, int width, int height, Fonts fontManager, int posX, int posY, GameField grid, Game game) {
        this.game = game;
        this.value = rand2_4();

        this.rect = new RoundRectangle2D.Float(x, y, width, height, 10, 10);
        this.fontManager = fontManager;

        this.gX = posX;
        this.gY = posY;
        this.grid = grid;
    }

    public void update(){
        pX = (int)rect.getX();
        pY = (int)rect.getY();
        nX = grid.getTileXPos(gX);
        nY = grid.getTileYPos(gY);
    }

    public void render(Graphics2D g){
        update();
        if(!game.animationRunning()) {
            TileRenderer.drawTile(g, rect, value);
        }
    }

    public int getValue(){
        return this.value;
    }

    private int rand2_4() {
        if((int)(Math.random() * 6) == 2) {
            return 4;
        }
        return 2;
    }

    public RoundRectangle2D getRRect() {
        return this.rect;
    }

    public void move(int direction){
        lastXPosition = (int)rect.getX();
        lastYPosition = (int)rect.getY();

        switch (direction){
            case InputManager.RIGHT:
                move(gX + 1, gY, direction);
                break;

            case InputManager.LEFT:
                move(gX - 1, gY, direction);
                break;

            case InputManager.UP:
                move(gX, gY - 1, direction);
                break;

            case InputManager.DOWN:
                move(gX, gY + 1, direction);
                break;
        }

        game.addAnimTile(this);
    }

    private void move(int _x, int _y, int direction) {
        merged = false;
        if(_x >= 0 && _x <= 3 && _y >= 0 && _y <= 3) {  //check if new position is not out of bounds
            if(grid.getTile(_x, _y) == null){
                moveTo(_x, _y);
                int dN = direction == InputManager.RIGHT || direction == InputManager.DOWN ? 1 : -1;
                if(direction > 1){      //0 and 1 are horizontal, so if it's bigger than 1, it's vertical
                    move(_x, _y + dN, direction);
                }else{
                    move(_x + dN, _y, direction);           //by calling this method the tile will automatically move until there's nowhere left to go
                }
            }else if(grid.getTile(_x, _y).getValue() == this.value && !grid.getTile(_x, _y).hasMerged()){
                grid.removeTile(_x, _y);
                this.value *= 2;

                game.addScore(this.value);
                merged = true;

                moveTo(_x, _y);
            }
        }
    }

    private void moveTo(int _x, int _y){
        game.tMoved = true; //tell the Game class that at least one tile has moved

        grid.updateTilePos(gX, gY, _x, _y, this);
        gX = _x;
        gY = _y;


        pX = grid.getTileXPos(gX);
        pY = grid.getTileYPos(gY);

        rect.setRoundRect(pX, pY, rect.getWidth(), rect.getHeight(), rect.getArcWidth(), rect.getArcHeight());
    }

    public boolean hasMerged(){
        return merged;
    }


    //animation movement stuff:
    public int getXPos(){
        return (int)rect.getX();
    }

    public int getYPos(){
        return (int)rect.getY();
    }

    public int getLastX(){
        return this.lastXPosition;
    }

    public int getLastY(){
        return this.lastYPosition;
    }

}
