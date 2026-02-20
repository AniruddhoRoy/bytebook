package com.example.project_7.COMPONENTS;

import com.example.project_7.CONSTANTS;
import com.example.project_7.EditPageController;
import com.example.project_7.LIB;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.print.DocFlavor;
import java.awt.*;
import java.net.URI;
import java.util.ArrayList;

import static com.example.project_7.Dialogs.ConformationAlert;
import static com.example.project_7.Dialogs.ErrorAlert;

public class Hyperlink_Component extends Base_Component{
    //main layout container
    private VBox root;
    //clickable hyperlink UI
    private Hyperlink hyperlink;
    //default URL
    private String urlText="https://www.google.com";
    private String displayText="Click here";

    public Hyperlink_Component(){
        initComponent();
    }

    public Hyperlink_Component(String displayText,String urlText){
        this.displayText=displayText;
        this.urlText=urlText;
        initComponent();
    }

    private void initComponent(){
        root=new VBox();
        root.setAlignment(Pos.CENTER);

    //hyperlink UI creation
        hyperlink=new Hyperlink(displayText);
        hyperlink.setStyle("-fx-font-size:16px;-fx-text-fill:blue");

        // ✅ Tooltip added here
        Tooltip tooltip = new Tooltip("Go to: " + urlText);
        Tooltip.install(hyperlink, tooltip);
//after clicking,opens URL
        hyperlink.setOnAction(e ->
                openWebPage(urlText));
//add hyperlink to layout
        root.getChildren().add(hyperlink);

        //Context Menu (Right-click menu)

        ContextMenu menu=new ContextMenu();
        MenuItem editText=new MenuItem("Edit Display Text");
        MenuItem editLink=new MenuItem("Edit Link");
        MenuItem delete=new MenuItem("Delete");

        //edit display text
        editText.setOnAction(e->{
            TextInputDialog dialog=new TextInputDialog(displayText);
            dialog.setTitle("Edit Display Text");
            dialog.setHeaderText("Change hyperlink text: ");
            dialog.setContentText("Enter new text: ");
            dialog.showAndWait().ifPresent(newText->{
                displayText=newText;
                hyperlink.setText(displayText);
            });
        });

        //edit URL
        editLink.setOnAction(e->{
            TextInputDialog dialog=new TextInputDialog(urlText);
            dialog.setTitle("Edit Link");
            dialog.setHeaderText("Change hyperlink URL: ");
            dialog.setContentText("Enter new URL: ");
            dialog.showAndWait().ifPresent(newURL->{
                urlText=newURL;
                tooltip.setText("Go to: "+newURL);
            });
        });
        delete.setOnAction(e->{
            if(ConformationAlert("Delete code","Are you sure?","do you really want to delete this code?")){
                delete(e);
            }
        });
        menu.getItems().addAll(editText,editLink,delete);
        //right click event handler
        hyperlink.setOnContextMenuRequested(e->{
            menu.show(hyperlink,e.getScreenX(),e.getScreenY());
        });

    }
    //open URL in default browser
    private void openWebPage(String urlText)
    {
        try{
            Desktop.getDesktop().browse(new URI(urlText));
        }catch (Exception e){
            ErrorAlert("Invalid URL","Cannot open link","The URL is not valid:\n"+urlText);
//            Alert alert=new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Invalid URL");
//            alert.setHeaderText("Cannot open link");
//            alert.setContentText("The URL is not valid:\n"+urlText);
//            alert.show();
        }
    }
    //Return UI part
    public VBox getHyperlinkComponent(){
        return root;
    }
    //Return button for toolbox
    public Button getComponentButton(ArrayList<Base_Component>components, EditPageController instance){
        Button button=new Button();
        button.setGraphic(new LIB().loadImageView(CONSTANTS.Default_hyperlink_icon,50));
        Tooltip tooltip = new Tooltip("Search Tool");
        Tooltip.install(button, tooltip);
        button.setOnAction(e->{
            components.add(new Hyperlink_Component());
            instance.refresh();
        });

        return button;
    }
    // Export for saving
    public Hyperlink_Component_Class export() {
        return new Hyperlink_Component_Class(displayText, urlText);
    }
    public String getHtml(){
        String html = """
            <div style="text-align: center;">
              <a href="%s"
                 target="_blank"
                 style="text-decoration: none; color: blue; font-size: 18px;">
                %s
              </a>
            </div>
            """.formatted(urlText, displayText);

        return html;
    }
}