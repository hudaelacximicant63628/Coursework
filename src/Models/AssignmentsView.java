package Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.Date;
import java.util.ArrayList;

public class AssignmentsView {
    private int assignmentID;
    private int descriptionID;
    private int classroomID;
    private int plannerID;
    private String teacher;
    private final SimpleStringProperty description;
    private final SimpleStringProperty title;
    private final SimpleIntegerProperty quantity;
    private final SimpleStringProperty format;
    private final SimpleStringProperty classroom;
    private final SimpleObjectProperty<LocalDate> deadline;


    public AssignmentsView(int classroomID, int plannerID, int assignmentID, int descriptionID, String classroom, String description, String title, int quantity, String format, LocalDate deadline) {
        this.description = new SimpleStringProperty(description);
        this.title = new SimpleStringProperty(title);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.format = new SimpleStringProperty(format);
        this.classroom = new SimpleStringProperty(classroom);
        this.deadline = new SimpleObjectProperty<>(deadline);
        this.plannerID= plannerID;
        this.classroomID = classroomID;
        this.assignmentID = assignmentID;
        this.descriptionID = descriptionID;

    }
    public AssignmentsView(String teacher, String classroom, String descriptionID, String title, int quantity, String format, LocalDate deadline) {
        this.description = new SimpleStringProperty(descriptionID);
        this.teacher = teacher;
        this.title = new SimpleStringProperty(title);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.format = new SimpleStringProperty(format);
        this.classroom = new SimpleStringProperty(classroom);
        this.deadline = new SimpleObjectProperty<>(deadline);
    }

    public int getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(int assignmentID) {
        this.assignmentID = assignmentID;
    }

    public int getDescriptionID() {
        return descriptionID;
    }

    public void setDescriptionID(int descriptionID) {
        this.descriptionID = descriptionID;
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public String getFormat() {
        return format.get();
    }

    public SimpleStringProperty formatProperty() {
        return format;
    }

    public void setFormat(String format) {
        this.format.set(format);
    }

    public String getClassroom() {
        return classroom.get();
    }

    public SimpleStringProperty classroomProperty() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom.set(classroom);
    }

    public LocalDate getDeadline() {
        return deadline.get();
    }

    public SimpleObjectProperty<LocalDate> deadlineProperty() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline.set(deadline);
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getClassroomID() {
        return classroomID;
    }

    public void setClassroomID(int classroomID) {
        this.classroomID = classroomID;
    }

    public int getPlannerID() {
        return plannerID;
    }

    public void setPlannerID(int plannerID) {
        this.plannerID = plannerID;
    }

    @Override
    public String toString() {
        return "AssignmentsView{" +
                "assignmentID=" + assignmentID +
                ", descriptionID=" + descriptionID +
                ", teacher='" + teacher + '\'' +
                ", description=" + description +
                ", title=" + title +
                ", quantity=" + quantity +
                ", format=" + format +
                ", classroom=" + classroom +
                ", deadline=" + deadline +
                '}';
    }
}
