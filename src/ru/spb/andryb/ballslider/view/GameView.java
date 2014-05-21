/**
 * 
 */
package ru.spb.andryb.ballslider.view;

import java.util.Iterator;

import ru.spb.andryb.ballslider.Constants;
import ru.spb.andryb.ballslider.MainActivity;
import ru.spb.andryb.ballslider.controller.Controller;
import ru.spb.andryb.ballslider.controller.ControllerInterface;
import ru.spb.andryb.ballslider.model.GameState;
import ru.spb.andryb.ballslider.model.Logic;
import ru.spb.andryb.ballslider.model.ModelInterface;
import ru.spb.andryb.ballslider.model.objects.GameObject;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * @author Andrew
 *
 */
public class GameView extends View implements ViewInterface{
    private ControllerInterface mController;
    private ModelInterface mModel;
    
    private float mXZoom = 0;
    private float mYZoom = 0;
    
	private final Paint mPaint = new Paint();
	
	private ShapeDrawable mDrawable;
	private MainActivity mActivity;
	private GameState previousState = GameState.MENU;
	
    private RefreshHandler mRedrawHandler = new RefreshHandler();
	
	class RefreshHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
        	GameView.this.invalidate();
        }
    };
	
	/**
	 * @param context
	 */
	public GameView(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setActivity (MainActivity activity) {
		this.mActivity = activity;
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
        mXZoom = (float) ((double)w/Constants.FIELD_WIDTH);
        mYZoom = (float) ((double)h/Constants.FIELD_HEIGHT);
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent e) {
		if (mModel.getState() == GameState.STARTED) {
        	mController.beginLeftAction();
        	
            // Normalize x,y between 0 and 1
            float x = e.getX() / this.getWidth();
            float y = e.getY() / this.getHeight();
            
        	System.out.println("Tapped, x:" + x);

            if (x < 0.45F) {
            	mController.beginLeftAction();
            } else if (x > 0.55F) {
            	mController.beginRightAction();
            } 
//            else {
//            	mController.endLeftAction();
//            	mController.endRightAction();
//            }
        }
    	return super.onTouchEvent(e);
    }
	
//	@Override
//    public boolean onClickListener(MotionEvent e) {
//		if (mModel.getState() == GameState.STARTED) {
//        	mController.beginLeftAction();
//        	
//        	System.out.println("TOUCH");
//        	
//            // Normalize x,y between 0 and 1
//            float x = e.getX() / this.getWidth();
//            float y = e.getY() / this.getHeight();
//
//            if (x < 0.45) {
//            	mController.beginLeftAction();
//            } else if (x > 0.55) {
//            	mController.beginRightAction();
//            } else {
//            	mController.endLeftAction();
//            	mController.endRightAction();
//            }
//        }
//    	return super.onTouchEvent(e);
//    }

	@Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        System.out.println("DRAW");
        
        if (mModel.getState() == GameState.GAMEOVER || mModel.getState() == GameState.MENU) {
            System.out.println("GameModeHasChanged");
        	mActivity.GameModeHasChanged(mModel.getState());
        	previousState = mModel.getState();
        	return;
        }
        
        try {
	        mDrawable = new ShapeDrawable(new RectShape());
	        mDrawable.getPaint().setColor(Color.WHITE);
	        mDrawable.setBounds(0, 
	        		0, 
	        		this.getWidth(), 
	        		this.getHeight());
	        mDrawable.draw(canvas);
	        
	        if (mModel == null)
	            return;
	        
	        if (mModel.getState() != GameState.STARTED
	                && mModel.getState() != GameState.GAMEOVER)
	            return;
	
	        mPaint.setColor(Color.rgb(100, 100, 200));
	        mPaint.setStyle(Style.FILL);
	        mPaint.setTextSize(40);
	        mPaint.setTextScaleX(1.1F);;
	        mPaint.setAlpha(200);
	        
	        canvas.drawText(
	        		"SCORE: " + mModel.getScore(), 
	        		(int)(this.getWidth()/10.0), 
	        		(int)(this.getHeight()/10.0), 
	        		mPaint
	        );
	        
	//        for (int x = 0; x < mXTileCount; x += 1) {
	//            for (int y = 0; y < mYTileCount; y += 1) {
	//                if (mTileGrid[x][y] > 0) {
	//                    canvas.sdrawBitmap(mTileArray[mTileGrid[x][y]], mXOffset + x * mTileSize,
	//                            mYOffset + y * mTileSize, mPaint);
	//                }
	//            }
	//        }
	        
	        if (mModel.getState() == GameState.STARTED) {
	        	mDrawable = new ShapeDrawable(new OvalShape());
	            mDrawable.getPaint().setColor(Color.BLACK);
	            mDrawable.setBounds((int)(mModel.getBall().getX()*mXZoom), 
	            		(int)(mModel.getBall().getY()*mYZoom), 
	            		(int)(mModel.getBall().getX()*mXZoom + mModel.getBall().getWidth()*mXZoom), 
	            		(int)(mModel.getBall().getY()*mYZoom + mModel.getBall().getHeight()*mYZoom));
	            mDrawable.draw(canvas);
	//        	canvas.drawRect(mModel.getBall().getX()*xZoom,
	//        			mModel.getBall().getY()*yZoom,
	//        			mModel.getBall().getWidth()*xZoom,
	//        			mModel.getBall().getHeight()*yZoom,
	//        			mPaint
	//        	);
	        }
	        synchronized( this ) {
		        for (int i = 0; i < mModel.getObjects().size(); i++) {
		            GameObject current = mModel.getObjects().get(i);
		            
		            if (current == null)
		            	break;
		            
		            canvas.drawRect(
		            		Math.round(current.getX()*mXZoom),
		            		Math.round(current.getY()*mYZoom),
		            		Math.round(current.getWidth()*mXZoom),
		            		Math.round(current.getHeight()*mYZoom),
		            		mPaint
		        	);
		            
		        	mDrawable = new ShapeDrawable(new RectShape());
		            mDrawable.getPaint().setColor(Color.BLACK);
		            mDrawable.setBounds((int)Math.round(current.getX()*mXZoom), 
		            		(int)(current.getY()*mYZoom), 
		            		(int)(current.getX()*mXZoom + current.getWidth()*mXZoom), 
		            		(int)(current.getY()*mYZoom + current.getHeight()*mYZoom));
		            mDrawable.draw(canvas);
		        }
	        }
        } catch (Exception e) {
        	System.out.println ("Exception: " + e.getLocalizedMessage());
        }
    }

	@Override
	public void repaintView() {
		mRedrawHandler.sendEmptyMessage(0);
		//activity.this.update();
        //this.invalidate();
		// TODO Auto-generated method stub
		
	}

	@Override
    public void setModel(ModelInterface model) {
		this.mModel = model;
	}

    @Override
    public void setController(ControllerInterface c) {
        this.mController = c;
    }
}
