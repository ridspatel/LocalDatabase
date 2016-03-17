package com.example.user.newdatabasedemo.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newdatabasedemo.R;
import com.example.user.newdatabasedemo.adapter.EmployeeAdapter;
import com.example.user.newdatabasedemo.bean.EmployeeBean;
import com.example.user.newdatabasedemo.bll.EmployeeBll;
import com.example.user.newdatabasedemo.utils.Util;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by user on 2/3/16.
 */
public class EmployeeList extends Activity implements View.OnClickListener {

    private ListView empListView;
    private RelativeLayout llEditView;
    private EditText edtEditname, edtEditemail, edtEditsal, edtEditdate;
    private ImageButton imageButton;
    private Button btnUpdateData;

    private Calendar cal;
    private int mCurrentYear, mCurrentMonth, mCurrentDay;
    private int mYear, mMonth, mDay;
    private DatePickerDialog mDatePickerDialog;

    private EmployeeAdapter employeeAdapter;
    private ArrayList<EmployeeBean> empList;
    private EmployeeBll employeeBll;
    private EmployeeBean employeeBean;

    private EmployeeBean getEmpBean;

    int empID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list);

        empListView = (ListView) findViewById(R.id.empListView);
        llEditView = (RelativeLayout) findViewById(R.id.llEditView);

        edtEditname = (EditText) findViewById(R.id.edtEditname);
        edtEditemail = (EditText) findViewById(R.id.edtEditemail);
        edtEditsal = (EditText) findViewById(R.id.edtEditsal);
        edtEditdate = (EditText) findViewById(R.id.edtEditdate);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        btnUpdateData = (Button) findViewById(R.id.btnUpdateData);

        employeeBean = new EmployeeBean();
        if (employeeBll == null) {
            employeeBll = new EmployeeBll(this);
        }
        empList = employeeBll.getEmployeeList();
        System.out.println("===empList.size()===" + empList.size());

        employeeAdapter = new EmployeeAdapter(EmployeeList.this, empList);
        empListView.setAdapter(employeeAdapter);

        cal = Calendar.getInstance();
        mCurrentYear = cal.get(Calendar.YEAR);
        mCurrentMonth = cal.get(Calendar.MONTH);
        mCurrentDay = cal.get(Calendar.DAY_OF_MONTH);

        mDatePickerDialog = new DatePickerDialog(this, mOnDateSetListener, mCurrentYear, mCurrentMonth, mCurrentDay);

        imageButton.setOnClickListener(this);
        btnUpdateData.setOnClickListener(this);
    }


    private void updateEmployee(int id, String name, String email, String salary, String date) {
        empListView.setVisibility(View.GONE);
        llEditView.setVisibility(View.VISIBLE);
        empID = id;
        edtEditname.setText(name);
        edtEditemail.setText(email);
        edtEditsal.setText(salary);
        edtEditdate.setText(date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton:
                mDatePickerDialog.show();
                break;

            case R.id.btnUpdateData:
                System.out.println("=====empID=========" + empID);
                String result = validate();
                if (result != null) {
                    System.out.println("=====result != null=========" + result);
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("=====result == null=========" + result);
                    employeeBean.id = empID;
                    employeeBean.name = edtEditname.getText().toString();
                    employeeBean.email = edtEditemail.getText().toString();
                    employeeBean.salary = edtEditsal.getText().toString();
                    employeeBean.date = edtEditdate.getText().toString();
                    employeeBll.update(employeeBean);
                    startActivity(getIntent());
                    finish();
                }
                break;

            case R.id.btnUpdate:
                getEmpBean = (EmployeeBean) v.getTag();
                System.out.println("=====btnUpdate::getEmpBean.id=========" + getEmpBean.id);
                updateEmployee(getEmpBean.id, getEmpBean.name, getEmpBean.email, getEmpBean.salary, getEmpBean.date);
                break;

            case R.id.btnDelete:
                getEmpBean = (EmployeeBean) v.getTag();
                System.out.println("=====btnDelete::getEmpBean.id=========" + getEmpBean.id);
                final Dialog dialog = new Dialog(EmployeeList.this);
                dialog.setContentView(R.layout.delete_dialog);
                dialog.setTitle("Delete Data");
                TextView text = (TextView) dialog.findViewById(R.id.textmsg);
                text.setText("Are You Sure Want To Delete Selected Data ?");
                Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
                Button btnNo = (Button) dialog.findViewById(R.id.btnNo);

                btnYes.setTag(getEmpBean);
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EmployeeBean empBean = (EmployeeBean) v.getTag();
                        System.out.println("=====employeeBll.delete(empBean.id)=========" + empBean.id);
                        employeeBll.delete(empBean.id);
                        startActivity(getIntent());
                        finish();
                        dialog.dismiss();
                    }
                });

                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
                break;
        }
    }

    private String validate() {
        String msg = null;
        if (edtEditname.getText().toString() == null || edtEditname.getText().toString().equals("")) {
            msg = "Please enter name";
            edtEditname.requestFocus();
            edtEditname.setSelection(edtEditname.length());
        } else if (edtEditemail.getText().toString() == null || edtEditemail.getText().toString().equals("")) {
            msg = "Please enter email";
            edtEditemail.requestFocus();
            edtEditemail.setSelection(edtEditemail.length());
        } else if (!Util.EMAIL_PATTERN.matcher(edtEditemail.getText().toString()).matches()) {
            msg = "Please enter valid email";
            edtEditemail.requestFocus();
            edtEditemail.setSelection(edtEditemail.length());
        } else if (edtEditsal.getText().toString() == null || edtEditsal.getText().toString().equals("")) {
            System.out.println("=====validate=========" + edtEditsal.getText().toString());
            msg = "Please enter salary";
            edtEditsal.requestFocus();
            edtEditsal.setSelection(edtEditsal.length());
        } else if (edtEditdate.getText().toString() == null || edtEditdate.getText().toString().equals("")) {
            msg = "Please select date";
            edtEditdate.requestFocus();
            edtEditdate.setSelection(edtEditdate.length());
        }

        return msg;
    }

    DatePickerDialog.OnDateSetListener mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            int month = mMonth + 1;
            mDay = dayOfMonth;
            edtEditdate.setText(mDay + "-" + month + "-" + mYear);
        }
    };
}
