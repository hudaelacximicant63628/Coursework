package Models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssignmentsViewService {

    public static void selectAll(List<AssignmentsView> targetList, DatabaseConnection database) {

        PreparedStatement statement1 = database.newStatement("SELECT Quantity, Format, Title, Description FROM Description ORDER BY DescriptionID");
        PreparedStatement statement2 = database.newStatement("SELECT Class FROM Classroom ORDER BY Class");
        PreparedStatement statement3 = database.newStatement("SELECT Deadline FROM Assignments ORDER BY AssignmentID");

        try {
            if (statement1 != null && statement2 != null && statement3 != null) {

                ResultSet results = database.runQuery(statement1);
                ResultSet results2 = database.runQuery(statement2);
                ResultSet results3 = database.runQuery(statement3);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");





                if (results != null && results2 != null && results3 != null) {
                    while (results.next()) {
                        LocalDate deadline = LocalDate.parse(results3.getString("Deadline"), formatter);
                        targetList.add(new AssignmentsView(results2.getString("Class"), results.getString("Description"), results.getString("Title"), results.getInt("Quantity"), results.getString("Format"), deadline));
                    }
                }
                }

        } catch (SQLException resultsException) {
            System.out.println("Database select all error: " + resultsException.getMessage());
        }
    }

}
