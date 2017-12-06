package Models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NotesService {

    public static void selectAll(User user, List<Notes> targetList, DatabaseConnection database) {

        PreparedStatement statement1 = database.newStatement(String.format("SELECT NotesID, UserID, Note FROM Notes WHERE UserID = %2d", user.getId()));
        try {
            if (statement1 != null) {

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

    public static Notes selectById(int id, DatabaseConnection database) {

        PreparedStatement statement = null;

        statement = database.newStatement("SELECT NotesID, UserID, Note WHERE NotesID = ?");

        Notes result = null;

        try {
            if (statement != null) {
                statement.setInt(1, id);
                ResultSet results = database.runQuery(statement);

                try {
                    if (results != null) {
                        result = new Notes(results.getInt("NotesID"), results.getInt("UserID"), results.getString("Note"));
                    }
                }catch (Exception e){

                }
            }
        } catch (SQLException resultsException) {
            System.out.println("Database select by id error: " + resultsException.getMessage());
        }

        return result;
    }



    public static void save(Notes notes, User user, DatabaseConnection database) {

        Notes existingItem = null;

        int notesID = notes.getNotesID();

        if(notesID != 0) existingItem = selectById(notesID, database);

        try {
            if (existingItem == null) {
                PreparedStatement statement = database.newStatement("INSERT INTO Notes (Note, UserID) VALUES (?,?)");
                statement.setString(1, notes.getNotes());
                statement.setInt(2, user.getId());
                database.executeUpdate(statement);
                notes.setNotesID(database.lastNewId());
            }
            else {
                PreparedStatement statement = database.newStatement("UPDATE Notes SET Note = ? WHERE NotesID = ?");
                statement.setString(1, notes.getNotes());
                statement.setInt(3, notesID);
                database.executeUpdate(statement);
            }
        } catch (SQLException resultsException) {
            System.out.println("Database saving error: " + resultsException.getMessage());
        }

    }



}
