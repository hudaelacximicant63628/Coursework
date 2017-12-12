package Models;

import Controllers.MainControllers;
import javafx.application.Application;
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

public class NotesStage extends Application{

    private ListView<User> listView;
    public static ObservableList<Notes> notesData;
    private Button user;
    private Button notes;
    private Button use;
    private TextField note;
    public static User userData;

    @Override
    public void start(Stage stage) throws Exception {
        MainControllers mainControllers = new MainControllers();
        stage.setTitle("Notes");
        Image icon = new Image(getClass().getResourceAsStream("/Resources/school_planner_icon.png"));
        stage.getIcons().add(icon);
        stage.setMaxHeight(580);
        stage.setMaxWidth(1200);

        notesData = FXCollections.observableArrayList();


        notes = new Button("Notes");
        use = new Button("Use");
        listView = new ListView();
        VBox userSceneVBox = new VBox();
        HBox userSceneHBox = new HBox();
        userSceneHBox.getChildren().addAll(notes,use);
        userSceneVBox.getChildren().addAll(listView, userSceneHBox);
        user = new Button("UserView");

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

        ObservableList selectedItems = notesTableView.getSelectionModel().getSelectedItems();
        Scene assignmentScene = new Scene(notesStageVBox);

        deleteNote.setOnAction(e -> {
            if(notesTableView.getSelectionModel().getSelectedItem() != null) {
                NotesService.delete(notesTableView.getSelectionModel().getSelectedItem(), mainControllers.databaseConnection);
                mainControllers.updateNotesTableView();
                note.clear();
            }
        });
        addNote.setOnAction(e -> {
            if(!(note.getText().isEmpty())) {
                NotesService.save(new Notes(0, userData.getId(), note.getText()), userData, mainControllers.databaseConnection);
                mainControllers.updateNotesTableView();
                note.clear();

            }

        });
        use.setOnAction(e -> {
            userData = listView.getSelectionModel().getSelectedItem();
            if(userData != null) {
                mainControllers.updateNotesTableView();
            }        });

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
