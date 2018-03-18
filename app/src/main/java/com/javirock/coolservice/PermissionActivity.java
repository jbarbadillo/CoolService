package com.javirock.coolservice;

import android.app.Activity;
import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by javier on 18/03/2018.
 */

public class PermissionActivity extends Activity {
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
