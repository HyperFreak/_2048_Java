package com.xieron.games.g2048.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

public class AnimationTile {

    private int xPos;
    private int yPos;

    private int destX;
    private int destY;

    private int speed;

    private int value;

    private RoundRectangle2D rect;

    private boolean moving = false;

    public AnimationTile(int startX, int startY, int destX, int destY, int animTime, int value, RoundRectangle2D base){
        this.xPos = startX;
        this.yPos = startY;
        this.destX = destX;
        this.destY = destY;
        this.value = value;
        this.rect = new RoundRectangle2D.Double(xPos, yPos, base.getWidth(), base.getHeight(), base.getArcWidth(), base.getArcHeight());

        int distanceX = destX - startX;
        int distanceY = destY - startY;
        int distance = distanceX + distanceY;   //one of them will be 0, by adding them there's no need to check which one's 0
        if(distance < 0) {
            distance *= -1;
        }

        //speed = px/frame
        //animTime = X frames      distance = Y px
        this.speed = distance / animTime;

        //System.out.println(this);
    }

    public void update(){
        if(moving){
            move();
            updateRectPos();

            if(xPos == destX && yPos == destY){
                moving = false;
            }
        }
    }

    private void move(){
        if(xPos < destX){
            xPos += speed;
            if(xPos > destX){
                xPos = destX;
            }
        }else if(xPos > destX){
            xPos -= speed;
            if(xPos < destX){
                xPos = destX;
            }
        }else if(yPos < destY){
            yPos += speed;
            if(yPos > destY){
                yPos = destY;
            }
        }else if(yPos > destY){
            yPos -= speed;
            if(yPos < destY){
                yPos = destY;
            }
        }
    }

    private void updateRectPos(){
        this.rect.setRoundRect(xPos, yPos, rect.getWidth(), rect.getHeight(), rect.getArcWidth(), rect.getArcHeight());
    }

    public void render(Graphics2D g){
        TileRenderer.drawTile(g, rect, value);
    }

    public boolean isMoving(){
        return this.moving;
    }

    public void startMoving(){
        this.moving = true;
    }

    public void setMerging(){
        this.value /= 2;
    }

    @Override
    public String toString(){
        return String.format("startPos: %d : %d | destPos: %d : %d | value: %d | Rect: ", xPos, yPos, destX, destY, value) + rect.toString();
    }

}
