package com.yamatoapps.shiftscheduler.Manager.ui.dashboard;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.yamatoapps.shiftscheduler.AddEmployee;
import com.yamatoapps.shiftscheduler.Manager.ViewSchedules;
import com.yamatoapps.shiftscheduler.R;
import com.yamatoapps.shiftscheduler.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.Calendar;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        GridView gridView =   root.findViewById(R.id.gvMenuItems);
        ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
        MenuAdapter menuAdapter =new MenuAdapter(root.getContext(), menuItems);
        menuAdapter.add(new MenuItem("VIEW/EDIT\nCURRENT\nSCHEDULE", R.drawable.calendar, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(root.getContext(), "Viewing schedules...",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(root.getContext(), ViewSchedules.class));
            }
        }));
        menuAdapter.add(new MenuItem("VIEW/ADD/EDIT\nEMPLOYEES", R.drawable.add_employe, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(root.getContext(), "Viewing employees...",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(root.getContext(), AddEmployee.class));
            }
        }));
        menuAdapter.add(new MenuItem("PUSH\nUPDATED\nSCHEDULE", R.drawable.scheduling, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(root.getContext(), "Updating schedules...",Toast.LENGTH_SHORT).show();
            }
        }));
        menuAdapter.add(new MenuItem("LOGOUT",R.drawable.check_out, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(root.getContext(), "Logging out...",Toast.LENGTH_SHORT).show();
                MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(root.getContext());
                alertDialogBuilder.setMessage("Are you sure you want to exit?");
                alertDialogBuilder.setTitle("Logging out");
                alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        getActivity().finish();
                    }
                });
                alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }));
        gridView.setAdapter(menuAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}