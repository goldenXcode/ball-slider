/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.spb.andryb.ballslider.view;

import ru.spb.andryb.ballslider.controller.ControllerInterface;
import ru.spb.andryb.ballslider.model.ModelInterface;

/**
 *
 * @author Admin
 */
public interface ViewInterface {
    public void repaintView();
    public void setModel(ModelInterface m);
    public void setController(ControllerInterface c);
}
