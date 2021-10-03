package com.xieron.games.g2048.ui;

import java.awt.Font;

public class Fonts {

    private Font f1;
    private Font f2;
    private Font f3;

    private final String ft = "Arial"; //ft = font type

    public Fonts(int tileSize){
        f1 = new Font(ft, Font.BOLD, tileSize / 2);
        f2 = new Font(ft, Font.BOLD, tileSize / 3);
        f3 = new Font(ft, Font.BOLD, tileSize / 4);
    }

    public Font getFont(int value){
        if(value > 100){
            if(value > 10000){
                return f3;
            }
            return f2;
        }

        return f1;
    }

}
