/**
 * 
 */
package ru.spb.andryb.ballslider.controller;

import java.util.List;

import ru.spb.andryb.ballslider.model.GameState;
import ru.spb.andryb.ballslider.view.GameView;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * @author andryb
 *
 */
public class AccelerometerListener implements SensorEventListener {
	
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
		mSensorManager.registerListener(this , mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
		mSensorManager.registerListener(this , mMagneticFieldSensor, SensorManager.SENSOR_DELAY_UI);
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
		
		System.out.println("X: " + String.valueOf(Math.round(Math.toDegrees(mOrientationData[0]))));
		System.out.println("Y: " + String.valueOf(Math.round(Math.toDegrees(mOrientationData[1]))));
		System.out.println("Z: " + String.valueOf(Math.round(Math.toDegrees(mOrientationData[2]))));
	}

}
