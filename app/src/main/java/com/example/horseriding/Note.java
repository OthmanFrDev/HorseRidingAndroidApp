package com.example.horseriding;

public class Note {
    int id;
    String date,notes;

    public Note() {

    }

    public Note(String date, String notes) {
        this.date=date;
        this.notes=notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Note(int id, String date, String notes) {
        this.id = id;

        this.date = date;
        this.notes = notes;
    }
}
