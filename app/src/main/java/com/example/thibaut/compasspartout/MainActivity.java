package com.example.thibaut.compasspartout;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;

public class MainActivity extends AppCompatActivity {


    private LocationManager lm;
    private SensorManager sm;


    private TextView latView;
    private TextView lngView;
    private TextView oriView;
    private ImageView compassImage;
    private float compassBearing;



    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get views
        latView = (TextView) findViewById(R.id.lat);
        lngView = (TextView) findViewById(R.id.lng);
        oriView = (TextView) findViewById(R.id.ori);
        compassImage = (ImageView) findViewById(R.id.imageView);

        //compassImage.setScaleType(ImageView.ScaleType.MATRIX);


        //mat = new Matrix();


        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener ls = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                if (location != null) {


                    latView.setText(Double.toString(location.getLatitude()));

                    lngView.setText(Double.toString(location.getLongitude()));

                } else {

                    latView.setText("Location is null");

                }


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        //Magnetic Field
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        final Sensor orientationSensor = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);



        

        SensorEventListener sl = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                compassBearing = (float) event.values[0] % 365;

                oriView.setText(Integer.toString( (int) compassBearing )  );

                rotateImage(360 - event.values[0] );


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };



        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 50, 1, ls);

        sm.registerListener(sl, orientationSensor, SensorManager.SENSOR_DELAY_UI);


    }


    private void rotateImage(float angle){

        compassImage.setRotation(angle);
    }


}
