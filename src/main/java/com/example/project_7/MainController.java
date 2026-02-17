package com.example.project_7;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;


public class MainController {
    Stage parentStage;
    @FXML
    private SplitPane rootNode;
    @FXML
    private  VBox treeViewNode;
    @FXML
    private ImageView openButton,createButton;
    @FXML
    public void initialize(){

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
            openFile(path);
        }
    }
    // custom elements
    private void openFile(String path){
        parentStage = (Stage) rootNode.getScene().getWindow();
        System.out.println(path);
        Process_Class process = Process_Class.read(path);
        if(process!=null){
            try {
                Main.recentHandler.isexist(process.fileName,path);
                load_recent_File_TreeView();
                new EditPage(parentStage,process,path).loadEditPage();
            }catch (Exception e){
                System.out.println("Error While Opening");
                System.out.println(e);
            }

        }
    }
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
   public  void load_recent_File_TreeView(){

       treeViewNode.getChildren().clear();
       Label heading_lable = new Label("Recent Files");
       heading_lable.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
       treeViewNode.getChildren().add(heading_lable);

       for (Item item : Main.recentHandler.get_recent_files()) {
           // File name label
           Label label = new Label(item.name);
           label.setPrefWidth(150);

           label.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

           // Buttons
           Button openButton = new Button("Open");
           Button removeButton = new Button("Remove");
            openButton.setOnAction(e->{
//                String filePath = item.path+"\\"+item.name+CONSTANTS.Applicaiton_Extention;
                String filePath = item.path;
                System.out.println(filePath);
            File file = new File(filePath);
            if(file.exists()){
            openFile(filePath);
            }else{
                System.out.println("File not exist");
                Main.recentHandler.remove_file(Main.recentHandler.get_recent_files().indexOf(item));
                load_recent_File_TreeView();
            }
            });
           removeButton.setOnAction(e->{
               Main.recentHandler.remove_file(Main.recentHandler.get_recent_files().indexOf(item));
               load_recent_File_TreeView();
           });
           openButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
           removeButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

           // Button container
           HBox buttonBox = new HBox( openButton, removeButton);
           buttonBox.setAlignment(Pos.CENTER_RIGHT);
           buttonBox.setSpacing(5);
           // Main row layout
           BorderPane row = new BorderPane();
           row.setLeft(label);
           row.setRight(buttonBox);

           row.setPadding(new Insets(5,8,5,8));
           row.setStyle("""
        -fx-background-color: white;
        -fx-border-color: #dddddd;
        -fx-border-radius: 5;
        -fx-background-radius: 5;
    """);
            treeViewNode.setPadding(new Insets(10));
            treeViewNode.setSpacing(5);
           treeViewNode.getChildren().add(row);
       }
   }
}
