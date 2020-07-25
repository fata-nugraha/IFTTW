package com.example.myroutines;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myroutines.app.Constant;
import com.example.myroutines.data.Action;
import com.example.myroutines.data.Condition;
import com.example.myroutines.data.Routine;
import com.example.myroutines.data.RoutineHelper;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class AddRoutine extends AppCompatActivity {

    private Spinner spinnerCondition;
    private Spinner spinnerAction;
    private Button timePickerButton;
    private Button datePickerButton;
    private EditText title;
    private EditText text;
    private RadioGroup rg;
    int year;
    int month;
    int day;
    int hour;
    int minute;
    int stateCond = 1;
    int stateAction = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_routine);
        spinnerCondition = findViewById(R.id.spinner);
        spinnerAction = findViewById(R.id.action);
        timePickerButton = findViewById(R.id.timepickerbutton);
        datePickerButton = findViewById(R.id.datepickerbutton);
        title = findViewById(R.id.notif_title);
        text = findViewById(R.id.notif_text);
        rg = findViewById(R.id.rg);

        setSpinnerCondition();
        setSpinnerAction();
        setTimeButton();
        setDateButton();

        Button addRoutine = findViewById(R.id.addRoutine);
        addRoutine.setOnClickListener(view -> addRoutine());
    }

    void setSpinnerCondition(){
        String[] arrayList = getResources().getStringArray(R.array.add_routines_condition);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondition.setAdapter(arrayAdapter);
        spinnerCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String condition = parent.getItemAtPosition(position).toString();

                if (condition.equals("Accelerometer")){
                    stateCond = 1;
                    timePickerButton.setVisibility(View.INVISIBLE);
                    datePickerButton.setVisibility(View.INVISIBLE);
                }
                else if (condition.equals("Alarm Exact")){
                    stateCond = 2;
                    timePickerButton.setVisibility(View.VISIBLE);
                    datePickerButton.setVisibility(View.VISIBLE);
                }
                else if (condition.equals("Alarm Repeat Day")){
                    stateCond = 3;
                    timePickerButton.setVisibility(View.VISIBLE);
                    datePickerButton.setVisibility(View.VISIBLE);
                }
                else if (condition.equals("Alarm Repeat Week")){
                    stateCond = 4;
                    timePickerButton.setVisibility(View.VISIBLE);
                    datePickerButton.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
    }

    void setSpinnerAction(){
        String[] arrayList = getResources().getStringArray(R.array.add_routines_action);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAction.setAdapter(arrayAdapter);
        spinnerAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String action = parent.getItemAtPosition(position).toString();
                if (action.equals("API")){
                    stateAction = 2;
                    title.clearFocus();
                    text.clearFocus();
                    title.setVisibility(View.GONE);
                    text.setVisibility(View.GONE);
                    rg.setVisibility(View.GONE);
                }
                else if (action.equals("Notification")){
                    stateAction = 1;
                    title.setText("");
                    text.setText("");
                    title.setVisibility(View.VISIBLE);
                    text.setVisibility(View.VISIBLE);
                    rg.setVisibility(View.GONE);
                }
                else if (action.equals("WiFi")){
                    stateAction = 3;
                    title.clearFocus();
                    text.clearFocus();
                    rg.clearCheck();
                    title.setVisibility(View.GONE);
                    text.setVisibility(View.GONE);
                    rg.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
    }

    void setTimeButton(){
        timePickerButton.setOnClickListener(v -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int mHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int mMinute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;
                    timePickerButton.setText(selectedHour + ":" + selectedMinute);
                }
            }, mHour, mMinute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });
    }

    void setDateButton(){
        datePickerButton.setOnClickListener(v -> {
            Calendar mcurrentDate = Calendar.getInstance();
            int mYear = mcurrentDate.get(Calendar.YEAR);
            int mMonth = mcurrentDate.get(Calendar.MONTH);
            int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog mDatePicker;
            mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                    selectedmonth = selectedmonth + 1;
                    year = selectedyear;
                    month = selectedmonth;
                    day = selectedday;
                    datePickerButton.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                }
            }, mYear, mMonth, mDay);
            mDatePicker.setTitle("Select Date");
            mDatePicker.show();
        });
    }

    public void addRoutine(){
        Condition cond;
        Action action;
        if (stateCond == 1){
            cond = new Condition(stateCond, LocalDateTime.now());
        }
        else{
            cond = new Condition(stateCond, LocalDateTime.of(year, month, day, hour, minute));
        }
        if (stateAction == 1){
            action = new Action(stateAction, "\"" + title.getText().toString() + "\"", "\"" + text.getText().toString() + "\"", -1);
        }
        else if (stateAction == 3){
            if (rg.getCheckedRadioButtonId() == R.id.onbutton){
                action = new Action(stateAction, "\"\"", "\"\"", Constant.WIFI_ON);
            }
            else {
                action = new Action(stateAction, "\"\"", "\"\"", Constant.WIFI_OFF);
            }
        }
        else{
            action = new Action(stateAction, "\"\"", "\"\"", -1);
        }
        RoutineHelper rh = new RoutineHelper(this.getApplicationContext());
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        Routine routine = new Routine(m, Constant.ROUTINE_ACTIVE, cond, action);
        rh.addRoutine(routine);
        if (routine.getCond().getId() != 1){
            AlarmHelper ah = new AlarmHelper();
            ah.setAlarm(getApplicationContext(), routine);
        }

        Intent ret = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(ret);
    }
}
