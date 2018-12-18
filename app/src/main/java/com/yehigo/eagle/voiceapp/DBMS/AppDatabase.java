package com.yehigo.eagle.voiceapp.DBMS;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Words.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SpeechData speechData();
}