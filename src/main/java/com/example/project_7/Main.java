package com.example.project_7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.imageio.IIOException;
import java.io.IOException;

public class Main extends Application {
    public static RecentHandler recentHandler = new RecentHandler();
    public static MainController controller;
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene = new Scene(loader.load());
        controller = loader.getController();
        new LIB().setIconAndTitle(stage,CONSTANTS.Applicaiton_icon_path,"ByteBook");
        stage.setScene(scene);
        stage.show();
    }
}
