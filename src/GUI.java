import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.util.Optional;


    public class GUI extends Application {
        private Button add;
        private Button remove;
        private Button modify;
        private Button userSceneButton;
        private Button AssignmentSceneButton;
        private Button notesButton;

        private DatePicker deadline;
        private TextField teacherName;
        private TextField className;
        private TextField description;
        private TextField title;
        private TextField quantity;
        private TextField format;

        private DatePicker DOB;
        private TextField userName;
        private TextField lastName;

        private static Pane notesStage;
        private TextArea notesTextArea;


        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage stage) {
            TableView tableView = new TableView();

            add = new Button("Add");
            remove = new Button("Remove");
            modify = new Button("Modify");
            userSceneButton = new Button("User");
            AssignmentSceneButton = new Button("Assignments");
            notesButton = new Button("Notes");

            DOB = new DatePicker();
            DOB.setPromptText("Enter DOB");
            userName = new TextField();
            userName.setPromptText("Enter first name");
            lastName = new TextField();
            lastName.setPromptText("Enter last name");

            title = new TextField();
            title.setPromptText("Enter title");
            description = new TextField();
            description.setPromptText("Enter description");
            className = new TextField();
            className.setPromptText("Enter class name");
            teacherName = new TextField();
            teacherName.setPromptText("Enter teacher name");
            deadline = new DatePicker();
            deadline.setPromptText("Enter deadline");
            quantity = new TextField();
            quantity.setPromptText("Enter quantity");
            format = new TextField();
            format.setPromptText("Enter format");


            HBox assignmentSceneHBox = new HBox();
            assignmentSceneHBox.getChildren().addAll(add, remove, modify, userSceneButton, notesButton);

            VBox assignmentSceneVBox = new VBox();
            assignmentSceneVBox.getChildren().addAll(tableView, title, description, className, teacherName, deadline, quantity, format, assignmentSceneHBox);
            assignmentSceneVBox.setSpacing(2);

            VBox userSceneVBOX = new VBox();
            userSceneVBOX.getChildren().addAll(userName,lastName, DOB, AssignmentSceneButton);
            userSceneVBOX.setSpacing(10);

            userSceneButton.setLineSpacing(10);

            assignmentSceneHBox.setPadding(new Insets(10,20,20,20));
            assignmentSceneHBox.setSpacing(10);

            Scene assignmentScene = new Scene(assignmentSceneVBox, 1000, 700);
            Scene userScene = new Scene(userSceneVBOX, 300, 150);
            stage.setTitle("School planner");

            add.setOnAction(e -> System.out.println("Add feature has not been implemented"));
            remove.setOnAction(e -> System.out.println("Remove features has not been implemented"));
            modify.setOnAction(e -> System.out.println("Modifying feature has not been implemented"));
            userSceneButton.setOnAction(e -> stage.setScene(userScene));
            AssignmentSceneButton.setOnAction(e -> stage.setScene(assignmentScene));
            notesButton.setOnAction(e -> openNotesStage());

            tableView.setMinHeight(470);


            closeConfirmation(stage);
            stage.setScene(assignmentScene);
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

        public void openNotesStage() {
            Stage stage = new Stage();
            stage.setTitle("Notes");
            stage.show();

            VBox notesStageVBox = new VBox();
            TableView notesTableView = new TableView<>();


            ObservableList<String> notesData = FXCollections.observableArrayList();
            TableColumn notesColumn = new TableColumn<>("Notes");

            notesTableView.setEditable(false);
            notesTableView.setPrefHeight(500);


            HBox notesStageHBox = new HBox();
            notesStageHBox.setSpacing(10);
            notesStageHBox.setPadding(new Insets(10, 10,10,10));



            Button addNote = new Button("Add note");
            Button deleteNote = new Button("Delete note");

            TextField note = new TextField();
            note.setPrefWidth(800);
            notesStageHBox.getChildren().addAll(addNote, deleteNote, note);


            notesTableView.getColumns().add(notesColumn);

            addNote.setOnAction(e -> notesData.add(note.getText()));

            notesTableView.setItems(notesData);



            note.setPromptText("Enter note");
            notesStageVBox.getChildren().addAll(notesTableView, notesStageHBox);


            stage.setScene(new Scene(notesStageVBox));




        }

    }
