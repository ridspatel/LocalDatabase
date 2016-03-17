package com.example.user.newdatabasedemo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.newdatabasedemo.R;
import com.example.user.newdatabasedemo.bean.EmployeeBean;

import java.util.ArrayList;

/**
 * Created by user on 2/3/16.
 */
public class EmployeeAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;
    ArrayList<EmployeeBean> empList;


    public EmployeeAdapter(Context context, ArrayList<EmployeeBean> eList) {
        this.context = context;
        this.empList = eList;
    }


    @Override
    public int getCount() {
        return empList.size();
    }

    @Override
    public Object getItem(int position) {
        return empList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return empList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;

        layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_list, null);
            holder = new Holder();
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtEmail = (TextView) convertView.findViewById(R.id.txtEmail);
            holder.txtSal = (TextView) convertView.findViewById(R.id.txtSal);
            holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            holder.btnUpdate = (Button) convertView.findViewById(R.id.btnUpdate);
            holder.btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.txtName.setText(empList.get(position).name);
        holder.txtEmail.setText(empList.get(position).email);
        holder.txtSal.setText(empList.get(position).salary);
        holder.txtDate.setText(empList.get(position).date);

        holder.btnUpdate.setTag(empList.get(position));
        holder.btnUpdate.setOnClickListener((View.OnClickListener) context);

        holder.btnDelete.setTag(empList.get(position));
        holder.btnDelete.setOnClickListener((View.OnClickListener) context);


        return convertView;
    }

    public class Holder {
        TextView txtName, txtEmail, txtSal, txtDate;
        Button btnUpdate, btnDelete;
    }
}
