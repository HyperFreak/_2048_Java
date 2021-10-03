package com.xieron.games.g2048.game;

import com.xieron.games.g2048.input.InputManager;
import com.xieron.games.g2048.ui.Colors;
import com.xieron.games.g2048.ui.Fonts;
import com.xieron.games.g2048.ui.Window;

import java.awt.Graphics2D;
import java.awt.Point;

public class Game {

    private InputManager input;

    private final int tilesX = 4, tilesY = 4;
    private GameField gameField;
    private final int fieldWidth = 550, fieldHeight = 550;
    private int tileGap = 25;
    private final int border = 20;
    private final int tileSize = 110;

    private boolean moving = false;
    private int tileCount = 0;
    private int stopCounter = 0;

    private Fonts fonts;

    public Game(InputManager input) {

        this.tileGap = (fieldHeight - (2 * border + tilesY * tileSize)) / (tilesY - 1);

        this.fonts = new Fonts(tileSize);

        this.input = input;
        this.gameField = new GameField(4, 4, Window.WIDTH / 2 - fieldWidth / 2, Window.HEIGHT - Window.WIDTH / 2 - fieldHeight / 2, fieldWidth, fieldHeight, 25, 25, tileSize, tileGap, border);

        //F = fieldHeight, B = border, T = tileSize, G = tileGap
        //F = 2*B + (n-1)*G + n*T   | - (2*B + n*T)
        //F - (2*B + n*T) = (n-1)*G
        //(F - (2*B + n*T) / (n - 1) = G



        this.initTiles();
        this.initControls();

        addTile();

    }

    public void update(){

    }

    public void render(Graphics2D g){
        g.setColor(Colors.BACKGROUND);
        g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);


        gameField.render(g);

    }

    private void initTiles() {
        Tile[][] tiles = new Tile[this.tilesX][this.tilesY];

        for(int i = 0; i < this.tilesX; i++){
            for(int j = 0; j < this.tilesY; j++){
                /*int x = (int)(gameField.getField().getX() + border + i * (tileSize + tileGap));
                int y = (int)(gameField.getField().getY() + border + j * (tileSize + tileGap));*/

                tiles[i][j] = null;//new Tile(x, y, tileSize, tileSize, fonts, i, j, gameField);
            }
        }

        this.gameField.setTiles(tiles);
    }

    private void moveTiles(byte dir){

        if(!moving) {
            moving = true;

            tileCount = 0;


            switch (dir) {
                case 0: //right

                    //TODO: move, starting right first then blah blah.... also. Add animation

                    for (int y = 0; y < tilesY; y++) {
                        for (int x = tilesX - 2; x >= 0; x--) {
                            if (gameField.getTile(x, y) != null) {
                                gameField.getTile(x, y).moveRight();

                                tileCount++;
                            }
                        }
                    }
                    //gameField.printTileArray();
                    break;
                case 1: //left
                    for (int y = 0; y < tilesY; y++) {
                        for (int x = 1; x < tilesX; x++) {
                            if (gameField.getTile(x, y) != null) {
                                gameField.getTile(x, y).moveLeft();

                                tileCount++;
                            }
                        }
                    }

                    break;
                case 2: //up
                    for (int x = 0; x < tilesX; x++) {
                        for (int y = 1; y < tilesY; y++) {
                            if (gameField.getTile(x, y) != null) {
                                gameField.getTile(x, y).moveUp();

                                tileCount++;
                            }
                        }
                    }

                    break;
                case 3: //down
                    for (int x = 0; x < tilesX; x++) {
                        for (int y = 2; y >= 0; y--) {
                            if (gameField.getTile(x, y) != null) {
                                gameField.getTile(x, y).moveDown();

                                tileCount++;
                            }
                        }
                    }
                    break;
            }
        }

    }

    public void stopMoving(){
        stopCounter++;
        if(stopCounter >= tileCount){
            stopCounter = 0;
            moving = false;
            addTile();
        }
    }

    private void addTile(){
        if(gameField.isFull()){
            return;
        }
        Point pos = randomFreeGridPos();

        int x = gameField.getTileXPos(pos.x);
        int y = gameField.getTileYPos(pos.y);

        Tile tile = new Tile(x, y, tileSize, tileSize, fonts, pos.x, pos.y, gameField, this);

        //System.out.println(String.format("Tile[%d][%d] = %d, %d", pos.x, pos.y, x, y));

        gameField.addTile(tile, pos.x, pos.y);
    }

    private Point randomFreeGridPos(){
        Point p = new Point(-1, -1);

        p.x = (int)(Math.random() * tilesX);
        p.y = (int)(Math.random() * tilesY);

        if(gameField.getTile(p.x, p.y) != null){
            p = randomFreeGridPos();
        }

        return p;
    }

    private void initControls() {
        this.input.setRightAction(() -> moveTiles((byte)0));
        this.input.setLeftAction(() -> moveTiles((byte)1));
        this.input.setDownAction(() -> moveTiles((byte)3));
        this.input.setUpAction(() -> moveTiles((byte)2));



    }



}
