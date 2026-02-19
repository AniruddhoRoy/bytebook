package com.example.project_7;

import com.example.project_7.COMPONENTS.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

import static com.example.project_7.Dialogs.ConformationAlert;

public class EditPage {
    String fileName;
    Stage parentStage;
    Process_Class process = null;
    String filePath;
    EditPage(Stage parentStage, String fileName){
        this.fileName = fileName;
        this.parentStage = parentStage;
    }
    EditPage(Stage parentStage,Process_Class process,String filePath){
        this.parentStage = parentStage;
        this.fileName = process.fileName;
        this.process = process;
        this.filePath = new File(filePath).getParent();
    }
    void loadEditPage() throws IOException {
        parentStage.close();
        FXMLLoader loader = new FXMLLoader(EditPage.class.getResource("EditPage.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(scene);
        stage.show();
        new LIB().setIconAndTitle(stage,CONSTANTS.Applicaiton_icon_path,fileName);
        EditPageController editPageController = loader.getController();
        if(process!=null){

            for(Component_Base_Classes components : process.components){
                if(components instanceof Image_Component_Class){
                    editPageController.components.add(((Image_Component_Class) components).export());
                }
                else if(components instanceof Heading_Component_Class){
                    editPageController.components.add(((Heading_Component_Class) components).export());
                }
                else if(components instanceof Media_Component_Class){
                    editPageController.components.add(((Media_Component_Class) components).export());
                }
                else if(components instanceof Paragraph_Component_Class){
                    editPageController.components.add(((Paragraph_Component_Class) components).export());
                }
                else if(components instanceof Code_Component_cpp_Class){
                    editPageController.components.add(((Code_Component_cpp_Class) components).export());
                }
                else if(components instanceof Hyperlink_Component_Class){
                    editPageController.components.add(((Hyperlink_Component_Class) components).export());
                }
                else if(components instanceof  ToDoList_Component_Class){
                    editPageController.components.add(((ToDoList_Component_Class) components).export());
                }
            }
            editPageController.isPriviouslySaved = true;
            editPageController.filePath = this.filePath;
            editPageController.fileName = this.fileName;
            editPageController.refresh();
        }
        stage.setOnCloseRequest(e->{
            if(!editPageController.isPriviouslySaved){
               boolean response = ConformationAlert("Current file is not saved ❌","Save the File : ?","");
               if(response){
                   editPageController.saveButtonHandeler();
               }
            }
            Main.controller.load_recent_File_TreeView();
            parentStage.show();
        });
    }
}
