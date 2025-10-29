package com.example.project_7;


import com.example.project_7.COMPONENTS.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Optional;

public class EditPageController {
    public static EditPageController instance;
    Process_Class process = new Process_Class();
    public ArrayList<Base_Component> components = new ArrayList<>();
    Stage parentStage;
    boolean isPriviouslySaved = false;
    String filePath;
    String fileName;
    @FXML
    public void initialize() {
        instance = this;
    }
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
    private void exportToPdfButtonHandeler(){
        refresh();
        String html="";
        for(Base_Component component : components)
        {
            if(component instanceof Image_Component){
                try {
                    html += ((Image_Component) component).getHtml();
                }catch (Exception e){
                    System.out.println("Error Reading image in pdf");
                }
            }
            else if(component instanceof Heading_Component){
                    html+=((Heading_Component) component).getHtml();

            }
            else if (component instanceof Media_Component) {
                try{
                    html+= ((Media_Component) component).getHtml();
                }catch (Exception e){
                    System.out.println("Error Reading media in pdf");
                }
            }
            else if(component instanceof Paragraph_Component){
                html+= ((Paragraph_Component) component).getHtml();
            }
            else if(component instanceof Code_Component_cpp){
                html+= ((Code_Component_cpp) component).getHtml();
            }
            else  if (component instanceof Hyperlink_Component){
                html+= ((Hyperlink_Component) component).getHtml();
            }
        }
        if(isPriviouslySaved){
            try {
                LIB.export_pdf(html,filePath,fileName);
            }catch (Exception e){
                System.out.println("Error While Exporting Html");
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save Changes");
            alert.setHeaderText("You Have to save current file to export PDF");

            ButtonType saveButton = new ButtonType("Save");
            ButtonType cancelButton = new ButtonType("Cancel");

            // Remove default buttons and add custom ones
            alert.getButtonTypes().setAll(saveButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent()) {
                if (result.get() == saveButton) {

                    saveButtonHandeler();
                    try {
                        LIB.export_pdf(html,filePath,fileName);
                    }catch (Exception e){
                        System.out.println("Error While Exporting Html");
                    }
                }  else {
                    System.out.println("User canceled the action.");

                }
            }
        }

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
            else if (component instanceof Heading_Component) {
                process.components.add(((Heading_Component) component).export());
            }
            else if(component instanceof Media_Component){
                process.components.add(((Media_Component) component).export());
            }
            else if (component instanceof Paragraph_Component) {
                process.components.add(((Paragraph_Component) component).export());
            }
            else if(component instanceof Code_Component_cpp){
                process.components.add(((Code_Component_cpp) component).export());
            }
            else if(component instanceof Hyperlink_Component){
                process.components.add(((Hyperlink_Component) component).export());
            }
            else if (component instanceof ToDoList_Component) {
                process.components.add(((ToDoList_Component) component).export());
            }
        }
        if(!isPriviouslySaved){
            String path = LIB.directoryChooser(parentStage);
            if(path!=null)
            {
                this.fileName = process.fileName;
                this.filePath = path;
                process.save(path);
                isPriviouslySaved = true;
            }
        }else{
            process.save(this.filePath);
        }

    }
    //custom Methods
    public void refresh(){
        containerNode.getChildren().clear();
        containerNode.setSpacing(10);
        for(Base_Component component : components)
        {
            if(component instanceof Image_Component){
                containerNode.getChildren().add(((Image_Component) component).getIamgecomponent(parentStage));
            }
            else if(component instanceof Heading_Component){
                containerNode.getChildren().add(((Heading_Component) component).getHeadingComponent());
            } else if (component instanceof Media_Component) {
                containerNode.getChildren().add(((Media_Component) component).getMediaComponent(parentStage));
            }
            else if(component instanceof Paragraph_Component){
                containerNode.getChildren().add(((Paragraph_Component) component).getPragraphComponent());
            }
            else if(component instanceof Code_Component_cpp){
                containerNode.getChildren().add(((Code_Component_cpp) component).getCodeComponentCpp());
            }
            else if(component instanceof Hyperlink_Component){
                containerNode.getChildren().add(((Hyperlink_Component) component).getHyperlinkComponent());

            }
            else if(component instanceof ToDoList_Component)
            {
                containerNode.getChildren().add(((ToDoList_Component) component).getToDoListComponent());
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
        root.setSpacing(10);
        HBox row1 = getHbox();
        row1.setSpacing(10);
        HBox row2 = getHbox();
        row2.setSpacing(10);
        HBox row3 = getHbox();
        row1.getChildren().add(new Image_Component().getComponentButton(components,stage));
        row1.getChildren().add(new Media_Component(true).getComponentButton(components,stage));
        row1.getChildren().add(new Media_Component(false).getComponentButton(components,stage));
        row2.getChildren().add(new Heading_Component().getComponentButton(components,stage));
        row2.getChildren().add(new Paragraph_Component().getComponentButton(components,stage));
        row2.getChildren().add(new Hyperlink_Component().getComponentButton(components,stage));
        row3.getChildren().add(new Code_Component_cpp().getComponentButton(components,stage));

        row3.getChildren().add(new ToDoList_Component().getComponentButton(components,stage));
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
