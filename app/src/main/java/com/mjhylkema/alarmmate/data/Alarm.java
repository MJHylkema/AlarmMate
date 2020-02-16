package com.mjhylkema.alarmmate.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.android.gms.maps.model.LatLng;
import com.mjhylkema.alarmmate.utils.Converters;

import java.util.Calendar;

@Entity(tableName = "alarm_table")
@TypeConverters({Converters.class})
public class Alarm {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "alarm_id")
    private int mId;

    @ColumnInfo(name = "alarm_title")
    private String mTitle;

    @ColumnInfo(name = "alarm_time")
    private Calendar mTime;

    @ColumnInfo(name = "alarm_active")
    private boolean mActive;

    @ColumnInfo(name = "active_active_days")
    public boolean[] mActiveDays;

    @ColumnInfo(name = "alarm_repeated")
    public boolean mRepeated;

    @ColumnInfo(name = "alarm_location")
    public LatLng mLocation;

    @Ignore
    public Alarm(String title, Calendar time, boolean active, boolean[] activeDays, boolean repeated, LatLng location)
    {
        this.mTitle = title;
        this.mTime = time;
        this.mActive = active;
        this.mActiveDays = activeDays;
        this.mRepeated = repeated;
        this.mLocation = location;
    }
}
