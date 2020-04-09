package com.example.androidbasics_broadcastreceiver;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateSMS extends AppCompatActivity {
    String phoneNo, sms;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createsms);
    }

    public void onClickCreateSms(View view) {
        EditText editTextSms = (EditText) findViewById(R.id.sms);
        EditText editTextPhone = (EditText) findViewById(R.id.phoneno);
        phoneNo = editTextPhone.getText().toString();
        sms = editTextSms.getText().toString();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, sms, null, null);
        Toast.makeText(getApplicationContext(), "SMS Sent!", Toast.LENGTH_LONG).show();
    }
}
