package com.litte.employeemanagement.contant;

/**
 * Created by litte on 2017/12/26.
 */

public interface IURL {
    String ROOT = "http://192.168.47.221:8080/EmployeeServer/";
    //获取登录验证码请求：
    String CODE_URL = ROOT+"getCode.do";
    //注册请求
    String REGISTER_URL = ROOT+"regist.do";
    //登陆请求
    String LOGIN_URL = ROOT+"login.do";
    //添加员工请求
    String ADDEMP_URL = ROOT+"addEmp";
    //查询员工信息
    String QUERY_EMP_URL = ROOT+"listEmp";
}
