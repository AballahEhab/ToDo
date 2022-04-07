package com.abdallahehab.todo;

import androidx.annotation.NonNull;

import java.util.Date;

public class NoteDataModel {

    private String title, body,date,id;

    public NoteDataModel(String title, String body, String date) {
        this.title = title;
        this.body = body;
        this.date = date;

    }

    public NoteDataModel(String title, String body, String date, String id) {
        this.title = title;
        this.body = body;
        this.date = date;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new NoteDataModel(getTitle(),getBody(),getDate(),getId());
    }
}
