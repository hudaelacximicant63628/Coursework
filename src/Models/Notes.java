package Models;

import javafx.beans.property.SimpleStringProperty;

public class Notes {
    private int notesID;
    private User userID;
    private final SimpleStringProperty notes;

    public Notes(int notesID, User userID, String notes) {
        this.notesID = notesID;
        this.userID = userID;
        this.notes = new SimpleStringProperty(notes);
    }

    public int getNotesID() {
        return notesID;
    }

    public User getUserID() {
        return userID;
    }

    public String getNotes() {
        return notes.get();
    }

    public void setNotesID(int notesID) {
        this.notesID = notesID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public void setNotes(String notes) { this.notes.set(notes);
    }

    @Override
    public String toString() {
        return "Notes{" +
                "notesID=" + notesID +
                ", userID=" + userID +
                ", notes='" + notes + '\'' +
                '}';
    }

}
