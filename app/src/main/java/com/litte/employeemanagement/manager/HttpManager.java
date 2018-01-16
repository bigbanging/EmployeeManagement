package com.litte.employeemanagement.manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.litte.employeemanagement.contant.IURL;
import com.litte.employeemanagement.entity.Employee;
import com.litte.employeemanagement.entity.User;
import com.litte.employeemanagement.util.ParamsUtils;
import com.litte.employeemanagement.util.StreamUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by litte on 2017/12/26.
 */

public class HttpManager {
    //登陆暗号标识
    static String SESSIONID = "";
    /**
     *  封装用户注册的方法
     * @param user
     * @return
     */
    public static boolean registerManager(User user){

        boolean flag = false;
        String path = IURL.REGISTER_URL;
//        BufferedReader reader = null;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            //拼接提交字符串数据
            Map<String,String> params = new HashMap<>();
            params.put("loginname", user.getLoginName());
            params.put("password", user.getPassword());
            params.put("realName", user.getRealName());
            params.put("email", user.getEmail());
            //根据拼接的字符串获得提交的数据
            /*StringBuilder sb = new StringBuilder();
            for (Map.Entry<String,String> entry:params.entrySet()){
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                sb.append("&");
            }
            String datas = sb.deleteCharAt(sb.length()-1).toString();*/
            String datas = ParamsUtils.createParams(params);
            byte[] bytes = datas.getBytes();
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length",String.valueOf(bytes.length));
            connection.connect();

            OutputStream os = connection.getOutputStream();
            os.write(bytes);
            os.flush();
            os.close();
            int statusCode = connection.getResponseCode();
            if (statusCode == 200){
                InputStream is = connection.getInputStream();
               /* InputStream is = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
                String line = "";
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine())!= null){
                    builder.append(line);
                }*/
//                String jsonStr = builder.toString();
                String jsonStr = StreamUtils.createStr(is);
                JSONObject jsonObject = new JSONObject(jsonStr);
                String result = jsonObject.getString("result");
                Log.i("TAG", "registerManager: loginName:"+ user.getLoginName());
                if ("ok".equals(result)){
                    flag = true;
                    Log.i("TAG", "registerManager: 注册成功 ！！result "+result);

                }else {
                    Log.i("TAG", "registerManager: 注册失败");
                }
            }else {
                Log.i("TAG", "registerManager:访问服务器失败 "+statusCode);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 封装登陆时获取验证码的方法
     * @return
     */
    public static Bitmap getLoginCode(){
        Bitmap codeBitmap = null;
        String path = IURL.CODE_URL;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.connect();
            int statusCode = connection.getResponseCode();
            if (statusCode == 200){
                SESSIONID = connection.getHeaderField("Set-Cookie").split(";")[0];
                InputStream is = connection.getInputStream();
                codeBitmap = BitmapFactory.decodeStream(is);
                Log.i("TAG", "getLoginCode:SESSIONID: "+SESSIONID);
            }else {
                Log.i("TAG", "getLoginCode:statusCode: "+statusCode);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return codeBitmap;
    }

    /**
     *  封装登陆的方法
     * @param loginName
     * @param password
     * @param code
     * @return
     */
    public static boolean loginHttpPost(String loginName,String password,String code){
        boolean flag = false;
        BufferedReader reader = null;
        try {
            URL url = new URL(IURL.LOGIN_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            //设置请求头信息 ***********************************************************************/
            connection.setRequestProperty("Cookie",SESSIONID);

            //拼接提交请求的参数
            Map<String,String>params = new HashMap<>();
            params.put("loginname",loginName);
            params.put("password",password);
            params.put("code",code);
            /*StringBuilder sb = new StringBuilder();
            for (Map.Entry<String,String> entry:params.entrySet()){
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                sb.append("&");
            }
            byte[] datas = sb.deleteCharAt(sb.length()-1).toString().getBytes();*/
            byte[] datas = ParamsUtils.createParams(params).getBytes();
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length",String.valueOf(datas.length));
            connection.connect();

            OutputStream os = connection.getOutputStream();
            os.write(datas);
            os.flush();
            os.close();

            int statusCode = connection.getResponseCode();
            if (statusCode == 200){
                InputStream is = connection.getInputStream();
                /*reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
                String line = "";
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine())!=null){
                    builder.append(line);
                }
                String jsonStr = builder.toString();*/
                String jsonStr = StreamUtils.createStr(is);
                JSONObject jsonObject = new JSONObject(jsonStr);
                String result = jsonObject.getString("result");
                if ("ok".equals(result)){
                    flag = true;
                    Log.i("TAG", "loginHttpPost: 登陆成功：result:"+result);
                }else {
                    String msg = jsonObject.getString("msg");
                    Log.i("TAG", "loginHttpPost:登陆失败 result:"+result+",msg:"+msg);
                }
            }else {
                Log.i("TAG", "loginHttpPost:访问服务器失败 statusCode:"+statusCode);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return flag;
    }

    /**
     *
     * 封装员工添加的post请求
     * @param employee
     * @return
     */
    public static boolean addEmpPos(Employee employee){
        boolean flag = false;
        BufferedReader reader = null;
        try {
            URL url = new URL(IURL.ADDEMP_URL);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            //拼接字符串  name:员工姓名 salary:员工薪水 age:员工年龄 gender:员工性别（1位字符:男为‘m’女为‘f’）

            Map<String,String> params = new HashMap<>();
            params.put("name",employee.getName());
            params.put("salary",String.valueOf(employee.getSalary()));
            params.put("age",String.valueOf(employee.getAge()));
            params.put("gender",employee.getGender());

            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry:params.entrySet()){
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                sb.append("&");
            }
            byte[] bytes = sb.deleteCharAt(sb.length()-1).toString().getBytes();//减去最后一位多余的
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length",String.valueOf(bytes.length));

            connection.connect();
            OutputStream os = connection.getOutputStream();
            os.write(bytes);
            os.flush();
            os.close();
            int statusCode = connection.getResponseCode();
            if (statusCode == 200){
                InputStream is = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
                String line = "";
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine())!=null){
                    builder.append(line);
                }
                String jsonStr = builder.toString();
                JSONObject jsonObject = new JSONObject(jsonStr);
                String result = jsonObject.getString("result");
                if ("ok".equals(result)){
                    flag = true;
                    Log.i("TAG", "addEmpPos: 添加成功"+result);
                }else {
                    String msg = jsonObject.getString("msg");//失败提示信息
                    Log.i("TAG", "addEmpPos: 添加失败"+result+",msg:"+msg);
                }
            }else {
                Log.i("TAG", "addEmpPos: 访问服务器失败"+statusCode);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 封装查询员工信息的方法
     * @return
     */
    public static List<Employee> queryEmpGet(){
        List<Employee> employeeList = new ArrayList<>();
        BufferedReader reader = null;
        try {
            URL url = new URL(IURL.QUERY_EMP_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.connect();
            int statusCode = connection.getResponseCode();
            if (statusCode == 200){
                InputStream is = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
                String line = "";
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine())!=null){
                    builder.append(line);
                }
                String jsonStr = builder.toString();
                JSONObject jsonObject = new JSONObject(jsonStr);
                String result = jsonObject.getString("result");
                if ("ok".equals(result)){

                    Log.i("TAG", "queryEmpGet:查询成功 result:"+result);
                    // 例：查询成功：{"result":"ok","data": [{"id":1,"name":"zhangsan","salary":12345.0,"age":12,"gender":"m"},

//                    String data = jsonObject.getString("data");
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        Employee employee = new Employee();
                        JSONObject jsonObj = array.getJSONObject(i);
                        int id  = jsonObj.getInt("id");
                        String name = jsonObj.getString("name");
                        double salary = jsonObj.getDouble("salary");
                        int age = jsonObj.getInt("age");
                        String gender = jsonObj.getString("gender");
//                        Employee employee = new Employee(id,name,salary,age,gender);
                        employee.setId(id);
                        employee.setName(name);
                        employee.setSalary(salary);
                        employee.setAge(age);
                        employee.setGender(gender);
                        employeeList.add(employee);
                    }
                }else {
                    String msg = jsonObject.getString("msg");
                    Log.i("TAG", "queryEmpGet:查询失败 msg:"+msg);
                }
            }else {
                Log.i("TAG", "queryEmpGet: 访问服务器失败");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return employeeList;
    }
}
