package com.example.project_7.COMPONENTS;

import com.example.project_7.CONSTANTS;
import com.example.project_7.EditPageController;
import com.example.project_7.LIB;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Base64;

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
        initComponent();
    }
    public Media_Component(String mediaPath,Double size,boolean isVideo){
        this.isVideo = isVideo;
        this.size = size;
        this.mediaPath = mediaPath;
        initComponent();
        File file = new File(mediaPath);

        // 🔹 Detach and safely dispose old player
        if (mediaView != null) mediaView.setMediaPlayer(null);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }

        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView = new MediaView(mediaPlayer);
        mediaView.setPreserveRatio(true);
        mediaView.setFitHeight(size);
        mediaView.fitWidthProperty().bind(root.widthProperty());

        addContextMenuToMediaContent();

        root.getChildren().clear();

        // 🔹 Wait until ready before showing video
        mediaPlayer.setOnReady(() -> {
            if (isVideo) {
                root.getChildren().add(mediaView);
            } else {
                imageView = new LIB().loadImageView(CONSTANTS.Media_music_disk_icon, size);
                addContextMenuTOImageVIew();
                root.getChildren().add(imageView);
            }
            addMediaControlButtons();
    });
    }
    void initComponent(){
        root = new VBox();
        root.setAlignment(Pos.CENTER);

        if(isVideo){
            imageView = new LIB().loadImageView(CONSTANTS.Media_Video_icon,this.size);
        }
        else {
        imageView = new LIB().loadImageView(CONSTANTS.Media_music_icon,this.size);
        }
        addContextMenuTOImageVIew();
        root.getChildren().add(imageView);
    }
void loadMediaHandeler(ActionEvent e){
    String[] types = isVideo
            ? new String[]{"mp4", "mkv", "avi", "mov", "wmv", "flv", "webm", "mpeg", "mpg", "3gp"}
            : new String[]{"mp3", "wav", "aac", "flac", "ogg", "m4a", "wma", "alac", "aiff", "opus"};

    String path = new LIB().fileOpenDialog(parentStage, types);
    if (path == null) return;

    mediaPath = path;
    File file = new File(path);

    // 🔹 Detach and safely dispose old player
    if (mediaView != null) mediaView.setMediaPlayer(null);
    if (mediaPlayer != null) {
        mediaPlayer.stop();
        mediaPlayer.dispose();
        mediaPlayer = null;
    }

    media = new Media(file.toURI().toString());
    mediaPlayer = new MediaPlayer(media);
    mediaView = new MediaView(mediaPlayer);
    mediaView.setPreserveRatio(true);
    mediaView.setFitHeight(size);
    mediaView.fitWidthProperty().bind(root.widthProperty());

    addContextMenuToMediaContent();

    root.getChildren().clear();

    // 🔹 Wait until ready before showing video
    mediaPlayer.setOnReady(() -> {
        if (isVideo) {
            root.getChildren().add(mediaView);
        } else {
            imageView = new LIB().loadImageView(CONSTANTS.Media_music_disk_icon, size);
            addContextMenuTOImageVIew();
            root.getChildren().add(imageView);
        }
        addMediaControlButtons();
    });
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
    //Custom Delete Method
    @Override
    void delete(ActionEvent e) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
        root.getChildren().clear();
    }
    void addContextMenuTOImageVIew(){
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
    public  VBox getMediaComponent(Stage stage){

        this.parentStage = stage;
        return root;
    }
    public Button getComponentButton(ArrayList<Base_Component> components, EditPageController instance,boolean isVideo){
        Button button = new Button();
        if(isVideo){
        button.setGraphic(new LIB().loadImageView(CONSTANTS.Media_Video_icon,50));
        }else {
        button.setGraphic(new LIB().loadImageView(CONSTANTS.Media_music_icon,50));
        }
        Tooltip tooltip=new Tooltip("Media tool");
        Tooltip.install(button,tooltip);
        button.setOnAction(e->{
//            containerNode.getChildren().add(this.getIamgecomponent(childStage));
            components.add(new Media_Component(isVideo));
            instance.refresh();
        });
        return button;
    }
    public Media_Component_Class export(){
        return new Media_Component_Class(this.mediaPath,this.size,this.isVideo);
    }
    public String getHtml() throws Exception {

        String base64 = LIB.Image_to_string(imageView.getImage());

        return "<div style=\"display: flex; justify-content: center;\"><img src='data:image/png;base64," + base64 + "' style='width:auto; height:200px;' /></div>";
    }


}
