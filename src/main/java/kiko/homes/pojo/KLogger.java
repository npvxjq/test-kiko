package kiko.homes.pojo;

import java.time.LocalDateTime;

public class KLogger {
    public static void info(String msg) {        System.out.println("-"+LocalDateTime.now()+"INFO: "+msg);    }
    public static void warning(String msg) {        System.out.println("-"+LocalDateTime.now()+"WARNING: "+msg);    }
    public static void severe(String msg) {        System.out.println("-"+LocalDateTime.now()+"ERROR: "+msg);    }
}
