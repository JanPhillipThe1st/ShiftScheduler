package com.yamatoapps.shiftscheduler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    Button btnSubmit;
    TextView tvName,tvEmail,tvConfirmEmail,tvPassword,tvConfirmPassword;
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tvName = findViewById(R.id.tvName);
        tvConfirmEmail = findViewById(R.id.tvConfirmEmail);
        tvEmail = findViewById(R.id.tvEmail);
        tvPassword = findViewById(R.id.tvPassword);
        tvConfirmPassword = findViewById(R.id.tvConfirmPassword);
        btnSubmit = findViewById(R.id.btnSubmit);
        ProgressDialog progressDialog = new ProgressDialog(Register.this);
        progressDialog.setTitle("Saving");
        progressDialog.setMessage("Saving your account information. Please wait.");
        btnSubmit.setOnClickListener(view ->{
            if (tvEmail.getText().toString().compareTo(tvConfirmEmail.getText().toString()) == 0 &&
                    tvPassword.getText().toString().compareTo(tvConfirmPassword.getText().toString()) == 0){

            Map<String,Object> map = new HashMap<>();
            map.put("name",tvName.getText().toString());
            map.put("username",tvEmail.getText().toString());
            map.put("password",tvPassword.getText().toString());
        progressDialog.show();
        db.collection("shift_employees").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Register.this);
                builder.setMessage("Successful Signup! You can now use your account.");
                builder.setTitle("Signup Success");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        progressDialog.dismiss();
                        finish();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
            }
            else{
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
                builder.setMessage("Password or Emails do not match.");
                builder.setTitle("Signup Failed");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }
}