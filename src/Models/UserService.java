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


    public static void deleteUser(User user, DatabaseConnection databaseConnection){
        ArrayList<AssignmentsView> assignmentsViews = new ArrayList<>();
        AssignmentService.selectAll(user, assignmentsViews, databaseConnection);

        PreparedStatement statement = databaseConnection.newStatement("DELETE FROM SchoolPlanner WHERE UserID = ? AND AssignmentID = ?");
        PreparedStatement statement2 = databaseConnection.newStatement("DELETE FROM Assignments WHERE AssignmentID = ?");
        PreparedStatement statement3 = databaseConnection.newStatement("DELETE FROM Classroom WHERE Class = ?");
        PreparedStatement statement4 = databaseConnection.newStatement("DELETE FROM Description WHERE DescriptionID = ?");
        PreparedStatement statement5 = databaseConnection.newStatement("DELETE FROM SchoolUser WHERE UserID = ?");
        PreparedStatement statement6 = databaseConnection.newStatement("DELETE From Notes WHERE UserID = ?");


        try{
            if (statement != null && statement2 != null && statement3 != null && statement4 != null && statement6 != null) {
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

                statement6.setInt(1, user.getId());
                databaseConnection.executeUpdate(statement6);
            }

        } catch (SQLException resultsException) {
            System.out.println("Database select all error: " + resultsException.getMessage());
        }
    }


    public static void save(User user, DatabaseConnection database) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String userDOBToString = user.getDOB().format(formatter);


        try {
                PreparedStatement statement = database.newStatement("INSERT INTO SchoolUser (Firstname, Lastname, DOB) VALUES (?,?,?)");
                statement.setString(1, user.getFirstName());
                statement.setString(2, user.getLastName());
                statement.setString(3, userDOBToString);
                database.executeUpdate(statement);
                user.setId(database.lastNewId());

        } catch (SQLException resultsException) {
            System.out.println("Database saving error: " + resultsException.getMessage());
        }

    }


}
