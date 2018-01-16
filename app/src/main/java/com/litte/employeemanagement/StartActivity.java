package com.litte.employeemanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.litte.employeemanagement.activity.AddEmployee;
import com.litte.employeemanagement.activity.LoginActivity;
import com.litte.employeemanagement.activity.RegisterActivity;
import com.litte.employeemanagement.activity.QueryEmployee;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }
    public void register(View view){
        startActivity(new Intent(StartActivity.this, RegisterActivity.class));
    }
    public void login(View view){
        startActivity(new Intent(StartActivity.this, LoginActivity.class));
    }
    public void addEmployee(View view){
        startActivity(new Intent(StartActivity.this, AddEmployee.class));
    }
    public void scanEmployee(View view){
        startActivity(new Intent(StartActivity.this, QueryEmployee.class));
    }
}
