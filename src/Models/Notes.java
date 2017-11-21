package Models;

import javafx.beans.property.SimpleStringProperty;

public class Notes {
    private int notesID;
    private UserView userViewID;
    private final SimpleStringProperty notes;

    public Notes(int notesID, UserView userViewID, String notes) {
        this.notesID = notesID;
        this.userViewID = userViewID;
        this.notes = new SimpleStringProperty(notes);
    }

    public int getNotesID() {
        return notesID;
    }

    public UserView getUserViewID() {
        return userViewID;
    }

    public String getNotes() {
        return notes.get();
    }

    public void setNotesID(int notesID) {
        this.notesID = notesID;
    }

    public void setUserViewID(UserView userViewID) {
        this.userViewID = userViewID;
    }

    public void setNotes(String notes) { this.notes.set(notes);
    }

    @Override
    public String toString() {
        return "Notes{" +
                "notesID=" + notesID +
                ", userViewID=" + userViewID +
                ", notes='" + notes + '\'' +
                '}';
    }

}
