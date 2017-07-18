package edu.ptucustomviewordrawable;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class LogUtils {
    public static boolean debugFlag = true;



    public static void logMainInfo(String msg) {
        if (StringUtils.isEmpty(msg))
            return;

        if (debugFlag)
            logDetailInfo("Main :" + msg, 1);
    }
    public static void logViewInfo(View msg) {
        if (msg==null)
            return;

        if (debugFlag)
            logDetailInfo("ViewInfo :\n" + getViewHierarchy(msg), 1);
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

    public static String getViewHierarchy(@NonNull View v) {
        StringBuilder desc = new StringBuilder();
        getViewHierarchy(v, desc, 0);
        return desc.toString();
    }

    private static void getViewHierarchy(View v, StringBuilder desc, int margin) {
        desc.append(getViewMessage(v, margin));
        if (v instanceof ViewGroup) {
            margin++;
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                getViewHierarchy(vg.getChildAt(i), desc, margin);
            }
        }
    }

    private static String getViewMessage(View v, int marginOffset) {
        String repeated = new String(new char[marginOffset]).replace("\0", "  ");
        String resourceId = v.getResources() != null ? (v.getId() > 0 ? v.getResources().getResourceName(v.getId()) : "no_id") : "no_resources";
        return repeated + "[" + v.getClass().getSimpleName()+"#"+Integer.toHexString(System.identityHashCode(v)) + "] " + resourceId + "\n";
    }
}
