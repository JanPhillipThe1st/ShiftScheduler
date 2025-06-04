package com.yamatoapps.shiftscheduler.Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yamatoapps.shiftscheduler.Manager.ui.EmployeeClass;
import com.yamatoapps.shiftscheduler.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewEmployees extends AppCompatActivity {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Button btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employees);
        btnExit = findViewById(R.id.btnExit);
        ArrayList<EmployeeClass> employeeClasses= new ArrayList<EmployeeClass>();
        EmployeeAdapter employeeAdapter =new EmployeeAdapter(this,employeeClasses);
        db.collection("employees").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                  for (DocumentSnapshot document : queryDocumentSnapshots){
                    SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                      employeeAdapter.add(new EmployeeClass(document.getString("name"),
                              document.getString("email"),
                                      document.getString("sex")
                              ,sfd.format( document.getDate("date_employed",
                              DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)),
                              document.getId(),
                              Integer.parseInt(document.get("age").toString()),
                              document.getBoolean("trained_opening"),
                              document.getBoolean("trained_closing")
                      ));
                  }
                ListView lvEmployees = findViewById(R.id.lvEmployees);
                lvEmployees.setAdapter(employeeAdapter);
            }
        });
        btnExit.setOnClickListener(view ->{
            finish();
        });
    }
}