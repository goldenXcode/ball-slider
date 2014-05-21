/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.spb.andryb.ballslider.model;

import ru.spb.andryb.ballslider.Constants;
import ru.spb.andryb.ballslider.MainActivity;
import ru.spb.andryb.ballslider.controller.ControllerInterface;
import ru.spb.andryb.ballslider.model.objects.Ball;
import ru.spb.andryb.ballslider.model.objects.GameObject;
import ru.spb.andryb.ballslider.model.objects.Stair;
import ru.spb.andryb.ballslider.model.objects.StairType;
import ru.spb.andryb.ballslider.view.ViewInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Administrator
 */
public class Logic implements ModelInterface{    
    /**
     * Main game timer for moving ball and objects, 
     * repaining game window and etc.
     */
    private Timer timer;
    /**
     * Task for main timer.
     */
    private TimerTask timerTask;
    /**
     * Count of main timer ticks from beginning game.
     */
    private long ticksCount;
    
    /**
     * True, if user activated left action,
     * False otherwise.
     */
    private boolean movingBallToLeft;
    /**
     * True, if user activated right action,
     * False otherwise.
     */
    private boolean movingBallToRight;
    /**
     * Current score of player.
     */
    private int score;
    /**
     * Current state of game.
     */
    private GameState state;
    /**
     * Ball :D
     */
    private Ball ball;
    /**
     * Collection with all game objects (for example - stairs),
     * except ball.
     */
    private ArrayList<GameObject> objects;
    
    /**
     * Link to realisation of view interface.
     */
    private ViewInterface view;    
    /**
     * Link to realisation of controller interface.
     */
    private ControllerInterface controller;
    
    /**
     * Logic default constructor.
     */
    public Logic(MainActivity activity) {
        this.ball = null;
        this.state = GameState.MENU;
        this.movingBallToLeft = false;
        this.movingBallToRight = false;
    }
    
    @Override
    public void setView(ViewInterface view) {
        this.view = view;
    }
    
    @Override
    public boolean StartGame() {
        if (state == GameState.STARTED)
            return false;
        
        initGame();
        initTimer();
        startTimer(0);
        
        Logger.getAnonymousLogger().info("GAME START");
        state = GameState.STARTED;
        return true;
    }
    
    /**
     * Initializing all required data.
     */
    private void initGame() {
        Logger.getAnonymousLogger().info("GAME INIT");
        
        int y;          // Y coord. for first stair and ball;
        Stair stair;    // First stair to creation.
        Random r;       // Random for getting Y coordinate.
        
        r = new Random();
        y = r.nextInt((int)(Constants.FIELD_HEIGHT*1.0/3)) 
                + (int)(Constants.FIELD_HEIGHT*2.0/3);
        
        // Creation first stair.
        stair = Stair.create(y, StairType.NORMAL);
        
        objects = new ArrayList<>();
        objects.add(stair);
        
        score = 0;
        
        // Creation ball on stair.
        ball = new Ball(
                (int) Math.round(stair.getX() + stair.getWidth()*1.0/2), 
                y - Constants.BALL_SIZE, 
                Constants.BALL_SIZE
        );
    }
    
