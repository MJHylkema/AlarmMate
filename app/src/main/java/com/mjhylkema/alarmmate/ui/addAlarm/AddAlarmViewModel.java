package com.mjhylkema.alarmmate.ui.addAlarm;

import android.app.Application;

import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import java.util.Calendar;

public class AddAlarmViewModel extends AndroidViewModel {

    public ObservableField<Calendar> mAlarmTime = new ObservableField<>(Calendar.getInstance());

    public AddAlarmViewModel(Application application) {
        super(application);
    }

}
