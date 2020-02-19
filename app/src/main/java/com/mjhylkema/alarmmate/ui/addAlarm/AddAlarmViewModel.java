package com.mjhylkema.alarmmate.ui.addAlarm;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import java.util.Calendar;

public class AddAlarmViewModel extends AndroidViewModel {

    public Calendar mAlarmTime;

    public AddAlarmViewModel(Application application) {
        super(application);
        mAlarmTime = Calendar.getInstance();
    }

}
