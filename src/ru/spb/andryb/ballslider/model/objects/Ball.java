/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.spb.andryb.ballslider.model.objects;

import ru.spb.andryb.ballslider.Constants;

/**
 * @author Administrator
 */
public class Ball extends GameObject {
    /**
     * Accelerate (offset) in X coordinate in each movement
     */
    private float dx;
    /**
     * Accelerate (offset) in Ycoordinate in each movement
     */
    private float dy;
    
    /**
     * User action to left.
     */
    private boolean movementByPlayerToLeft;
    /**
     * User action to right.
     */
    private boolean movementByPlayerToRight;
    
    /**
     * Default Ball constructor.
     * @param x, X coordinate (of top left corner).
     * @param y, Y coordinate (of top left corner).
     * @param radius, ball radius.
     */
    public Ball(int x, int y, int radius) {
        super(x, y, radius, radius);
        
        this.dx = 0;
        this.dy = 0;
    }
    
    // ################################################################################
    // Required changing move and intersectionWithStair methods by math coord. and etc.
    // ################################################################################
    
    /**
     * Calculating next ball coordinates (
     * @param movingBallToLeft
     * @param movingBallToRight 
     */
    public void move(boolean movingBallToLeft, boolean movingBallToRight) {
        movementByPlayerToRight = movingBallToRight;
        movementByPlayerToLeft = movingBallToLeft;
        
        if ( movingBallToLeft || movingBallToRight)
        	System.out.println("R: " + movingBallToRight + " L: " + movingBallToLeft);
        
        dx += (float)((movingBallToLeft == true) 
                ? -Constants.OFFSET_BALL_IN_X * Constants.OFFSET_TAP_INCREMENT
                : (movingBallToRight == true) 
                ? Constants.OFFSET_BALL_IN_X * Constants.OFFSET_TAP_INCREMENT
                : 0.0);

        toRealCoordinates();
        x += dx;
        
        dy += Constants.OFFSET_BALL_IN_Y;
        y += dy;
    }
            
    public boolean intersectionWithStair(Stair stair) {
        // Getting previous changings
        float previousDx = dx;
        float previousDy = dy;
        float previousY = y - dy;
        boolean angleChanged = false;
        
        float offsetInY = 0;

        dy = (dy > 0.2) ? (-dy*0.5F) : 0;

        if (stair.getX() > x+width/2) {
            dx -= Constants.STAIR_X_ANGLE_FACTOR * (
                    stair.getX() 
                    - (x+width/2)
                    );
            offsetInY = (float) (
                (width/2)
                - Math.sqrt(
                    (width/2)*(width/2) 
                    - (
                        stair.getX()
                        - (x+width/2)
                    ) * (
                        stair.getX()
                        - (x+width/2)
                    )
                )
            );
            angleChanged = true;
        } else if (stair.getX()+stair.getWidth() < x+width/2) {
            dx += Constants.STAIR_X_ANGLE_FACTOR * (
                    (x+width/2) 
                    - stair.getX()
                    - stair.getWidth()
                    );
            offsetInY = (float) (
                (width/2)
                - Math.sqrt(
                    (width/2)*(width/2) 
                    - (
                        (x+width/2)
                        - stair.getX()
                        - stair.getWidth()
                    ) * (
                        (x+width/2)
                        - stair.getX()
                        - stair.getWidth() 
                    )
                )
            );
            
//            if (Float.isNaN(offsetInY))
//                System.err.println("OFFSET: " + offsetInY);

            angleChanged = true;
        }

        // Canceling previous X changing
        x -= previousDx;

        if (dx != 0) {
            if (dx > 0 && movementByPlayerToRight == false) {
                dx -= Constants.OFFSET_BALL_IN_X;
                if (dx < 0)
                    dx = 0;
            }
            if (dx < 0 && movementByPlayerToLeft == false) {
                dx += Constants.OFFSET_BALL_IN_X;
                if (dx > 0)
                    dx = 0;
            }
        }
        
        y = stair.getY() - height + offsetInY;
        
        // Получить текущий дх и ду, сравнить координаты по уравнению с предыдущим дх и ду
        // If angle has been changed
        //if (y - previousY > Math.abs(dx) && angleChanged) {
//        if (Math.abs(dx) > 1 && angleChanged) {
//            // Canceling Y changings
//            y = previousY + previousDy;
//            dy = previousDy;
//        }        
        if (y + dy > previousY) {
            // Canceling Y changings
            y = previousY + previousDy;
            dy = previousDy;
        }
        
        toRealCoordinates();
        x += dx;
        return true;
    }
    
    private void toRealCoordinates() {
//        if (x > Constants.FIELD_WIDTH)
//            x -= Constants.FIELD_WIDTH;
//        else if (x < 0)
//            x += Constants.FIELD_WIDTH;
        if (x + width > Constants.FIELD_WIDTH) {
            x -= x + width - Constants.FIELD_WIDTH;
            dx *= -Constants.WALL_INVERSE_ACCEELRATE_FACTOR;
        } else if (x < 0) {
            x *= -1;
            dx *= -Constants.WALL_INVERSE_ACCEELRATE_FACTOR;
        }
    }
    
    public float getDx() {return dx;}
//    public void setDx(float dx) {this.dx = dx;}

    public float getDy() {return dy;}
//    public void setDy(float dy) {this.dy = dy;}
}
