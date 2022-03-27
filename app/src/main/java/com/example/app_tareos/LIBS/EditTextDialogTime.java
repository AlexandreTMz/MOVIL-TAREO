package com.example.app_tareos.LIBS;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditTextDialogTime implements View.OnClickListener, TimePickerDialog.OnTimeSetListener{
    EditText _editText;
    Activity act;

    Calendar myCalendar = Calendar.getInstance();

    int hour = -1, min = -1;

    public EditTextDialogTime(Context context, EditText editTextViewID)
    {
        this.act = (Activity)context;
        this._editText = editTextViewID;
        this._editText.setOnClickListener(this);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        String AM_PM;
        hour = hourOfDay;
        min = minute;
        if (hourOfDay < 12) {
            AM_PM = "AM";
        } else {
            AM_PM = "PM";
        }
        _editText.setText(String.format("%02d", hour) + " : " + String.format("%02d", min) + " " + AM_PM);
    }

    @Override
    public void onClick(View view) {
        if (hour == -1 || min == -1) {
            hour =myCalendar.get(Calendar.HOUR);
            min = myCalendar.get(Calendar.MINUTE);
        }

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this.act,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                this,
                hour,
                min,
                false);
        timePickerDialog.setTitle("Seleccione una hora");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    // updates the date in the birth date EditText
    private void updateDisplay() {
        int houra = myCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = myCalendar.get(Calendar.MINUTE);
        String am_pm = "";
        if (myCalendar.get(Calendar.AM_PM) == Calendar.AM)
            am_pm += " AM";
        else if (myCalendar.get(Calendar.AM_PM) == Calendar.PM)
            am_pm += " PM";
        _editText.setText(String.format("%02d", houra) + ":" + String.format("%02d", minute) + am_pm);

    }

}
