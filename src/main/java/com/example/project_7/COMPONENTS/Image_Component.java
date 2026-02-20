package com.example.project_7.COMPONENTS;

import com.example.project_7.CONSTANTS;
import com.example.project_7.EditPageController;
import com.example.project_7.LIB;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

import static com.example.project_7.Dialogs.*;

public class Image_Component extends Base_Component{
    //image display size
    private double size = 200;
    ImageView imageView ;
    //after hovering shows text
    Tooltip tooltip = new Tooltip();
    public Image_Component()
    {
        //default placeholder image loads
        imageView = new LIB().loadImageView(
                CONSTANTS.Default_image_icon_path,150
        );
    }
    public Image_Component(String base64,double size){
        this.size = size;

        try{
            imageView = new ImageView();
            imageView.setPreserveRatio(true);
            imageView.setImage(LIB.String_to_Image(base64));
            imageView.setFitHeight(size);
        }catch (Exception e){
            ErrorAlert("Image Loading Error","Something Broken",e.getMessage());
            System.out.println("cannot convert string to image");
            imageView = new LIB().loadImageView(CONSTANTS.Default_image_icon_path,size);
            tooltip.setText("Add a new image");
        }
        Tooltip.install(imageView,tooltip);//Attach tooltip
    }

    public VBox getIamgecomponent(Stage stage){
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(imageView);

        //Explanation:
        //It tells JavaFX to treat the entire rectangular area (the bounds) of the ImageView as clickable and hoverable,
        // including transparent parts of the image.
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
        delete.setOnAction(e->{
            if(ConformationAlert("Delete code","Are you sure?","do you really want to delete this code?")){
                delete(e);
            }
        });
        contextMenu.getItems().addAll(loadImage, size,delete);
        loadImage.setOnAction(e->{
            String[] types = {"png", "jpg", "jpeg", "gif", "bmp", "webp"};
            String path = new LIB().fileOpenDialog(stage,types);

            if (path == null) {
                WarningAlert("Warning","No Image Selected","Please select an image file.");
//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.setTitle("No Image Selected");
//                alert.setHeaderText(null);
//                alert.setContentText("Please select an image file.");
//                alert.show();
                return;
            }
            //shows selected image

            if(path!=null){
                File file = new File(path);
                Image image = new Image(file.toURI().toString()); // Convert path to URI
                imageView.setImage(image);
                InformationAlert("Image Loaded","Success","Image loaded successfully:\n" + file.getName());
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                  alert.setTitle("Image Loaded");
//                  alert.setHeaderText("Success");
//                  alert.setContentText("Image loaded successfully:\n" + file.getName());
//                  alert.show();

                //update tooltip text with file name
                tooltip.setText("Image: "+file.getName());
            }
        });
        // Show ContextMenu on right-click
        imageView.setOnContextMenuRequested(event -> {
            contextMenu.show(imageView, event.getScreenX(), event.getScreenY());
        });
        return root;
    }
   public Button getComponentButton(ArrayList<Base_Component> components, EditPageController instance){
        Button button = new Button();
       tooltip=new Tooltip("Add image");
       Tooltip.install(button,tooltip);
        button.setGraphic(new LIB().loadImageView("/icons8-image-64.png",50));
        button.setOnAction(e->{
//            containerNode.getChildren().add(this.getIamgecomponent(childStage));
//            components.add(this);
            components.add(new Image_Component());
            instance.refresh();
        });
        return button;
    }
    public Image_Component_Class export() {
        try{
        return new Image_Component_Class(LIB.Image_to_string(imageView.getImage()),imageView.getFitHeight());
        }catch (Exception e){
            ErrorAlert("Image Exporting Error","Something Broken internally",e.getMessage());
            System.out.println(e);
        }
        return null;
    }
    public String getHtml()throws Exception{

            String base64 = LIB.Image_to_string(imageView.getImage());

            // Return HTML <img> tag with base64 data
//            return "<img src='data:image/png;base64," + base64 + "' style='max-width:100%; height:auto;' />";
        return "<div style='text-align: center; padding:10px'>" +
                "<img src='data:image/png;base64," + base64 + "' style='max-width:100%; height:auto;' />" +
                "</div>";


    }
}
//test line