package com.litte.employeemanagement.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.litte.employeemanagement.R;
import com.litte.employeemanagement.adapter.EmployeeAdapter;
import com.litte.employeemanagement.entity.Employee;
import com.litte.employeemanagement.manager.HttpManager;

import java.util.List;

public class QueryEmployee extends AppCompatActivity {

    private ListView listView;
    EmployeeAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_employee);
        listView = (ListView) findViewById(R.id.list_item);
        adapter = new EmployeeAdapter(this);
        listView.setAdapter(adapter);
        asyncQueryEmployee();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            List<Employee> employees = (List<Employee>) msg.obj;
            if (employees!=null){
                adapter.addEmp(employees);
            }
        }
    };
    private void asyncQueryEmployee() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Employee> employeeList = HttpManager.queryEmpGet();
                Message message = handler.obtainMessage();
                message.obj = employeeList;
                handler.sendMessage(message);
            }
        }).start();
    }

    public void add(View view){
        startActivity(new Intent(QueryEmployee.this,AddEmployee.class));
    }
}
