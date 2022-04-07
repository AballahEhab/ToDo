package com.abdallahehab.todo;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Constants {

    final static  String TITLE_ID = "title";
    final static String BODY_ID = "body";
    final static String DATE_ID = "date";
    final static String NOTE_ID = "date";
    final static String NOTE_STATUS = "note_status";

    @RequiresApi(api = Build.VERSION_CODES.O)
    static String getCurrentDateAsString(){
        LocalDateTime date = LocalDateTime.now();   ;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dtf.format(date);
    }
}
