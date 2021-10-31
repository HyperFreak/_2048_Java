package com.xieron.games.g2048.game;

import com.xieron.games.g2048.input.InputManager;
import com.xieron.games.g2048.ui.UIButton;
import com.xieron.games.g2048.ui.Window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class GameOverScreen {

    private final Game game;

    private final UIButton newGameBtn;
    private final String gOText = "Game Over!";
    private final String nHS = "New Highscore!";

    private final Font tFont;
    private final Font tFont2;    //for highscore thing

    private int alpha = 0;
    private int fadeSpeed = 20;
    boolean fading = false;    //0 = no fading, 1 = fade in, -1 = fade out

    public GameOverScreen(InputManager input, Game game){
        this.game = game;
        this.newGameBtn = new UIButton(Window.WIDTH / 2 - 125, Window.HEIGHT / 2, 250, 45, "New Game");
        newGameBtn.setTask(game::resetGame);
        newGameBtn.setActive(false);
        input.addButton(newGameBtn);

        this.tFont = new Font("Arial", Font.BOLD, 86);
        this.tFont2 = new Font("Arial", Font.BOLD, 52);

    }

    private void update(){
        if(fading){
            if(alpha < 220){
                alpha += fadeSpeed;

                if(alpha > 220){
                    alpha = 220;
                    fading = false;
                }
            }
            newGameBtn.setAlpha(alpha);
        }

        newGameBtn.setActive(alpha >= 130);
    }

    public void render(Graphics2D g){
        update();

        g.setColor(new Color(0, 0, 0, alpha));    //black transparent background
        g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);

        g.setColor(new Color(255, 255, 255, alpha + 35));
        g.setFont(tFont);


        if(game.highScoreWasBroken()){
            g.drawString(gOText, Window.WIDTH / 2 - g.getFontMetrics().stringWidth(gOText) / 2, Window.HEIGHT / 2 - 180);
            g.setFont(tFont2);
            g.drawString(nHS, Window.WIDTH / 2 - g.getFontMetrics().stringWidth(nHS) / 2, Window.HEIGHT / 2 - 110);
            String hs = Long.toString(game.getHighScore());
            g.drawString(hs, Window.WIDTH / 2 - g.getFontMetrics().stringWidth(hs) / 2, Window.HEIGHT / 2 - 35);

        }else{
            g.drawString(gOText, Window.WIDTH / 2 - g.getFontMetrics().stringWidth(gOText) / 2, Window.HEIGHT / 2 - 50);
        }

        newGameBtn.render(g);
    }

    public void setActive(boolean b){
        this.newGameBtn.setActive(b);
    }

    public void startGOScreen(){
        alpha = 0;
        fading = true;
    }

}
