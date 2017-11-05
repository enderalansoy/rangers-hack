package com.example.ford.serviceapplication;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.openxc.VehicleManager;
import com.openxc.measurements.EngineSpeed;
import com.openxc.measurements.Measurement;
import com.openxc.measurements.TransmissionGearPosition;
import com.openxc.measurements.VehicleSpeed;
import com.openxc.remote.VehicleService;
import com.openxc.units.Boolean;

import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;

import static android.content.ContentValues.TAG;
import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class NotificationService extends Service{
    Timer timer;
    private double vehicleSpeed = 0;
    private String vitesPosition = "";
    private VehicleManager mVehicleManager;
    @Override
    public void onCreate() {
        //Toast.makeText(this, "Servis Çalıştı.Bu Mesaj Servis Class'dan", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getApplicationContext(), VehicleManager.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);


        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (true) {
                    try {
                        Thread.sleep(10000);
                        Intent dialogIntent = new Intent(NotificationService.this, StarterActivity.class);
                        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(dialogIntent);
                        onDestroy();
                        unbindService(mConnection);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {

                    }
                } else {

                }


            }
        }, 0, 10000);
    }



    @Override
    public void onDestroy() {//Servis stopService(); metoduyla durdurulduğunda çalışır
        timer.cancel();
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    VehicleSpeed.Listener mVehicleSpeedListener = new VehicleSpeed.Listener() {
        @Override
        public void receive(Measurement measurement) {
            final VehicleSpeed speed = (VehicleSpeed) measurement;
            if (speed!=null)
                vehicleSpeed = speed.getValue().doubleValue();

            vehicleSpeed = 0;
        }
    };

    TransmissionGearPosition.Listener mTransmissionGearPositionListener = new TransmissionGearPosition.Listener() {
        @Override
        public void receive(Measurement measurement) {
            final TransmissionGearPosition position = (TransmissionGearPosition) measurement;
            if(position != null)
                vitesPosition = position.getValue().toString();

        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        // Called when the connection with the VehicleManager service is
        // established, i.e. bound.
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            Log.i(TAG, "Bound to VehicleManager");
            // When the VehicleManager starts up, we store a reference to it
            // here in "mVehicleManager" so we can call functions on it
            // elsewhere in our code.
            //Toast.makeText(getApplicationContext(),"Servis Baglandi", Toast.LENGTH_LONG).show();
            mVehicleManager = ((VehicleManager.VehicleBinder) service)
                    .getService();

            // We want to receive updates whenever the EngineSpeed changes. We
            // have an EngineSpeed.Listener (see above, mSpeedListener) and here
            // we request that the VehicleManager call its receive() method
            // whenever the EngineSpeed changes
            mVehicleManager.addListener(VehicleSpeed.class, mVehicleSpeedListener);
            //mVehicleManager.addListener(TransmissionGearPosition.class, mTransmissionGearPositionListener);
        }

        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
            Log.w(TAG, "VehicleManager Service  disconnected unexpectedly");
            mVehicleManager = null;
        }
    };

}
