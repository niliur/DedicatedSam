package exploremakingapps.powerinfo;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Views
        TextView cap = (TextView) findViewById(R.id.cap);
        TextView capRemaining = (TextView) findViewById(R.id.capRemaining);
        TextView currentAve = (TextView) findViewById(R.id.currentAve);
        TextView currentIns = (TextView) findViewById(R.id.currentIns);
        TextView energyRemaining = (TextView) findViewById(R.id.energyRemaining);

        //Get data from BatteryManager
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        cap.setText("Battery Capacity (mAh):");
    }
}
