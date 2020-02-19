package com.mjhylkema.alarmmate.ui;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mjhylkema.alarmmate.databinding.FragmentAddAlarmBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddAlarmFragment extends Fragment {

    private AddAlarmViewModel mViewModel;
    private FragmentAddAlarmBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddAlarmBinding.inflate(inflater, container, false);

        mViewModel = ViewModelProviders.of(this).get(AddAlarmViewModel.class);

        addSetTimeListener();
        addRepeatListener();


        return binding.getRoot();
    }

    private void addSetTimeListener() {
        binding.addAlarmTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour, minute;
                hour = mViewModel.calendar.get(Calendar.HOUR_OF_DAY);
                minute = mViewModel.calendar.get(Calendar.MINUTE);

                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mViewModel.calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        mViewModel.calendar.set(Calendar.MINUTE, selectedMinute);

                        SimpleDateFormat formatter = new SimpleDateFormat ("hh:mm aa");
                        binding.addAlarmTimeText.setText(formatter.format(mViewModel.calendar.getTime()));
                    }
                }, hour, minute, false);
                timePicker.setTitle("Select Time");
                timePicker.show();
            }
        });
    }

    private void addRepeatListener() {
        binding.addAlarmRepeatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.addAlarmRepeatSwitch.toggle();
            }
        });
    }
}
