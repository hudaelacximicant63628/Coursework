package Models;

import Controllers.MainControllers;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class NotesStage{

    private ListView<User> listView;
    public static ObservableList<Notes> notesData;
    private Button user;
    private Button notes;
    private Button use;
    private TextField note;
    public static User userData;

    public NotesStage() throws Exception {
        Stage stage = new Stage();
        start(stage);
    }

    public void start(Stage notesStage) throws Exception {
        MainControllers mainControllers = new MainControllers();
        notesStage.setTitle("Notes");
        Image icon = new Image(getClass().getResourceAsStream("/Resources/school_planner_icon.png"));
        notesStage.getIcons().add(icon);
        notesStage.setMaxHeight(580);
        notesStage.setMaxWidth(1200);

        notesData = FXCollections.observableArrayList();


        notes = new Button("Notes");
        use = new Button("Use");
        listView = new ListView();
        VBox userSceneVBox = new VBox();
        HBox userSceneHBox = new HBox();
        userSceneHBox.getChildren().addAll(notes,use);
        userSceneVBox.getChildren().addAll(listView, userSceneHBox);
        user = new Button("User");

        ObservableList<User> userList = FXCollections.observableArrayList();
        UserService.selectAll(userList, mainControllers.databaseConnection);

        listView.setItems(userList);



        Scene userScene = new Scene(userSceneVBox, 500, 150);

        VBox notesStageVBox = new VBox();

        HBox notesStageHBox = new HBox();
        notesStageHBox.setSpacing(10);
        notesStageHBox.setPadding(new Insets(10, 10, 10, 10));

        Button addNote = new Button("Add note");
        Button deleteNote = new Button("Delete note");

        note = new TextField();
        note.setPrefWidth(800);
        notesStageHBox.getChildren().addAll(addNote, deleteNote,user,  note);


        TableView<Notes> notesTableView = new TableView<>();
        TableColumn notesColumn = new TableColumn<>("Notes");
        notesTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));

        notesTableView.getColumns().add(notesColumn);
        notesTableView.setPrefHeight(500);


        note.setPromptText("Enter note");
        notesStageVBox.getChildren().addAll(notesTableView, notesStageHBox);

        Scene assignmentScene = new Scene(notesStageVBox);

        deleteNote.setOnAction(e -> {
            if(notesTableView.getSelectionModel().getSelectedItem() != null) {
                NotesService.delete(notesTableView.getSelectionModel().getSelectedItem(), mainControllers.databaseConnection);
                mainControllers.updateNotesTableView();
                note.clear();
            }
        });
        addNote.setOnAction(e -> {
            if(!(note.getText().isEmpty()) && userData != null) {
                NotesService.save(new Notes(0, userData.getId(), note.getText()), userData, mainControllers.databaseConnection);
                mainControllers.updateNotesTableView();
                note.clear();

            }else{
                AssignmentsStage.errorReporter.setText("Select a user for the notes");
            }

        });
        use.setOnAction(e -> {  
            userData = listView.getSelectionModel().getSelectedItem();
            System.out.println(userData);
            if(userData != null) {
                mainControllers.updateNotesTableView();
            }        });

        notes.setOnAction(e -> notesStage.setScene(assignmentScene));
        user.setOnAction(e -> notesStage.setScene(userScene));

        notesTableView.setItems(notesData);

        notesStage.setScene(assignmentScene);

        mainControllers.closeConfirmation(notesStage);
        notesStage.show();

    }



//    public static void main(String[] args) {
//        launch(args);
//    }

}
