package com.example.project_7.COMPONENTS;

import com.example.project_7.CONSTANTS;
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
    void addContextMenu(){
        ContextMenu contextMenu = new ContextMenu();
        Menu size = new Menu("Size");
        MenuItem delete = new MenuItem("Delete");
        delete.setOnAction(this::delete);
        MenuItem x1 = new MenuItem("1x");
        MenuItem x2 = new MenuItem("2x");
        MenuItem x3 = new MenuItem("3x");
        MenuItem x4 = new MenuItem("4x");
        x1.setOnAction(e -> setHeight(100));
        x2.setOnAction(e -> setHeight(200));
        x3.setOnAction(e -> setHeight(300));
        x4.setOnAction(e -> setHeight(400));
// Add items to submenu
        size.getItems().addAll(x1, x2, x3, x4);
        contextMenu.getItems().addAll(size,delete);
        webViewHtmlEditor.setContextMenuEnabled(false);
        webViewHtmlEditor.setOnContextMenuRequested(e->{
            contextMenu.show(root,e.getScreenX(),e.getScreenY());
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
            root.getChildren().setAll(htmlEditor);
        });


// Listen to the WebView's focus
        webViewHtmlEditor.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                webEngine.loadContent(htmlEditor.getHtmlText());
                root.getChildren().setAll(webView);
            }
        });
        return root;
    }
    public Button getComponentButton(ArrayList<Base_Component> components, Stage childStage){
        Button button = new Button();
        button.setGraphic(new LIB().loadImageView(CONSTANTS.Paragraph_icon,50));
        button.setOnAction(e->{
//            containerNode.getChildren().add(this.getIamgecomponent(childStage));
            components.add(this);
            childStage.close();
        });
        return button;
    }
    public Paragraph_Component_Class export(){
        return new Paragraph_Component_Class(htmlEditor.getHtmlText(),this.size);
    }
}
