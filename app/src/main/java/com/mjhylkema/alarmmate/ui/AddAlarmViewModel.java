package com.mjhylkema.alarmmate.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import java.util.Calendar;

public class AddAlarmViewModel extends AndroidViewModel {

    public Calendar calendar;

    public AddAlarmViewModel(Application application) {
        super(application);
        calendar = Calendar.getInstance();
    }

}
