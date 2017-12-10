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

        PreparedStatement statement = database.newStatement(String.format("SELECT AssignmentID FROM SchoolPlanner WHERE UserID = %2d", userId));


        try {
            if (statement != null) {

                ResultSet results1 = database.runQuery(statement);

                if (results1 != null) {
                    while (results1.next()) {
                        assignmentID = results1.getInt("AssignmentID");

                        PreparedStatement preparedStatement5 = database.newStatement(String.format("SELECT PlannerID FROM SchoolPlanner WHERE AssignmentID = %2d", assignmentID));
                        if (database.runQuery(preparedStatement5) != null) {
                            ResultSet results5 = database.runQuery(preparedStatement5);
                            while (results5.next()) {
                                plannerID = results5.getInt("PlannerID");


                                PreparedStatement statement2 = database.newStatement(String.format("SELECT ClassID, DescriptionID, Deadline FROM Assignments WHERE AssignmentID = %2d", assignmentID));
                                if (database.runQuery(statement2) != null) {
                                    ResultSet results2 = database.runQuery(statement2);
                                    while (results2.next()) {
                                        deadline = LocalDate.parse(results2.getString("Deadline"), formatter);
                                        descriptionID = results2.getInt("DescriptionID");
                                        classID = results2.getInt("ClassID");

                                        PreparedStatement statement3 = database.newStatement(String.format("SELECT Quantity, Format, Title, Description FROM Description WHERE DescriptionID = %2d", descriptionID));
                                        if (database.runQuery(statement3) != null) {
                                            ResultSet results3 = database.runQuery(statement3);
                                            while (results3.next()) {
                                                quantity = results3.getInt("Quantity");
                                                format = results3.getString("Format");
                                                title = results3.getString("Title");
                                                description = results3.getString("Description");

                                                PreparedStatement statement4 = database.newStatement(String.format("SELECT Class FROM Classroom WHERE ClassID = %2d", classID));
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
                        targetList.add(new AssignmentsView(plannerID, classID, assignmentID, descriptionID, classroom, description, title, quantity, format, deadline));
                    }
                }

                    }

        } catch (SQLException resultsException) {
            System.out.println("Database select all error: " + resultsException.getMessage());
        }
    }

    public static void modifyAssignment(AssignmentsView selectedAssignmentToModify, Assignments assignments, Description description, Classroom classroom, DatabaseConnection databaseConnection) {

        PreparedStatement statement = databaseConnection.newStatement("UPDATE Classroom SET Teacher = ?, Class = ? WHERE ClassID = ?");
        PreparedStatement statement2 = databaseConnection.newStatement("UPDATE Description SET Quantity = ?, Format = ?, Title = ?, Description = ? WHERE DescriptionID = ?");
        PreparedStatement statement3 = databaseConnection.newStatement("UPDATE Assignments SET Deadline = ? WHERE AssignmentID = ?");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String assignmentsDeadlineToString = assignments.getDeadline().format(formatter);

        try {
            statement.setString(1, classroom.getTeacher());
            statement.setString(2, classroom.getClassroom());
            statement.setInt(3, selectedAssignmentToModify.getClassroomID());

            statement2.setInt(1, description.getQuantity());
            statement2.setString(2, description.getFormat());
            statement2.setString(3, description.getTitle());
            statement2.setString(4, description.getDescription());
            statement2.setInt(5, selectedAssignmentToModify.getDescriptionID());

            statement3.setString(1, assignmentsDeadlineToString);
            statement3.setInt(2, selectedAssignmentToModify.getAssignmentID());


            databaseConnection.executeUpdate(statement);
            databaseConnection.executeUpdate(statement2);
            databaseConnection.executeUpdate(statement3);


        } catch (
                SQLException resultsException) {
            System.out.println("Database select all error: " + resultsException.getMessage());
        }
    }

    public static void delete(User user, SchoolPlanner schoolPlanner, Assignments assignments, Description description, Classroom classroom, DatabaseConnection database){


            PreparedStatement statement = database.newStatement("DELETE FROM SchoolPlanner WHERE PlannerID = ?");
            PreparedStatement statement2 = database.newStatement("DELETE FROM Assignments WHERE AssignmentID = ?");
            PreparedStatement statement3 = database.newStatement("DELETE FROM Classroom WHERE Class = ?");
            PreparedStatement statement4 = database.newStatement("DELETE FROM Description where DescriptionID = ?");


            try{
                if (statement != null && statement2 != null && statement3 != null && statement4 != null) {
                    statement.setInt(1, schoolPlanner.getPlannerID());
                    statement2.setInt(1, assignments.getAssignmentID());
                    statement3.setString(1, classroom.getClassroom());
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
    public static Object selectById(String tablename, int id, DatabaseConnection database) {

        PreparedStatement statement = null;

        if(tablename.equals("CLASSROOM")) {
            String classroomTable = "SELECT ClassID, Class, Teacher FROM CLASSROOM WHERE ClassID = ?";
            statement = database.newStatement(classroomTable);
        }else if(tablename.equals("DESCRIPTION")){
            String descriptionTable = "SELECT DescriptionID, Quantity, Format, Title, Description FROM Description WHERE DescriptionID = ?";
            statement = database.newStatement(descriptionTable);
        }else if(tablename.equals("ASSIGNMENTS")){
            String assignmentsTable = "SELECT AssignmentID, Class, DescriptionID, Deadline FROM Assignments WHERE AssignmentID = ?";
            statement = database.newStatement(assignmentsTable);
        }


        Object result = null;

        try {
            if (statement != null) {

                statement.setInt(1, id);
                ResultSet results = database.runQuery(statement);

                try {
                    if (results != null) {
                        result = new Classroom(results.getInt("ClassID"), results.getString("Class"), results.getString("Teacher"));
                        result = new Description(results.getInt("DescriptionID"), results.getString("Description"), results.getString("Title"), results.getInt("Quantity"), results.getString("Format"));
                        result = new Assignments(results.getInt("AssignmentID"), results.getString("Class"), results.getInt("DescriptionID"), results.getDate("Deadline").toLocalDate());
                    }
                }catch (Exception e){

                }
            }
        } catch (SQLException resultsException) {
            System.out.println("Database select by id error: " + resultsException.getMessage());
        }

        return result;
    }


    public static void save(Description description, Classroom classroom, DatabaseConnection database) {

        Object existingItem = null;
        Object existingItem2 = null;

        int descriptionID = description.getDescriptionID();
        int classroomID = classroom.getClassID();

        existingItem = selectById("CLASSROOM", classroomID, database);
        if(descriptionID != 0) existingItem2 = selectById("DESCRIPTION", descriptionID, database);

        try {
            if (existingItem == null) {
                PreparedStatement statement = database.newStatement("INSERT INTO Classroom (Class, Teacher) VALUES (?,?)");
                statement.setString(1, classroom.getClassroom());
                statement.setString(2, classroom.getTeacher());
                database.executeUpdate(statement);
                classroom.setClassID(database.lastNewId());
            }
            else {
                PreparedStatement statement = database.newStatement("UPDATE Classroom SET Teacher = ? WHERE ClassID = ?");
                statement.setString(1, classroom.getTeacher());
                statement.setInt(2, classroom.getClassID());
                database.executeUpdate(statement);
            }
            if (existingItem2 == null) {
                PreparedStatement statement = database.newStatement("INSERT INTO Description (Quantity, Format, Title, Description) VALUES (?,?,?,?)");
                statement.setInt(1, description.getQuantity());
                statement.setString(2, description.getFormat());
                statement.setString(3, description.getTitle());
                statement.setString(4, description.getDescription());

                database.executeUpdate(statement);
                description.setDescriptionID(database.lastNewId());
            }
            else {
                PreparedStatement statement = database.newStatement("UPDATE Description SET Quantity = ?, Format = ?, Title = ?, Description = ? WHERE DescriptionID = ?");
                statement.setInt(1, description.getQuantity());
                statement.setString(2, description.getFormat());
                statement.setString(3, description.getTitle());
                statement.setString(4, description.getDescription());

                database.executeUpdate(statement);
            }
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

    public static void saveToAssignments(int descriptionid, int classroom, Assignments assignments, DatabaseConnection database) {

        Object existingItem = null;

        int assignmentsID = assignments.getAssignmentID();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String assignmentsDeadlineToString = assignments.getDeadline().format(formatter);


        if(assignmentsID != 0) existingItem = selectById("DESCRIPTION", assignmentsID, database);



        try {
            if (existingItem == null) {
                PreparedStatement statement = database.newStatement("INSERT INTO Assignments (ClassID, DescriptionID, Deadline) VALUES (?,?,?)");
                statement.setInt(1, classroom);
                statement.setInt(2, descriptionid);
                statement.setString(3, assignmentsDeadlineToString );
                database.executeUpdate(statement);
                assignments.setAssignmentID(database.lastNewId());
            }
            else {
                PreparedStatement statement = database.newStatement("UPDATE Assignments SET DescriptionID = ?, Deadline = ? WHERE AssignmentID = ?");
                statement.setInt(1, descriptionid);
                statement.setString(2 , assignmentsDeadlineToString);
                statement.setInt(3, assignmentsID);
                database.executeUpdate(statement);
            }
        } catch (SQLException resultsException) {
            System.out.println("Database saving error: " + resultsException.getMessage());
        }

    }


}
