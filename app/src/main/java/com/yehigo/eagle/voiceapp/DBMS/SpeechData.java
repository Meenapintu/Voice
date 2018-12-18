package com.yehigo.eagle.voiceapp.DBMS;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SpeechData {
    @Query("SELECT * FROM words")
    List<Words> getAll();

    @Insert
    void insertAll(Words... words);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Words word);

    @Delete
    void delete(Words words);
}