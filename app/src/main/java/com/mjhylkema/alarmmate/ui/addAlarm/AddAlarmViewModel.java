package com.mjhylkema.alarmmate.ui.addAlarm;

import android.app.Application;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.libraries.places.api.model.Place;

import java.util.Calendar;

public class AddAlarmViewModel extends AndroidViewModel {

    public ObservableField<Calendar> mAlarmTime = new ObservableField<>(Calendar.getInstance());
    public ObservableBoolean mRepeated = new ObservableBoolean(false);
    public ObservableField<boolean[]> mActiveDays = new ObservableField<>(new boolean[7]);
    public ObservableField<Place> mLocation = new ObservableField<>();

    public AddAlarmViewModel(Application application) {
        super(application);
    }

}
