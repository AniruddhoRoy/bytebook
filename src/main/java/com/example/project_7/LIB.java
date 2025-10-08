package com.example.project_7;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Random;

public class LIB {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generate_Id(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }
    void setIconAndTitle(Stage stage, String imagePath, String Title){
        try{
            stage.getIcons().add(new Image(getClass().getResourceAsStream(imagePath)));
        }catch (Exception e){
            System.out.println("Error Loading Image");
            System.out.println(e);
        }

        stage.setTitle(Title);
    }
    public  ImageView loadImageView(String imagePath, double height){
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(height);
        return imageView;
    }
    public static String directoryChooser(Stage stage){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Location");
        File dir = directoryChooser.showDialog(stage);
        if(dir!=null)
        {
            return dir.getAbsolutePath();
        }
        return null;
    }
   public static String  fileOpenDialog(Stage stage , String[] types){
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Select Your File");
    for(String type :types){
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(type,"*"+type));
    }
//    FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("file","*"+CONSTANT.Applicaiton_extention);
//    fileChooser.getExtensionFilters().addAll(filter);
    File file = fileChooser.showOpenDialog(stage);
    if(file!=null){
        try{
        return file.getAbsolutePath();
        }catch (Exception e){
            System.out.println("Error While Reading the file");
            System.out.println(e);
        }

    }
    return null;
}
    static boolean isValidFileName(String fileName) {
        // Return false if null or empty
        if (fileName == null || fileName.isEmpty()) return false;

        // Regex: only letters, digits, underscores, and hyphens allowed
        return fileName.matches("[a-zA-Z0-9_-]+");
    }
}
