package com.mjhylkema.alarmmate.ui.addAlarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mjhylkema.alarmmate.databinding.FragmentAddAlarmBinding;
import com.mjhylkema.alarmmate.ui.dialogs.AlarmTimeDialog;

import java.util.Calendar;
import java.util.List;

import ca.antonious.materialdaypicker.MaterialDayPicker;
import ca.antonious.materialdaypicker.MaterialDayPicker.Weekday;

public class AddAlarmFragment extends Fragment {

    private static final String DIALOG_TIME_PICKER = "TIME_PICKER";
    private static final int REQUEST_ALARM_TIME = 1;

    private AddAlarmViewModel mViewModel;
    private FragmentAddAlarmBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentAddAlarmBinding.inflate(inflater, container, false);

        mViewModel = ViewModelProviders.of(this).get(AddAlarmViewModel.class);

        AddAlarmActionListener actionListener = getAddAlarmActionListener();
        mBinding.setActionListener(actionListener);
        mBinding.setViewModel(mViewModel);
        mBinding.setLifecycleOwner(this);

        setupDaySelectionListener();

        return mBinding.getRoot();
    }

    private AddAlarmActionListener getAddAlarmActionListener() {
        return new AddAlarmActionListener() {
            @Override
            public void onSetTimeClicked() {
                setupTimePicker();
            }

            @Override
            public void onSetRepeatClicked() {
                mViewModel.mRepeated.set(!mViewModel.mRepeated.get());
            }
        };
    }

    private void setupTimePicker() {
        AlarmTimeDialog dialogFragment = AlarmTimeDialog.newInstance(mViewModel.mAlarmTime.get().getTimeInMillis());
        dialogFragment.setTargetFragment(AddAlarmFragment.this, REQUEST_ALARM_TIME);
        dialogFragment.show(getFragmentManager(), DIALOG_TIME_PICKER);
    }

    private void setupDaySelectionListener() {
        mBinding.addAlarmDayPicker.setDaySelectionChangedListener(new MaterialDayPicker.DaySelectionChangedListener() {
            @Override
            public void onDaySelectionChanged(List<Weekday> list) {
                boolean[] activeDays = mViewModel.mActiveDays.get().clone();
                activeDays[0] = list.indexOf(Weekday.SUNDAY) > 0;
                activeDays[1] = list.indexOf(Weekday.MONDAY) > 0;
                activeDays[2] = list.indexOf(Weekday.TUESDAY) > 0;
                activeDays[3] = list.indexOf(Weekday.WEDNESDAY) > 0;
                activeDays[4] = list.indexOf(Weekday.THURSDAY) > 0;
                activeDays[5] = list.indexOf(Weekday.FRIDAY) > 0;
                activeDays[6] = list.indexOf(Weekday.SATURDAY) > 0;
                mViewModel.mActiveDays.set(activeDays);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_ALARM_TIME:
                Calendar calendarResponse = Calendar.getInstance();
                calendarResponse.setTimeInMillis(data.getLongExtra(AlarmTimeDialog.OUT_DATE, 0));
                mViewModel.mAlarmTime.set(calendarResponse);
                break;
        }

    }
}
