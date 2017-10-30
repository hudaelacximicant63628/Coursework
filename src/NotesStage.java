import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class NotesStage extends Application{

    private ListView listView;
    private Button user;
    private Button notes;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Notes");
        Image icon = new Image(getClass().getResourceAsStream("school_planner_icon.png"));
        stage.getIcons().add(icon);
        stage.setMinHeight(580);
        stage.setMinWidth(1000);


        notes = new Button("Notes");
        listView = new ListView();
        VBox userSceneVBox = new VBox();
        userSceneVBox.getChildren().addAll(listView, notes);
        user = new Button("User");



        Scene userScene = new Scene(userSceneVBox, 300, 150);

        VBox notesStageVBox = new VBox();

        HBox notesStageHBox = new HBox();
        notesStageHBox.setSpacing(10);
        notesStageHBox.setPadding(new Insets(10, 10, 10, 10));

        Button addNote = new Button("Add note");
        Button deleteNote = new Button("Delete note");

        TextField note = new TextField();
        note.setPrefWidth(800);
        notesStageHBox.getChildren().addAll(addNote, deleteNote,user,  note);

        ObservableList<String> notesData = FXCollections.observableArrayList();


        TableView notesTableView = new TableView<>();

        TableColumn<String, String> notesColumn = new TableColumn<>("Notes");
        notesTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        notesColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));


        notesTableView.getColumns().add(notesColumn);
        notesTableView.setPrefHeight(500);


        note.setPromptText("Enter note");
        notesStageVBox.getChildren().addAll(notesTableView, notesStageHBox);

        ObservableList selectedItems = notesTableView.getSelectionModel().getSelectedItems();
        Scene assignmentScene = new Scene(notesStageVBox);

        deleteNote.setOnAction(e -> notesData.remove(selectedItems.get(0)));

        addNote.setOnAction(e -> notesData.add(note.getText()));
        notes.setOnAction(e -> stage.setScene(assignmentScene));
        user.setOnAction(e -> stage.setScene(userScene));



        notesTableView.setItems(notesData);

        stage.setScene(assignmentScene);


        closeConfirmation(stage);
        stage.show();

    }


    private void closeConfirmation(Stage stage){
        stage.setOnCloseRequest((WindowEvent we) ->
        {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Confirmation");
            a.setHeaderText("Do you really want to leave?");
            Optional<ButtonType> closeResponse = a.showAndWait();
            if (closeResponse.get() == ButtonType.OK){
                System.exit(0);
            }else{
                we.consume();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
