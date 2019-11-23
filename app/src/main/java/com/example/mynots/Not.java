package com.example.mynots;

import java.util.Map;

public class Not {
     String title;
     String note;
     String timestamp;
     String id;

    public Not( String id,String title, String note, String timestamp) {
        this.title = title;
        this.note = note;
        this.timestamp = timestamp;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
