package com.xieron.games.g2048.input;

import com.xieron.games.g2048.ui.UIButton;

import java.awt.event.*;
import java.util.ArrayList;

public class InputManager implements KeyListener, MouseListener, MouseMotionListener {

    private Runnable pressLeft;
    private Runnable pressRight;
    private Runnable pressUp;
    private Runnable pressDown;

    private boolean rightPressed = false;
    private boolean leftPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    private boolean dPressed = false;
    private boolean aPressed = false;
    private boolean wPressed = false;
    private boolean sPressed = false;

    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    private final ArrayList<UIButton> buttons;

    public InputManager(){
        pressLeft = this::noFunc;
        pressRight = this::noFunc;
        pressUp = this::noFunc;
        pressDown = this::noFunc;

        buttons = new ArrayList<>();
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

    private void noFunc(){
        System.out.println("No Function has been assigned to this key yet!");
    }

    //Mouse input

    @Override
    public void mousePressed(MouseEvent e) {
        if(!buttons.isEmpty()){
            for(UIButton b : buttons){
                if(b.isActive())
                    b.mousePressed(e.getPoint(), e.getButton());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!buttons.isEmpty()){
            for(UIButton b : buttons){
                if(b.isActive())
                    b.mouseReleased(e.getPoint(), e.getButton());
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!buttons.isEmpty()){
            for(UIButton b : buttons){
                if(b.isActive())
                    b.mouseMoved(e.getPoint());
            }
        }
    }

    public void addButton(UIButton btn){
        buttons.add(btn);
    }

    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}

}
