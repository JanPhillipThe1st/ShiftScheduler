package com.yamatoapps.shiftscheduler.Manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.fonts.FontStyle;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.CalendarView;

import com.applandeo.materialcalendarview.CalendarDay;
import com.applandeo.materialcalendarview.CalendarUtils;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnCalendarDayClickListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yamatoapps.shiftscheduler.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class ViewSchedules extends AppCompatActivity {

    com.applandeo.materialcalendarview.CalendarView scheduleCalendar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<EventDay> events = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedules);
        scheduleCalendar = findViewById(R.id.scheduleCalendar);
        getSchedules();

        scheduleCalendar.setOnCalendarDayClickListener(new OnCalendarDayClickListener() {
            @Override
            public void onClick(@NonNull CalendarDay calendarDay) {
                Calendar selectedCalendar = calendarDay.getCalendar();
                Intent setScheduleIntent = new Intent(ViewSchedules.this,SetSchedule.class);
                HashMap<String, Object> map = new HashMap<String,Object>();
                map.put("year",selectedCalendar.get(Calendar.YEAR));
                map.put("month",selectedCalendar.get(Calendar.MONTH));
                map.put("date",selectedCalendar.get(Calendar.DAY_OF_MONTH));
                setScheduleIntent.putExtra("calendar",map);
                startActivity(setScheduleIntent);
            }
        });
    }

    public static Drawable getCircleDrawableWithText(Context context, String string) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.sample_circle);
        Drawable text = CalendarUtils.getDrawableText(context, string, null, android.R.color.holo_red_light, 12);
        Drawable[] layers = {background, text};
        return new LayerDrawable(layers);
    }
    public void getSchedules(){
        db.collection("schedules").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    events.clear();
                    for (DocumentSnapshot document:value.getDocuments()){
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR,Integer.parseInt(document.get("year").toString()));
                        calendar.set(Calendar.MONTH,Integer.parseInt(document.get("month").toString()));
                        calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(document.get("date").toString()));

                        events.add(new EventDay(calendar, getCircleDrawableWithText(ViewSchedules.this, document.getString("schedule"))));

                    }
                    com.applandeo.materialcalendarview.CalendarView calendarView = scheduleCalendar;
                    calendarView.setEvents(events);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });

    }
}