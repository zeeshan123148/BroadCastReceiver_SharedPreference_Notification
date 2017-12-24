package com.example.zeeshan.broadcastreceiver_sharedpreference_notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Button btnSave;
    EditText etName;
    TextView tvBatteryLevel;
    SharedPreferences sharedPreferences;
    final static String Key = "MyPreferneces";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting references
        btnSave = (Button) findViewById(R.id.btnSave);
        etName = (EditText) findViewById(R.id.etName);
        tvBatteryLevel = (TextView) findViewById(R.id.tvBatteryStatus);
        sharedPreferences = getSharedPreferences(Key, MODE_PRIVATE);

        //gettingSavedName
        String name = sharedPreferences.getString("name", "");
        etName.setText(name);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", etName.getText().toString());
                editor.apply();
                Toast.makeText(getApplicationContext(), "Name Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_LOW");
        intentFilter.addAction("android.intent.action.BATTERY_LOW");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.BATTERY_LOW")) {
                tvBatteryLevel.setText("Battery Level is Low");
            } else if (intent.getAction().equals("android.intent.action.BATTERY_OKAY")) {
                tvBatteryLevel.setText("Battery Level is Okay");
            }
        }
    };
}

