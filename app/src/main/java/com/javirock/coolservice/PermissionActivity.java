package com.javirock.coolservice;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


/**
 * Created by javier on 18/03/2018.
 */

public class PermissionActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.i("onCreate");

        // TODO: check if every permission is ok, if not, start activity for result
        Intent btIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        btIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(btIntent);

        finish();

    }
        @Override
        public void finish() {
            ResultReceiver receiver =
                    getIntent().getParcelableExtra(CoolService.KEY_RECEIVER);
            Bundle resultData = new Bundle();

            resultData.putString(CoolService.KEY_MESSAGE, "Hello world!");
            receiver.send(CoolService.RESULT_OK, resultData);

            super.finish();
        }


}
