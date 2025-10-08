package com.example.project_7;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainController {
    Stage parentStage;
    @FXML
    private SplitPane rootNode;
    @FXML
    public void initialize(){
//
        rootNode.setStyle(
                "-fx-background-color: linear-gradient(to right, #a0c4ff, #ffffff);"
        );
    }
    @FXML
    private void createButtonHandeler(){
        parentStage = (Stage) rootNode.getScene().getWindow();
        enterFileNameDialog();
    }
    @FXML
    private void openButtonHandeler(){
        parentStage = (Stage) rootNode.getScene().getWindow();
        String[] types = {".dt"};
        String path = LIB.fileOpenDialog(parentStage,types);
        if(path!=null)
        {
            Process_Class process = Process_Class.read(path);
            if(process!=null){
                try {
                    new EditPage(parentStage,process,path).loadEditPage();
                }catch (Exception e){
                    System.out.println("Error While Opening");
                    System.out.println(e);
                }

            }
        }
    }
    // custom elements
    void enterFileNameDialog() {
//        parentStage.close();
        Stage childStage = new Stage(StageStyle.DECORATED);
        childStage.initModality(Modality.APPLICATION_MODAL);
        childStage.initOwner(parentStage);
        HBox row1 = new HBox();
        row1.setAlignment(Pos.CENTER);
        row1.setSpacing(10);
        HBox row2 = new HBox();
        Button confirm = new Button("Confirm");
        row2.setAlignment(Pos.CENTER);
        row2.getChildren().addAll(confirm);
        HBox row3 = new HBox();
        row3.setAlignment(Pos.CENTER);
        Label errorMsg = new Label();
        errorMsg.setStyle("-fx-text-fill: red;");
        row3.getChildren().addAll(errorMsg);
        VBox  root= new VBox();
        root.setAlignment(Pos.CENTER);
        TextField fileNameTextField = new TextField();
        fileNameTextField.setPromptText("Enter File Name");
        Label filenamelabel = new Label("File Name");
        row1.getChildren().addAll(filenamelabel,fileNameTextField);
        root.setSpacing(10);
        root.getChildren().addAll(row1,row2,row3);
        childStage.setScene(new Scene(root,300,100));
        confirm.setOnAction((e)->{
            if(LIB.isValidFileName(fileNameTextField.getText())){
                childStage.close();
                try {
                    new EditPage(parentStage,fileNameTextField.getText()).loadEditPage();
                }catch (Exception exception){
                    System.out.println("Error while loading editpage");
                    System.out.println(exception);
                }

            }else{
                errorMsg.setText("Not a valid name !");
            }
        });
        childStage.show();
    }
}
