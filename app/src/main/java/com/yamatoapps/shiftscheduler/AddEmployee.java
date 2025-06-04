package com.yamatoapps.shiftscheduler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yamatoapps.shiftscheduler.Manager.ViewEmployees;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddEmployee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        final Calendar c = Calendar.getInstance();
        Button btnSelectDate = findViewById(R.id.btnSelectDate);
        Button btnSaveEmployee = findViewById(R.id.btnExit);
        Button btnViewEmployees = findViewById(R.id.btnViewEmployees);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Switch switch_opening, switch_closing;
        switch_closing = findViewById(R.id.switch_closing);
        switch_opening = findViewById(R.id.switch_opening);
        TextView tvEmail, tvName, tvAge, tvSex;
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvAge = findViewById(R.id.tvAge);
        tvSex = findViewById(R.id.tvSex);
        btnViewEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddEmployee.this, ViewEmployees.class));
            }
        });
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // our day, month and year.
                int month1,date1,year1;
                month1 = c.get(Calendar.MONTH);
                date1 = c.get(Calendar.DAY_OF_MONTH);
                year1 = c.get(Calendar.YEAR);
                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                       AddEmployee.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                c.set(Calendar.DATE,dayOfMonth);
                                c.set(Calendar.MONTH,monthOfYear);
                                c.set(Calendar.YEAR,year);
                                TimePickerDialog timePickerDialog = new TimePickerDialog(
                                        // on below line we are passing context.
                                        AddEmployee.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                                c.set(Calendar.HOUR_OF_DAY,i);
                                                c.set(Calendar.MINUTE,i1);
                                                c.set(Calendar.SECOND,0);
                                                c.set(Calendar.MILLISECOND,0);
                                                btnSelectDate.setText(c.getTime().toLocaleString());
                                            }
                                        },0,0,true);
                                // at last we are calling show to
                                // display our date picker dialog.
                                timePickerDialog.show();
                            }
                        },year1,month1,date1);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });
        btnSaveEmployee.setOnClickListener( view -> {
            ProgressDialog progressDialog = new ProgressDialog(AddEmployee.this);
            progressDialog.setTitle("Saving your data...");
            progressDialog.setMessage("Please wait. Saving your data to the cloud.");
            progressDialog.show();
            Map<String,Object> map = new HashMap<>();
            map.put("name",tvName.getText().toString());
            map.put("date_employed",c.getTime());
            map.put("email",tvEmail.getText().toString());
            map.put("age",Integer.parseInt(tvAge.getText().toString()));
            map.put("sex",tvSex.getText().toString());
            map.put("trained_opening",switch_opening.isChecked());
            map.put("trained_closing",switch_closing.isChecked());
            db.collection("employees").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(AddEmployee.this);
                    alertDialogBuilder.setMessage("Your data was successfully saved!\nWhat would you like to do?");
                    alertDialogBuilder.setTitle("Operation Success");
                    alertDialogBuilder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            progressDialog.dismiss();
                            finish();
                        }
                    });
                    alertDialogBuilder.setPositiveButton("See Employees", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            progressDialog.dismiss();
                            startActivity(new Intent(AddEmployee.this, ViewEmployees.class));
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        });
    }
}