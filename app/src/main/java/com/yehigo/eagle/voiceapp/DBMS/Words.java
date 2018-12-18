package com.yehigo.eagle.voiceapp.DBMS;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Words {



    @NonNull
    @ColumnInfo(name = "word")
    @PrimaryKey
    public String word;

    @ColumnInfo(name = "sound")
    public Byte sound;

}
