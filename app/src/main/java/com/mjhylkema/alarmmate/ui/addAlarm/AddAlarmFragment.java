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
import com.mjhylkema.alarmmate.ui.dialogs.AlarmActiveDaysDialog;
import com.mjhylkema.alarmmate.ui.dialogs.AlarmTimeDialog;

import java.util.Calendar;

public class AddAlarmFragment extends Fragment {

    private static final String DIALOG_TIME_PICKER = "TIME_PICKER";
    private static final String DIALOG_ACTIVE_DAYS_PICKER = "ACTIVE_DAYS_PICKER";
    private static final int REQUEST_ALARM_TIME = 1;
    private static final int REQUEST_ACTIVE_DAYS = 2;

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
        mBinding.setLifecycleOwner(this);

        mBinding.setViewModel(mViewModel);

        return mBinding.getRoot();
    }

    private AddAlarmActionListener getAddAlarmActionListener() {
        return new AddAlarmActionListener() {
            @Override
            public void onSetTimeClicked() {
                setupTimePicker();
            }

            @Override
            public void onActiveDaysClicked() {
                setupActiveDays();
            }
        };
    }

    private void setupTimePicker() {
        AlarmTimeDialog dialogFragment = AlarmTimeDialog.newInstance(mViewModel.mAlarmTime.get().getTimeInMillis());
        dialogFragment.setTargetFragment(AddAlarmFragment.this, REQUEST_ALARM_TIME);
        dialogFragment.show(getFragmentManager(), DIALOG_TIME_PICKER);
    }

    private void setupActiveDays() {
        AlarmActiveDaysDialog dialogFragment = AlarmActiveDaysDialog.newInstance(mViewModel.mActiveDays.get());
        dialogFragment.setTargetFragment(AddAlarmFragment.this, REQUEST_ACTIVE_DAYS);
        dialogFragment.show(getFragmentManager(), DIALOG_ACTIVE_DAYS_PICKER);
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
            case REQUEST_ACTIVE_DAYS:
                boolean[] boolArrayResponse = data.getBooleanArrayExtra(AlarmActiveDaysDialog.OUT_ACTIVE_DAYS);
                mViewModel.mActiveDays.set(boolArrayResponse);
        }

    }
}
