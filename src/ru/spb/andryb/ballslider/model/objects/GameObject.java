/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.spb.andryb.ballslider.model.objects;

/**
 *
 * @author Administrator
 */
public abstract class GameObject {
    protected float x;        // Coordinate x
    protected float y;        // Coordinate y
    protected int width;    // Object width
    protected int height;   // Object height
    
    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void delete() {}

    public float getX() {return x;}
    public void setX(float x) {this.x = x;}

    public float getY() { return y; }
    public void setY(float y) {this.y = y;}

    public int getWidth() {return width;}
    public void setWidth(int width) {this.width = width;}

    public int getHeight() {return height;}
    public void setHeight(int height) {this.height = height;}
    
}
