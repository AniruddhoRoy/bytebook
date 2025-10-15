package com.example.project_7;
import com.example.project_7.COMPONENTS.Base_Component;
import com.itextpdf.html2pdf.HtmlConverter;

import java.io.FileOutputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
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
    public  static void DeleteComponent(String id){
       EditPageController editPageController = EditPageController.instance;
        if (editPageController == null) {
            System.out.println("Error: EditPageController instance is null!");
            return;
        }

        for (Base_Component component : editPageController.components) {
            if (component.id.equals(id)) {
                editPageController.components.remove(component);
                System.out.println("Component Found and Removed");
                break;
            }
        }

        editPageController.refresh();
    }
    public static void export_pdf(String htmlCode,String filePath,String file_name) throws Exception{
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Ensure the file path ends with a separator
        if (!filePath.endsWith(File.separator)) {
            filePath += File.separator;
        }

        // Complete HTML document with minimal CSS reset
        String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                  <meta charset="UTF-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  <title>Exported PDF</title>
                  <style>
                    * {
                      margin: 0;
                      padding: 0;
                      box-sizing: border-box;
                    }
                    body {
                      font-family: Arial, sans-serif;
                      display: flex;
                      flex-direction: column;
                      gap: 5px;
                      padding: 10px;
                    }
                  </style>
                </head>
                <body>
                  %s
                </body>
                </html>
                """.formatted(htmlCode);

        // Create the PDF file
        String outputPath = filePath + file_name + ".pdf";
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            HtmlConverter.convertToPdf(html, fos);
        }

        System.out.println("✅ PDF created: " + outputPath);
    }
}
