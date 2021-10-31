package com.xieron.games.g2048.game;

import com.xieron.games.g2048.ui.Colors;

import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

public class GameField {

    private final int tilesX;
    private final int tilesY;
    private final RoundRectangle2D field;
    private RoundRectangle2D[][] tileBG;

    private Tile[][] tiles;
    private final int border;
    private final int tileGap;
    private final int tileSize;

    public GameField(int tilesX, int tilesY, int xPos, int yPos, int width, int height, int arcW, int arcH, int tileSize, int tileGap, int border){
        this.tilesX = tilesX;
        this.tilesY = tilesY;

        field = new RoundRectangle2D.Float(xPos, yPos, width, height, arcW, arcH);

        this.border = border;
        this.tileGap = tileGap;
        this.tileSize = tileSize;
        initTileBG();
    }


    public void render(Graphics2D g){

        g.setColor(Colors.GAMEFIELD);
        g.fill(field);

        //tile background
        g.setColor(Colors.TILE[0]);
        for(int i = 0; i < tilesX; i++){
            for(int j = 0; j < tilesY; j++){
                g.fill(tileBG[i][j]);
            }
        }

        for(int i = 0; i < tilesX; i++){
            for(int j = 0; j < tilesY; j++){
                if(tiles[i][j] != null){
                    tiles[i][j].render(g);
                }
            }
        }

    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public void addTile(Tile t, int i, int j){
        tiles[i][j] = t;
    }

    public Tile getTile(int x, int y){
        return tiles[x][y];
    }

    private void initTileBG() {
        tileBG = new RoundRectangle2D[tilesX][tilesY];

        for(int i = 0; i < tilesX; i++){
            for(int j = 0; j < tilesY; j++){
                int x = (int)(field.getX() + border + i * (tileSize + tileGap));
                int y = (int)(field.getY() + border + j * (tileSize + tileGap));

                tileBG[i][j] = new RoundRectangle2D.Float(x, y, tileSize, tileSize, 10, 10);
            }
        }
    }

    public boolean isFull(){
        for(int i = 0; i < tilesX; i++){
            for(int j = 0; j < tilesY; j++){
                if(tiles[i][j] == null){
                    return false;
                }
            }
        }

        return true;
    }

    public boolean possibleMove(){
        //check for every tile if there's at least one tile with the same value next to it
        for(int i = 0; i < tilesX; i++){
            for(int j = 0; j < tilesY; j++){
                int val = getTile(i, j).getValue();
                if(i != 0){
                    if(getTile(i - 1, j).getValue() == val){
                        return true;
                    }
                }

                if(j != 0){
                    if(getTile(i, j - 1).getValue() == val){
                        return true;
                    }
                }

                if(i < tilesX - 1){
                    if(getTile(i + 1, j).getValue() == val){
                        return true;
                    }
                }

                if(j < tilesY - 1){
                    if(getTile(i, j + 1).getValue() == val){
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public int getTileXPos(int xIndex){
        return (int)(field.getX() + border + xIndex * (tileSize + tileGap));
    }

    public int getTileYPos(int yIndex){
        return (int)(field.getY() + border + yIndex * (tileSize + tileGap));
    }

    public void updateTilePos(int cX, int cY, int nX, int nY, Tile t){
        tiles[nX][nY] = t;
        tiles[cX][cY] = null;
    }

    public void removeTile(int cX, int cY){
        tiles[cX][cY] = null;
    }

}
