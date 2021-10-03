package com.xieron.games.g2048.ui;

import javax.swing.JFrame;

public class Window extends JFrame {

    public static final int WIDTH = 600, HEIGHT = 750;

    public Window(){
        super("2048");
        this.setSize(WIDTH + 15, HEIGHT + 39);
                            // + 16 because for some reason, the window wasn't actually 600PX wide, but 584PX
                            // same for the + 39 in the HEIGHT value, I really have no idea why it's doing that though...
        this.add(new GamePanel());

        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(3);
    }

}
