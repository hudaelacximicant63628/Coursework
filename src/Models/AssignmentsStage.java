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
import jdk.nashorn.internal.ir.Assignment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;


    public class AssignmentsStage extends Application {
        private Button add;
        private Button addUser;
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
        private TextArea errorReporter;
        private ListView userListView;

        private DatePicker DOB;
        private TextField userName;
        private TextField lastName;

        private MainControllers mainControllers;
        private static AssignmentsView enteredData;
        private static ObservableList<AssignmentsView> tableViewData = FXCollections.observableArrayList();
        public static UserView userViewInfo;

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage stage) {
            TableView<AssignmentsView> tableView = new TableView();

            add = new Button("Add");
            addUser = new Button("Add user");

            remove = new Button("Remove");
            modify = new Button("Modify");
            userSceneButton = new Button("UserView");
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
            errorReporter = new TextArea();
            errorReporter.setMaxHeight(100);
            errorReporter.setEditable(false);
            errorReporter.setStyle("-fx-font-size: 20");
            userListView = new ListView();


            HBox userSceneHBox = new HBox();

            HBox assignmentSceneHBox = new HBox();
            assignmentSceneHBox.getChildren().addAll(add, remove, modify, userSceneButton);

            VBox assignmentSceneVBox = new VBox();
            assignmentSceneVBox.getChildren().addAll(tableView, title, description, className, teacherName, deadline, quantity, format, assignmentSceneHBox, errorReporter);
            assignmentSceneVBox.setSpacing(2);

            VBox userSceneVBOX = new VBox();
            userSceneVBOX.getChildren().addAll(userName,lastName, DOB, AssignmentSceneButton, addUser);
            userSceneHBox.getChildren().addAll(userSceneVBOX, userListView);
            userSceneVBOX.setSpacing(10);
            userSceneHBox.setSpacing(10);

            userSceneButton.setLineSpacing(10);

            assignmentSceneHBox.setPadding(new Insets(10,20,20,20));
            assignmentSceneHBox.setSpacing(10);

            Scene assignmentScene = new Scene(assignmentSceneVBox, 1000, 830);
            Scene userScene = new Scene(userSceneHBox, 300, 180);
            stage.setTitle("School planner");

            Image icon = new Image(getClass().getResourceAsStream("/Resources/school_planner_icon.png"));
            stage.getIcons().add(icon);

            mainControllers = new MainControllers();

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

//            can be used to get ID selected from tableview so that data can be easily removed/updated
//            tableView.getSelectionModel().getSelectedItem().getAssignmentID();
//            tableView.getSelectionModel().getSelectedItem().getDescriptionID();
//

            //Button--------------------------------------------------------------------------------------------------

            add.setOnAction(e -> {
                getUserInfo();
                writeEnteredData();
                mainControllers.addAssignment();
            });
            remove.setOnAction(e -> System.out.println("Remove feature has not been implemented"));
            modify.setOnAction(e -> System.out.println("Modifying feature has not been implemented"));
            userSceneButton.setOnAction(e -> stage.setScene(userScene));
            AssignmentSceneButton.setOnAction(e -> stage.setScene(assignmentScene));

            tableView.setMinHeight(470);
            //---------------------------------------------------------------------------------------------------------------------


            //---------------------------------------------------------------------------------------------------------------------

            mainControllers.updateAssignmentsViewTableView();
            System.out.println(tableViewData.size());
            closeConfirmation(stage);
            stage.setScene(assignmentScene);
            stage.show();

        }

        public void getUserInfo() {
            String firstName = userName.getText();
            String lastName = this.lastName.getText();
            LocalDate DOB = this.DOB.getValue();

            if (firstName == null || lastName == null || DOB == null) {
                errorReporter.setText("User info has not been filled in correctly" + "\n");
                userViewInfo = null;
            } else {
                userViewInfo = new UserView(0,firstName, lastName, DOB);
            }
        }

        public void writeEnteredData(){
            try {
                String classroom = this.className.getText();
                String description = this.description.getText();
                String title = this.title.getText();
                String teacher = this.teacherName.getText();
                int quantity = Integer.parseInt(this.quantity.getText());
                String format = this.format.getText();
                LocalDate deadline = this.deadline.getValue();

                enteredData = new AssignmentsView(teacher, classroom, description, title, quantity, format, deadline);
            }catch(NumberFormatException e){
                    errorReporter.appendText("Options have not been filled properly");
                }
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
