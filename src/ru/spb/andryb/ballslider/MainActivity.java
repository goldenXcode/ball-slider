/**
 * 
 */
package ru.spb.andryb.ballslider;

import ru.spb.andryb.ballslider.R;
import ru.spb.andryb.ballslider.R.string;
import ru.spb.andryb.ballslider.controller.Controller;
import ru.spb.andryb.ballslider.controller.ControllerInterface;
import ru.spb.andryb.ballslider.model.GameState;
import ru.spb.andryb.ballslider.model.Logic;
import ru.spb.andryb.ballslider.model.ModelInterface;
import ru.spb.andryb.ballslider.view.GameView;
import ru.spb.andryb.ballslider.view.ViewInterface;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * @author Andrew
 *
 */
public class MainActivity extends Activity  {
    
	private GameView mGameWindow;
	private TextView mTextView;
	private ModelInterface mModel;
	private ControllerInterface mController;
	
	private GameStateChangeHandler mGameStateChangeHandler = new GameStateChangeHandler();
	
	public GameStateChangeHandler getGameStateChangeHandle() {return mGameStateChangeHandler;}
	
	@SuppressLint("HandlerLeak")
	public class GameStateChangeHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
        	GameState newState = GameState.values()[msg.what];
        	
    		CharSequence text;
    		switch (newState) {
    			case STARTED:
    	            setContentView(mGameWindow);
    				break;
    			case ERROR:
    				break;
    			case GAMEOVER:
    	            setContentView(R.layout.gameover_layout);
    				mTextView = (TextView) findViewById(R.id.score_text);
    				mTextView.setText(mModel.getScore() + "");
    				break;
    			case MENU:
    	            setContentView(R.layout.menu_layout);
    				break;
    			case PAUSE:
    	            setContentView(R.layout.pause_layout);
    				break;
    		}
    		
    		mGameWindow.gameStateChanged(newState);
        }
        
        public void stateChanged (GameState state) {
            this.sendEmptyMessage(state.ordinal());
        }
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		mTextView = new TextView(this);
        mModel = new Logic(this, mGameStateChangeHandler);
        mGameWindow = new GameView(this);
        mController = new Controller(mModel, mGameWindow);

        mGameWindow.init(this);
		
		//mTextView.setX(mGameWindow.getWidth());
		mTextView.setGravity(Gravity.CENTER);
		mTextView.setTextSize(30);
		mTextView.setTextColor(Color.rgb(100, 100, 200));

		CharSequence text = getResources().getText(R.string.mode_ready);
		mTextView.setText(text);
		
        //setContentView(mTextView);
		setContentView(R.layout.welcome_layout);
        
//        mTextView.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                mController.startGame();
//                //GameModeHasChanged(mModel.getState());
//				return true;
//            }
//        });
        
        /*
        mGameWindow.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mModel.getState() == GameState.STARTED) {
                	mController.beginLeftAction();
                	
                	System.out.println("TOUCH");
                	
                    // Normalize x,y between 0 and 1
                    float x = event.getX() / v.getWidth();
                    float y = event.getY() / v.getHeight();

                    if (x < 0.45) {
                    	mController.beginLeftAction();
                    } else if (x > 0.55) {
                    	mController.beginRightAction();
                    } else {
                    	mController.endLeftAction();
                    	mController.endRightAction();
                    }
                }
                return false;
            }
        });
        */
        
        //mGameWindow = (GameView) findViewById(R.id.test1);
        
        //mGameWindow.setVisibility(View.VISIBLE);
        
        // Hide action bar
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();
	}
	
	public void clickOnView(View v) {
		switch (v.getId()) {
			case R.id.WelcomeFrame:
				setContentView(R.layout.menu_layout);
				//mGameStateChangeHandler.stateChanged(GameState.MENU);
		        //setContentView(mTextView);
				//setContentView(R.layout.main_layout);
				break;
			case R.id.MenuFrame:
                mController.startGame();
				break;
			case R.id.GameoverFrame:
                mController.startGame();
				break;
			case R.id.PauseFrame:
                mController.startGame();
				break;
			default:
				break;
		}
	};
	
//	@Override
//	protected void onStart() {
//		super.onStart();
//	}
//	
//	@Override
//    protected void onRestart() {
//		super.onRestart();
//	}
//	
	@Override
    protected void onResume() {
		super.onResume();
		//mModel.resume();
	}
	
	@Override
    protected void onPause() {
		System.out.println("onStop()");
		super.onPause();
		mModel.pause();
	}
//	
	@Override
    protected void onStop() {
		System.out.println("onStop()");
		super.onStop();
	}
//	
//	@Override
//    protected void onDestroy() {
//		super.onDestroy();
//	}

}
