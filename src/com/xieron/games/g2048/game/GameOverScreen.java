package com.xieron.games.g2048.game;

import com.xieron.games.g2048.ui.UIButton;

import java.awt.Color;
import java.awt.Graphics2D;

public class GameOverScreen {

    private UIButton newGameBtn;

    private int screenWidth, screenHeight;

    public GameOverScreen(int screenWidth, int screenHeight){
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
    }

    public void render(Graphics2D g){
        g.setColor(new Color(0, 0, 0, 140));    //black transparent background
        g.fillRect(0, 0, screenWidth, screenHeight);
    }



}
