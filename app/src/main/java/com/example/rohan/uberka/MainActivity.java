package com.example.rohan.uberka;

import android.content.Intent;
import android.net.ParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    public void redirectActivity() {

        if (ParseUser.getCurrentUser().getString("riderOrDriver").equals("rider")) {

            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);

        } else {

            Intent intent = new Intent(getApplicationContext(), DriverActivity.class);
            startActivity(intent);


        }
    }
    public void getStarted(View view) {

        Switch userTypeSwitch = (Switch) findViewById(R.id.userTypeSwitch);

        Log.i("Switch value", String.valueOf(userTypeSwitch.isChecked()));

        String userType = "rider";

        if (userTypeSwitch.isChecked()) {

            userType = "driver";

        }

        ParseUser.getCurrentUser().put("riderOrDriver", userType);

        Log.i("Info", "Redirecting as " + userType);
        redirectActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        if (ParseUser.getCurrentUser() == null) {

            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, com.parse.ParseException e) {
                    if (e == null) {

                        Log.i("Info", "Anonymous login successful");

                    } else {

                        Log.i("Info", "Anonymous login failed");

                    }


                }
            });


        }
else {

            if (ParseUser.getCurrentUser().get("riderOrDriver") != null) {

                Log.i("Info", "Redirecting as " + ParseUser.getCurrentUser().get("riderOrDriver"));
                redirectActivity();

            }


        }



        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}
