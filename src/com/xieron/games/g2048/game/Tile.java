package com.xieron.games.g2048.game;

import com.xieron.games.g2048.input.InputManager;
import com.xieron.games.g2048.ui.Colors;
import com.xieron.games.g2048.ui.Fonts;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

public class Tile {

    private int num;
    private int value;

    private int pX, pY; //pixel position
    private int gX, gY; //grid position
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
        if((int)(Math.random() * 6) == 2) {
            return 4;
        }
        return 2;
    }

    public void move(int direction){
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
                this.num++;

                merged = true;

                moveTo(_x, _y);
            }
        }

    }

    private void moveTo(int _x, int _y){
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

}
