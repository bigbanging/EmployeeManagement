package com.litte.employeemanagement.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.litte.employeemanagement.R;
import com.litte.employeemanagement.StartActivity;
import com.litte.employeemanagement.entity.User;
import com.litte.employeemanagement.manager.HttpManager;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_register_username;
    private EditText et_register_password;
    private EditText et_register_confirm_password;
    private EditText et_register_real_name;
    private EditText et_register_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_register_username = (EditText) findViewById(R.id.et_register_username);
        et_register_password = (EditText) findViewById(R.id.et_register_password);
        et_register_confirm_password = (EditText) findViewById(R.id.et_register_confirm_password);
        et_register_real_name = (EditText) findViewById(R.id.et_register_real_name);
        et_register_email = (EditText) findViewById(R.id.et_register_email);
    }
    public void employeeRegister(View view){
        String loginName = et_register_username.getText().toString();
        String password = et_register_password.getText().toString();
        String confirmPassword = et_register_confirm_password.getText().toString();
        String realName = et_register_real_name.getText().toString();
        String email = et_register_email.getText().toString();

        if (TextUtils.isEmpty(loginName)||TextUtils.isEmpty(password)||TextUtils.isEmpty(confirmPassword)||TextUtils.isEmpty(realName)||
                TextUtils.isEmpty(email)){
            Toast.makeText(this, "注册信息不完整", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirmPassword)){
            Toast.makeText(this, "密码输入不相同", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(-1,loginName,password,realName,email);
        //发送注册请求 异步请求
        asyncRegister(user);
    }

    private void asyncRegister(final User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = HttpManager.registerManager(user);
                //将返回结果返回到Ui主线程
                Message message = handler.obtainMessage();
                message.obj = flag;
                handler.sendMessage(message);
            }
        }).start();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Boolean flag = (Boolean) msg.obj;
            if (flag){
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                cleanContent();
                startActivity(new Intent(RegisterActivity.this, StartActivity.class));
            }else {
                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
    /*清空输入框内容
     */
    public void cleanContent(){
        et_register_email.setText("");
        et_register_real_name.setText("");
        et_register_confirm_password.setText("");
        et_register_password.setText("");
        et_register_username.setText("");
    }
}
