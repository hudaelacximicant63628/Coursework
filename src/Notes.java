public class Notes {
    private int notesID;
    private User userID;
    private String notes;

    public Notes(int notesID, User userID, String notes) {
        this.notesID = notesID;
        this.userID = userID;
        this.notes = notes;
    }

    public int getNotesID() {
        return notesID;
    }

    public User getUserID() {
        return userID;
    }

    public String getNotes() {
        return notes;
    }
}
