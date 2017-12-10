package Controllers;

import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.security.cert.CertificateNotYetValidException;
import java.util.ArrayList;


public class MainControllers {

    private static User saveUserToDatabase;

    public DatabaseConnection databaseConnection ;

    public MainControllers() {
        this.databaseConnection = new DatabaseConnection("coursework.db");
    }

    public void addAssignment() {

        AssignmentsView assignmentDataEntered = AssignmentsStage.enteredData;

        User userDataEntered = AssignmentsStage.userData;

        if (userDataEntered != null || assignmentDataEntered != null) {

            saveUserToDatabase = userDataEntered;

            //adds to database
            Description saveDescriptionToDatabase = new Description(0, assignmentDataEntered.getDescription(), assignmentDataEntered.getTitle(), assignmentDataEntered.getQuantity(), assignmentDataEntered.getFormat());
            Classroom saveClassroomToDatabase = new Classroom(assignmentDataEntered.getClassroom(), assignmentDataEntered.getTeacher());
            Assignments saveAssignmentToDatabase = new Assignments(0, saveClassroomToDatabase.getClassroom(), saveDescriptionToDatabase.getDescriptionID(), assignmentDataEntered.getDeadline());

            AssignmentService.save(saveDescriptionToDatabase, saveClassroomToDatabase, databaseConnection);
            AssignmentService.saveToAssignments(saveDescriptionToDatabase.getDescriptionID(), saveClassroomToDatabase.getClassroom(), saveAssignmentToDatabase, databaseConnection);

            AssignmentService.saveToPlanner(saveUserToDatabase, saveAssignmentToDatabase, databaseConnection);

            updateAssignmentsViewTableView();

        }
    }
    public void deleteAssignment(AssignmentsView assignmentsView){
        Assignments assignments = new Assignments(assignmentsView.getAssignmentID(), assignmentsView.getClassroom(), assignmentsView.getDescriptionID(), assignmentsView.getDeadline());
        Description description = new Description(assignmentsView.getDescriptionID(), assignmentsView.getDescription(), assignmentsView.getTitle(), assignmentsView.getQuantity(), assignmentsView.getFormat());
        Classroom classroom = new Classroom(assignmentsView.getClassroom(), assignmentsView.getTeacher());

        AssignmentService.delete(AssignmentsStage.userData, assignments, description, classroom, databaseConnection);
        updateAssignmentsViewTableView();
    }

    public void modifyAssignment() {
        if (AssignmentsStage.enteredData != null) {
            AssignmentsView enteredData = AssignmentsStage.enteredData;
            Assignments assignments = new Assignments(enteredData.getAssignmentID(), enteredData.getClassroom(), enteredData.getDescriptionID(), enteredData.getDeadline());
            Description description = new Description(enteredData.getDescriptionID(), enteredData.getDescription(), enteredData.getTitle(), enteredData.getQuantity(), enteredData.getFormat());
            Classroom classroom = new Classroom(enteredData.getClassroom(), enteredData.getTeacher());

            AssignmentService.modifyAssignment(AssignmentsStage.userData, assignments, description, classroom, databaseConnection);
            updateAssignmentsViewTableView();
        }


    }


    //remove all entries made by user including itself

    public void addUser() {
        AssignmentsStage.userListViewData.clear();
        ArrayList<User> users = new ArrayList<>();
        UserService.selectAll(users, databaseConnection);

        if (AssignmentsStage.userData != null) {
            for (User user : users) {
                if (user.getDOB() != AssignmentsStage.userData.getDOB() && user.getFirstName().equals(AssignmentsStage.userData.getFirstName()) && user.getLastName().equals(AssignmentsStage.userData.getLastName())) {
                    UserService.save(AssignmentsStage.userData, databaseConnection);
                }
            }
            updateUserListView();

        }
    }

    public void deleteUser(){
        UserService.deleteUser(AssignmentsStage.userData, databaseConnection);
        AssignmentsStage.userListViewData.clear();
        updateUserListView();
        updateAssignmentsViewTableView();
    }



    public void updateAssignmentsViewTableView(){
        AssignmentsStage.getTableViewData().clear();
        AssignmentsStage.errorReporter.clear();
        AssignmentService.selectAll(AssignmentsStage.userData,AssignmentsStage.getTableViewData(), databaseConnection);
    }
    public void updateUserListView(){
        UserService.selectAll(AssignmentsStage.userListViewData, databaseConnection);
    }

    public void updateNotesTableView(){
        NotesStage.notesData.clear();
        NotesService.selectAll(NotesStage.userData, NotesStage.notesData, databaseConnection);
    }



}
