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
        this.deadline = deadline;
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
}

