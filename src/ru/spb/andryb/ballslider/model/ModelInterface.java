/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.spb.andryb.ballslider.model;

import ru.spb.andryb.ballslider.controller.ControllerInterface;
import ru.spb.andryb.ballslider.model.objects.Ball;
import ru.spb.andryb.ballslider.model.objects.GameObject;
import ru.spb.andryb.ballslider.view.ViewInterface;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Admin
 */
public interface ModelInterface {    
    /**
     * Starting game.
     * Includes all required operations.
     * @return true, if launch is successful.
     */
    public boolean StartGame();
    /**
     * Getting current game state.
     * @return state.
     */
    public GameState getState();
    /**
     * Getting current player score.
     * @return score.
     */
    public int getScore();
    /**
     * Getting Ball object
     * @return Ball object or null, if ball is not exist.
     */
    public Ball getBall();
    /**
     * Getting iterator (begin) for objects collection.
     * @return Iterator for GameObject.
     */
    public ArrayList<GameObject> getObjects();
    /**
     * Setting link on view in model.
     * @param v, view.
     */
    public void setView(ViewInterface v);
    /**
     * Setting link on controller in model.
     * @param c, controller.
     */
    public void setController(ControllerInterface c);

    /**
     * Set of player action on ball moving.
     * @param action, From -100.0 to +100.0
     * 				  -100.0 - maximum power to left ball move
     * 				  +100.0 - maximum power to right ball move
     *    		      0.0 - player not affected on ball
     */
    public void setPlayerAction(float action);
//    /**
//     * Begin of a left ball moving (response on a user action).
//     */
//    public void BeginMovingBallToLeft();
//    /**
//     * Begin of a right ball moving (response on a user action).
//     */
//    public void BeginMovingBallToRight();
//    /**
//     * Stop of a left ball moving (response on a user action).
//     */
//    public void StopMovingBallToLeft();
//    /**
//     * Stop of a right ball moving (response on a user action).
//     */
//    public void StopMovingBallToRight();
    /**
     * Pause game.
     * @return true, if pause is successful.
     */
    public boolean pause();
    /**
     * Resume game, if game has been paused.
     * @return true, if resume is successful.
     */
    public boolean resume();
    /**
     * Stopping game.
     * Includes all required operations.
     * @return true, if launch is successful.
     */
    public boolean StopGame();
}
