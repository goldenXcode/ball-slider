/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.spb.andryb.ballslider;

/**
 * Constants for project.
 * @author Admin
 */
public class Constants {
    /**
     * Player controller: tap on screen or accelerometer
     */
    public static final boolean ACCELEROMETER_ENABLED           = true;
    /**
     * Timer delay. (in milliseconds).
     */
    public static final int TIMER_DELAY_ms                      = 5;
    /**
     * Ball moving  delay, in X and Y coord-s). (in timers ticks)
     */
    public static final int BALL_MOVE_DELAY                     = 3;
    /**
     * Objects moving up delay. (in timers ticks)
     */
    public static final int OBJECTS_MOVE_DELAY                  = 3;
    /**
     * Stairs creation delay. (in timer ticks)
     */
    public static final int STAIR_CREATION_DELAY                = 3;
    /**
     * Score creation delay. (in timer ticks)
     */
    public static final int SCORE_ADDING_DELAY					= 20;
    /**
     * Width of field in logic.
     */
    public static final int FIELD_WIDTH                         = 480*2;
    /**
     * Height of field in logic.
     */
    public static final int FIELD_HEIGHT                        = 850*2;
    
    /**
     * Minimal count of normal stairs in each moment.
     */
    public static final int MIN_NORMAL_STAIRS_COUNT             = 2;
    
    /**
     * Diameter of ball.
     */
    public static final int BALL_SIZE                           = (int)(FIELD_WIDTH*1.0/12);
    /**
     * Reducion acceleration of ball in X coordinates in specefied 
     * times at collision with wall. (Factor: 0..1)
     */
    public static final float WALL_INVERSE_ACCEELRATE_FACTOR    = 0.8F;
    
    /**
     * Chance to create stair, consiedering creation delay. (in percents: 0..100)
     */
    public static final int CHANCE_TO_CREATE_STAIR              = 50;
    /**
     * Delay of attempt to create. (in timer ticks)
     */
    public static final int CHANCE_TO_CREATE_STAIR_DELAY        = 10;
    /**
     * Minimum distance between last and new stairs.
     */
    public static final int MIN_DIST_TO_PREV_STAIR              = (int)(FIELD_HEIGHT*1.0/8);
    /**
     * Offset from the bottom of logical field to stair creation.
     */
    public static final int STAIR_CREATION_DOWN_OFFSET          = (int)(FIELD_HEIGHT*1.0/8);
    
    /**
     * Minimum width of stair.
     */
    public static final int MIN_STAIR_WIDTH                     = (int)(FIELD_WIDTH*1.0/5);    
    /**
     * Maximum width of stair.
     */
    public static final int MAX_STAIR_WIDTH                     = (int)(FIELD_WIDTH*1.0/2);
    /**
     * Height of stair.
     */
    public static final int STAIR_HEIGHT                        = (int)(FIELD_HEIGHT*1.0/50);
    /**
     * Acceleratin the ball in X coordinates in each moment, 
     * if ball is on the edge of the stair. (Factor: 0..1)
     */
    public static final float STAIR_X_ANGLE_FACTOR              = 0.01F;
    
    /**
     * Slowing the ball at contact with objects. (Factor: 0..1)
     */
    public static final float OFFSET_BALL_IN_X                  = FIELD_WIDTH*1.0F/9000;
    /**
     * Acceleratin the ball in Y coordinates in each moment, 
     * if ball is free. (Factor: 0..1)
     */
    public static final float OFFSET_BALL_IN_Y                  = FIELD_WIDTH*1.0F/3500;
    /**
     * Offset factor Y coordinate, relative to number of timer ticks, 
     * when stair is moving up.
     * (Factor: 0..1)
     */
    public static final float OFFSET_STAIR_FACTOR_IN_Y          = 1.0F/5000;
    /**
     * Increase in ball acceleration in X coordinates 
     * when the user impact. (Factor)
     */
    public static final float OFFSET_TAP_INCREMENT              = 1.2F;
}
