package com.xieron.games.g2048.ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class UIButton {

    private boolean pressed = false;
    private boolean hovered = false;

    private String text;
    private Runnable buttonTask;

    private final RoundRectangle2D bounds;

    private Color normalCol;
    private final Color hoverCol;
    private final Color pressCol;

    private final Font font;

    private boolean active = true;

    public UIButton(int x, int y, int width, int height, String text){
        this.bounds = new RoundRectangle2D.Float(x, y, width, height, 10, 10);

        this.text = text;

        //placeholder colors
        normalCol = new Color(0x3A, 0x5D, 0xCD);
        hoverCol = new Color(0x1A, 0x3D, 0xAD);
        pressCol = new Color(0x0A, 0x1D, 0x8D);

        font = new Font("Arial", Font.BOLD, (int)((float)height / 1.5F));
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

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(text, (int)(bounds.getX() + bounds.getWidth() / 2 - g.getFontMetrics().stringWidth(text) / 2), (int)(bounds.getY() + bounds.getHeight() / 2 + g.getFontMetrics().getHeight() / 3));
    }

    public void mouseMoved(Point mPos){
        hovered = bounds.contains(mPos);
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

    public void setTask(Runnable task){
        this.buttonTask = task;
    }

    //specifically for Game Over Screen:
    public void setAlpha(int alpha){
        this.normalCol = new Color(normalCol.getRed(), normalCol.getGreen(), normalCol.getBlue(), alpha);
    }

}
