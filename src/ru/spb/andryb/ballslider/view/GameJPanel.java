///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package ru.spb.andryb.ballslider.view;
//
//import ru.spb.andryb.ballslider.Constants;
//import ru.spb.andryb.ballslider.model.GameState;
//import ru.spb.andryb.ballslider.model.ModelInterface;
//import ru.spb.andryb.ballslider.model.objects.GameObject;
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.util.Iterator;
//import javax.swing.JPanel;
//
///**
// *
// * @author Administrator
// */
//public class GameJPanel extends JPanel {
//    private ModelInterface model;
//    
//    private float xZoom;
//    private float yZoom;
//    
//    public GameJPanel() {
//        setLayout(null);
//        setFocusable(true);
//        
//        xZoom = 0;
//        yZoom = 0;
//    }
//    
//    public void resized(int width, int height) {
//        super.setSize(width, height);
//        xZoom = (float) ((double)width/Constants.FIELD_WIDTH);
//        yZoom = (float) ((double)height/Constants.FIELD_HEIGHT);
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        
//        if (model == null)
//            return;
//        
//        if (model.getState() != GameState.STARTED
//                && model.getState() != GameState.GAMEOVER)
//            return;
//        
//        Graphics2D g2d = (Graphics2D)g;
//        
//        //BasicStroke bordersPen = new BasicStroke(3);
//        //g2d.setStroke(bordersPen);
//        g2d.setColor(Color.black);
//        
////        g2d.drawLine(0, 100, 600, 100);
////        g2d.drawLine(0, 200, 600, 200);
////        g2d.drawLine(0, 300, 600, 300);
////        g2d.drawLine(0, 400, 600, 400);
////        g2d.drawLine(0, 500, 600, 500);
////        g2d.drawLine(0, 600, 600, 600);
////        g2d.drawLine(0, 700, 600, 700);
//        
////        if (model.getBall() != null)
//        if (model.getState() == GameState.STARTED)
//            g2d.drawOval(
//                    Math.round(model.getBall().getX()*xZoom), 
//                    Math.round(model.getBall().getY()*yZoom), 
//                    Math.round(model.getBall().getWidth()*xZoom), 
//                    Math.round(model.getBall().getHeight()*yZoom)
//            );
//        
//        for (Iterator<GameObject> i = model.getObjectsIterator(); i.hasNext(); ) {
//            GameObject current = i.next();
//            
//            g2d.drawRect(
//                    Math.round(current.getX()*xZoom), 
//                    Math.round(current.getY()*yZoom), 
//                    Math.round(current.getWidth()*xZoom), 
//                    Math.round(current.getHeight()*yZoom)
//            );
//        }
//        
//    }
//    
//    public ModelInterface getModel() {return model;}
//
//    public void setModel(ModelInterface model) {this.model = model;}
//}
