package com.xieron.games.g2048.game;

import com.xieron.games.g2048.input.InputManager;
import com.xieron.games.g2048.ui.Colors;
import com.xieron.games.g2048.ui.Fonts;
import com.xieron.games.g2048.ui.Window;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Game {

    private final InputManager input;
    private final Fonts fonts;
    private final GameField gameField;

    private final int tilesX = 4, tilesY = 4;
    private final int tileSize = 110;

    public boolean tMoved = false;
    private boolean moving = false;
    private final int animSpeed = 8;

    private long score = 0;
    private long scoreToBeAdded = 0;
    private String scoreTXT = "Score: 0";

    private boolean gameOver;

    private final ArrayList<AnimationTile> animTiles;

    public Game(InputManager input) {
        int fieldHeight = 550;
        int border = 20;
        int fieldWidth = 550;
        int tileGap = (fieldHeight - (2 * border + tilesY * tileSize)) / (tilesY - 1);

        this.fonts = new Fonts(tileSize);
        this.input = input;
        this.gameField = new GameField(4, 4, Window.WIDTH / 2 - fieldWidth / 2, Window.HEIGHT - Window.WIDTH / 2 - fieldHeight / 2, fieldWidth, fieldHeight, 25, 25, tileSize, tileGap, border);

        animTiles = new ArrayList<>();
        TileRenderer.init(fonts);

        //F = fieldHeight, B = border, T = tileSize, G = tileGap
        //F = 2*B + (n-1)*G + n*T   | - (2*B + n*T)
        //F - (2*B + n*T) = (n-1)*G
        //(F - (2*B + n*T) / (n - 1) = G

        this.initTiles();
        this.initControls();

        addTile();
    }

    public void update(){
        if(moving){     //if the animation is ongoing
            for(AnimationTile t : animTiles){
                t.update();
            }
            if(animOver()){
                endRound();
            }
        }
    }

    public void render(Graphics2D g){
        g.setColor(Colors.BACKGROUND);
        g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);

        gameField.render(g);

        if(moving) {
            for (AnimationTile t : animTiles) {
                t.render(g);
            }
        }

        g.setFont(fonts.getFont(2));
        g.setColor(Color.WHITE);
        scoreTXT = String.format("Score: %d", score);
        g.drawString(scoreTXT, 35, 145);
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
        this.input.setRightAction(() -> moveTiles(InputManager.RIGHT));
        this.input.setLeftAction(() -> moveTiles(InputManager.LEFT));
        this.input.setDownAction(() -> moveTiles(InputManager.DOWN));
        this.input.setUpAction(() -> moveTiles(InputManager.UP));
    }

    private void moveH(int start, int count, int end, int direction){
        for(int y = 0; y < 4; y++){
            for(int x = start; x != end; x += count) {
                if(gameField.getTile(x, y) != null){
                    gameField.getTile(x, y).move(direction);
                }
            }
        }
    }

    private void moveV(int start, int count, int end, int direction){
        for(int x = 0; x < 4; x++){
            for(int y = start; y != end; y += count) {
                if(gameField.getTile(x, y) != null){
                    gameField.getTile(x, y).move(direction);
                }
            }
        }
    }

    public void moveTiles(int direction){
        if(moving){
            endRound();
        }
        animTiles.clear();
        switch(direction){
            case InputManager.RIGHT:
                moveH(3, -1, -1, direction);
                break;
            case InputManager.LEFT:
                moveH(0, 1, 4, direction);
                break;
            case InputManager.UP:
                moveV(0, 1, 4, direction);
                break;
            case InputManager.DOWN:
                moveV(3, -1, -1, direction);
                break;
        }

        if(tMoved) {    //only generate a new Tile if there has been actual movement (otherwise just a button press would generate new tiles)
            startAnimation();

            tMoved = false;
            //addTile();
        }
    }

    private void startAnimation(){
        for(AnimationTile t : animTiles){
            t.startMoving();
        }
        moving = true;  //start animation
    }

    public void addAnimTile(Tile in){
        if(in != null){
            AnimationTile aT = new AnimationTile(in.getLastX(), in.getLastY(), in.getXPos(), in.getYPos(), animSpeed, in.getValue(), in.getRRect());
            if(in.hasMerged()){
                aT.setMerging();
            }
            animTiles.add(aT);
        }
    }

    private boolean animOver(){
        for(AnimationTile t : animTiles){
            if(t.isMoving()) {
                return false;
            }
        }
        return true;
    }

    public boolean animationRunning(){
        return this.moving;
    }

    public void addScore(int value){
        this.scoreToBeAdded += value;
    }

    private void endRound(){
        moving = false;
        addTile();
        this.score += scoreToBeAdded;
        scoreToBeAdded = 0;
        checkGameOver();
    }

    private void checkGameOver(){
        if(gameField.isFull()){
            if(!gameField.possibleMove()){
                gameOver = true;
            }
        }
    }

}
