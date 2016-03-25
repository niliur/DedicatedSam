package com.example.david.imasam;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.messaging.Message;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.MessageDeliveryInfo;
import com.sinch.android.rtc.messaging.MessageFailureInfo;
import com.sinch.android.rtc.messaging.WritableMessage;

import java.util.List;

public class WifiActivity extends Activity {

    private SinchClient sinchClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_activity);
        final android.content.Context context = this.getApplicationContext();



        final View sendbutton = findViewById(R.id.sendbutton);
        final EditText clientside = (EditText) findViewById(R.id.nameField);
        final ImageView connection = (ImageView) findViewById(R.id.connectionStatus);
        final ImageView wifitext = (ImageView) findViewById(R.id.wifi_text);
        final ImageView logo = (ImageView) findViewById(R.id.imalogo);
        final ImageView toplogo = (ImageView) findViewById(R.id.toplogo);

        toplogo.setVisibility(View.INVISIBLE);

        sendbutton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              if (clientside.getText().toString().equals(""))
                                                  Toast.makeText(WifiActivity.this, "Write your unique nam here", Toast.LENGTH_SHORT).show();
                                              else {

                                                  // Instantiate a SinchClient using the SinchClientBuilder.

                                                  sinchClient = Sinch.getSinchClientBuilder().context(context)
                                                          .applicationKey("b9fe18db-32c1-4025-aad8-b08454dfa3c4")
                                                          .applicationSecret("YiiLEUNX00Wp/JnVInhiOw==")
                                                          .environmentHost("sandbox.sinch.com")
                                                          .userId(clientside.getText().toString())
                                                          .build();


                                                  sinchClient.setSupportMessaging(true);
                                                  sinchClient.startListeningOnActiveConnection();
                                                  sinchClient.start();

                                                  final MessageClient messageClient = sinchClient.getMessageClient();
                                                  //fromWeb.setText(sinchClient.getLocalUserId());
                                                  messageClient.addMessageClientListener(new MessageClientListener() {
                                                      @Override
                                                      public void onIncomingMessage(MessageClient messageClient, final Message message) {

                                                          //fromWeb.setText(message.getTextBody());
                                                          clientside.setText(message.getTextBody());

                                                          Log.d("myapp", "incoming");

                                                          if (BluetoothChooser.valid())
                                                              BluetoothChooser.write(message.getTextBody().getBytes());

                                                      }

                                                      @Override
                                                      public void onMessageSent(MessageClient messageClient, Message message, String s) {
                                                          Log.d("myapp", "outgoing");
                                                      }

                                                      @Override
                                                      public void onMessageFailed(MessageClient messageClient, Message message, MessageFailureInfo messageFailureInfo) {
                                                          Log.d("myapp", "fail");

                                                      }

                                                      @Override
                                                      public void onMessageDelivered(MessageClient messageClient, MessageDeliveryInfo messageDeliveryInfo) {

                                                      }

                                                      @Override
                                                      public void onShouldSendPushData(MessageClient messageClient, Message message, List<PushPair> list) {

                                                      }
                                                  });

                                                  connection.setImageResource(R.drawable.connected);
                                                  wifitext.setBackgroundResource(R.drawable.connectedtext);
                                                  clientside.setVisibility(View.INVISIBLE);
                                                  logo.setVisibility(View.INVISIBLE);
                                                  sendbutton.setVisibility(View.INVISIBLE);
                                                  toplogo.setVisibility(View.VISIBLE);
                                              }
                                          }

                                      }
        );



    }
    @Override
    public void onDestroy() {
        if (sinchClient != null) {
            sinchClient.stopListeningOnActiveConnection();
            sinchClient.terminate();
        }
        super.onDestroy();
    }

}
