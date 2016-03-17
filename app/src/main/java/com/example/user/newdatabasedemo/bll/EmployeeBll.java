package com.example.user.newdatabasedemo.bll;

import android.content.Context;
import android.database.Cursor;

import com.example.user.newdatabasedemo.bean.EmployeeBean;
import com.example.user.newdatabasedemo.utils.DBHelper;

import java.util.ArrayList;

/**
 * Created by user on 1/3/16.
 */
public class EmployeeBll {
    public Context context;

    public EmployeeBll(Context context) {
        this.context = context;
    }

    public void verify(EmployeeBean employeeBean) {
        DBHelper dbHelper = null;
        String sql = null;
        Cursor cur = null;

        try {
            sql = "SELECT id FROM Employee WHERE id = " + employeeBean.id;
            dbHelper = new DBHelper(this.context);
            cur = dbHelper.query(sql);
            if (cur != null && cur.getCount() > 0) {
                System.out.println("=====verify BLL::::::::update=======" + employeeBean.id);
                update(employeeBean);
            } else {
                System.out.println("=====verify BLL::::::::insert=======" + employeeBean.id);
                insert(employeeBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cur != null && !cur.isClosed()) {
                cur.close();
            }
            if (dbHelper != null) {
                dbHelper.close();
            }
            dbHelper = null;
            sql = null;
            cur = null;
            System.gc();
        }

    }

    public void insert(EmployeeBean employeeBean) {
        DBHelper dbHelper = null;
        String sql = null;

        try {
            sql = "INSERT INTO Employee(name,email,salary,date)" + " VALUES ('" + employeeBean.name + "','" + employeeBean.email + "','" + employeeBean.salary + "','" + employeeBean.date + "')";
            dbHelper = new DBHelper(this.context);
            dbHelper.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
            dbHelper = null;
            sql = null;
            System.gc();
        }
    }


    public void update(EmployeeBean employeeBean) {
        DBHelper dbHelper = null;
        String sql = null;

        try {
            dbHelper = new DBHelper(this.context);
            sql = "UPDATE Employee SET " + "name ='" + employeeBean.name + "',email ='" + employeeBean.email + "',salary='" + employeeBean.salary + "',date='" + employeeBean.date + "' WHERE id=" + employeeBean.id;
            dbHelper.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
            dbHelper = null;
            sql = null;
            System.gc();
        }
    }

    public void delete(int id) {
        DBHelper dbHelper = null;
        String sql = null;

        try {
            dbHelper = new DBHelper(this.context);
            sql = "DELETE FROM Employee WHERE id = " + id;
            dbHelper.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
            dbHelper = null;
            sql = null;
            System.gc();
        }
    }

    public ArrayList<EmployeeBean> getEmployeeList() {
        DBHelper dbHelper = null;
        String sql = null;
        Cursor cur = null;
        EmployeeBean employeeBean = null;
        ArrayList<EmployeeBean> employeeList = null;

        try {
            sql = "SELECT id, name, email, salary, date FROM Employee";
            dbHelper = new DBHelper(this.context);
            cur = dbHelper.query(sql);

            employeeList = new ArrayList<EmployeeBean>();
            if (cur != null && cur.getCount() > 0) {
                while (cur.moveToNext()) {

                    employeeBean = new EmployeeBean();
                    employeeBean.id = cur.getInt(0);
                    employeeBean.name = cur.getString(1);
                    employeeBean.email = cur.getString(2);
                    employeeBean.salary = cur.getString(3);
                    employeeBean.date = cur.getString(4);
                    employeeList.add(employeeBean);
                }
            }
            System.out.println("=====BLL::::::::employeeList=======" + employeeList.size());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cur != null && !cur.isClosed()) {
                cur.close();
            }
            if (dbHelper != null) {
                dbHelper.close();
            }
            dbHelper = null;
            sql = null;
            cur = null;
            employeeBean = null;
            System.gc();
        }
        return employeeList;
    }


}
