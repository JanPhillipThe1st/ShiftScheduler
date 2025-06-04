package com.yamatoapps.shiftscheduler.Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yamatoapps.shiftscheduler.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SetSchedule extends AppCompatActivity {

    public String scheduleString = "";
    public  TextView tvScheduleString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_schedule);

        tvScheduleString = findViewById(R.id.tvScheduleString);
        Button btnSave = findViewById(R.id.btnSave);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Intent calendarIntent = getIntent();
        HashMap<String, Object> map = (HashMap<String, Object>)calendarIntent.getSerializableExtra("calendar");
        TextView tvDateString = findViewById(R.id.tvDateString);
        int selectedYear = Integer.parseInt(map.get("year").toString());
        int selectedMonth = Integer.parseInt(map.get("month").toString());
        int selectedDate = Integer.parseInt(map.get("date").toString());
        CheckBox cbOpening = findViewById(R.id.cbOpening);
        CheckBox cbClosing = findViewById(R.id.cbClosing);
        CheckBox cbAllday = findViewById(R.id.cbAllday);
        CheckBox cbHoliday = findViewById(R.id.cbHoliday);
        //Wahh wahh wahhh
        /*
        I am not crazy! I know he swapped those numbers.
        I knew it was 1216. One after Magna Carta.
        As if I could ever make such a mistake.
        Never. Never! I just – I just couldn’t prove it.
        He covered his tracks, he got that idiot at the copy shop to lie for him.
        You think this is something? You think this is bad? This? This chicanery?
        He’s done worse. That billboard! Are you telling me that a man just happens to fall like that?
        No! He orchestrated it! Jimmy! He defecated through a sunroof!
        And I saved him! And I shouldn’t have. I took him into my own firm! What was I thinking?
        He’ll never change. He’ll never change! Ever since he was 9, always the same!
        Couldn’t keep his hands out of the cash drawer! But not our Jimmy!
        Couldn’t be precious Jimmy! Stealing them blind! And HE gets to be a lawyer? What a sick joke!
        I should’ve stopped him when I had the chance! …And you, you have to stop him!
         */
        cbOpening.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              searchAndDestroy(b,'O');
            }
        });
        cbClosing.setOnCheckedChangeListener((button,value) ->{
            searchAndDestroy(value,'C');
        });
        cbAllday.setOnCheckedChangeListener((button,value) ->{
            searchAndDestroy(value,'A');
        });
        cbHoliday.setOnCheckedChangeListener((button,value) ->{
            searchAndDestroy(value,'H');
        });
        tvDateString.setText(selectedYear+" - "+(selectedMonth+1)+" - "+selectedDate);
        btnSave.setOnClickListener(view ->{
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(SetSchedule.this);
            alertDialogBuilder.setTitle("Saving Schedule");
            alertDialogBuilder.setMessage("Are you sure you want to save this schedule?");
            alertDialogBuilder.setPositiveButton("YES", (dialogInterface, i) -> {
                dialogInterface.dismiss();
                ProgressDialog progressDialog = new ProgressDialog(SetSchedule.this);
                progressDialog.setTitle("Saving to cloud");
                progressDialog.setMessage("Please wait while app is saving data to the cloud.");
                progressDialog.show();
                Map<String,Object> scheduleMap = new HashMap<String,Object>();
                scheduleMap.put("year",selectedYear);
                scheduleMap.put("month",selectedMonth);
                scheduleMap.put("date",selectedDate);
                scheduleMap.put("schedule",scheduleString.toString());
                db.collection("schedules").add(scheduleMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        MaterialAlertDialogBuilder successAlertDialogBuilder = new MaterialAlertDialogBuilder(SetSchedule.this);
                        successAlertDialogBuilder.setTitle("Saving Success!");
                        successAlertDialogBuilder.setMessage("The data hase been successfully saved!");
                        successAlertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterfaceSuccess, int i) {
                                dialogInterfaceSuccess.dismiss();
                                progressDialog.dismiss();
                                finish();
                            }
                        });
                        successAlertDialogBuilder.create().show();
                    }
                });
            });
            alertDialogBuilder.setNegativeButton("NO", (dialogInterface, i) -> {
                dialogInterface.dismiss();

            });
            alertDialogBuilder.create().show();
        });
    }
    public void searchAndDestroy(boolean b,Character what){
        String newScheduleString = "";
        if (b){
            scheduleString += what;
        }
        else{
            //Search for that nigga
            for (char character:scheduleString.toCharArray()){
                if (character == what){

                    //And delete it
                }
                else{
                    newScheduleString+=character;
                }
            }
            scheduleString = newScheduleString;
        }
            tvScheduleString.setText(scheduleString);
    }
}