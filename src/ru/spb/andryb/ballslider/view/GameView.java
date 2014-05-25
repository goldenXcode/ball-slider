/**
 * 
 */
package ru.spb.andryb.ballslider.view;

import java.util.Iterator;
import java.util.List;

import ru.spb.andryb.ballslider.Constants;
import ru.spb.andryb.ballslider.MainActivity;
import ru.spb.andryb.ballslider.R;
import ru.spb.andryb.ballslider.MainActivity.GameStateChangeHandler;
import ru.spb.andryb.ballslider.controller.AccelerometerListener;
import ru.spb.andryb.ballslider.controller.Controller;
import ru.spb.andryb.ballslider.controller.ControllerInterface;
import ru.spb.andryb.ballslider.model.GameState;
import ru.spb.andryb.ballslider.model.Logic;
import ru.spb.andryb.ballslider.model.ModelInterface;
import ru.spb.andryb.ballslider.model.objects.GameObject;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
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
	
    private RefreshHandler mRedrawHandler = new RefreshHandler();
	
	@SuppressLint("HandlerLeak")
	class RefreshHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
        	GameView.this.invalidate();
        }
    };

    private AccelerometerListener mAccelerometerListener;
    
    private class AccelerometerListener implements SensorEventListener {
    	
    	private SensorManager mSensorManager;
    	private Sensor mAccelerometerSensor;
    	private Sensor mMagneticFieldSensor;
    	
    	private float[] mRotationMatrix;
    	private float[] mAccelerometerData;
    	private float[] mMagneticFieldData;
    	private float[] mOrientationData;
    	
    	private Activity mActivity;

    	/**
    	 * Default constructor;
    	 */
    	public AccelerometerListener(Activity parentActivity) {
    		super();
    		
    		this.mActivity = parentActivity;
    		
    		mRotationMatrix    = new float[16];
    		mAccelerometerData = new float[3];
    		mMagneticFieldData = new float[3];
    		mOrientationData   = new float[3];
    		
    		mSensorManager = (SensorManager) mActivity.getSystemService(Context.SENSOR_SERVICE);
    		
    		List <Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
    		
    		if (sensors.size() > 0) {
    			for (Sensor sensor : sensors) {
    				switch (sensor.getType()) {
    					case Sensor.TYPE_ACCELEROMETER :
    						if (mAccelerometerSensor == null) mAccelerometerSensor = sensor;
    						break;
    					case Sensor.TYPE_MAGNETIC_FIELD :
    						if (mMagneticFieldSensor == null) mMagneticFieldSensor = sensor;
    						break;
    					default: 
    						break;
    				}
    			}
    		}
    	}
    	
    	public void register() {
    		mSensorManager.registerListener(this , mAccelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
    		mSensorManager.registerListener(this , mMagneticFieldSensor, SensorManager.SENSOR_DELAY_GAME);
    	}
    	
    	public void unregister() {
    		mSensorManager.unregisterListener(this);
    	}
    	
    	/* (non-Javadoc)
    	 * @see android.hardware.SensorEventListener#onAccuracyChanged(android.hardware.Sensor, int)
    	 */
    	@Override
    	public void onAccuracyChanged(Sensor arg0, int arg1) {
    		// TODO Auto-generated method stub

    	}

    	/* (non-Javadoc)
    	 * @see android.hardware.SensorEventListener#onSensorChanged(android.hardware.SensorEvent)
    	 */
    	@Override
    	public void onSensorChanged (SensorEvent event) {
    		switch (event.sensor.getType()) {
    			case Sensor.TYPE_ACCELEROMETER :		
    				System.arraycopy(event.values, 0, mAccelerometerData, 0, 3);
    				break;
    			case Sensor.TYPE_MAGNETIC_FIELD :
    				System.arraycopy(event.values, 0, mMagneticFieldData, 0, 3);
    				break;
    			default: 
    				break;
    		}
    		
    		SensorManager.getRotationMatrix(mRotationMatrix, null, mAccelerometerData, mMagneticFieldData);
    		SensorManager.getOrientation(mRotationMatrix, mOrientationData);
    		
//    		System.out.println("X: " + String.valueOf(Math.round(Math.toDegrees(mOrientationData[0]))));
//    		System.out.println("Y: " + String.valueOf(Math.round(Math.toDegrees(mOrientationData[1]))));
//    		System.out.println("Z: " + String.valueOf(Math.round(Math.toDegrees(mOrientationData[2]))));
    		
        	mController.setPlayerAction((float)(Math.toDegrees(mOrientationData[2])*100.0/35.0));
        	//System.out.println(String.valueOf((Math.toDegrees(mOrientationData[2])*100.0/25.0)));
    	}

    }
	
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

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	public void init(MainActivity parent) {
		mAccelerometerListener = new AccelerometerListener(parent);
		//mAccelerometerListener.register();
		
        this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("Untapped");
				if (!Constants.ACCELEROMETER_ENABLED)
					mController.setPlayerAction(0.0F);
			}
        });
	        
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
        mXZoom = (float) ((double)w/Constants.FIELD_WIDTH);
        mYZoom = (float) ((double)h/Constants.FIELD_HEIGHT);
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent e) {
		if (!Constants.ACCELEROMETER_ENABLED)
			if (mModel.getState() == GameState.STARTED) {	        	
	            // Normalize x,y between 0 and 1
	            float x = e.getX() / this.getWidth();
	            float y = e.getY() / this.getHeight();
	            
	        	System.out.println("Tapped, x:" + x);
	
	            if (x < 0.45F) {
	            	mController.setPlayerAction(-100.0F);
	            } else if (x > 0.55F) {
	            	mController.setPlayerAction(100.0F);
	            } 
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
//        if (mModel.getState() == GameState.GAMEOVER || mModel.getState() == GameState.MENU) {
//            System.out.println("GameModeHasChanged");
//        	mActivity.GameModeHasChanged(mModel.getState());
//        	previousState = mModel.getState();
//        	return;
//        }
        
        try {
	        synchronized( this ) {
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
		        mPaint.setTextSize(43);
		        mPaint.setTextScaleX(1.1F);;
		        mPaint.setAlpha(180);
		        
		        canvas.drawText(
		        		"SCORE: " + mModel.getScore(), 
		        		(int)(this.getWidth()/10.0), 
		        		(int)(this.getHeight()/10.0), 
		        		mPaint
		        );
	
		        mPaint.setAlpha(0);
		        mPaint.setColor(Color.WHITE);
		        
		//        for (int x = 0; x < mXTileCount; x += 1) {
		//            for (int y = 0; y < mYTileCount; y += 1) {
		//                if (mTileGrid[x][y] > 0) {
		//                    canvas.sdrawBitmap(mTileArray[mTileGrid[x][y]], mXOffset + x * mTileSize,
		//                            mYOffset + y * mTileSize, mPaint);
		//                }
		//            }
		//        }
		        
		        if (mModel.getState() == GameState.STARTED) {
		        	for (int i = 5; i > 0; i--) {
			        	mDrawable = new ShapeDrawable(new OvalShape());
			            mDrawable.getPaint().setColor(Color.rgb(20 * (5-i), 20 * (5-i), 20 * (5-i)));
			            mDrawable.setBounds((int)(mModel.getBall().getX()*mXZoom + mModel.getBall().getWidth()*mXZoom*(5-i)/10), 
			            		(int)(mModel.getBall().getY()*mYZoom + mModel.getBall().getHeight()*mYZoom*(5-i)/10), 
			            		(int)(mModel.getBall().getX()*mXZoom + mModel.getBall().getWidth()*mXZoom*i/5), 
			            		(int)(mModel.getBall().getY()*mYZoom + mModel.getBall().getHeight()*mYZoom*i/5));
			            mDrawable.draw(canvas);
		        	}
		//        	canvas.drawRect(mModel.getBall().getX()*xZoom,
		//        			mModel.getBall().getY()*yZoom,
		//        			mModel.getBall().getWidth()*xZoom,
		//        			mModel.getBall().getHeight()*yZoom,
		//        			mPaint
		//        	);
		        }
		        
		        for (int i = 0; i < mModel.getObjects().size(); i++) {
		            GameObject current = mModel.getObjects().get(i);
		            
//		            if (current == null)
//		            	break;
		            
//		            canvas.drawRect(
//		            		Math.round(current.getX()*mXZoom),
//		            		Math.round(current.getY()*mYZoom),
//		            		Math.round(current.getWidth()*mXZoom),
//		            		Math.round(current.getHeight()*mYZoom),
//		            		mPaint
//		        	);
		            
		        	mDrawable = new ShapeDrawable(new RectShape());
		            mDrawable.getPaint().setColor(Color.BLACK);
		            mDrawable.getPaint().setStyle(Style.STROKE);
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
	

	
	public void gameStateChanged(GameState newState) {	
		switch (newState) {
			case STARTED:
				if (Constants.ACCELEROMETER_ENABLED)
					changeAccelerometerStatus(true);
				break;
			case ERROR:
				if (Constants.ACCELEROMETER_ENABLED)
					changeAccelerometerStatus(false);
				break;
			case GAMEOVER:
				if (Constants.ACCELEROMETER_ENABLED)
					changeAccelerometerStatus(false);
				break;
			case MENU:
				if (Constants.ACCELEROMETER_ENABLED)
					changeAccelerometerStatus(false);
				break;
			case PAUSE:
				if (Constants.ACCELEROMETER_ENABLED)
					changeAccelerometerStatus(false);
				break;
		}
	}
	
	public void changeAccelerometerStatus(boolean enabled) {
		if (enabled)
			mAccelerometerListener.register();
		else 
			mAccelerometerListener.unregister();
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
