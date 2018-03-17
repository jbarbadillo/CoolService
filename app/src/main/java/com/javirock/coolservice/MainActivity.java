package com.javirock.coolservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent service = new Intent(MainActivity.this, CoolService.class);
        service.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        startService(service);
    }
}
