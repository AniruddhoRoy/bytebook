package com.example.project_7.COMPONENTS;

import com.example.project_7.CONSTANTS;
import com.example.project_7.EditPageController;
import com.example.project_7.LIB;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;

public class Paragraph_Component extends Base_Component {
    HTMLEditor htmlEditor = new HTMLEditor();
    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();
    WebView webViewHtmlEditor = (WebView) htmlEditor.lookup(".web-view");
    VBox root = new VBox();
    double size = 300;
    void setHeight(double val){
        webView.setMinHeight(val);
        webViewHtmlEditor.setMinHeight(val);
        this.size = val;
    }
void addContextMenu() {
    ContextMenu contextMenu = new ContextMenu();

    // Size menu
    Menu sizeMenu = new Menu("Size");

    // Height options in pixels
    int[] heights = {100, 150, 200, 250, 300, 350, 400, 450, 500};

    // Add MenuItems dynamically
    for (int h : heights) {
        MenuItem item = new MenuItem(h + " px");
        item.setOnAction(e -> setHeight(h));
        sizeMenu.getItems().add(item);
    }

    // Delete option
    MenuItem delete = new MenuItem("Delete");
    delete.setOnAction(e->{
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Paragraph");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("do you really want to delete Paragraph?");

        //wait for user response
        alert.showAndWait().ifPresent(response->{
            if(response==ButtonType.OK)
            {
                delete(e);//call Base_Component delete
            }
        });
    });

    // Add menus to context menu
    contextMenu.getItems().addAll(sizeMenu, delete);

    // Disable default HTMLEditor WebView context menu
    webViewHtmlEditor.setContextMenuEnabled(false);

    // Show custom context menu on right-click
    webViewHtmlEditor.setOnContextMenuRequested(e -> {
        contextMenu.show(root, e.getScreenX(), e.getScreenY());
    });
}

    public Paragraph_Component(){
        setHeight(size);
      addContextMenu();
    }
    public Paragraph_Component(String initialValue,double size){
        htmlEditor.setHtmlText(initialValue);
        webEngine.loadContent(initialValue);
        setHeight(size);
        addContextMenu();
    }


    public VBox getPragraphComponent() {

        root.setAlignment(Pos.CENTER);


        root.getChildren().setAll(webView);

        webView.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edit Paragraph");
            alert.setHeaderText("Switch to edit mode?");
            alert.setContentText("Unsaved changes may be lost.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    root.getChildren().setAll(htmlEditor);
                }
            });
        });


// Listen to the WebView's focus
        webViewHtmlEditor.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal){
                webEngine.loadContent(htmlEditor.getHtmlText());
                root.getChildren().setAll(webView);
            }
        });
        return root;
    }
    public Button getComponentButton(ArrayList<Base_Component> components, EditPageController instance){
        Button button = new Button();
        button.setGraphic(new LIB().loadImageView(CONSTANTS.Paragraph_icon,50));
        //Tooltip added here
        Tooltip tooltip=new Tooltip("Add a new paragraph");
        Tooltip.install(button,tooltip);
        button.setOnAction(e->{
//            containerNode.getChildren().add(this.getIamgecomponent(childStage));
                components.add(new Paragraph_Component());
            instance.refresh();

        });
        return button;
    }
    public Paragraph_Component_Class export(){
        return new Paragraph_Component_Class(htmlEditor.getHtmlText(),this.size);
    }
    public String getHtml(){
        return htmlEditor.getHtmlText();
    };
}
