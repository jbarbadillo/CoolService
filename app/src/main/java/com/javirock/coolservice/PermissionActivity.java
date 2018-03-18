package com.javirock.coolservice;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.content.ContextCompat;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


/**
 * Activity for requesting all necessary permissions for the service to run
 */
public class PermissionActivity extends Activity {
    private static final int REQUEST_BLUETOOTH_ENABLE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.i("onCreate");

        launchCheckPermissions();
    }
    private void launchCheckPermissions(){
        if(checkPermissions()){
            finish();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_BLUETOOTH_ENABLE) {
            if(resultCode == Activity.RESULT_OK){
                Logger.i("REQUEST_BLUETOOTH_ENABLE OK!");
                launchCheckPermissions();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Logger.i("REQUEST_BLUETOOTH_ENABLE CANCELED!");
                launchCheckPermissions();
            }
        }
    }
    /**
     * Checks all necessary permissions for the app to run
     * @return True if all permissions are OK
     */
    private boolean checkPermissions(){
        if(checkBluetoothEnable()){
            return true;
        }else{
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, REQUEST_BLUETOOTH_ENABLE);
            return false;
        }

    }
    private boolean checkBluetoothEnable(){
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (!adapter.isEnabled()){
            return false;
        }else{
            return true;
        }
    }
    @Override
    public void finish() {
        ResultReceiver receiver =
                getIntent().getParcelableExtra(CoolService.KEY_RECEIVER);
        Bundle resultData = new Bundle();

        resultData.putString(CoolService.KEY_MESSAGE, "Permissions OK");
        receiver.send(CoolService.RESULT_OK, resultData);

        super.finish();
    }


}
