package com.xieron.games.g2048.ui;

import com.xieron.games.g2048.game.Game;
import com.xieron.games.g2048.input.InputManager;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends JPanel {

    private final Timer timer;
    private final Game game;

    public GamePanel(){
        super();
        this.timer = new Timer();
        InputManager inputManager = new InputManager();

        this.addKeyListener(inputManager);
        this.addMouseListener(inputManager);
        this.addMouseMotionListener(inputManager);

        this.setFocusable(true);
        this.requestFocus();

        this.game = new Game(inputManager);
    }

    @Override
    public void addNotify(){
        super.addNotify();

        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0, 1000 / 60);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        this.game.update();
        this.game.render(g2D);

        g2D.dispose();
    }

}
