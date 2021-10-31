package com.xieron.games.g2048.game;

import com.xieron.games.g2048.input.InputManager;
import com.xieron.games.g2048.ui.Colors;
import com.xieron.games.g2048.ui.Fonts;
import com.xieron.games.g2048.ui.UIButton;
import com.xieron.games.g2048.ui.Window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.RoundRectangle2D;
import java.io.*;
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

    private long highScore = 0;
    private boolean highScoreBroken = false;
    private final RoundRectangle2D highScoreBG;
    private final Font hsFont;
    private long score = 0;
    private long scoreToBeAdded = 0;
    private String scoreTXT = "Score: 0";

    private boolean gameOver;
    private final GameOverScreen gOScreen;

    private final UIButton btnReset;

    private final ArrayList<AnimationTile> animTiles;

    public Game(InputManager input) {
        int fieldHeight = 550;
        int border = 20;
        int fieldWidth = 550;
        int tileGap = (fieldHeight - (2 * border + tilesY * tileSize)) / (tilesY - 1);

        //F = fieldHeight, B = border, T = tileSize, G = tileGap
        //F = 2*B + (n-1)*G + n*T   | - (2*B + n*T)
        //F - (2*B + n*T) = (n-1)*G
        //(F - (2*B + n*T) / (n - 1) = G

        this.fonts = new Fonts(tileSize);
        this.input = input;
        this.gameField = new GameField(4, 4, Window.WIDTH / 2 - fieldWidth / 2, Window.HEIGHT - Window.WIDTH / 2 - fieldHeight / 2, fieldWidth, fieldHeight, 25, 25, tileSize, tileGap, border);

        animTiles = new ArrayList<>();
        TileRenderer.init(fonts);

        this.btnReset = new UIButton(Window.WIDTH - 225, 20, 200, 35, "New Game");
        this.btnReset.setTask(this::resetGame);
        input.addButton(btnReset);

        loadHighScore();

        this.gOScreen = new GameOverScreen(input, this);
        this.highScoreBG = new RoundRectangle2D.Float(33, 20, 200, 65, 15, 15);
        hsFont = new Font("Arial", Font.PLAIN, 21);

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

        g.setColor(Color.DARK_GRAY);
        g.fill(highScoreBG);

        g.setFont(fonts.getFont(2));
        g.setColor(Color.WHITE);
        scoreTXT = String.format("Score: %d", score);
        g.drawString(scoreTXT, 35, 145);

        btnReset.render(g);

        g.setFont(hsFont);
        g.drawString("Highscore:", (int)(highScoreBG.getX() + highScoreBG.getWidth() / 2 - g.getFontMetrics().stringWidth("Highscore:") / 2), (int)highScoreBG.getY() + g.getFontMetrics().getHeight());
        g.drawString(Long.toString(highScore), (int)(highScoreBG.getX() + highScoreBG.getWidth() / 2 - g.getFontMetrics().stringWidth(Long.toString(highScore)) / 2), (int)(highScoreBG.getY() + g.getFontMetrics().getHeight() * 2 + 5));


        if(gameOver) {
            gOScreen.render(g);
        }
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

        Tile tile = new Tile(x, y, tileSize, tileSize, pos.x, pos.y, gameField, this);
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

        if(tMoved) {
            startAnimation();
            tMoved = false;
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

        checkHighScore();

        checkGameOver();
    }

    private void checkHighScore(){
        if(score > highScore){
            highScoreBroken = true;
            highScore = score;
        }
    }

    public boolean highScoreWasBroken(){
        return highScoreBroken;
    }

    public long getHighScore(){
        return this.highScore;
    }

    private void loadHighScore() {
        String file = System.getenv("APPDATA") + "\\hf2048.hscr";
        File f = new File(file);
        if(f.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(f));
                String input = reader.readLine();
                this.highScore = Long.parseLong(input);

                reader.close();
            }catch(Exception exc){
                exc.printStackTrace();
            }
        }else{
            this.highScore = 0;
        }
    }

    private void saveHighScore(){
        String file = System.getenv("APPDATA") + "\\hf2048.hscr";
        File f = new File(file);

        if(f.exists()){
            f.delete();
        }

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            String output = Long.toString(this.highScore);
            writer.write(output);

            writer.close();
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }

    private void checkGameOver(){
        if(gameField.isFull()){
            if(!gameField.possibleMove()){
                gameOver = true;
                //gOScreen.setActive(true);
                gOScreen.startGOScreen();
                btnReset.setActive(false);
            }
        }
    }

    public void resetGame(){
        saveHighScore();
        moving = false;
        this.score = 0;
        this.scoreToBeAdded = 0;
        this.initTiles();
        animTiles.clear();
        gameOver = false;
        addTile();
        gOScreen.setActive(false);
        btnReset.setActive(true);
        highScoreBroken = false;
    }

}
