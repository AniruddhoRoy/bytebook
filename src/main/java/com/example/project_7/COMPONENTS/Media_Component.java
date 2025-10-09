package com.example.project_7.COMPONENTS;

import com.example.project_7.CONSTANTS;
import com.example.project_7.LIB;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class Media_Component extends Base_Component{
    private String mediaPath = "";
    private double size = 200;
    VBox root;
    boolean isVideo = false;
    ImageView imageView;
    Stage parentStage;
    Media media;
    MediaPlayer mediaPlayer;
    MediaView mediaView;
    Button playButton = new Button("Play");
    Button pauseButton = new Button("Pause");
    Button resetButton = new Button("Reset");
    public Media_Component(boolean isVideo){
        this.isVideo = isVideo;
        initCOmponent();
    }
    void initCOmponent(){
        root = new VBox();
        root.setAlignment(Pos.CENTER);

        if(isVideo){
            imageView = new LIB().loadImageView(CONSTANTS.Media_Video_icon,this.size);
        }
        else {
        imageView = new LIB().loadImageView(CONSTANTS.Media_music_icon,this.size);
        }
        addCOntextMenuTOImageVIew();
        root.getChildren().add(imageView);
    }
    void loadMediaHandeler(ActionEvent e){
        String[] types;
        if (isVideo) {
            types = new String[]{"mp4", "mkv", "avi", "mov", "wmv", "flv", "webm", "mpeg", "mpg", "3gp"};
        } else {
            types = new String[]{"mp3", "wav", "aac", "flac", "ogg", "m4a", "wma", "alac", "aiff", "opus"};
        }

        String path = new LIB().fileOpenDialog(parentStage,types);
        if(path!=null){
            mediaPath = path;
            File file = new File(path);
            media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(false);
            mediaView = new MediaView(mediaPlayer);
            root.getChildren().clear();
            mediaView.preserveRatioProperty().setValue(true);
            mediaView.setFitHeight(size);
            addContextMenuToMediaContent();

            if(isVideo){
                root.getChildren().add(mediaView);
            }else{
                imageView = new LIB().loadImageView(CONSTANTS.Media_music_disk_icon,size);
                addCOntextMenuTOImageVIew();
                root.getChildren().add(imageView);
            }
            addMediaControlButtons();
        }
    }
    void addContextMenuToMediaContent(){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem loadMedia = new MenuItem("Load Media");
        loadMedia.setOnAction(this::loadMediaHandeler);
        Menu size = new Menu("Size");
        MenuItem delete = new MenuItem("Delete");
        MenuItem x1 = new MenuItem("1x");
        MenuItem x2 = new MenuItem("2x");
        MenuItem x3 = new MenuItem("3x");
        MenuItem x4 = new MenuItem("4x");
        x1.setOnAction(e -> mediaView.setFitHeight(100));
        x2.setOnAction(e -> mediaView.setFitHeight(200));
        x3.setOnAction(e -> mediaView.setFitHeight(300));
        x4.setOnAction(e -> mediaView.setFitHeight(400));
// Add items to submenu
        size.getItems().addAll(x1, x2, x3, x4);
        delete.setOnAction(this::delete);
        contextMenu.getItems().addAll(loadMedia, size,delete);
        mediaView.setOnContextMenuRequested(event ->{
            contextMenu.show(mediaView,event.getScreenX(), event.getScreenY());
        });
    }
    void addCOntextMenuTOImageVIew(){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem loadMedia = new MenuItem("Load Media");
        loadMedia.setOnAction(this::loadMediaHandeler);
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
        contextMenu.getItems().addAll(loadMedia, size,delete);
        imageView.setOnContextMenuRequested(event ->{
            contextMenu.show(imageView,event.getScreenX(), event.getScreenY());
        });
    }
    void addMediaControlButtons(){
        HBox controlsButton = new HBox();
        controlsButton.setSpacing(10);
        controlsButton.setAlignment(Pos.CENTER);
        playButton.setOnAction(event->{
            mediaPlayer.play();
        });
        pauseButton.setOnAction(e->{
            mediaPlayer.pause();
        });
        resetButton.setOnAction(e->{
            mediaPlayer.stop();
        });
        controlsButton.getChildren().addAll(playButton,pauseButton,resetButton);
        root.getChildren().add(controlsButton);
    }
    public  VBox getMediaCOmponent(Stage stage){

        this.parentStage = stage;
        return root;
    }
    public Button getComponentButton(ArrayList<Base_Component> components, Stage childStage){
        Button button = new Button();
        if(isVideo){
        button.setGraphic(new LIB().loadImageView(CONSTANTS.Media_Video_icon,50));
        }else {
        button.setGraphic(new LIB().loadImageView(CONSTANTS.Media_music_icon,50));
        }
        button.setOnAction(e->{
//            containerNode.getChildren().add(this.getIamgecomponent(childStage));
            components.add(this);
            childStage.close();
        });
        return button;
    }


}
