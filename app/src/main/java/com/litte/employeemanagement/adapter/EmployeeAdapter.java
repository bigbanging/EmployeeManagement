package com.litte.employeemanagement.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.litte.employeemanagement.R;
import com.litte.employeemanagement.entity.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by litte on 2017/12/28.
 */

public class EmployeeAdapter extends BaseAdapter {
    private List<Employee> employeeList = new ArrayList<>();
    Context context;

    public EmployeeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return employeeList.size();
    }

    @Override
    public Object getItem(int position) {
        return employeeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.list_tem,null);
            holder = new ViewHolder();
            holder.textView_id = convertView.findViewById(R.id.list_item_id);
            holder.textView_name = convertView.findViewById(R.id.list_item_name);
            holder.textView_salary = convertView.findViewById(R.id.list_item_salary);
            holder.textView_age = convertView.findViewById(R.id.list_item_age);
            holder.textView_gender = convertView.findViewById(R.id.list_item_gender);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Employee employee = (Employee) getItem(position);
        holder.textView_id.setText(String.valueOf(employee.getId()));
        holder.textView_name.setText(employee.getName());
        holder.textView_salary.setText(String.valueOf(employee.getSalary()));
        holder.textView_age.setText(String.valueOf(employee.getAge()));
//        holder.textView_gender.setText(employee.getGender());
        String gender = "" ;
        if (employee.getGender().equals("m")){
            gender = "男";
        }else {
            gender = "女";
        }
        holder.textView_gender.setText(gender);
        return convertView;
    }
    private class ViewHolder{
        TextView textView_id;
        TextView textView_name;
        TextView textView_salary;
        TextView textView_age;
        TextView textView_gender;

    }
    public void addEmp(List<Employee> employees){
        if (employees != null){
            employeeList.addAll(employees);
            notifyDataSetChanged();
        }
    }
}
