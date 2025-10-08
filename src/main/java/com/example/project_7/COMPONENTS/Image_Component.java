package com.example.project_7.COMPONENTS;

import com.example.project_7.CONSTANTS;
import com.example.project_7.LIB;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class Image_Component extends Base_Component{
    private String  imagePath = "";
    private double size = 200;
    ImageView imageView ;
    public Image_Component(){
        imageView = new LIB().loadImageView(CONSTANTS.Default_image_icon_path,150);
    }
    public Image_Component(String imagePath,double size){
        this.imagePath = imagePath;
        this.size = size;
        if(!imagePath.isEmpty()){
            imageView = new ImageView();
            imageView.setPreserveRatio(true);
            File file = new File(imagePath);
            Image image = new Image(file.toURI().toString()); // Convert path to URI
            imageView.setImage(image);
            imageView.setFitHeight(size);
        }else{
            imageView = new LIB().loadImageView(CONSTANTS.Default_image_icon_path,size);
        }
    }

    public VBox getIamgecomponent(Stage stage){
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(imageView);

        imageView.setPickOnBounds(true);

        // Create ContextMenu
        ContextMenu contextMenu = new ContextMenu();
        MenuItem loadImage = new MenuItem("LoadImage");
        Menu size = new Menu("Size");
        MenuItem delete = new MenuItem("Delete");
        MenuItem x1 = new MenuItem("1x");
        MenuItem x2 = new MenuItem("2x");
        MenuItem x3 = new MenuItem("3x");
        MenuItem x4 = new MenuItem("4x");
        x1.setOnAction(e -> imageView.setFitHeight(100));
        x2.setOnAction(e -> imageView.setFitHeight(200));
        x3.setOnAction(e -> imageView.setFitHeight(300));
        x4.setOnAction(e -> imageView.setFitHeight(400));
// Add items to submenu
        size.getItems().addAll(x1, x2, x3, x4);
        delete.setOnAction(this::delete);
        contextMenu.getItems().addAll(loadImage, size,delete);
        loadImage.setOnAction(e->{
            String[] types = {"png", "jpg", "jpeg", "gif", "bmp", "webp"};
            String path = new LIB().fileOpenDialog(stage,types);
            if(path!=null){
                imagePath = path;
                File file = new File(path);
                Image image = new Image(file.toURI().toString()); // Convert path to URI
                imageView.setImage(image);
            }
        });
        // Show ContextMenu on right-click
        imageView.setOnContextMenuRequested(event -> {
            contextMenu.show(imageView, event.getScreenX(), event.getScreenY());
        });
        return root;
    }
   public Button getComponentButton(ArrayList<Base_Component> components, Stage childStage){
        Button button = new Button();
        button.setGraphic(new LIB().loadImageView("/icons8-image-64.png",50));
        button.setOnAction(e->{
//            containerNode.getChildren().add(this.getIamgecomponent(childStage));
            components.add(this);
            childStage.close();
        });
        return button;
    }
    public Image_Component_Class export(){
        return new Image_Component_Class(imagePath,imageView.getFitHeight());
    }
}
