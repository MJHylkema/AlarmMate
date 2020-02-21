package com.mjhylkema.alarmmate.ui.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mjhylkema.alarmmate.utils.RoomConverters;

import java.util.Calendar;

public class AlarmTimeDialog extends DialogFragment {

    public static final String OUT_DATE = "OUT_DATE";

    private static final String ARG_DATE = "DATE";
    private Calendar mCalendar;

    public static AlarmTimeDialog newInstance(long date) {
        Bundle args = new Bundle();
        args.putLong(ARG_DATE, date);
        AlarmTimeDialog fragment = new AlarmTimeDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        long argDate = getArguments().getLong(ARG_DATE);

        if (argDate == 0) {
            mCalendar = Calendar.getInstance();
        } else {
            mCalendar = RoomConverters.calendarFromLong(argDate);
        }
        int hour, minute;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = mCalendar.get(Calendar.HOUR_OF_DAY);
            minute = mCalendar.get(Calendar.MINUTE);
        } else {
            hour = mCalendar.get(Calendar.HOUR_OF_DAY);
            minute = mCalendar.get(Calendar.MINUTE);
        }

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mCalendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                    mCalendar.set(Calendar.MINUTE, timePicker.getMinute());
                } else {
                    mCalendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                    mCalendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                }
                long outboundTime = RoomConverters.calendarToLong(mCalendar);
                sendResult(Activity.RESULT_OK, outboundTime);
                dismiss();
            }
        }, hour, minute, false);
        timePickerDialog.setTitle("Select Time");

        return timePickerDialog;
    }

    private void sendResult(int resultCode, long date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(OUT_DATE, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}
