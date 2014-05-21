/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.spb.andryb.ballslider.model.objects;

import ru.spb.andryb.ballslider.Constants;
import java.util.Random;

/**
 *
 * @author Administrator
 */
public class Stair extends GameObject {
    private StairType type;
    private static int normalStairsCount = 0;
    
    public Stair(int _x, int _y, int _width, int _height, StairType _type) {
        super(_x, _y, _width, _height);
        
        if (_type == StairType.NORMAL)
            normalStairsCount++;
    }
    
    @Override
    public void delete() {
        if (type == StairType.NORMAL)
            normalStairsCount--;
    }
    
    public static Stair create(int _y, StairType _type) {
        Stair stair;
        int x, width, intervalInX, maxWidth;
        Random r;
        
        r = new Random();
                        
        intervalInX = Constants.FIELD_WIDTH - Constants.MIN_STAIR_WIDTH;
        
        x = r.nextInt(intervalInX);
        
        maxWidth = (x + Constants.MAX_STAIR_WIDTH > Constants.FIELD_WIDTH) 
                ? Constants.FIELD_WIDTH - x
                : Constants.MAX_STAIR_WIDTH;
        
        width = r.nextInt(maxWidth - Constants.MIN_STAIR_WIDTH) 
                + Constants.MIN_STAIR_WIDTH;

        stair = new Stair(x,
                _y,
                width,
                Constants.STAIR_HEIGHT,
                _type);
        
        return stair;
    }
    
    public boolean hasIntersectedWithBall(Ball _b) {
        if (_b.getY() + _b.getDy() <= y && _b.getY() + _b.getDy() + _b.getHeight() >= y)
            if (_b.getX() >= x && _b.getX() <= x + width ||
                    _b.getX() + _b.getWidth() >= x 
                    && _b.getX() + _b.getWidth() <= x + width)
                return true;
        return false;
    }

    public StairType getType() {
        return type;
    }
    
//    public void setType(StairType type) {
//        this.type = type;
//    }
    
    public static int getNormalStairsCount() {
        return normalStairsCount;
    }

    public static void setNormalStairsCount(int aNormalStairsCount) {
        normalStairsCount = aNormalStairsCount;
    }
}
