package Models;

import javafx.util.converter.LocalDateStringConverter;

import javax.crypto.spec.DESedeKeySpec;
import javax.xml.transform.Result;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.List;

public class AssignmentService {
    //used for assignmentsview table-view
    public static void selectAll(List<AssignmentsView> targetList, DatabaseConnection database) {



        PreparedStatement statement1 = database.newStatement("SELECT Class FROM Classroom ORDER BY Class");
        PreparedStatement statement2 = database.newStatement("SELECT DescriptionID, Quantity, Format, Title, Description FROM Description ORDER BY DescriptionID");
        PreparedStatement statement3 = database.newStatement("SELECT AssignmentID, Deadline FROM Assignments ORDER BY AssignmentID");

        try {
            if (statement1 != null && statement2 != null && statement3 != null) {

                ResultSet results1 = database.runQuery(statement1);
                ResultSet results2 = database.runQuery(statement2);
                ResultSet results3 = database.runQuery(statement3);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                if (results1 != null && results2 != null && results3 != null) {
                    while (results1.next() && results2.next() && results3.next()) {

                        LocalDate deadline = LocalDate.parse(results3.getString("Deadline"), formatter);
                        targetList.add(new AssignmentsView(results3.getInt("AssignmentID"), results2.getInt("DescriptionID"),results1.getString("Class"), results2.getString("Description"), results2.getString("Title"), results2.getInt("Quantity"), results2.getString("Format"), deadline));
                    }
                }
            }

        } catch (SQLException resultsException) {
            System.out.println("Database select all error: " + resultsException.getMessage());
        }
    }

    public static void selectAll(User user, List<AssignmentsView> targetList, DatabaseConnection database) {

        int userId = user.getId();
        int assignmentID = 0;
        int descriptionID = 0;
        LocalDate deadline = null;
        String classroom = null;
        int quantity = 0;
        String format = null;
        String title = null;
        String description = null;

        PreparedStatement statement = database.newStatement(String.format("SELECT AssignmentID FROM SchoolPlanner WHERE UserID = %2d", userId));


        try {
            if (statement != null) {

                ResultSet results1 = database.runQuery(statement);

                if (results1 != null) {
                    while (results1.next()) {
                        assignmentID = results1.getInt("AssignmentID");
                    }
                    PreparedStatement statement2 = database.newStatement(String.format("SELECT Class, DescriptionID, Deadline FROM Assignments WHERE AssignmentID = %2d", assignmentID));
                    if(database.runQuery(statement2) != null){
                        ResultSet results2 = database.runQuery(statement2);
                        while(results2.next()){
                            deadline = LocalDate.parse(results2.getString("Deadline"));
                            descriptionID = results2.getInt("DescriptionID");
                            classroom = results2.getString("Class");
                        }
                        PreparedStatement statement3 = database.newStatement(String.format("SELECT Quantity, Format, Title, Description FROM Description WHERE DescriptionID = %2d", descriptionID));
                        if(database.runQuery(statement3) != null){
                            ResultSet results3 = database.runQuery(statement3);
                            while (results3.next()){
                                quantity = results3.getInt("Quantity");
                                format = results3.getString("Format");
                                title = results3.getString("Title");
                                description = results3.getString("Description");
                            }
                            targetList.add(new AssignmentsView(assignmentID, descriptionID, classroom, description, title, quantity, format, deadline));
                        }


                    }


                }
            }
        } catch (SQLException resultsException) {
            System.out.println("Database select all error: " + resultsException.getMessage());
        }
    }

    //Select a certain ID from assignments-view class and users scene in assignment stage
    public static Object selectByIdClassroom(String tablename, int id, DatabaseConnection database) {

        PreparedStatement statement = null;

        if(tablename.equals("CLASSROOM")) {
            String classroomTable = "SELECT Class, Teacher FROM CLASSROOM WHERE Class = ?";
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
                        result = new Classroom(results.getString("Class"), results.getString("Teacher"));
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
    public static Object selectByIdClassroom(String  id, DatabaseConnection database) {

        PreparedStatement statement = database.newStatement("SELECT Class, Teacher FROM Classroom WHERE Class = ?");

        Object result = null;

        try {
            if (statement != null) {

                statement.setString(1, id);
                ResultSet results = database.runQuery(statement);

                    if (results != null) {
                        result = new Classroom(results.getString("Class"), results.getString("Teacher"));
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
        String classroomID = classroom.getClassroom();

        existingItem = selectByIdClassroom(classroomID, database);
        if(descriptionID != 0) existingItem2 = selectByIdClassroom("DESCRIPTION", descriptionID, database);

        try {
            if (existingItem == null) {
                PreparedStatement statement = database.newStatement("INSERT INTO Classroom (Class, Teacher) VALUES (?,?)");
                statement.setString(1, classroom.getClassroom());
                statement.setString(2, classroom.getTeacher());
                database.executeUpdate(statement);
            }
            else {
                PreparedStatement statement = database.newStatement("UPDATE Classroom SET Teacher = ? WHERE Class = ?");
                statement.setString(1, classroom.getTeacher());
                statement.setString(2, classroom.getClassroom());
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
                PreparedStatement statement = database.newStatement("UPDATE Description SET Quantity = ?, Format = ?, Title = ?, Description = ? WHERE DesriptionID = ?");
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

    public static void saveToPlanner(User user, Assignments assignments, DatabaseConnection database) {

        try {
                PreparedStatement statement = database.newStatement("INSERT INTO SchoolPlanner (UserID, AssignmentID) VALUES (?,?)");
                statement.setInt(1, user.getId());
                statement.setInt(2, assignments.getAssignmentID());
                database.executeUpdate(statement);
        } catch (SQLException resultsException) {
            System.out.println("Database saving error: " + resultsException.getMessage());
        }

    }

    public static void saveToAssignments(int descriptionid, String classroom, Assignments assignments, DatabaseConnection database) {

        Object existingItem = null;

        int assignmentsID = assignments.getAssignmentID();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String assignmentsDeadlineToString = assignments.getDeadline().format(formatter);


        if(assignmentsID != 0) existingItem = selectByIdClassroom("DESCRIPTION", assignmentsID, database);



        try {
            if (existingItem == null) {
                PreparedStatement statement = database.newStatement("INSERT INTO Assignments (Class, DescriptionID, Deadline) VALUES (?,?,?)");
                statement.setString(1, classroom);
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
