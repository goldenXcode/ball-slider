/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.spb.andryb.ballslider.controller;

/**
 * @author Admin
 */
public interface ControllerInterface {
    /**
     * Response to game start action.
     * @return true, if launch is successful.
     */
    public boolean startGame();
    /**
     * Set of player action on ball moving.
     * @param action, From -100.0 to +100.0
     * 				  -100.0 - maximum power to left ball move
     * 				  +100.0 - maximum power to right ball move
     *    		      0.0 - player not affected on ball
     */
    public void setPlayerAction(float action);
//    /**
//     * Response to begin of a left user action.
//     */
//    public void beginLeftAction();
//    /**
//     * Response to begin of a right user action.
//     */
//    public void beginRightAction();
//    /**
//     * Response to end of a left user action.
//     */
//    public void endLeftAction();
//    /**
//     * Response to end of a right user action.
//     */
//    public void endRightAction();
}
