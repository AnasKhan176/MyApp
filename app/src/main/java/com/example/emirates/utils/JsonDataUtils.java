package com.example.emirates.utils;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class JsonDataUtils {
    public static String getAssetJsonData(Context context) {
        String json;
        try {
            InputStream is = context.getAssets().open("Response.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            return null;
        }
        return json;
    }
}
