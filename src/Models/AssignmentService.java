package Models;

import Controllers.MainControllers;
import javafx.util.converter.LocalDateStringConverter;

import javax.crypto.spec.DESedeKeySpec;
import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class AssignmentService {

    //used for assignmentsview table-view

    public static void selectAll(User user, List<AssignmentsView> targetList, DatabaseConnection database) {

        int userId = user.getId();
        int plannerID = 0;
        int classID = 0;
        int assignmentID = 0;
        int descriptionID = 0;
        LocalDate deadline = null;
        String classroom = null;
        int quantity = 0;
        String format = null;
        String title = null;
        String description = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        PreparedStatement statement = database.newStatement("SELECT AssignmentID FROM SchoolPlanner WHERE UserID = ?");


        try {
            if (statement != null) {

                statement.setInt(1, userId);
                ResultSet results1 = database.runQuery(statement);

                if (results1 != null) {
                    while (results1.next()) {
                        assignmentID = results1.getInt("AssignmentID");

                        PreparedStatement preparedStatement5 = database.newStatement("SELECT PlannerID FROM SchoolPlanner WHERE AssignmentID = ?");
                        preparedStatement5.setInt(1, assignmentID);

                        if (database.runQuery(preparedStatement5) != null) {

                            ResultSet results5 = database.runQuery(preparedStatement5);

                            while (results5.next()) {
                                plannerID = results5.getInt("PlannerID");


                                PreparedStatement statement2 = database.newStatement("SELECT ClassID, DescriptionID, Deadline FROM Assignments WHERE AssignmentID = ?");
                                statement2.setInt(1, assignmentID);

                                if (database.runQuery(statement2) != null) {
                                    ResultSet results2 = database.runQuery(statement2);

                                    while (results2.next()) {
                                        deadline = LocalDate.parse(results2.getString("Deadline"), formatter);
                                        descriptionID = results2.getInt("DescriptionID");
                                        classID = results2.getInt("ClassID");

                                        PreparedStatement statement3 = database.newStatement("SELECT Quantity, Format, Title, Description FROM Description WHERE DescriptionID = ?");
                                        statement3.setInt(1, descriptionID);
                                        if (database.runQuery(statement3) != null) {

                                            ResultSet results3 = database.runQuery(statement3);
                                            while (results3.next()) {
                                                quantity = results3.getInt("Quantity");
                                                format = results3.getString("Format");
                                                title = results3.getString("Title");
                                                description = results3.getString("Description");

                                                PreparedStatement statement4 = database.newStatement("SELECT Class FROM Classroom WHERE ClassID = ?");
                                                statement4.setInt(1, classID);
                                                if (database.runQuery(statement4) != null) {
                                                    ResultSet results4 = database.runQuery(statement4);
                                                    while (results4.next()) {
                                                        classroom = results4.getString("Class");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        targetList.add(new AssignmentsView(classID, plannerID, assignmentID, descriptionID, classroom, description, title, quantity, format, deadline));
                    }
                }

                    }

        } catch (SQLException resultsException) {
            System.out.println("Database select all error: " + resultsException.getMessage());
        }
    }



    public static void delete(SchoolPlanner schoolPlanner, Assignments assignments, Description description, Classroom classroom, DatabaseConnection database){


            PreparedStatement statement = database.newStatement("DELETE FROM SchoolPlanner WHERE PlannerID = ?");
            PreparedStatement statement2 = database.newStatement("DELETE FROM Assignments WHERE AssignmentID = ?");
            PreparedStatement statement3 = database.newStatement("DELETE FROM Classroom WHERE ClassID = ?");
            PreparedStatement statement4 = database.newStatement("DELETE FROM Description where DescriptionID = ?");


            try{
                if (statement != null && statement2 != null && statement3 != null && statement4 != null) {
                    statement.setInt(1, schoolPlanner.getPlannerID());
                    statement2.setInt(1, assignments.getAssignmentID());
                    statement3.setInt(1, classroom.getClassID());
                    statement4.setInt(1, description.getDescriptionID());
                    database.executeUpdate(statement);
                    database.executeUpdate(statement2);
                    database.executeUpdate(statement3);
                    database.executeUpdate(statement4);
                }

            } catch (SQLException resultsException) {
                System.out.println("Database select all error: " + resultsException.getMessage());
        }
    }



    //Select a certain ID from assignments-view class and users scene in assignment stage

    public static void save(Description description, Classroom classroom, Assignments assignments, DatabaseConnection database) {


        int descriptionID = description.getDescriptionID();
        int classroomID = classroom.getClassID();
        int assignmentsID = assignments.getAssignmentID();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String assignmentsDeadlineToString = assignments.getDeadline().format(formatter);

        try {
                PreparedStatement statement = database.newStatement("UPDATE Classroom SET Teacher = ?, Class = ? WHERE ClassID = ?");
                statement.setString(1, classroom.getTeacher());
                statement.setString(2, classroom.getClassroom());
                statement.setInt(3, classroomID);
                database.executeUpdate(statement);

                PreparedStatement statement2 = database.newStatement("UPDATE Description SET Quantity = ?, Format = ?, Title = ?, Description = ? WHERE DescriptionID = ?");
                statement2.setInt(1, description.getQuantity());
                statement2.setString(2, description.getFormat());
                statement2.setString(3, description.getTitle());
                statement2.setString(4, description.getDescription());
                statement2.setInt(5, descriptionID);

                database.executeUpdate(statement2);

                PreparedStatement statement3 = database.newStatement("UPDATE Assignments SET DescriptionID = ?, Deadline = ? WHERE AssignmentID = ?");
                statement3.setInt(1, descriptionID);
                statement3.setString(2 , assignmentsDeadlineToString);
                statement3.setInt(3, assignmentsID);

                database.executeUpdate(statement3);


        } catch (SQLException resultsException) {
            System.out.println("Database saving error: " + resultsException.getMessage());
        }

    }

    public static void save(Description description, Classroom classroom, DatabaseConnection database) {
        try {
                PreparedStatement statement = database.newStatement("INSERT INTO Classroom (Class, Teacher) VALUES (?,?)");
                statement.setString(1, classroom.getClassroom());
                statement.setString(2, classroom.getTeacher());
                database.executeUpdate(statement);
                classroom.setClassID(database.lastNewId());

                PreparedStatement statement2 = database.newStatement("INSERT INTO Description (Quantity, Format, Title, Description) VALUES (?,?,?,?)");
                statement2.setInt(1, description.getQuantity());
                statement2.setString(2, description.getFormat());
                statement2.setString(3, description.getTitle());
                statement2.setString(4, description.getDescription());

                database.executeUpdate(statement2);
                description.setDescriptionID(database.lastNewId());


        } catch (SQLException resultsException) {
            System.out.println("Database saving error: " + resultsException.getMessage());
        }

    }

    public static void saveToPlanner(SchoolPlanner schoolPlanner, DatabaseConnection database) {
        try {
                PreparedStatement statement = database.newStatement("INSERT INTO SchoolPlanner (UserID, AssignmentID) VALUES (?,?)");
                statement.setInt(1, schoolPlanner.getUserID());
                statement.setInt(2, schoolPlanner.getAssignmentID());
                database.executeUpdate(statement);
                schoolPlanner.setPlannerID(database.lastNewId());
        } catch (SQLException resultsException) {
            System.out.println("Database saving error: " + resultsException.getMessage());
        }

    }

    public static void saveToAssignments(int descriptionid, int classID, Assignments assignments, DatabaseConnection database) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String assignmentsDeadlineToString = assignments.getDeadline().format(formatter);


        try {
                PreparedStatement statement = database.newStatement("INSERT INTO Assignments (ClassID, DescriptionID, Deadline) VALUES (?,?,?)");
                statement.setInt(1, classID);
                statement.setInt(2, descriptionid);
                statement.setString(3, assignmentsDeadlineToString );
                database.executeUpdate(statement);
                assignments.setAssignmentID(database.lastNewId());

        } catch (SQLException resultsException) {
            System.out.println("Database saving error: " + resultsException.getMessage());
        }

    }


}
