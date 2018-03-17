package com.javirock.coolservice;

/**
 * Created by javier on 17/03/2018.
 */

public class Constants {
    public interface ACTION {
        public static String INIT_ACTION = "com.marothiatechs.foregroundservice.action.init";


        public static String MAIN_ACTION = "com.javirock.coolservice.action.main";
        public static String STARTFOREGROUND_ACTION = "com.javirock.coolservice.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "com.javirock.coolservice.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }
}
