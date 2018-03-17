package com.javirock.coolservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CoolService extends Service {
    public CoolService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
