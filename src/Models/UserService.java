package Models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserService {

    public static void selectAll(List<UserView> targetList, DatabaseConnection database) {



        PreparedStatement statement1 = database.newStatement("SELECT UserID, Firstname, Lastname, DOB FROM SchoolUser ORDER BY UserID");

        try {
            if (statement1 != null) {

                ResultSet results1 = database.runQuery(statement1);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                if (results1 != null) {
                    while (results1.next()) {

                        LocalDate dob = LocalDate.parse(results1.getString("DOB"), formatter);
                        targetList.add(new UserView(results1.getInt("UserID"), results1.getString("Firstname"), results1.getString("Lastname"), dob));
                    }
                }
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
