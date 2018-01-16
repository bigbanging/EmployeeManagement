package com.litte.employeemanagement.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.litte.employeemanagement.R;
import com.litte.employeemanagement.manager.HttpManager;


public class LoginActivity extends AppCompatActivity {

    private EditText et_login_username;
    private EditText et_login_password;
    private EditText et_login_code;
    private ImageView img_login_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_login_username = (EditText) findViewById(R.id.et_login_username);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        et_login_code = (EditText) findViewById(R.id.et_login_code);
        img_login_code = (ImageView) findViewById(R.id.img_login_code);
        //异步加载验证码
        asyncLoadCode();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == 1){
                Bitmap bitmap = (Bitmap) msg.obj;
                img_login_code.setImageBitmap(bitmap);
            }else if (what == 2){
                //用户登陆的处理结果
                Boolean flag = (Boolean) msg.obj;
                if (flag){
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    private void asyncLoadCode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmapCode = HttpManager.getLoginCode();
                Message message = handler.obtainMessage();
                message.what = 1;
                message.obj = bitmapCode;
                handler.sendMessage(message);
            }
        }).start();
    }

    public void login_login(View view){
        String loginName = et_login_username.getText().toString();
        String password = et_login_password.getText().toString();
        String code = et_login_code.getText().toString();
        //发送异步请求 登陆操作
        asyncLogin(loginName,password,code);
        startActivity(new Intent(this,QueryEmployee.class));

    }

    private void asyncLogin(final String loginName, final String password, final String code) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = HttpManager.loginHttpPost(loginName,password,code);
                Message message = handler.obtainMessage();
                message.what = 2;
                message.obj = flag;
                handler.sendMessage(message);
            }
        }).start();
    }

    public void login_getCode(View view){
        asyncLoadCode();
    }
}
