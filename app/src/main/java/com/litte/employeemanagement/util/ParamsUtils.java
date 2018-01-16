package com.litte.employeemanagement.util;

import java.util.Map;

/**
 * Created by litte on 2017/12/26.
 */

public class ParamsUtils {
    public static String createParams(Map<String,String> params){
        //根据拼接的字符串获得提交的数据
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String,String> entry:params.entrySet()){
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("&");
        }
        String datas = sb.deleteCharAt(sb.length()-1).toString();
        return datas;
    }
}
