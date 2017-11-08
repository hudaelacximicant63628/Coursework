package Models;

import java.io.Console;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClassroomService {

    public static void selectAll(List<Classroom> targetList, DatabaseConnection database) {

        PreparedStatement statement = database.newStatement("SELECT Class, Teacher FROM Classroom ORDER BY Class");

        try {
            if (statement != null) {

                ResultSet results = database.runQuery(statement);

                if (results != null) {
                    while (results.next()) {
                        targetList.add(new Classroom(results.getString("Class"), results.getString("Teacher")));
                    }
                }
            }
        } catch (SQLException resultsException) {
            System.out.println("Database select all error: " + resultsException.getMessage());
        }
    }
    public static Classroom selectById(String Class, DatabaseConnection database) {

        Classroom result = null;

        PreparedStatement statement = database.newStatement("SELECT Class, Teacher FROM Classroom WHERE Class = ?");

        try {
            if (statement != null) {

                statement.setString(1, Class);
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
    public static void save(Classroom itemToSave, DatabaseConnection database) {

        Classroom existingItem = null;
        if (itemToSave.getClassroom() != "") existingItem = selectById(itemToSave.getClassroom(), database);

        try {
            if (existingItem == null) {
                PreparedStatement statement = database.newStatement("INSERT INTO Classroom (a, b) VALUES (?, ?)");
                statement.setString(1, itemToSave.getClassroom());
                statement.setString(2, itemToSave.getTeacher());
                database.executeUpdate(statement);
            }
            else {
                PreparedStatement statement = database.newStatement("UPDATE Classroom SET b = ? WHERE id = ?");
                statement.setString(1, itemToSave.getTeacher());
                statement.setString(3, itemToSave.getClassroom());
                database.executeUpdate(statement);
            }
        } catch (SQLException resultsException) {
            System.out.println("Database saving error: " + resultsException.getMessage());
        }
    }

}
