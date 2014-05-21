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
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		mTextView = new TextView(this);
        mModel = new Logic(this);
        mGameWindow = new GameView(this);
        mController = new Controller(mModel, mGameWindow);
        
        mGameWindow.setActivity(this);
		
		//mTextView.setX(mGameWindow.getWidth());
		mTextView.setGravity(Gravity.CENTER);
		mTextView.setTextSize(30);
		mTextView.setTextColor(Color.rgb(100, 100, 200));

		CharSequence text = getResources().getText(R.string.mode_ready);
		mTextView.setText(text);
		
        setContentView(mTextView);
        
        mTextView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mController.startGame();
                GameModeHasChanged(mModel.getState());
				return true;
            }
        });
        
        mGameWindow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("Untapped");
            	mController.endLeftAction();
            	mController.endRightAction();
			}
        });
        
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
    
	public void GameModeHasChanged(GameState newState) {
		CharSequence text;
		switch (newState) {
			case STARTED:
	            setContentView(mGameWindow);
				break;
			case ERROR:
				break;
			case GAMEOVER:
				text = getResources().getString(R.string.mode_lose, mModel.getScore());
				mTextView.setText(text);
	            setContentView(mTextView);
				break;
			case MENU:
				text = getResources().getText(R.string.mode_ready);
				mTextView.setText(text);
	            setContentView(mTextView);
				break;
		}
	}
	
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
//	@Override
//    protected void onResume() {
//		super.onResume();
//	}
//	
//	@Override
//    protected void onPause() {
//		super.onPause();
//	}
//	
//	@Override
//    protected void onStop() {
//		super.onStop();
//	}
//	
//	@Override
//    protected void onDestroy() {
//		super.onDestroy();
//	}

}
