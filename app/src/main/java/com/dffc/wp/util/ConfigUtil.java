package com.dffc.wp.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by zhuruyi on 16/11/1.
 */
public class ConfigUtil {
    private static final String TAG = "ConfigUtil";

    private static final String PATH = "/sdcard/wage.config";

    public static String URL = "";

    public static void initConfig(){
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(PATH));
            String line = br.readLine();
            URL = line.split("=")[1];
            Log.d(TAG, "URL=>" + URL);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
