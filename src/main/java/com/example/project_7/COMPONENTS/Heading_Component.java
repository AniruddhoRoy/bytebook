package com.example.project_7.COMPONENTS;

import com.example.project_7.CONSTANTS;
import com.example.project_7.LIB;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;


public class Heading_Component extends Base_Component{
    VBox root;
    TextField heading;
    String style_background_color = "-fx-background-color: lightyellow;" ;
    String style_font_weight = "-fx-font-weight: bold;";
    String style_font_Size = "-fx-font-size: 20px;";
    String style_font_family = "-fx-font-family: 'Arial';";
    String style_font_color = "-fx-text-fill: darkblue;";

    void initComponents(){
        root = new VBox();
        heading = new TextField();
        heading.setPromptText("Enter Heading");

        root.getChildren().add(heading);
        ContextMenu contextMenu = new ContextMenu();
        Menu background_menu = new Menu("Backgorund");
        Menu font_weight_menu = new Menu("Font Weight");
        Menu font_size_menu = new Menu("Font Size");
        Menu font_family_menu = new Menu("Font Family");
        Menu font_color_menu = new Menu("Font Color");
        MenuItem delete  = new MenuItem("Delete");
        //delete Menu Item

        delete.setOnAction(this::delete);
        //Working of Background menu
        for(Pair<String,String> color : CONSTANTS.Background_colors){
            MenuItem menuItem = new MenuItem(color.getKey());
            menuItem.setOnAction(e->{
                style_background_color = color.getValue();
                loadStyle();
            });
            background_menu.getItems().add(menuItem);
        }
        //working of Font Weight Menu
        for(Pair<String,String> fontWeight : CONSTANTS.fontWeights){
            MenuItem menuItem = new MenuItem(fontWeight.getKey());
            menuItem.setOnAction(e->{
                style_font_weight = fontWeight.getValue();
                loadStyle();
            });
            font_weight_menu.getItems().add(menuItem);
        }
        //Working of Font Size Menu
        for(Pair<String,String> fontFamily : CONSTANTS.fontSizes){
            MenuItem menuItem = new MenuItem(fontFamily.getKey());
            menuItem.setOnAction(e->{
                style_font_Size = fontFamily.getValue();
                loadStyle();
            });
            font_size_menu.getItems().add(menuItem);
        }
        //Working of Font Family Menu
        for(Pair<String,String> fontFamily : CONSTANTS.fontFamilys){
            MenuItem menuItem = new MenuItem(fontFamily.getKey());
            menuItem.setOnAction(e->{
                style_font_family = fontFamily.getValue();
                loadStyle();
            });
            font_family_menu.getItems().add(menuItem);
        }

        //Working of Font Color Menu
        for(Pair<String,String> color : CONSTANTS.headingColors){
            MenuItem menuItem = new MenuItem(color.getKey());
            menuItem.setOnAction(e->{
                style_font_color = color.getValue();
                loadStyle();
            });
            font_color_menu.getItems().add(menuItem);
        }
        // Attach context menu to VBox
        heading.setOnContextMenuRequested(event ->
                contextMenu.show(root, event.getScreenX(), event.getScreenY())
        );

        // Optional: hide context menu when clicking elsewhere
        heading.setOnMouseClicked(event -> contextMenu.hide());
        contextMenu.getItems().addAll(background_menu,
                font_weight_menu,
                font_size_menu,
                font_family_menu,
                font_color_menu,
                delete
        );
        loadStyle();
    }
    private void loadStyle(){
        heading.setStyle(
                style_background_color
                        +style_font_weight
                        +style_font_Size
                        +style_font_family
                        +style_font_color
        );
    }
    public Heading_Component(){
        initComponents();

    }
    public Heading_Component(
            String heading_text,
            String style_background_color,
            String style_font_weight,
            String style_font_Size,
            String style_font_family,
            String style_font_color
    ) {
        initComponents();
        heading.setText(heading_text);
        this.style_background_color = style_background_color;
        this.style_font_weight = style_font_weight;
        this.style_font_Size = style_font_Size;
        this.style_font_family = style_font_family;
        this.style_font_color = style_font_color;
    }
    public VBox getHeadingComponent(){
        return root;
    }

    public Button getComponentButton(ArrayList<Base_Component> components, Stage childStage){
        Button button = new Button();
        button.setGraphic(new LIB().loadImageView(CONSTANTS.Heading_Component_Icon,50));
        button.setOnAction(e->{
//            containerNode.getChildren().add(this.getIamgecomponent(childStage));
            components.add(this);
            childStage.close();
        });
        return button;
    }
    public Heading_Component_Class export(){
        return new Heading_Component_Class(
                heading.getText(),
                style_background_color,
                style_font_weight,
                style_font_Size,
                style_font_family,
                style_font_color
        );
    }
    String convertFxToHtmlCss(String fxStyle) {
        return fxStyle
                .replace("-fx-background-color", "background-color")
                .replace("-fx-font-weight", "font-weight")
                .replace("-fx-font-size", "font-size")
                .replace("-fx-font-family", "font-family")
                .replace("-fx-text-fill", "color");
    }
    public String getHtml(){
        String combinedStyle = convertFxToHtmlCss(
                style_background_color+
                style_font_weight+
                style_font_Size+
                style_font_family+
                style_font_color
        );
        combinedStyle+="padding: 10px;";

        String html = """
    <div style="%s">%s</div>
                    """.formatted(combinedStyle, heading.getText());
        return html;
    };
}
