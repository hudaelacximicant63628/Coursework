package Models;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    public static void selectAll(List<User> targetList, DatabaseConnection database) {



        PreparedStatement statement1 = database.newStatement("SELECT UserID, Firstname, Lastname, DOB FROM SchoolUser ORDER BY UserID");

        try {
            if (statement1 != null) {

                ResultSet results1 = database.runQuery(statement1);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                if (results1 != null) {
                    while (results1.next()) {

                        LocalDate dob = LocalDate.parse(results1.getString("DOB"), formatter);
                        targetList.add(new User(results1.getInt("UserID"), results1.getString("Firstname"), results1.getString("Lastname"), dob));
                    }
                }
            }

        } catch (SQLException resultsException) {
            System.out.println("Database select all error: " + resultsException.getMessage());
        }
    }

    public static void selectUserRelatedData(User user, List<AssignmentsView> targetList, DatabaseConnection database) {

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

    public static void deleteUser(User user, DatabaseConnection databaseConnection){
        ArrayList<AssignmentsView> assignmentsViews = new ArrayList<>();
        selectUserRelatedData(user, assignmentsViews, databaseConnection);

        PreparedStatement statement = databaseConnection.newStatement("DELETE FROM SchoolPlanner WHERE UserID = ? AND AssignmentID = ?");
        PreparedStatement statement2 = databaseConnection.newStatement("DELETE FROM Assignments WHERE AssignmentID = ?");
        PreparedStatement statement3 = databaseConnection.newStatement("DELETE FROM Classroom WHERE Class = ?");
        PreparedStatement statement4 = databaseConnection.newStatement("DELETE FROM Description where DescriptionID = ?");
        PreparedStatement statement5 = databaseConnection.newStatement("DELETE FROM SchoolUser where UserID = ?");


        try{
            if (statement != null && statement2 != null && statement3 != null && statement4 != null) {
                for(int i = 0; i < assignmentsViews.size(); i++) {
                    statement.setInt(1, user.getId());
                    statement.setInt(2, assignmentsViews.get(i).getAssignmentID());
                    statement2.setInt(1, assignmentsViews.get(i).getAssignmentID());
                    statement3.setString(1, assignmentsViews.get(i).getClassroom());
                    statement4.setInt(1, assignmentsViews.get(i).getDescriptionID());

                    databaseConnection.executeUpdate(statement);
                    databaseConnection.executeUpdate(statement2);
                    databaseConnection.executeUpdate(statement3);
                    databaseConnection.executeUpdate(statement4);
                }
                statement5.setInt(1, user.getId());
                databaseConnection.executeUpdate(statement5);

            }

        } catch (SQLException resultsException) {
            System.out.println("Database select all error: " + resultsException.getMessage());
        }
    }

    public static Object selectById(int id, DatabaseConnection database) {

        PreparedStatement statement = null;

        statement = database.newStatement("SELECT UserID, Firstname, Lastname, DOB WHERE UserID = ?");


        Object result = null;

        try {
            if (statement != null) {

                statement.setInt(1, id);
                ResultSet results = database.runQuery(statement);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                try {
                    if (results != null) {
                        LocalDate dob = LocalDate.parse(results.getString("Deadline"), formatter);
                        result = new User(results.getInt("UserID"), results.getString("Firstname"), results.getString("Lastname"),dob);
                    }
                }catch (Exception e){

                }
            }
        } catch (SQLException resultsException) {
            System.out.println("Database select by id error: " + resultsException.getMessage());
        }

        return result;
    }
    public static void delete(User user, Assignments assignments, Description description, Classroom classroom, DatabaseConnection database){

    }

    public static void save(User user, DatabaseConnection database) {

        Object existingItem = null;

        int userID = user.getId();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String userDOBToString = user.getDOB().format(formatter);

        if(userID != 0) existingItem = selectById(userID, database);

        try {
            if (existingItem == null) {
                PreparedStatement statement = database.newStatement("INSERT INTO SchoolUser (Firstname, Lastname, DOB) VALUES (?,?,?)");
                statement.setString(1, user.getFirstName());
                statement.setString(2, user.getLastName());
                statement.setString(3, userDOBToString);
                database.executeUpdate(statement);
                user.setId(database.lastNewId());
            }
            else {
                PreparedStatement statement = database.newStatement("UPDATE SchoolUser SET Firstname = ?, Lastname = ?, DOB = ? WHERE UserID = ?");
                statement.setString(1, user.getFirstName());
                statement.setString(2, user.getLastName());
                statement.setString(3, userDOBToString);
                statement.setInt(4, userID);
                database.executeUpdate(statement);
            }
        } catch (SQLException resultsException) {
            System.out.println("Database saving error: " + resultsException.getMessage());
        }

    }


}
