package fr.caensup.testsensor;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private EditText tvSensorList = null;
    SensorManager sensorManager = null;
    Sensor gravitySensor = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSensorList = findViewById(R.id.tvSensorList);
        sensorManager=(SensorManager)
                this.getSystemService(SENSOR_SERVICE);
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    private String getAllSensors() {
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        String sensors = "";
        for (Sensor s : sensorList) {
            sensors += s.getName() + "\n";
        }
        return sensors;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        String graviteXYZ = "Gravité mesurée: \n";
        graviteXYZ += " x=" + roundOff(sensorEvent.values[0]) + "\n";
        graviteXYZ += " y=" + roundOff(sensorEvent.values[1]) + "\n";
        graviteXYZ += " z=" + roundOff(sensorEvent.values[2]) + "\n";
        tvSensorList.setText(graviteXYZ);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private double roundOff(float value) {
        double roundOff = Math.round(value * 100.0) / 100.0;
        return  roundOff;
    }
}