package Models;

public class Classroom {
    private int classID;
    private String classroom;
    private String teacher;

    public Classroom(int classID, String classroom, String teacher) {
        this.classID = classID;
        this.classroom = classroom;
        this.teacher = teacher;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "classID=" + classID +
                ", classroom='" + classroom + '\'' +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
