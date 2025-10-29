package com.example.project_7;

import com.example.project_7.COMPONENTS.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

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
        stage.setTitle(fileName);
        if(process!=null){
            EditPageController editPageController = loader.getController();
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
            parentStage.show();
        });
    }
}
