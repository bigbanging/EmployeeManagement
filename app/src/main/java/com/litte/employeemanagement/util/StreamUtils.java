package com.litte.employeemanagement.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by litte on 2017/12/26.
 */

public class StreamUtils {
    public static String createStr(InputStream is){
        BufferedReader reader = null;
        String jsonStr = "";
        try {
            reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine())!=null){
                builder.append(line);
            }
            jsonStr = builder.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonStr;
    }
}
