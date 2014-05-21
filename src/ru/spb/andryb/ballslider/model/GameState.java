/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.spb.andryb.ballslider.model;

/**
 * Possible states of the game.
 * @author Admin
 */
public enum GameState {
    /**
     * Game hasn't started,
     * menu is showing in the widnow.
     * Waiting for starting.
     */
    MENU,
    /**
     * Game started.
     * Showing the game window.
     */
    STARTED, 
    /**
     * Game is over.
     * Ball will not be showed.
     * But logic isn't stopped.
     */
    GAMEOVER, 
    /**
     * If any exception.
     */
    ERROR
}