    /**
     * Initializing all data for timer, including task.
     * Timer task includes:
     * - Objects movements;
     * - Ball movement;
     * - Check on GameOver;
     * - Objects creation;
     * - Request for repaint game window.
     */
    private void initTimer() {
        Logger.getAnonymousLogger().info("TIMER INIT");
        timer = new Timer();
        
        timerTask = new TimerTask() {
            @Override
            // On timer tick
            public void run() {     
                // Objects (only stairs) movements, if it's necessary
                if (ticksCount % Constants.OBJECTS_MOVE_DELAY == 0) {
                    for (int i = 0; i < objects.size(); i++) {
                        GameObject current = objects.get(i);

                        // If object is above the top of game field,
                        // it must be destroyed
                        if (current.getY() < -current.getHeight()) {
                            objects.remove(current);
                            current.delete();
                            System.out.println("removed obj");
                            i--;
                        } else {
                            // Object moving up (+ accelerate relative ticksCount)
                            current.setY(current.getY() 
                                - (1 + ticksCount*Constants.OFFSET_STAIR_FACTOR_IN_Y)
                            );
                        }
                    }
                }
                
                // Ball movement, if it's necessary.
                if (state == GameState.STARTED) {
                    if (ticksCount % Constants.BALL_MOVE_DELAY == 0) {
                        // Next ball movement (at dx, dy)
                        ball.move(movingBallToLeft, movingBallToRight);

                        // Checking for game over
                        if (ball.getY() < 0 
                                || ball.getY() > (Constants.FIELD_HEIGHT
                                + Constants.STAIR_CREATION_DOWN_OFFSET)) {
                            GameOver();
                        }    
                        
                        // Checking by intersections with ball
                        for (GameObject current : objects) {
                            if (current.getClass() == Stair.class)
                                if (((Stair)current).hasIntersectedWithBall(ball)) {
                                    ball.intersectionWithStair((Stair)current);
                                    //break;
                                }
                        }
                    }
                }
                
                // Stair creation, if it's necessary.
                if (ticksCount % Constants.STAIR_CREATION_DELAY == 0) {
                    int distanceToPreviousStair;
                    distanceToPreviousStair = Constants.FIELD_HEIGHT 
                            + Constants.STAIR_CREATION_DOWN_OFFSET;

                    if (objects.size() > 0) {
                        distanceToPreviousStair -= objects.get(objects.size()-1).getY();
                    }
                    
                    if (distanceToPreviousStair > Constants.MIN_DIST_TO_PREV_STAIR 
                            && ticksCount % Constants.CHANCE_TO_CREATE_STAIR_DELAY == 0) {
                        Random r;
                        r = new Random();
                        
                        // Getting chance to stair creation.
                        if (r.nextInt(100) < Constants.CHANCE_TO_CREATE_STAIR*1.0/objects.size()) {
                            StairType stairType;
                            stairType = StairType.NORMAL;

                            // Getting type of new stair.
                            if (Stair.getNormalStairsCount() >= Constants.MIN_NORMAL_STAIRS_COUNT) {
                                StairType[] values;
                                values = StairType.values();
                                stairType = values[r.nextInt(values.length)];
                            }
                                
                            // Create new stair.
                            Stair stair = Stair.create(Constants.FIELD_HEIGHT 
                                    + Constants.STAIR_CREATION_DOWN_OFFSET,
                                    stairType);
                            objects.add(stair);
                            System.out.println("stair created: x1:" + stair.getX() 
                                    + " x2:" + stair.getX() + stair.getWidth());
                        }
                    }
                }
                
                // Request to view for repaint game window. 
                if (view != null)
                    view.repaintView();
                
                // Check for ticksCount value and increase it.
                if(ticksCount == Long.MAX_VALUE) {
                    ticksCount = 0;
                    System.err.println("Error. TicsCount > Long.MAX_VALUE.");
                } else
                    ticksCount++;
                
                if (ticksCount % Constants.SCORE_ADDING_DELAY == 0)
                	score ++;
            }
        };
    }
    
    /**
     * Starting game timer with delay.
     * @param startDelay, delay before start (in milliseconds).
     */
    private void startTimer(int startDelay) {
        Logger.getAnonymousLogger().log(Level.INFO, "TIMER START (delay: {0})", startDelay);
        ticksCount = 0;
        timer.schedule(timerTask, startDelay, Constants.TIMER_DELAY_ms);
    }
    
    /**
     * Ending game, if ball has been destroyed.
     */
    private void GameOver() {
        Logger.getAnonymousLogger().info("GAME OVER");
        state = GameState.GAMEOVER;
        
        // Restart test
        StopGame();
    }
    
    @Override
    public Ball getBall() {return ball;}
    
    @Override
    public ArrayList<GameObject> getObjects() {
        	return objects;
    }
    
    @Override
    public GameState getState() {
        return state;
    }
    
    @Override
    public int getScore() {
        return score;
    }
    
    @Override
    public void setController(ControllerInterface c) {
        this.controller = c;
    }

    @Override
    public void BeginMovingBallToLeft() {
        movingBallToLeft = true;
        movingBallToRight = false;
    }

    @Override
    public void BeginMovingBallToRight() {
        movingBallToRight = true;
        movingBallToLeft = false;
    }

    @Override
    public void StopMovingBallToLeft() {
        movingBallToLeft = false;
    }

    @Override
    public void StopMovingBallToRight() {
        movingBallToRight = false;
    }

    @Override
    public boolean StopGame() {
        Logger.getAnonymousLogger().info("GAME STOP");
        //state = GameState.MENU;
        
        timerTask.cancel();
        timer.cancel();
        timer.purge();
        
        //ball = null;
        
        // Restart test
        //StartGame();
        return true;
    }
}
