package com.example.project_7;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

import static com.example.project_7.Dialogs.*;

public class DELETE extends Application{
    @Override


    public void start(Stage stage) throws Exception {
        VBox root=new VBox(10);
        root.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(root,400,300));
        stage.setTitle("ChoiceDialog Example");
        stage.show();

        Button button=new Button("Click");
        Label label=new Label("result");
        root.getChildren().addAll(button,label);

        button.setOnAction(e->{
            try {
//                WarningAlert("this is information alart","this is heading","this is message");
                ConformationAlert("this is information alart","this is heading","this is message");
//                WarningAlert("this is information alart","this is heading","this is message");
            }catch (Exception exception){
                System.out.println(exception);
            }

        });

    }










//    private void dialogResponse(Stage stage) throws Exception {
//        BorderPane root=new BorderPane();
//        Scene scene=new Scene(root,400,300);
//        stage.setScene(scene);
//        stage.setTitle("Dialog Example");
//        stage.show();
//        Button button=new Button("show dialog");
//        root.setCenter(button);
//        button.setOnAction(e->{
//            Dialog dialog=new Dialog();
//            dialog.setTitle("My Dialog");
//            dialog.setHeaderText("are you a player?");
//            dialog.setContentText("Please select an option.");
//            dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
//            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
//            dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
//            Optional<ButtonType>result=dialog.showAndWait();
//
//            if(result.get()==ButtonType.YES){
//                System.out.println("YES is selected");
//            } else if (result.get()==ButtonType.NO) {
//                System.out.println("NO is selected");
//            } else if(result.get()==ButtonType.CANCEL){
//                System.out.println("CANCEL is selected");
//            } else{
//                System.out.println("NOTHING is selected");
//            }
//        });
//    }
    public static void main(String[] args)
    {
        launch(args);
    }


}
