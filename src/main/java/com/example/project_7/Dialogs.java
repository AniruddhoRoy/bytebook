package com.example.project_7;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Optional;

public class Dialogs {
    public static void InformationAlert(String title,String heading,String msg)
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(heading);
        alert.setContentText(msg);
        alert.show();
    }

    public static void WarningAlert(String title,String heading,String msg)
    {
        Alert alert=new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(heading);
        alert.setContentText(msg);
        alert.show();
    }

    public static void ErrorAlert(String title,String heading,String msg)
    {
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(heading);
        alert.setContentText(msg);
        alert.show();
    }

    public static void ConformationAlert(String title,String heading,String msg)
    {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(heading);
        alert.setContentText(msg);
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.OK)
        {
            System.out.println("File deleted successfully");
        }
        else if(result.get()==ButtonType.CANCEL)
        {
            System.out.println("canceled");
        }
    }

    /////////Dialogs////////
    public void textInputDialog(String title,String heading,String msg) {
        TextInputDialog dialog=new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(heading);
        dialog.setContentText(msg);
        Optional<String>result=dialog.showAndWait();
        result.ifPresent((name)->{
            System.out.println(name);
        });
    }
    public void LoginpageDialog()  {
        Dialog dialog=new Dialog();
        dialog.setTitle("Custom Dialog");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().setContent(createLoginForm());
        dialog.show();
    }

    public Node createLoginForm()
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
}
