package sample;

import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class Main extends Application {


    Button buttonG = new Button("Press me and feel free");

    @Override
    public void start(Stage primaryStage) throws Exception{

        final File[] currentFile = {null};
        final MediaPlayer[] player = {null};

        HBox hBox = new HBox();
        Button buttonA = new Button("Choose");
        Button buttonB = new Button("Make a bada-boom");
        Button buttonC = new Button("Way out");
        hBox.getChildren().addAll(buttonA, buttonB, buttonC);

        VBox vBox = new VBox();
        Button buttonD = new Button("Play it");
        Button buttonE = new Button("Stop it");
        Button buttonF = new Button("Pause it");
        vBox.getChildren().addAll(buttonD, buttonF, buttonE);

        BorderPane borderPane = new BorderPane();

        Label label = new Label("Just nothing");
        Label label1 = new Label("Another one");
        borderPane.setCenter(buttonG);
        borderPane.setBottom(label);



        BorderPane borderPaneMain = new BorderPane();
        borderPaneMain.setTop(hBox);
        borderPaneMain.setRight(vBox);
        borderPaneMain.setCenter(borderPane);

        primaryStage.setScene(new Scene(borderPaneMain, 300, 250));
        primaryStage.show();

        buttonA.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            currentFile[0] = fileChooser.showOpenDialog(primaryStage);
            player[0] = new MediaPlayer(new Media("file://" + currentFile[0].getAbsolutePath()));
            label.setText(currentFile[0].getName());
            label1.setText(Paths.get(currentFile[0].toURI()).toString());
        });
        buttonD.setOnAction(e -> {
            player[0].play();
        });
        buttonE.setOnAction(e -> {
            player[0].stop();
        });
        buttonF.setOnAction(e -> {
            player[0].pause();
        });
        buttonB.setOnAction(e -> {
            Image image = new Image("file:///home/andrew/Pictures/Nuclear_Boomjpg");
            MediaPlayer boomPlayer = new MediaPlayer(new Media("file:///home/andrew/Music/Ordinary_boom.mp3"));
            boomPlayer.play();
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(300);
            imageView.setFitWidth(300);
            borderPane.setRight(imageView);
        });
        buttonC.setOnAction(e -> {
            primaryStage.close();
        });
        buttonG.setOnAction(e -> {

            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    int timeToGo = 60*1000;
                    final int step = 1000;
                    while (timeToGo > 0){
                        showTime(timeToGo/1000);
                        try {
                            Thread.sleep(step);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        timeToGo = timeToGo - step;
                    }
                    player[0].play();
                    return null;
                }
            };
//            Service<Integer> service = new Service<Integer>() {
//                @Override
//                protected Task<Integer> createTask() {
//
//                    return task;
//                }
//            };


            Thread th = new Thread(task);
            th.start();
        });}

        public void showTime(int time){
        buttonG.setText(String.valueOf(time));
    }






    public static void main(String[] args) {
        launch(args);
    }
}
