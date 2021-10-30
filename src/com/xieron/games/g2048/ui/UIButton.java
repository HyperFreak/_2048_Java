package com.xieron.games.g2048.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class UIButton {

    private boolean pressed = false;
    private boolean hovered = false;

    private String text;
    private Runnable buttonTask;

    private Rectangle bounds;

    private Color normalCol;
    private Color hoverCol;
    private Color pressCol;

    private boolean active = false;

    public UIButton(int x, int y, int width, int height, String text){
        this.bounds = new Rectangle(x, y, width, height);
        this.text = text;

        //placeholder colors
        normalCol = new Color(0xFFFF00);
        hoverCol = new Color(0xFFFFAA);
        pressCol = new Color(0xAAAA11);
    }

    public void render(Graphics2D g){
        if(pressed){
            g.setColor(pressCol);
        }else if(hovered){
            g.setColor(hoverCol);
        }else{
            g.setColor(normalCol);
        }
        g.fill(bounds);
    }

    public void setText(String text){
        this.text = text;
    }

    public void mouseMoved(Point mPos){
        if(bounds.contains(mPos)){
            hovered = true;
        }
    }

    public void mousePressed(Point mPos, int button){
        if(button == MouseEvent.BUTTON1){
            if(bounds.contains(mPos)){
                hovered = false;
                pressed = true;
            }
        }
    }

    public void mouseReleased(Point mPos, int button){
        if(button == MouseEvent.BUTTON1){
            if(bounds.contains(mPos)){
                buttonTask.run();
            }

            pressed = false;
        }
    }

    public void setActive(boolean b){
        this.active = b;
    }

    public boolean isActive() {
        return this.active;
    }

}
