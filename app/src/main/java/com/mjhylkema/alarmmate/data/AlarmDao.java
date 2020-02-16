package com.mjhylkema.alarmmate.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface AlarmDao {
    @Query("select * from alarm_table")
    LiveData<List<Alarm>> getAllAlarms();

    @Query("SELECT * FROM alarm_table WHERE alarm_id=:id")
    Alarm getById(int id);

    @Insert(onConflict = IGNORE)
    void insert(Alarm alarm);

    @Update
    void update(Alarm alarm);

    @Delete
    void delete(Alarm alarm);

    @Query("UPDATE alarm_table set alarm_active=:active where alarm_id=:id")
    void updateActive(int id, boolean active);

    @Insert(onConflict = REPLACE)
    void insertOrReplaceAlarm(Alarm alarm);

    @Query("DELETE FROM alarm_table")
    void deleteAll();
}
