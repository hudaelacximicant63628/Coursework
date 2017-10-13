import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NotesStage extends Application{


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Notes");
        stage.show();

        VBox notesStageVBox = new VBox();

        HBox notesStageHBox = new HBox();
        notesStageHBox.setSpacing(10);
        notesStageHBox.setPadding(new Insets(10, 10, 10, 10));

        Button addNote = new Button("Add note");
        Button deleteNote = new Button("Delete note");

        TextField note = new TextField();
        note.setPrefWidth(800);
        notesStageHBox.getChildren().addAll(addNote, deleteNote, note);


        TableView notesTableView = new TableView<>();

        TableColumn<String, String> notesColumn = new TableColumn<>("Notes");



        notesTableView.getColumns().add(notesColumn);
        notesTableView.setPrefHeight(500);


        note.setPromptText("Enter note");
        notesStageVBox.getChildren().addAll(notesTableView, notesStageHBox);



        deleteNote.setOnAction(e -> System.out.println("The delete button has not been initialised yet"));

        addNote.setOnAction(e -> System.out.println("The add button has not been initialised"));



        stage.setScene(new Scene(notesStageVBox));

    }

    public static void main(String[] args) {
        launch(args);
    }

}
