package com.mjhylkema.alarmmate.data;

import android.content.Context;

import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.mjhylkema.alarmmate.utils.Converters;

@Database(entities = {Alarm.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AlarmDatabase extends RoomDatabase {

    private static AlarmDatabase mINSTANCE;

    @VisibleForTesting
    public static final String DATABASE_NAME = "alarm-db";

    public abstract AlarmDao alarmDao();

    public static AlarmDatabase getInstance(final Context context) {
        if (mINSTANCE == null) {
            synchronized (AlarmDatabase.class) {
                if (mINSTANCE == null) {
                    mINSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AlarmDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration() // TODO Add Proper Migration
                            .build();
                }
            }
        }
        return mINSTANCE;
    }
}
