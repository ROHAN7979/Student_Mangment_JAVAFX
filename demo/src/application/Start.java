package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Optional;

public class Start extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Management App");

        // Create a VBox to hold the UI elements
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        // Load the background image (change "background.jpg" to the path of your image file)
        Image backgroundImage = new Image("file:///C:/Users/rohan/Downloads/javafxx/demo/src/application/SDB-icon.png");

        // Create a BackgroundImage
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );

        // Create a Background with the BackgroundImage
        Background backgroundObject = new Background(background);

        // Apply the Background to the VBox (your container)
        vbox.setBackground(backgroundObject);

        // Create buttons for adding, deleting, and displaying students
        Button addStudentButton = new Button("Add Student");
        Button deleteStudentButton = new Button("Delete Student");
        Button displayStudentButton = new Button("Display Students");

        // Create a TextArea to display student information
        TextArea studentTextArea = new TextArea();
        studentTextArea.setEditable(false);

        // Add event handlers to the buttons
        addStudentButton.setOnAction(e -> {
            // Prompt the user for student information
            Dialog<MyPair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Add Student");
            dialog.setHeaderText("Enter student details:");

            // Set the button types
            ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

            // Create text input fields
            TextField nameField = new TextField();
            TextField phoneField = new TextField();
            TextField cityField = new TextField();

            // Set the content of the dialog
            dialog.getDialogPane().setContent(new VBox(10, new Label("Name:"), nameField, new Label("Phone:"), phoneField, new Label("City:"), cityField));

            // Request focus on the name field by default
            Platform.runLater(nameField::requestFocus);

            // Convert the result to a pair when the add button is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButton) {
                    return new MyPair<>(nameField.getText(), phoneField.getText());
                }
                return null;
            });

            Optional<MyPair<String, String>> result = dialog.showAndWait();

            result.ifPresent(studentData -> {
                // Create a student object and add it to the database
                Student st = new Student();
                st.setStudentName(studentData.getKey());
                st.setStudentPhone(studentData.getValue());
                st.setStudentCity(cityField.getText());

                boolean added = StudentDao.insertStudnetToDB(st);
                if (added) {
                    studentTextArea.appendText("Student added successfully\n");
                } else {
                    studentTextArea.appendText("Error adding student\n");
                }
            });
        });

        deleteStudentButton.setOnAction(e -> {
            // Prompt the user for the student ID to delete
            TextInputDialog deleteDialog = new TextInputDialog();
            deleteDialog.setTitle("Delete Student");
            deleteDialog.setHeaderText("Enter the student ID to delete:");
            deleteDialog.setContentText("Student ID:");

            Optional<String> result = deleteDialog.showAndWait();

            result.ifPresent(studentId -> {
                try {
                    int userId = Integer.parseInt(studentId);
                    boolean deleted = StudentDao.deleteStudent(userId);
                    if (deleted) {
                        studentTextArea.appendText("Student deleted successfully\n");
                    } else {
                        studentTextArea.appendText("Error deleting student\n");
                    }
                } catch (NumberFormatException ex) {
                    studentTextArea.appendText("Invalid student ID format\n");
                }
            });
        });

        displayStudentButton.setOnAction(e -> {
            // Display all students
            studentTextArea.clear();
            boolean displayed = StudentDao.DisplayAll();
            if (!displayed) {
                studentTextArea.appendText("Error displaying students\n");
            }
        });

        // Add UI elements to the VBox
        vbox.getChildren().addAll(addStudentButton, deleteStudentButton, displayStudentButton, studentTextArea);

        // Create the scene and set it on the stage
        Scene scene = new Scene(vbox, 450, 450); // Adjust the dimensions as needed
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }
}
