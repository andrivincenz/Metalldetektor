package ch.appquest.metalldetektor;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.FloatMath;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {
	private SensorManager mSensorManager;
	private Sensor mMagnetField;
	private TextView lblValue;
	private ProgressBar mProgress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		mMagnetField = mSensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).get(0);
		lblValue = (TextView)findViewById(R.id.lblValue);
		
		if (mMagnetField == null) {
			Toast.makeText(getApplicationContext(), "Sensor nicht vorhanden", Toast.LENGTH_LONG).show();
			finish();
			// activity close
		}

		mProgress = (ProgressBar) findViewById(R.id.Progress);
		mProgress.setMax((int) mMagnetField.getMaximumRange());
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mMagnetField!=null){
		mSensorManager.registerListener(this, mMagnetField, SensorManager.SENSOR_DELAY_NORMAL);
		}
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mSensorManager.unregisterListener(this);
	}
	
	
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		 if (event.sensor == mMagnetField) {
		float value = event.values[0];
			lblValue.setText(value + "");
			float[] mag = event.values;
			double betrag = android.util.FloatMath.sqrt(mag[0] * mag[0] + mag[1] * mag[1] + mag[2] * mag[2]);
			mProgress.setProgress((int) betrag);
		}
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
}

