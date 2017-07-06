package edu.ptucustomviewordrawable;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtils {
    public static boolean debugFlag = true;



    public static void logMainInfo(String msg) {
        if (StringUtils.isEmpty(msg))
            return;

        if (debugFlag)
            logDetailInfo("Main :" + msg, 1);
    }


    private static void logDetailInfo(String msg, int stackMethodCount) {
        if (debugFlag) {
            String s = null;
            int stackMethod = 3 + stackMethodCount;//dalvik.system.VMStack.getThreadStackTrace(Native Method)->java.lang.Thread.getStackTrace(Thread.java:579)->logDetail
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            if (stackTrace != null && stackTrace.length > stackMethod) {
                StackTraceElement stackTraceElement = stackTrace[stackMethod];//android 调用堆栈 [0]dalvik.system.VMStack#getThreadStackTrace [1]java.lang.Thread#getStackTrace->[2]LogUtils#logDetailInfo->[3]调用的方法
                //java调用堆栈 [0]java.lang.Thread#getStackTrace->[1]LogUtils#logDetailInfo->[2]调用的方法
                s = "\n[" + stackTraceElement.getClassName() + "#" + stackTraceElement.getMethodName() + ":" + stackTraceElement.getLineNumber() + "] \n";
            } else {
                s = "[] ";
            }
            Log.i("wqcpz", s + msg);
        }
    }




    public static String format(String jsonStr) {
        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        for (int i = 0; i < jsonStr.length(); i++) {
            char c = jsonStr.charAt(i);
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c + "\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c + "\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }

        return jsonForMatStr.toString();

    }

    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }
}
