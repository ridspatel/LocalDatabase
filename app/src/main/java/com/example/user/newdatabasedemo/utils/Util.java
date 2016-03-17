package com.example.user.newdatabasedemo.utils;

import android.content.Context;

import java.util.regex.Pattern;

/**
 * Created by user on 1/3/16.
 */
public class Util {

    public static void SystemUpdate(Context context) {
        DBHelper dbHelper = null;
        int level;

        try {
            dbHelper = new DBHelper(context);
            level = Integer.parseInt(Pref.getValue(context, "LEVEL", "0"));

            if (level == 0) {
                dbHelper.upgrade(level);
                level++;
            }
            Pref.setValue(context, "LEVEL", level + "");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null)
                dbHelper.close();
            dbHelper = null;
            level = 0;
        }

    }

    public static Pattern EMAIL_PATTERN = Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$");
}
