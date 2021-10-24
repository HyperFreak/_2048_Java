package com.xieron.games.g2048.game;

import com.xieron.games.g2048.ui.Colors;
import com.xieron.games.g2048.ui.Fonts;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

public class TileRenderer {

    private static Fonts fontManager;

    public static void init(Fonts fm){
        fontManager = fm;
    }

    public static void drawTile(Graphics2D g, RoundRectangle2D rect, int value){
        int num = log2(value);

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

    //logarithm base 2
    private static int log2(int x){
        return (int) (Math.log(x) / Math.log(2));
    }

}
