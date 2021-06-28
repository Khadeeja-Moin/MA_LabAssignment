package com.task.todolistapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    DbHelper dbHelper;
    ArrayAdapter<String> mAdapter;
    ListView lstTask;

    Button btnSave;
    RadioGroup Radgrp;
    RadioButton comp, incomp;
    TextView day_text, date_text;
    private int currentHour;
    Date currentTime;
    SimpleDateFormat currentDay;
    String dayInformation, dateInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);
        lstTask = (ListView) findViewById(R.id.lstTask);
        // ACCESSING THE ELEMENTS IN THE .XML FILE
        day_text = findViewById(R.id.weekDayText);
        date_text = findViewById(R.id.dateText);
        // CALCULATING TIME PROPERTIES
        currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        currentDay = new SimpleDateFormat("EEEE");
        currentTime = Calendar.getInstance().getTime();
        // SETTING + DISPLAYING THE DAY/DATE IN THE UPPER MENU
        dayInformation = currentDay.format(currentTime);
        dateInformation = java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(currentTime);
        day_text.setText(dayInformation);
        date_text.setText(dateInformation);
        
        loadTaskList();


    }

    private void loadTaskList() {
        ArrayList<String> taskList = dbHelper.getTaskList();
        if (mAdapter == null){
            mAdapter = new ArrayAdapter<String>(this,R.layout.row,R.id.task_title,taskList);
            lstTask.setAdapter(mAdapter);
        }
        else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu,menu);

        //Change menu icon color
        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.action_add_task:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add new Task")
                        .setMessage("Tell us about your next task")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());

                                dbHelper.insertNewTask(task);
                                Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_LONG).show();
                                loadTaskList();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveStatus(View view){
        View parent = (View) view.getParent();
        btnSave = (Button) findViewById(R.id.btnSave);
        Radgrp = (RadioGroup) findViewById(R.id.Radgrp);
        comp = (RadioButton) findViewById(R.id.stat_comp);
        incomp = (RadioButton) findViewById(R.id.stat_incomp);
        String msg = "Select task status";
        int radioID = Radgrp.getCheckedRadioButtonId();
        if (comp.isChecked()){
            msg = "Task Completed";
        }
        if (incomp.isChecked()){
            msg = "Task Incomplete";
        }
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void deleteTask(View view){
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        dbHelper.deleteTask(task);
        Toast.makeText(this, "Deleted successfully", Toast.LENGTH_LONG).show();
        loadTaskList();

    }
}