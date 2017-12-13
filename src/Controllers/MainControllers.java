package Controllers;

import Models.*;

public class MainControllers {

    private static User saveUserToDatabase;

    public DatabaseConnection databaseConnection ;

    public MainControllers() {
        this.databaseConnection = new DatabaseConnection("coursework.db");
    }

    public void addAssignment() {

        if (AssignmentsStage.userData != null && AssignmentsStage.enteredData != null) {

                AssignmentsView assignmentDataEntered = AssignmentsStage.enteredData;
                User userDataEntered = AssignmentsStage.userData;
                saveUserToDatabase = userDataEntered;
                System.out.println(saveUserToDatabase);

                //adds to database
                Description saveDescriptionToDatabase = new Description(0, assignmentDataEntered.getDescription(), assignmentDataEntered.getTitle(), assignmentDataEntered.getQuantity(), assignmentDataEntered.getFormat());
                Classroom saveClassroomToDatabase = new Classroom(0,assignmentDataEntered.getClassroom(), assignmentDataEntered.getTeacher());
                Assignments saveAssignmentToDatabase = new Assignments(0, saveClassroomToDatabase.getClassroom(), saveDescriptionToDatabase.getDescriptionID(), assignmentDataEntered.getDeadline());

                AssignmentService.save(saveDescriptionToDatabase, saveClassroomToDatabase, databaseConnection);
                //last new id is needed in order to save to assignments it is NOT to be confused with changing primary key values stored in a table
                AssignmentService.saveToAssignments(saveDescriptionToDatabase.getDescriptionID(), saveClassroomToDatabase.getClassID(), saveAssignmentToDatabase, databaseConnection);

                SchoolPlanner schoolPlanner = new SchoolPlanner(0, saveUserToDatabase.getId(), saveAssignmentToDatabase.getAssignmentID());


                //also used to save to planner
                AssignmentService.saveToPlanner(schoolPlanner, databaseConnection);

            updateAssignmentsViewTableView();

        }else{
            AssignmentsStage.errorReporter.setText("Data has not been entered properly");
        }
    }
    public void deleteAssignment(AssignmentsView assignmentsView){
        if(assignmentsView != null) {
            Assignments assignments = new Assignments(assignmentsView.getAssignmentID(), assignmentsView.getClassroom(), assignmentsView.getDescriptionID(), assignmentsView.getDeadline());
            Description description = new Description(assignmentsView.getDescriptionID(), assignmentsView.getDescription(), assignmentsView.getTitle(), assignmentsView.getQuantity(), assignmentsView.getFormat());
            Classroom classroom = new Classroom(assignmentsView.getClassroomID(), assignmentsView.getClassroom(), assignmentsView.getTeacher());
            SchoolPlanner schoolPlanner = new SchoolPlanner(assignmentsView.getPlannerID(), AssignmentsStage.userData.getId(), assignmentsView.getAssignmentID());


            AssignmentService.delete(schoolPlanner, assignments, description, classroom, databaseConnection);
            updateAssignmentsViewTableView();
        }
    }

    public void modifyAssignment(AssignmentsView selectedAssignmentToModify) {
        if (AssignmentsStage.enteredData != null && AssignmentsStage.userData != null && selectedAssignmentToModify != null) {
            AssignmentsView enteredData = AssignmentsStage.enteredData;
            Assignments assignments = new Assignments(enteredData.getAssignmentID(), enteredData.getClassroom(), enteredData.getDescriptionID(), enteredData.getDeadline());
            Description description = new Description(enteredData.getDescriptionID(), enteredData.getDescription(), enteredData.getTitle(), enteredData.getQuantity(), enteredData.getFormat());
            Classroom classroom = new Classroom(enteredData.getClassroomID(), enteredData.getClassroom(), enteredData.getTeacher());

            AssignmentService.modifyAssignment(selectedAssignmentToModify, assignments, description, classroom, databaseConnection);
            updateAssignmentsViewTableView();
        }


    }

    public void addUser() {
        AssignmentsStage.userListViewData.clear();
        if(AssignmentsStage.userData != null) {
            UserService.save(AssignmentsStage.userData, databaseConnection);
        }
        updateUserListView();
        AssignmentsStage.userData = null;


    }

    public void deleteUser() {
        if (AssignmentsStage.userData != null) {
            UserService.deleteUser(AssignmentsStage.userData, databaseConnection);
            AssignmentsStage.userListViewData.clear();
            updateUserListView();
            updateAssignmentsViewTableView();
        }
    }



    public void updateAssignmentsViewTableView() {
        AssignmentsStage.getTableViewData().clear();
        AssignmentsStage.errorReporter.clear();
        if (AssignmentsStage.userData != null) {
            AssignmentService.selectAll(AssignmentsStage.userData, AssignmentsStage.getTableViewData(), databaseConnection);
        }
    }
    public void updateUserListView(){
        UserService.selectAll(AssignmentsStage.userListViewData, databaseConnection);
    }

    public void updateNotesTableView(){
        NotesStage.notesData.clear();
        NotesService.selectAll(NotesStage.userData, NotesStage.notesData, databaseConnection);
    }



}
