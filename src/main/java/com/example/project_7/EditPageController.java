package com.example.project_7;


import com.example.project_7.COMPONENTS.Base_Component;
import com.example.project_7.COMPONENTS.Component_Base_Classes;
import com.example.project_7.COMPONENTS.Image_Component;
import com.example.project_7.COMPONENTS.Image_Component_Class;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

public class EditPageController {
    Process_Class process = new Process_Class();
    ArrayList<Base_Component> components = new ArrayList<>();
    Stage parentStage;
    boolean isPriviouslySaved = false;
    String filePath;
    @FXML
    private BorderPane rootNode;
    @FXML
    private VBox containerNode;
    @FXML
    private void addButtonHandeler(){

        parentStage = (Stage) rootNode.getScene().getWindow();
        showAddItemsDialogBox();
        refresh();
    }
    @FXML
     private void saveButtonHandeler(){
        parentStage = (Stage) rootNode.getScene().getWindow();
        process.fileName = parentStage.getTitle();
        process.components.clear();
        for (Base_Component component:components){
            if(component instanceof Image_Component){
                process.components.add(((Image_Component) component).export());
            }
        }
        if(!isPriviouslySaved){
            String path = LIB.directoryChooser(parentStage);
            if(path!=null)
            {
                this.filePath = path;
                process.save(path);
                isPriviouslySaved = true;
            }
        }else{
            process.save(this.filePath);
        }

    }
    //custom Methods
    void refresh(){
        containerNode.getChildren().clear();
        for(Base_Component component : components)
        {
            if(component instanceof Image_Component){
                containerNode.getChildren().add(((Image_Component) component).getIamgecomponent(parentStage));
            }
        }
    }
    //custom dialog

    void showAddItemsDialogBox(){
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.initOwner(parentStage);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        VBox root = new VBox();
        root.setPadding(new Insets(15));
        HBox row1 = getHbox();
        HBox row2 = getHbox();
        HBox row3 = getHbox();
        row1.getChildren().add(new Image_Component().getComponentButton(components,stage));
        root.getChildren().addAll(row1,row2,row3);
        Scene scene = new Scene(root,400,300);
        new LIB().setIconAndTitle(stage, CONSTANTS.Applicaiton_icon_path,"Select Item");
        stage.setScene(scene);
        stage.show();
        stage.setOnHidden(event -> {
            refresh();
        });
    }
    private HBox getHbox(){
        HBox root = new HBox();
        return root;
    }
}
