/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.spb.andryb.ballslider.controller;

import ru.spb.andryb.ballslider.model.ModelInterface;
import ru.spb.andryb.ballslider.view.ViewInterface;

/**
 * @author Admin
 */
public class Controller implements ControllerInterface{
    /**
     * Link to realisation of model interface.
     */
    private ModelInterface model;
    /**
     * Link to realisation of view interface.
     */
    private ViewInterface view;
    
    /**
     * Controller default constructor.
     * @param model implementing of model interface
     * @param view implementing of view interface
     */
    public Controller(ModelInterface model, ViewInterface view) {
        this.model = model;
        this.view = view;
        
        model.setView(view);
        model.setController(this);
        view.setModel(model);
        view.setController(this);
    }
    
    @Override
    public boolean startGame() {
        return model.StartGame();
    }
    
    @Override
    public void setPlayerAction(float action) {
    	model.setPlayerAction(action);
    }

//    @Override
//    public void beginLeftAction() {
//        model.BeginMovingBallToLeft();
//    }
//
//    @Override
//    public void beginRightAction() {
//        model.BeginMovingBallToRight();
//    }
//
//    @Override
//    public void endLeftAction() {
//        model.StopMovingBallToLeft();
//    }
//
//    @Override
//    public void endRightAction() {
//        model.StopMovingBallToRight();
//    }
}
