package com.yamatoapps.shiftscheduler.Manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yamatoapps.shiftscheduler.EditEmployee;
import com.yamatoapps.shiftscheduler.Manager.ui.EmployeeClass;
import com.yamatoapps.shiftscheduler.R;

import java.util.ArrayList;

public class EmployeeAdapter extends ArrayAdapter<EmployeeClass> {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public EmployeeAdapter(@NonNull Context context, ArrayList<EmployeeClass> employees) {
        super(context, 0, employees);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        EmployeeClass employeeClass = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.employee_list_item, parent, false);
        }
        TextView tvName = convertView.findViewById(R.id.tvEmployeeName);
        TextView tvEmail = convertView.findViewById(R.id.tvEmployeeEmail);
        TextView  tvAge = convertView.findViewById(R.id.tvEmployeeAge);
        TextView tvSex = convertView.findViewById(R.id.tvEmployeeSex);
        TextView tvEmploymentDate = convertView.findViewById(R.id.tvEmploymentDate);
        Button btnEdit, btnDelete;
        btnEdit = convertView.findViewById(R.id.btnEdit);
        btnDelete = convertView.findViewById(R.id.btnDelete);

        tvName.setText(employeeClass.name);
        tvEmail.setText(employeeClass.email);
        tvAge.setText(String.valueOf(employeeClass.age));
        tvSex.setText(employeeClass.sex);
        tvEmploymentDate.setText(employeeClass.date_employed);
        btnDelete.setOnClickListener(view -> {
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(parent.getContext());
            alertDialogBuilder.setTitle("Delete Employee");
            alertDialogBuilder.setMessage("Are you sure you want to delete this information?");
            alertDialogBuilder.setPositiveButton("NO", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            alertDialogBuilder.setNegativeButton("YES", (dialogInterface, i) -> {

                MaterialAlertDialogBuilder deleteDialogBuilder = new MaterialAlertDialogBuilder(parent.getContext());
                deleteDialogBuilder.setTitle("Delete success");
                deleteDialogBuilder.setMessage("Emploeyee information deleted successfully!");
                deleteDialogBuilder.setPositiveButton("OK", (deleteDialogBuilderDialogInterface,j)->{
                    deleteDialogBuilderDialogInterface.dismiss();
                    Activity context = (Activity) parent.getContext();
                });
                db.collection("employees").document(employeeClass.document_id).delete().addOnSuccessListener(unused -> {
                    deleteDialogBuilder.create().show();
                    dialogInterface.dismiss();
                });
            });
            alertDialogBuilder.create().show();
        });
        btnEdit.setOnClickListener(view -> {
            Intent intent = new Intent(parent.getContext(), EditEmployee.class);
            intent.putExtra("document_id",employeeClass.document_id);
            parent.getContext().startActivity(intent);
        });
        return convertView;
    }
}
