package com.example.androidbasics_broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_RECEIVE_SMS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
    }

    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        String message = intent.getStringExtra(IncomingSms.MESSAGE);
        TextView textView = (TextView) findViewById(R.id.textview);
        textView.setText(message);
    }

    public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, CreateSMS.class));
    }

    public void checkPermissions() {
        //check if permission not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            //if permission not granted, check if user has denied permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                // user has denied, a popup will appear
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_RECEIVE_SMS);
            } else {
                // a popup will appear for asking permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_RECEIVE_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        //will check the request code
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_RECEIVE_SMS: {
                //check whether the result is greater than 0 and is equal to PERMISSION_GRANTED
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //broadcast receiver will work in background
                    Toast.makeText(getApplicationContext(), "Thanks for permitting!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Give permissions!", Toast.LENGTH_LONG).show();
                    this.finish();
                }
            }
        }
    }
}