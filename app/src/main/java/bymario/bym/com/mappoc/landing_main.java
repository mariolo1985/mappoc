package bymario.bym.com.mappoc;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


public class landing_main extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_main);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if (isGooglePlayServiceAvail(getBaseContext())) {
                            // MOVE TO FINDER LAYOUT
                            logMessage("Found Play Service");
                            Intent finderIntent = new Intent(landing_main.this, finder.class);
                            startActivity(finderIntent);
                        } else {
                            // MOVE TO 'NO G-PLAY SERVICE' LAYOUT
                            logMessage("No Service Found");
                            Intent errorIntent = new Intent(landing_main.this, gserviceError.class);
                            startActivity(errorIntent);
                        }
                    }
                },
                1000);

    }


    // CHECK FOR GOOGLE SERVICE
    public boolean isGooglePlayServiceAvail(Context activity) {
        boolean isAvail = false;

        GoogleApiAvailability gApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = gApiAvailability.isGooglePlayServicesAvailable(activity);

        // check for errors
        /*
        if (resultCode!= ConnectionResult.SUCCESS){
            if (gApiAvailability.isUserResolvableError(resultCode)){
                gApiAvailability.getErrorDialog(activity,resultCode,2404).show();
            }
        }*/

        return resultCode == ConnectionResult.SUCCESS;
    }

    // HELPERS
    public void logMessage(String msg) {
        Log.v("M A P P O C ", msg);
    }
}

