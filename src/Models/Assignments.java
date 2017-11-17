package Models;

import java.time.LocalDate;

public class Assignments {

    private int assignmentID;
    private String classroom;
    private int descriptionID;
    private LocalDate deadline;

    public Assignments(int assignmentID, String classroom, int description, LocalDate deadline) {
        this.assignmentID = assignmentID;
        this.classroom = classroom;
        this.descriptionID = description;
        this.deadline = deadline; {
        };
    }

    public int getAssignmentID() {
        return assignmentID;
    }

    public String getClassroom() {
        return classroom;
    }

    public int getDescriptionID() {
        return descriptionID;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setAssignmentID(int assignmentID) {
        this.assignmentID = assignmentID;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public void setDescriptionID(int descriptionID) {
        this.descriptionID = descriptionID;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Assignments{" +
                "assignmentID=" + assignmentID +
                ", classroom=" + classroom +
                ", descriptionID=" + descriptionID +
                ", deadline=" + deadline +
                '}';
    }
}

