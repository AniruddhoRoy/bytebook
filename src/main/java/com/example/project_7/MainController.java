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

import static com.example.project_7.Dialogs.ErrorAlert;


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
                ErrorAlert("Opening Error","Error While Opening",path);
            }

        }
    }
    void enterFileNameDialog() {

        Stage childStage = new Stage(StageStyle.DECORATED);
        childStage.initModality(Modality.APPLICATION_MODAL);
        childStage.initOwner(parentStage);
        childStage.setTitle("Create File");

        // ===== Label + Input =====
        Label fileNameLabel = new Label("File Name:");

        TextField fileNameTextField = new TextField();
        fileNameTextField.setPromptText("Enter file name...");
        fileNameTextField.setPrefWidth(200);

        HBox inputRow = new HBox(10, fileNameLabel, fileNameTextField);
        inputRow.setAlignment(Pos.CENTER_LEFT);

        // ===== Error Message =====
        Label errorMsg = new Label();
        errorMsg.setStyle("-fx-text-fill: red;");

        // ===== Button =====
        Button confirm = new Button("Confirm");
        confirm.setDefaultButton(true); // press Enter to confirm
        confirm.setPrefWidth(100);

        HBox buttonRow = new HBox(confirm);
        buttonRow.setAlignment(Pos.CENTER_RIGHT);

        // ===== Root Layout =====
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER_LEFT);
        root.getChildren().addAll(inputRow, errorMsg, buttonRow);

        Scene scene = new Scene(root, 350, 150);
        childStage.setScene(scene);

        // ===== Button Action =====
        confirm.setOnAction(e -> {
            String fileName = fileNameTextField.getText().trim();

            if (LIB.isValidFileName(fileName)) {
                childStage.close();
                try {
                    new EditPage(parentStage, fileName).loadEditPage();
                } catch (Exception exception) {
                    System.out.println("Error while loading edit page");
                    exception.printStackTrace();
                    ErrorAlert("Creating Error","Error While Creating file !","FILE :"+fileName);
                }
            } else {
                errorMsg.setText("Please enter a valid file name.");
            }
        });

        childStage.showAndWait();
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
