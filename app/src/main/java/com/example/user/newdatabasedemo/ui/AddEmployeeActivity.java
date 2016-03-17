package com.example.user.newdatabasedemo.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.newdatabasedemo.R;
import com.example.user.newdatabasedemo.bean.EmployeeBean;
import com.example.user.newdatabasedemo.bll.EmployeeBll;
import com.example.user.newdatabasedemo.utils.Util;

import java.util.Calendar;

public class AddEmployeeActivity extends Activity implements View.OnClickListener {

    EditText editname, editemail, editsal, editdate;
    ImageButton imageButton;
    Button btnsubmit, btnshowdata;

    Calendar cal;
    int mCurrentYear, mCurrentMonth, mCurrentDay;
    int mYear, mMonth, mDay;
    DatePickerDialog mDatePickerDialog;

    private EmployeeBean employeeBean;
    private EmployeeBll employeeBll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_employee);

        editname = (EditText) findViewById(R.id.editname);
        editemail = (EditText) findViewById(R.id.editemail);
        editsal = (EditText) findViewById(R.id.editsal);
        editdate = (EditText) findViewById(R.id.editdate);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        btnsubmit = (Button) findViewById(R.id.btnsubmit);
        btnshowdata = (Button) findViewById(R.id.btnshowdata);

        employeeBean = new EmployeeBean();
        employeeBll = new EmployeeBll(this);

        cal = Calendar.getInstance();
        mCurrentYear = cal.get(Calendar.YEAR);
        mCurrentMonth = cal.get(Calendar.MONTH);
        mCurrentDay = cal.get(Calendar.DAY_OF_MONTH);

        mDatePickerDialog = new DatePickerDialog(this, mOnDateSetListener, mCurrentYear, mCurrentMonth, mCurrentDay);

        imageButton.setOnClickListener(this);
        btnsubmit.setOnClickListener(this);
        btnshowdata.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton:
                mDatePickerDialog.show();
                break;

            case R.id.btnsubmit:
                String result = validate();
                if (result != null) {
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                } else {
                    employeeBean.name = editname.getText().toString();
                    employeeBean.email = editemail.getText().toString();
                    employeeBean.salary = editsal.getText().toString();
                    employeeBean.date = editdate.getText().toString();
                    employeeBll.verify(employeeBean);

                    Toast.makeText(this, "Data Inserted", Toast.LENGTH_LONG).show();

                    editname.setText("");
                    editemail.setText("");
                    editsal.setText("");
                    editdate.setText("");
                }
                break;

            case R.id.btnshowdata:
                Intent i = new Intent(AddEmployeeActivity.this, EmployeeList.class);
                startActivity(i);
                break;
        }
    }

    private String validate() {
        String msg = null;
        if (editname.getText().toString() == null && editname.getText().toString().equals("")) {
            msg = "Please enter name";
            editname.requestFocus();
            editname.setSelection(editname.length());
        } else if (editemail.getText().toString() == null && editemail.getText().toString().equals("")) {
            msg = "Please enter email";
            editemail.requestFocus();
            editemail.setSelection(editemail.length());
        } else if (!Util.EMAIL_PATTERN.matcher(editemail.getText().toString()).matches()) {
            msg = "Please enter valid email";
            editemail.requestFocus();
            editemail.setSelection(editemail.length());
        } else if (editsal.getText().toString() == null && editsal.getText().toString().equals("")) {
            msg = "Please enter salary";
            editsal.requestFocus();
            editsal.setSelection(editsal.length());
        } else if (editdate.getText().toString() == null && editdate.getText().toString().equals("")) {
            msg = "Please select date";
            editdate.requestFocus();
            editdate.setSelection(editdate.length());
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
            editdate.setText(mDay + "-" + month + "-" + mYear);
        }
    };
}
