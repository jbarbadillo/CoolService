package com.javirock.coolservice;

/**
 * Created by javier on 17/03/2018.
 */

public class Constants {
    public interface ACTION {
        public static String MAIN_ACTION = "com.marothiatechs.foregroundservice.action.main";
        public static String INIT_ACTION = "com.marothiatechs.foregroundservice.action.init";
        public static String PREV_ACTION = "com.marothiatechs.foregroundservice.action.prev";
        public static String PLAY_ACTION = "com.marothiatechs.foregroundservice.action.play";
        public static String NEXT_ACTION = "com.marothiatechs.foregroundservice.action.next";
        public static String STARTFOREGROUND_ACTION = "com.marothiatechs.foregroundservice.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "com.marothiatechs.foregroundservice.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }
}
