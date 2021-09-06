package DeleteDirectoryForVitya;

import com.sun.javafx.scene.control.behavior.TwoLevelFocusBehavior;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SeachSettings extends Application{
    static String directory;
    static String directorydelete;
    public static void main(String[] args) throws IOException {
        Application.launch();

        FileVisitor fileVisitor = new FileVisitor();
        fileVisitor.setDirectory(directory);

        Files.walkFileTree(Paths.get(directory), fileVisitor);
    }


    @Override
    public void start(Stage stage) throws Exception {
        // установка надписи

        javafx.scene.control.Button button = new javafx.scene.control.Button();
        javafx.scene.control.Button buttonclose = new Button();

        button.setText("Выбрать путь к дирректории");
        button.setLayoutX(140);
        button.setLayoutY(140);
        button.setPrefSize(200,35);

        Text textField = new Text();
        textField.setText("Выбранная директоррия:");
        textField.setLayoutX(170);
        textField.setLayoutY(70);

        buttonclose.setText("Delete");
        buttonclose.setLayoutY(220);
        buttonclose.setLayoutX(390);
        buttonclose.setPrefSize(70,20);

        Text text = new Text();
        text.setText("");
        text.setLayoutX(210);
        text.setLayoutY(110);

        Pane root = new Pane();
        root.getChildren().addAll(button,text, textField, buttonclose);

        button.setOnAction(actionEvent -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File file = directoryChooser.showDialog(stage);
            directorydelete = file.toString();
            textField.setText("Выбранная директоррия: ");
            text.setText(directorydelete);
        });

        buttonclose.setOnAction(actionEvent -> {
            directory = directorydelete;
            stage.close();
        });

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Delete UID!");
        stage.setWidth(500);
        stage.setHeight(300);
        stage.show();
    }
}
