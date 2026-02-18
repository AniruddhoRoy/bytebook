package com.example.project_7;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class DELETE extends Application{
    @Override


    public void start(Stage stage) throws Exception {
        VBox root=new VBox(10);
        root.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(root,400,300));
        stage.setTitle("ChoiceDialog Example");
        stage.show();

        Button button=new Button("show days");
        Label label=new Label("result");
        root.getChildren().addAll(button,label);

        button.setOnAction(e->{
            String[] days={"m","t"};
            String defaultValue=days[0];
            ChoiceDialog<String>dialog=new ChoiceDialog<>(defaultValue,days);
            dialog.setTitle("Days selection");
            dialog.setHeaderText("select a day");
            Optional<String>result=dialog.showAndWait();
            result.ifPresent((item)->{
                label.setText(item);
            });
        });

    }

    private void dialog(Stage stage) throws Exception {
        BorderPane root=new BorderPane();
        Scene scene=new Scene(root,400,300);
        stage.setScene(scene);
        stage.setTitle("Dialog example");
        stage.show();
        Button button=new Button("show dialog");
        root.setCenter(button);
        button.setOnAction(e->{
            Dialog dialog=new Dialog();
            dialog.setTitle("Test Dialog");
            dialog.setHeaderText("testing");
            dialog.setContentText("hello,this is my test dialog");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES,ButtonType.NO,ButtonType.CANCEL);
            dialog.show();
        });
    }

    private void textInput(Stage stage) throws Exception {
        VBox root=new VBox(10);
        root.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(root,400,400));
        stage.setTitle("TextInputDialog Example");
        stage.show();

        Button b1=new Button("Take user input");
        Label label=new Label("Name will appear here");
        b1.setOnAction(e->{
            TextInputDialog dialog=new TextInputDialog();
            dialog.setTitle("Custom Dialog");
            dialog.setHeaderText("Enter your name");
            dialog.setContentText("name: ");
            Optional<String>result=dialog.showAndWait();
            result.ifPresent((name)->{
                label.setText(name);
            });
        });
        root.getChildren().addAll(b1,label);
    }


    private void alertDialog(Stage stage) throws Exception {
        HBox root=new HBox(20);
        root.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(root,400,400));
        stage.show();

        Button b1=new Button("Simple");
        Button b2=new Button("Info");
        Button b3=new Button("Warning");
        Button b4=new Button("Error");
        Button b5=new Button("Confirmation");

        root.getChildren().addAll(b1,b2,b3,b4,b5);

        b1.setOnAction(e->{showInformationAlert();});
        b2.setOnAction(e->{showWarningAlert();});
        b3.setOnAction(e->{showErrorAlert();});
        b4.setOnAction(e->{showConformationAlert();});
        b5.setOnAction(e->{showSimpleAlert();});

        //  showInformationAlert();
        //  showWarningAlert();
        //  showErrorAlert();
        // showConformationAlert();
        //  showSimpleAlert();
    }

    private void showInformationAlert()
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Test connection");
        alert.setHeaderText("connection");
        alert.setContentText("My message");
        alert.show();
    }

    private void showWarningAlert()
    {
        Alert alert=new Alert(Alert.AlertType.WARNING);
        alert.setTitle("warning alert");
        alert.setHeaderText("battery alert");
        alert.setContentText("battery charge is low");
        alert.show();
    }

    private void showErrorAlert()
    {
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("error alert");
        alert.setHeaderText("cannot add the user");
        alert.setContentText("user already exists");
        alert.show();
    }

    private void showConformationAlert()
    {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deletion");
        alert.setHeaderText("Delete File");
        alert.setContentText("Are you sure you want to delete this file ");
        Optional<ButtonType>result=alert.showAndWait();
        if(result.get()==ButtonType.OK)
        {
            System.out.println("File deleted successfully");
        }
        else if(result.get()==ButtonType.CANCEL)
        {
            System.out.println("canceled");
        }
    }

    private void showSimpleAlert()
    {
        Alert alert=new Alert(Alert.AlertType.NONE);
        alert.setTitle("Simple Alert");
        alert.setHeaderText("Simple Demo");
        alert.setContentText("This is my message");
        alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        alert.show();
    }

    private void custom(Stage stage) throws Exception {
        BorderPane root=new BorderPane();
        Scene scene=new Scene(root,400,300);
        stage.setScene(scene);
        stage.setTitle("Custom Dialog Example");
        stage.show();
        Button button=new Button("show dialog");
        root.setCenter(button);

        button.setOnAction(e->{
            Dialog dialog=new Dialog();
            dialog.setTitle("Custom Dialog");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.getDialogPane().setContent(createLoginForm());
            dialog.show();
        });
    }

    private Node createLoginForm()
    {
        GridPane gridPane=new GridPane();
        gridPane.add(new Label("Username: "),0,0);
        gridPane.add(new Label("Password: "),0,1);
        gridPane.add(new TextField(),1,0);
        gridPane.add(new TextField(),1,1);
        gridPane.add(new TextField(),1,1);
        gridPane.add(new Button("submit"),0,2);
        return gridPane;
    }


    private void dialogResponse(Stage stage) throws Exception {
        BorderPane root=new BorderPane();
        Scene scene=new Scene(root,400,300);
        stage.setScene(scene);
        stage.setTitle("Dialog Example");
        stage.show();
        Button button=new Button("show dialog");
        root.setCenter(button);
        button.setOnAction(e->{
            Dialog dialog=new Dialog();
            dialog.setTitle("My Dialog");
            dialog.setHeaderText("are you a player?");
            dialog.setContentText("Please select an option.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
            Optional<ButtonType>result=dialog.showAndWait();

            if(result.get()==ButtonType.YES){
                System.out.println("YES is selected");
            } else if (result.get()==ButtonType.NO) {
                System.out.println("NO is selected");
            } else if(result.get()==ButtonType.CANCEL){
                System.out.println("CANCEL is selected");
            } else{
                System.out.println("NOTHING is selected");
            }
        });
    }
    public static void main(String[] args)
    {
        launch(args);
    }


}
