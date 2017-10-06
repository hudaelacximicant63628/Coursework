import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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
            assignmentSceneHBox.getChildren().addAll(add, remove, modify, userSceneButton);

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

            add.setOnAction(e -> System.out.println("Adding..."));
            remove.setOnAction(e -> System.out.println("Removing..."));
            modify.setOnAction(e -> System.out.println("Modifying..."));
            userSceneButton.setOnAction(e -> stage.setScene(userScene));
            AssignmentSceneButton.setOnAction(e -> stage.setScene(assignmentScene));

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


}
