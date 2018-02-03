package Models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NotesService {

    public static void selectAll(User user, List<Notes> targetList, DatabaseConnection database) {

        PreparedStatement statement1 = database.newStatement("SELECT NotesID, UserID, Note FROM Notes WHERE UserID = ?");
        try {
            if (statement1 != null) {

                statement1.setInt(1, user.getId());

                ResultSet results1 = database.runQuery(statement1);

                if (results1 != null) {
                    while (results1.next()) {

                        targetList.add(new Notes(results1.getInt("NotesID"), results1.getInt("UserID"), results1.getString("Note")));
                    }
                }
            }

        } catch (SQLException resultsException) {
            System.out.println("Database select all error: " + resultsException.getMessage());
        }
    }

    public static void save(Notes notes, User user, DatabaseConnection database) {
        try {
                PreparedStatement statement = database.newStatement("INSERT INTO Notes (Note, UserID) VALUES (?,?)");
                statement.setString(1, notes.getNotes());
                statement.setInt(2, user.getId());
                database.executeUpdate(statement);
        } catch (SQLException resultsException) {
            System.out.println("Database saving error: " + resultsException.getMessage());
        }

    }
    public static void delete(Notes notes, DatabaseConnection database){
        PreparedStatement statement = database.newStatement("DELETE FROM Notes WHERE NotesID = ?");
        try{
            if (statement != null) {
                statement.setInt(1, notes.getNotesID());
                database.executeUpdate(statement);
            }
        } catch (SQLException resultsException) {
            System.out.println("Database select all error: " + resultsException.getMessage());
        }
    }



}
