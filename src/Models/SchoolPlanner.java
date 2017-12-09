package Models;

public class SchoolPlanner {
    private int plannerID;
    private int userID;
    private int assignmentID;

    public SchoolPlanner(int plannerID, int userID, int assignmentID) {
        this.plannerID = plannerID;
        this.userID = userID;
        this.assignmentID = assignmentID;
    }

    public int getPlannerID() {
        return plannerID;
    }

    public void setPlannerID(int plannerID) {
        this.plannerID = plannerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(int assignmentID) {
        this.assignmentID = assignmentID;
    }

    @Override
    public String toString() {
        return "SchoolPlanner{" +
                "plannerID=" + plannerID +
                ", userID=" + userID +
                ", assignmentID=" + assignmentID +
                '}';
    }
}
