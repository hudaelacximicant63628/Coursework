package Models;

import javafx.beans.property.SimpleStringProperty;

public class Notes {
    private int notesID;
    private int userID;
    private String notes;

    public Notes(int notesID, int userID, String notes) {
        this.notesID = notesID;
        this.userID = userID;
        this.notes = notes;
    }

    public int getNotesID() {
        return notesID;
    }

    public void setNotesID(int notesID) {
        this.notesID = notesID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Notes{" +
                "notesID=" + notesID +
                ", userID=" + userID +
                ", notes=" + notes +
                '}';
    }
}
