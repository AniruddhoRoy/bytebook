package com.example.project_7;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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
    private VBox treeViewNode;
    @FXML
    public void initialize(){
//
//                "-fx-background-color: linear-gradient(to right, #a0c4ff, #ffffff);"
//        rootNode.setStyle(
//                "-fx-background-color: linear-gradient(to right, #0f2027, #203a43, #2c5364);"
//        );
        Image image = new Image(getClass().getResourceAsStream(CONSTANTS.Default_Home_Page_Icon));
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100,100,true,true,true,true)
        );
        Background background = new Background(backgroundImage);
        rootNode.setBackground(background);
        rootNode.getStylesheets().add(getClass().getResource("/CSS/splitPane.css").toExternalForm());
        treeViewNode.setAlignment(Pos.TOP_CENTER);
        load_recent_File_TreeView();
    }
    @FXML
    private void createButtonHandeler(){
        parentStage = (Stage) rootNode.getScene().getWindow();
        enterFileNameDialog();
    }
    @FXML
    private void openButtonHandeler(){
        parentStage = (Stage) rootNode.getScene().getWindow();
        String[] types = {CONSTANTS.Applicaiton_Extention};
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
    void load_recent_File_TreeView(){
        TreeView<String> treeView = new TreeView<>();
        treeView.setPadding(new Insets(15,0,0,0));
        treeView.getStylesheets().add(getClass().getResource("/CSS/treeview.css").toExternalForm());
        TreeItem<String > root = new TreeItem<>();
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        VBox.setVgrow(treeView, Priority.ALWAYS);
        for(int i = 0 ; i<10 ; i++)
        {
            TreeItem<String > newTreeItem = new TreeItem<String>("Saved file name [time] ",new LIB().loadImageView(CONSTANTS.Default_Application_Extention_Logo,10));
            root.getChildren().add(newTreeItem);
        }
        treeView.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->{
            TreeItem<String > treeItem = (TreeItem<String>) nv;
            System.out.println(treeItem.getValue());
        });
        treeViewNode.getChildren().add(treeView);
    }
}
