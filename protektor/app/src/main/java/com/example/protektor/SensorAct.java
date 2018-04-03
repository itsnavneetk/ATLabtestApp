package com.example.protektor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by Navneet on 25-02-2018.
 */

public class SensorAct implements SensorEventListener {

    SensorManager mSensorManager;
    Sensor mAccelerometer;

    private float[] gravity = new float[3];
    private float[] linear_acceleration = new float[3];

    double aX, aY, aZ;   // these are the acceleration in x,y and z axis

    String TAG = "sMess";

    SensorAct() {
    }

    SensorAct(SensorManager mSensorManager1, Sensor mAccelerometer1){
        mSensorManager = mSensorManager1;
        mAccelerometer = mAccelerometer1;
    }

    protected void onResume() {
        System.out.println("onResume()");
        // mSensorManager.registerListener(this, mAccelerometerUnc, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);

        //    mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
       Log.d(TAG,"onSensorChanged(SensorEvent event)");


        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Log.d(TAG,"event.sensor.getType()==Sensor.TYPE_ACCELEROMETER");
            aX = event.values[0];
            aY = event.values[1];
            aZ = event.values[2];


            final float alpha = 0.8f;

            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

            linear_acceleration[0] = event.values[0] - gravity[0];
            linear_acceleration[1] = event.values[1] - gravity[1];
            linear_acceleration[2] = event.values[2] - gravity[2];

            Log.d(TAG,"******linear_acceleration[2]  :  " + linear_acceleration[2]);

            if (linear_acceleration[2] > 5) {
                //    tx4.setText(String.valueOf(linear_acceleration[2]));
                Log.d(TAG,String.valueOf(linear_acceleration[2]));
            }

            if (linear_acceleration[2] < -5) {
                //tx5.setText(String.valueOf(linear_acceleration[2]));
                Log.d(TAG,String.valueOf(linear_acceleration[2]));
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Log.d(TAG,"onAccuracyChanged(Sensor sensor, int accuracy)");
    }
}
