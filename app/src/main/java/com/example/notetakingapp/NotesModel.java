package com.example.notetakingapp;

public class NotesModel {

    // Parameters

    private int id;
    private String NoteTitle;
    private String NoteBody;

    // Constructors

    public NotesModel(int id, String noteTitle, String noteBody) {
        this.id = id;
        NoteTitle = noteTitle;
        NoteBody = noteBody;
    }

    // To string

    @Override
    public String toString() {
        return NoteTitle;
    }

    // Getters and Setters

    public NotesModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return NoteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        NoteTitle = noteTitle;
    }

    public String getNoteBody() {
        return NoteBody;
    }

    public void setNoteBody(String noteBody) {
        NoteBody = noteBody;
    }
}
