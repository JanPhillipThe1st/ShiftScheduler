package com.yamatoapps.shiftscheduler.Manager.ui;

public class EmployeeClass {
    public String name = "";
    public String email = "";
    public String sex = "";
    public String date_employed = "";
    public String document_id = "";
    public int age = 0;
    public  boolean trained_opening = false;
    public  boolean trained_closing = false;




    public EmployeeClass(String name, String email, String sex, String date_employed, String document_id, int age, boolean trained_opening, boolean trained_closing) {
        this.name = name;
        this.email = email;
        this.sex = sex;
        this.date_employed = date_employed;
        this.document_id = document_id;
        this.age = age;
        this.trained_opening = trained_opening;
        this.trained_closing = trained_closing;
    }
}
