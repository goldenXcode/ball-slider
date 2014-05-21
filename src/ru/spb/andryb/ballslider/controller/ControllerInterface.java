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
     * Response to begin of a left user action.
     */
    public void beginLeftAction();
    /**
     * Response to begin of a right user action.
     */
    public void beginRightAction();
    /**
     * Response to end of a left user action.
     */
    public void endLeftAction();
    /**
     * Response to end of a right user action.
     */
    public void endRightAction();
}
