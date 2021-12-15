package com.example.mmnotificationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int tasknotificationId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.set_task_time).setOnClickListener(this);
        findViewById(R.id.cancel_task_time).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        EditText editTask = findViewById(R.id.edit_task);
        TimePicker taskTime = findViewById(R.id.task_time);

        // Intent
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        intent.putExtra("Task Notification ID", tasknotificationId);
        intent.putExtra("message", editTask.getText().toString());

        // PendingIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
        );

        // AlarmManager
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        switch (v.getId()) {
            case R.id.set_task_time:
                int hour = taskTime.getCurrentHour();
                int minute = taskTime.getCurrentMinute();

                // Create time.
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, hour);
                startTime.set(Calendar.MINUTE, minute);
                startTime.set(Calendar.SECOND, 0);
                long alarmStartTime = startTime.getTimeInMillis();

                // Set Alarm
                alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, pendingIntent);
                Toast.makeText(this, "Task Created.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.cancel_task_time:
                alarmManager.cancel(pendingIntent);
                Toast.makeText(this, "Task Canceled", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}