package com.xieron.games.g2048.input;

import com.xieron.games.g2048.main.Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener {

    private Runnable pressLeft;
    private Runnable pressRight;
    private Runnable pressUp;
    private Runnable pressDown;

    private Runnable pressEnter;
    private Runnable pressEsc;
    private Runnable pressBack;

    private boolean rightPressed = false;
    private boolean leftPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    private boolean dPressed = false;
    private boolean aPressed = false;
    private boolean wPressed = false;
    private boolean sPressed = false;

    private boolean enterPressed = false;
    private boolean escPressed = false;
    private boolean backPressed = false;

    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;


    public InputManager(){
        pressLeft = this::noFunc;
        pressRight = this::noFunc;
        pressUp = this::noFunc;
        pressDown = this::noFunc;

        pressEnter = this::noFunc;
        pressEsc = this::noFunc;
        pressBack = this::noFunc;
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            //left / A
            case KeyEvent.VK_A:
                if(!aPressed){
                    aPressed = true;
                    pressLeft.run();
                }
                break;
            case KeyEvent.VK_LEFT:
                if(!leftPressed){
                    leftPressed = true;
                    pressLeft.run();
                }
                break;

                //right / D
            case KeyEvent.VK_D:
                if(!dPressed){
                    dPressed = true;
                    pressRight.run();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(!rightPressed){
                    rightPressed = true;
                    pressRight.run();
                }
                break;

                //up / W
            case KeyEvent.VK_W:
                if(!wPressed){
                    wPressed = true;
                    pressUp.run();
                }
                break;
            case KeyEvent.VK_UP:
                if(!upPressed){
                    upPressed = true;
                    pressUp.run();
                }
                break;

                //down / S
            case KeyEvent.VK_S:
                if(!sPressed){
                    sPressed = true;
                    pressDown.run();
                }
                break;
            case KeyEvent.VK_DOWN:
                if(!downPressed){
                    downPressed = true;
                    pressDown.run();
                }
                break;

                //enter / escape
            case KeyEvent.VK_ENTER:
                if(!enterPressed){
                    enterPressed = true;
                    pressEnter.run();
                }
                break;
            case KeyEvent.VK_ESCAPE:
                if(!escPressed){
                    escPressed = true;
                    pressEsc.run();
                }
                break;
            case KeyEvent.VK_BACK_SPACE:
                if(!backPressed){
                    backPressed = true;
                    pressBack.run();
                }
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){
            //left / A
            case KeyEvent.VK_A:
                aPressed = false;
                break;
            case KeyEvent.VK_LEFT:
                leftPressed = false;
                break;

                //right / D
            case KeyEvent.VK_D:
                dPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                break;

                //up / W
            case KeyEvent.VK_W:
                wPressed = false;
                break;
            case KeyEvent.VK_UP:
                upPressed = false;
                break;

                //down / S
            case KeyEvent.VK_S:
                sPressed = false;
                break;
            case KeyEvent.VK_DOWN:
                downPressed = false;
                break;

                //enter / escape
            case KeyEvent.VK_ENTER:
                enterPressed = false;
                break;
            case KeyEvent.VK_ESCAPE:
                escPressed = false;
                break;
            case KeyEvent.VK_BACK_SPACE:
                backPressed = false;
                break;
        }
    }




    //public setters
    public void setLeftAction(Runnable task){
        this.pressLeft = task;
    }

    public void setRightAction(Runnable task){
        this.pressRight = task;
    }

    public void setUpAction(Runnable task){
        this.pressUp = task;
    }

    public void setDownAction(Runnable task){
        this.pressDown = task;
    }

    public void setEnterAction(Runnable task){
        this.pressEnter = task;
    }

    public void setEscAction(Runnable task){
        this.pressEsc = task;
    }

    public void setBackAction(Runnable task){
        this.pressBack = task;
    }


    //public getters
    public boolean isRightPressed() {
        return this.rightPressed;
    }

    public boolean isLeftPressed() {
        return this.leftPressed;
    }

    public boolean isUpPressed() {
        return this.upPressed;
    }

    public boolean isDownPressed() {
        return this.downPressed;
    }

    public boolean isDPressed() {
        return this.dPressed;
    }

    public boolean isAPressed() {
        return this.aPressed;
    }

    public boolean isWPressed() {
        return this.wPressed;
    }

    public boolean isSPressed() {
        return this.sPressed;
    }

    public boolean isEnterPressed(){
        return this.enterPressed;
    }

    public boolean isEscPressed(){
        return this.escPressed;
    }

    public boolean isBackPressed(){
        return this.backPressed;
    }


    private void noFunc(){
        System.out.println("No Function has been assigned to this key yet!");
    }

}
