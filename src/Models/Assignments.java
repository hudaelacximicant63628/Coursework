package Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.util.Date;

public class Assignments {

    private int assignmentID;
    private Classroom classroom;
    private Description description;
    private Date deadline;

    public Assignments(int assignmentID, Classroom classroom, Description description, Date deadline) {
        this.assignmentID = assignmentID;
        this.classroom = classroom;
        this.description = description;
        this.deadline = deadline; {
        };
    }

    public int getAssignmentID() {
        return assignmentID;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public Description getDescription() {
        return description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setAssignmentID(int assignmentID) {
        this.assignmentID = assignmentID;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Assignments{" +
                "assignmentID=" + assignmentID +
                ", classroom=" + classroom +
                ", description=" + description +
                ", deadline=" + deadline +
                '}';
    }
}

