package com.litte.employeemanagement.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.litte.employeemanagement.R;
import com.litte.employeemanagement.entity.Employee;
import com.litte.employeemanagement.manager.HttpManager;

public class AddEmployee extends AppCompatActivity {

    private EditText et_add_name;
    private EditText et_add_salary;
    private EditText et_add_age;
    private RadioButton rb_man,rb_woman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        initUI();
    }

    private void initUI() {
        et_add_name = (EditText) findViewById(R.id.et_add_name);
        et_add_salary = (EditText) findViewById(R.id.et_add_salary);
        et_add_age = (EditText) findViewById(R.id.et_add_age);
        rb_man = (RadioButton) findViewById(R.id.rb_man);
        rb_woman = (RadioButton) findViewById(R.id.rb_woman);
    }

    public void add_addEmp(View view){
        String name = et_add_name.getText().toString();
        double salary = Double.parseDouble(et_add_salary.getText().toString());
        int age = Integer.parseInt(et_add_age.getText().toString());

        String gender = "";
        if (rb_man.isChecked()){
            gender = "m";
        }else if (rb_woman.isChecked()){
            gender = "f";
        }
        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "员工姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (salary<0){
            Toast.makeText(this, "请正确输入员工薪水", Toast.LENGTH_SHORT).show();
            return;
        }
        if (age<=0||age>120){
            Toast.makeText(this, "请输入正确的员工年龄", Toast.LENGTH_SHORT).show();
            return;
        }
        //封装可用信息
        Employee employee = new Employee(-1,name,salary,age,gender);
        //实行异步加载 执行添加 指令
        asyncAddEmp(employee);
        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,QueryEmployee.class));
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                Boolean flag = (Boolean) msg.obj;
                if (flag){
                    Toast.makeText(AddEmployee.this, "添加成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AddEmployee.this, "添加失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    private void asyncAddEmp(final Employee employee) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = HttpManager.addEmpPos(employee);
                Message message = handler.obtainMessage();
                message.obj = flag;
                message.what = 1;
                handler.sendMessage(message);
            }
        }).start();
    }
}
