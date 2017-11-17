package Controllers;

import Models.*;
import javafx.scene.control.DatePicker;
import jdk.nashorn.internal.ir.Assignment;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainControllers {
    private ArrayList<AssignmentsView> assignmentsViewList;
    private AssignmentsStage assignmentsStage;

    private ArrayList<AssignmentsView> targetList;
    public DatabaseConnection databaseConnection ;

    public MainControllers() {
        this.assignmentsViewList = new ArrayList<>();
        this.databaseConnection = new DatabaseConnection("coursework.db");
    }

    public boolean addAssignment(){

            AssignmentsView data = AssignmentsStage.getEnteredData();


            //adds to database
            Description databaseDescription = new Description(0, data.getDescription(), data.getTitle(), data.getQuantity(), data.getFormat());
            Classroom databaseClassroom = new Classroom(data.getClassroom(), data.getTeacher());
            Assignments databaseAssignments = new Assignments(0, databaseClassroom.getClassroom(), databaseDescription.getDescriptionID(), data.getDeadline());

            System.out.println(databaseDescription.getDescription());

            AssignmentService.save(databaseDescription, databaseClassroom, databaseConnection);
            AssignmentService.saveToAssignments(databaseDescription.getDescriptionID(), databaseClassroom.getClassroom(), databaseAssignments, databaseConnection);

//        }
        return true;

    }

    public ArrayList updateAssignmentsViewTableView(){
        targetList= new ArrayList<>();
        AssignmentService.selectAll(targetList, databaseConnection);
        return targetList;
    }

}
