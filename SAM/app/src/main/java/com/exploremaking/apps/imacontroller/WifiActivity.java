package com.exploremaking.apps.imacontroller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.messaging.Message;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.MessageDeliveryInfo;
import com.sinch.android.rtc.messaging.MessageFailureInfo;

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

        //margins
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int buttonMarginBase = metrics.widthPixels/80;
        marginchangerWifiRelative(toplogo, 4 * buttonMarginBase, 4 * buttonMarginBase, 2 * buttonMarginBase, 2 * buttonMarginBase);
        marginchangerWifiLinear(wifitext, 10*buttonMarginBase, 10*buttonMarginBase, 0, buttonMarginBase);
        marginchangerWifiRelative(sendbutton, 4*buttonMarginBase, 4*buttonMarginBase, 0, 0);
        marginchangerWifiRelative(logo, 4*buttonMarginBase, 4*buttonMarginBase, 0, 0);


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

    @Override
    public void onBackPressed(){
        final Intent bluetoothactivity = new Intent(this, BluetoothChooser.class);
        startActivity(bluetoothactivity);
        BluetoothChooser.cancel();
        finish();
    }

    public void marginchangerWifiRelative(View something, int marginLeft, int marginRight, int marginUp, int marginDown){
        RelativeLayout.LayoutParams barframeright = (RelativeLayout.LayoutParams)something.getLayoutParams();
        barframeright.topMargin = marginUp;
        barframeright.bottomMargin = marginDown;
        barframeright.rightMargin = marginRight;
        barframeright.leftMargin = marginLeft;
        something.setLayoutParams(barframeright);
    }

    public void marginchangerWifiLinear(View something, int marginLeft, int marginRight, int marginUp, int marginDown){
        LinearLayout.LayoutParams barframeright = (LinearLayout.LayoutParams)something.getLayoutParams();
        barframeright.topMargin = marginUp;
        barframeright.bottomMargin = marginDown;
        barframeright.rightMargin = marginRight;
        barframeright.leftMargin = marginLeft;
        something.setLayoutParams(barframeright);
    }
}
