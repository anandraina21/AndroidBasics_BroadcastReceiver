package com.example.androidbasics_broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;


public class IncomingSms extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SMS Broadcast Receiver";
    String msg, phoneNo;
    public static String MESSAGE = "Message";

    public void onReceive(Context context, Intent intent) {
        //retrieves action to be performed and display on log
        Log.i(TAG, "Intent received: " + intent.getAction());
        if (intent.getAction() == SMS_RECEIVED) {
            //retrieves a map of extended data from the intent
            Bundle dataBundle = intent.getExtras();
            if (dataBundle != null) {
                //creating pdu(protocol data unit) object which is used for transferring message
                Object[] pdusObj = (Object[]) dataBundle.get("pdus");
                final SmsMessage[] message = new SmsMessage[pdusObj.length];

                for (int i = 0; i < pdusObj.length; i++) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // for api 23 and higher
                        String format = dataBundle.getString("format");
                        message[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i], format);
                    } else {
                        message[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    }
                    //get message and phone number
                    msg = message[i].getMessageBody();
                    phoneNo = message[i].getDisplayOriginatingAddress();
                }

                Toast.makeText(context, "msg: " + msg + "\nphoneNo: " + phoneNo, Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(context, MainActivity.class);
                intent1.putExtra(MESSAGE, "msg: " + msg + "\nphoneNo: " + phoneNo);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
        }
    }

}
