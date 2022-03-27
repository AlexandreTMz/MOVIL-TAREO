package com.example.app_tareos.LIBS;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditTexDialogDate implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    EditText _editText;
    Activity act;

    Calendar myCalendar = Calendar.getInstance();

    public EditTexDialogDate(Context context, EditText editTextViewID)
    {
        this.act = (Activity)context;
        this._editText = editTextViewID;
        this._editText.setOnClickListener(this);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // TODO Auto-generated method stub
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateDisplay();
    }
    @Override
    public void onClick(View v) {
        new DatePickerDialog(this.act, this, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    // updates the date in the birth date EditText
    private void updateDisplay() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        _editText.setText(sdf.format(myCalendar.getTime()));
    }
}