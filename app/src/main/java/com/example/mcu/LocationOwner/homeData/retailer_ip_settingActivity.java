package com.example.mcu.LocationOwner.homeData;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mcu.R;

public class retailer_ip_settingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_ip_setting);
        String ip_setting = getIntent().getStringExtra("ip");
        Toast.makeText(this, ip_setting, Toast.LENGTH_SHORT).show();
    }
}