package Controllers;

import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;


public class MainControllers {
    private ArrayList<AssignmentsView> assignmentsViewList;
    private AssignmentsStage assignmentsStage;

    private static ArrayList<Integer> descriptionIdTracker = new ArrayList<>();
    private static ArrayList<Integer> assignmentIdTracker = new ArrayList<>();
    private static ArrayList<Integer> userIdTracker = new ArrayList<>();


    private ArrayList<AssignmentsView> targetList;
    public DatabaseConnection databaseConnection ;

    public MainControllers() {
        this.assignmentsViewList = new ArrayList<>();
        this.databaseConnection = new DatabaseConnection("coursework.db");
    }



    public boolean addAssignment() {

        AssignmentsView data = AssignmentsStage.getUserEnteredData();

        UserView userDataEntered = AssignmentsStage.userViewInfo;

        if (userDataEntered != null) {

            User saveUserToDatabase = new User(0,userDataEntered.getFirstName(), userDataEntered.getLastname(), userDataEntered.getDOB());
            UserService.save(saveUserToDatabase, databaseConnection);

            //adds to database
            Description saveDescriptionToDatabase = new Description(0, data.getDescription(), data.getTitle(), data.getQuantity(), data.getFormat());
            Classroom saveClassroomToDatabase = new Classroom(data.getClassroom(), data.getTeacher());
            Assignments saveAssignmentToDatabase = new Assignments(0, saveClassroomToDatabase.getClassroom(), saveDescriptionToDatabase.getDescriptionID(), data.getDeadline());

            AssignmentService.save(saveDescriptionToDatabase, saveClassroomToDatabase, databaseConnection);
            AssignmentService.saveToAssignments(saveDescriptionToDatabase.getDescriptionID(), saveClassroomToDatabase.getClassroom(), saveAssignmentToDatabase, databaseConnection);




            //TRACKS THE PK OF THE DESCRIPTION AND ASSIGNMENT TABLE
            descriptionIdTracker.add(saveDescriptionToDatabase.getDescriptionID());
            assignmentIdTracker.add(saveAssignmentToDatabase.getAssignmentID());
            userIdTracker.add(saveUserToDatabase.getId());

            for (int i : descriptionIdTracker) {
                System.out.println(i);
            }
            for (int j : assignmentIdTracker) {
                System.out.println(j);
            }
            for(int m : userIdTracker){
                System.out.println(m);
            }


            updateAssignmentsViewTableView();

            return true;

        }
        return false;
    }

    public void updateAssignmentsViewTableView(){
        targetList= new ArrayList<>();
        AssignmentsStage.getTableViewData().clear();
        AssignmentService.selectAll(AssignmentsStage.getTableViewData(), databaseConnection);
    }

}
