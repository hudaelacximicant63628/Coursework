package Models;

import Controllers.MainControllers;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.time.LocalDate;
import java.util.Optional;


    public class AssignmentsStage extends Application {
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

        private MainControllers mainControllers;
        private static AssignmentsView enteredData;
        private static ObservableList<AssignmentsView> tableViewData = FXCollections.observableArrayList();

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

            Image icon = new Image(getClass().getResourceAsStream("/Resources/school_planner_icon.png"));
            stage.getIcons().add(icon);

            mainControllers = new MainControllers();

            //Button--------------------------------------------------------------------------------------------------

            add.setOnAction(e -> {
                        writeEnteredData();
                        mainControllers.addAssignment();
                    });
            remove.setOnAction(e -> System.out.println("Remove features has not been implemented"));
            modify.setOnAction(e -> System.out.println("Modifying feature has not been implemented"));
            userSceneButton.setOnAction(e -> stage.setScene(userScene));
            AssignmentSceneButton.setOnAction(e -> stage.setScene(assignmentScene));

            tableView.setMinHeight(470);
            //---------------------------------------------------------------------------------------------------------------------
            //TABLE COLUMNS

            //has equal spacing for each column

            tableView.setPrefSize(400, 300);
            tableView.setItems(tableViewData);

            TableColumn titleColumn = new TableColumn<>("Title");
            titleColumn.setMinWidth(200);
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            tableView.getColumns().add(titleColumn);

            TableColumn classColumn = new TableColumn<>("Class");
            classColumn.setCellValueFactory(new PropertyValueFactory<>("classroom"));
            tableView.getColumns().add(classColumn);

            TableColumn formatColumn = new TableColumn<>("Format");
            formatColumn.setCellValueFactory(new PropertyValueFactory<>("format"));
            tableView.getColumns().add(formatColumn);

            TableColumn deadlineColumn = new TableColumn<>("Deadline");
            deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
            tableView.getColumns().add(deadlineColumn);

            TableColumn quantityColumn = new TableColumn<>("Quantity");
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            tableView.getColumns().add(quantityColumn);

            TableColumn descriptionColumn = new TableColumn<>("Description");
            descriptionColumn.setMinWidth(500);
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            tableView.getColumns().add(descriptionColumn);




            //---------------------------------------------------------------------------------------------------------------------

            mainControllers.updateAssignmentsViewTableView();
            closeConfirmation(stage);
            stage.setScene(assignmentScene);
            stage.show();

        }

        public void writeEnteredData(){
                String classroom = this.className.getText();
                String description = this.description.getText();
                String title = this.title.getText();
                String teacher = this.teacherName.getText();
                int quantity = Integer.parseInt(this.quantity.getText());
                String format = this.format.getText();
                LocalDate deadline = this.deadline.getValue();

                enteredData = new AssignmentsView(teacher, classroom,description,title,quantity,format,deadline);

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

        public DatePicker getDOB() {
            return DOB;
        }

        public TextField getUserName() {
            return userName;
        }

        public TextField getLastName() {
            return lastName;
        }

        public DatePicker getDeadline() {
            return deadline;
        }

        public TextField getTeacherName() {
            return teacherName;
        }

        public TextField getClassName() {
            return className;
        }

        public TextField getDescription() {
            return description;
        }

        public TextField getTitle() {
            return title;
        }

        public TextField getQuantity() {
            return quantity;
        }

        public TextField getFormat() {
            return format;
        }

        public static AssignmentsView getUserEnteredData(){
            AssignmentsView enteredDataCopy = enteredData;
            return enteredDataCopy;
        }
        public static ObservableList getTableViewData(){
            return tableViewData;
        }




    }
