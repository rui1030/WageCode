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

    public static String H5URL = "";
    public static String UPLOAD_URL = "";

    public static void initConfig(){
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(PATH));
            String line = null;
            while ((line = br.readLine()) != null){
                String[] arr = line.split("=");
                if("h5url".equalsIgnoreCase(arr[0])){
                    H5URL = arr[1];
                }else if("uploadUrl".equalsIgnoreCase(arr[0])){
                    UPLOAD_URL = arr[1];
                }
            }
            Log.d(TAG, "H5URL=>" + H5URL + ",UPLOAD_URL=>" + UPLOAD_URL);
            br.close();
            br = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
