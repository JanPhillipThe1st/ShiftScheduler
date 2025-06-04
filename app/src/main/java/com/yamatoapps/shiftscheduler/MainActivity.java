package com.yamatoapps.shiftscheduler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yamatoapps.shiftscheduler.Manager.ManagerDashboard;

public class MainActivity extends AppCompatActivity {
    Button btnRegister,btnSignin;
    TextView tvUsername, tvPassword;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegister = findViewById(R.id.btnRegister);
        btnSignin = findViewById(R.id.btnSignin);
        tvUsername = findViewById(R.id.tvUsername);
        tvPassword = findViewById(R.id.tvPassword);
        btnSignin.setOnClickListener(view ->{
            ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Logging in");
            progressDialog.setMessage("Logging in. Please wait.");

            progressDialog.show();
            db.collection("shift_employees").where(Filter.and(Filter.equalTo("username",tvUsername.getText().toString()),
                            Filter.equalTo("password",tvPassword.getText().toString())))
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this);
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(queryDocumentSnapshots.size() > 0){
                                builder.setMessage("Successfully logged in!");
                                builder.setTitle("Login Success.");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        progressDialog.dismiss();
                                        startActivity(new Intent(MainActivity.this, ManagerDashboard.class));
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        else{
                                builder.setMessage("No user found!");
                                builder.setTitle("Login Failed.");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        progressDialog.dismiss();
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                        }
                        }
                    });
        });
        btnRegister.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,Register.class));

        });
    }
}