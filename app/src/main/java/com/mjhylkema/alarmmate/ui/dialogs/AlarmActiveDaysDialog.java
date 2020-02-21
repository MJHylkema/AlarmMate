package com.mjhylkema.alarmmate.ui.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.mjhylkema.alarmmate.R;

public class AlarmActiveDaysDialog extends DialogFragment {

    public static final String OUT_ACTIVE_DAYS = "OUT_ACTIVE_DAYS";

    private static final String ARG_ACTIVE_DAYS = "ARG_ACTIVE_DAYS";
    private boolean[] mActiveDays;

    public static AlarmActiveDaysDialog newInstance(boolean[] activeDays) {
        Bundle args = new Bundle();
        args.putBooleanArray(ARG_ACTIVE_DAYS, activeDays.clone());
        AlarmActiveDaysDialog fragment = new AlarmActiveDaysDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        mActiveDays = getArguments().getBooleanArray(ARG_ACTIVE_DAYS);

        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
            .setTitle(R.string.set_active)
            .setMultiChoiceItems(R.array.days_of_week, mActiveDays, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int selectedDay, boolean isChecked) {
                    mActiveDays[selectedDay] = isChecked;
                }
            })
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendResult(Activity.RESULT_OK);
                    dismiss();
                }
            })
            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dismiss();
                }
            })
            .create();

        return alertDialog;
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(OUT_ACTIVE_DAYS, mActiveDays);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
