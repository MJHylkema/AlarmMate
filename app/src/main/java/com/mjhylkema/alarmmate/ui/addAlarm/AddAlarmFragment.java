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

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.mjhylkema.alarmmate.R;
import com.mjhylkema.alarmmate.databinding.FragmentAddAlarmBinding;
import com.mjhylkema.alarmmate.ui.dialogs.AlarmTimeDialog;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import ca.antonious.materialdaypicker.MaterialDayPicker;
import ca.antonious.materialdaypicker.MaterialDayPicker.Weekday;

public class AddAlarmFragment extends Fragment {

    private static final String DIALOG_TIME_PICKER = "TIME_PICKER";
    private static final int REQUEST_ALARM_TIME = 1;
    private static final int REQUEST_AUTOCOMPLETE = 2;

    private AddAlarmViewModel mViewModel;
    private FragmentAddAlarmBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(AddAlarmViewModel.class);

        Places.initialize(getContext(), getString(R.string.google_maps_api_key));

        AddAlarmActionListener actionListener = getAddAlarmActionListener();

        mBinding = FragmentAddAlarmBinding.inflate(inflater, container, false);
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

            @Override
            public void onSetLocationClicked() {
                setupLocationPicker();
            }
        };
    }

    private void setupTimePicker() {
        AlarmTimeDialog dialogFragment = AlarmTimeDialog.newInstance(mViewModel.mAlarmTime.get().getTimeInMillis());
        dialogFragment.setTargetFragment(AddAlarmFragment.this, REQUEST_ALARM_TIME);
        dialogFragment.show(getFragmentManager(), DIALOG_TIME_PICKER);
    }

    private void setupLocationPicker() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(getContext());
        startActivityForResult(intent, REQUEST_AUTOCOMPLETE);
    }

    private void setupDaySelectionListener() {
        mBinding.addAlarmDayPicker.setDaySelectionChangedListener(new MaterialDayPicker.DaySelectionChangedListener() {
            @Override
            public void onDaySelectionChanged(List<Weekday> list) {
                boolean[] activeDays = mViewModel.mActiveDays.get().clone();
                activeDays[0] = list.contains(Weekday.SUNDAY);
                activeDays[1] = list.contains(Weekday.MONDAY);
                activeDays[2] = list.contains(Weekday.TUESDAY);
                activeDays[3] = list.contains(Weekday.WEDNESDAY);
                activeDays[4] = list.contains(Weekday.THURSDAY);
                activeDays[5] = list.contains(Weekday.FRIDAY);
                activeDays[6] = list.contains(Weekday.SATURDAY);
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
            case REQUEST_AUTOCOMPLETE:
                Place place = Autocomplete.getPlaceFromIntent(data);
                mViewModel.mLocation.set(place);
                break;
        }

    }
}
