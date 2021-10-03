package com.xieron.games.g2048.ui;

import java.awt.Color;

public class Colors {

    public static final Color BACKGROUND = new Color(0x3A, 0x50, 0x6B);
    public static final Color GAMEFIELD =  new Color(0x1C, 0x25, 0x41);

    public static final Color[] TILE = {
            Color.LIGHT_GRAY,
            Color.WHITE,    //2
            new Color(0x9B, 0xA9, 0xD4),//4
            new Color(0x8C, 0xD2, 0xD1),//8
            new Color(0x57, 0x6E, 0xB7),//16
            new Color(0x45, 0x5B, 0xA2),//32
            new Color(0x2A, 0x4D, 0xBD),//64
            new Color(0x46, 0x8F, 0x99),//128
            new Color(0x3A, 0x76, 0x7E),//256
            new Color(0x2C, 0x81, 0x8C),//512
            new Color(0x35, 0x9D, 0xAA),//1024
            new Color(0x4B, 0xB9, 0xC7),//2048
            new Color(0x21, 0x60, 0x68),//4096
            Color.BLACK//8192 and further
    };

}
