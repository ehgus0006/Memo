package com.codesample.memo.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Memo {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String body;
    public long time;
}
