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
import android.widget.TextView;

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



        final Button sendbutton = (Button) findViewById(R.id.sendbutton);
        final EditText clientside = (EditText) findViewById(R.id.nameField);
        final TextView fromWeb = (TextView) findViewById(R.id.recieved);




        final MessageClient messageClient;

        sendbutton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              // Create a WritableMessage
                                              WritableMessage message = new WritableMessage(
                                                      "test1", clientside.getText().toString());


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

                                                      if(BluetoothChooser.valid())
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

                                              fromWeb.setText("ready");
                                          }


                                      }
        );



    }
    @Override
    public void onDestroy() {
        sinchClient.stopListeningOnActiveConnection();
        sinchClient.terminate();
        super.onDestroy();
    }

}
