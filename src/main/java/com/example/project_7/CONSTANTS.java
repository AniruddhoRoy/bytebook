package com.example.project_7;

import javafx.util.Pair;

public class CONSTANTS {
    //basic
    public static final String Applicaiton_icon_path = "/application_icon.png";
    public static final String Default_image_icon_path = "/Default_imageIcon.png";
    public static final String Applicaiton_Extention = ".bbk";
    public static final String Applicaiton_Name = "ByteBook";
    public static final int Component_id_lenght = 6;

    //Heading Component
    public static final String Heading_Component_Icon = "/Heading_Image.png";
    public static final Pair<String, String>[] Background_colors = new Pair[] {
            new Pair<>("White", "-fx-background-color: white;"),
            new Pair<>("LightYellow", "-fx-background-color: lightyellow;"),
            new Pair<>("LightBlue", "-fx-background-color: lightblue;"),
            new Pair<>("LightGreen", "-fx-background-color: lightgreen;"),
            new Pair<>("LightGray", "-fx-background-color: lightgray;"),
            new Pair<>("Beige", "-fx-background-color: beige;"),
            new Pair<>("Lavender", "-fx-background-color: lavender;"),
            new Pair<>("Pink", "-fx-background-color: pink;"),
            new Pair<>("PeachPuff", "-fx-background-color: peachpuff;"),
            new Pair<>("MistyRose", "-fx-background-color: mistyrose;"),
            new Pair<>("LightCoral", "-fx-background-color: lightcoral;"),
            new Pair<>("AliceBlue", "-fx-background-color: aliceblue;"),
            new Pair<>("Honeydew", "-fx-background-color: honeydew;"),
            new Pair<>("Thistle", "-fx-background-color: thistle;"),
            new Pair<>("LavenderBlush", "-fx-background-color: lavenderblush;")
    };
    public static final Pair<String, String>[] headingColors = new Pair[] {
            new Pair<>("Black", "-fx-text-fill: black;"),
            new Pair<>("DarkBlue", "-fx-text-fill: darkblue;"),
            new Pair<>("DarkRed", "-fx-text-fill: darkred;"),
            new Pair<>("DarkGreen", "-fx-text-fill: darkgreen;"),
            new Pair<>("Purple", "-fx-text-fill: purple;"),
            new Pair<>("Maroon", "-fx-text-fill: maroon;"),
            new Pair<>("Navy", "-fx-text-fill: navy;"),
            new Pair<>("Teal", "-fx-text-fill: teal;"),
            new Pair<>("DarkOrange", "-fx-text-fill: darkorange;"),
            new Pair<>("Crimson", "-fx-text-fill: crimson;"),
            new Pair<>("MediumBlue", "-fx-text-fill: mediumblue;"),
            new Pair<>("DarkMagenta", "-fx-text-fill: darkmagenta;"),
            new Pair<>("SaddleBrown", "-fx-text-fill: saddlebrown;"),
            new Pair<>("Indigo", "-fx-text-fill: indigo;"),
            new Pair<>("Olive", "-fx-text-fill: olive;")
    };
    public static final  Pair<String, String>[] fontFamilys = new Pair[] {
            new Pair<>("Arial", "-fx-font-family: 'Arial';"),
            new Pair<>("Verdana", "-fx-font-family: 'Verdana';"),
            new Pair<>("Tahoma", "-fx-font-family: 'Tahoma';"),
            new Pair<>("Georgia", "-fx-font-family: 'Georgia';"),
            new Pair<>("Times New Roman", "-fx-font-family: 'Times New Roman';"),
            new Pair<>("Courier New", "-fx-font-family: 'Courier New';"),
            new Pair<>("Impact", "-fx-font-family: 'Impact';"),
            new Pair<>("Comic Sans MS", "-fx-font-family: 'Comic Sans MS';"),
            new Pair<>("Lucida Console", "-fx-font-family: 'Lucida Console';"),
            new Pair<>("Palatino Linotype", "-fx-font-family: 'Palatino Linotype';")
    };
    public static final Pair<String, String>[] fontSizes = new Pair[] {
            new Pair<>("Small", "-fx-font-size: 16px;"),
            new Pair<>("Medium", "-fx-font-size: 18px;"),
            new Pair<>("Large", "-fx-font-size: 20px;"),
            new Pair<>("Extra Large", "-fx-font-size: 22px;"),
            new Pair<>("XXL", "-fx-font-size: 24px;"),
            new Pair<>("Huge", "-fx-font-size: 26px;"),
            new Pair<>("Giant", "-fx-font-size: 28px;"),
            new Pair<>("Massive", "-fx-font-size: 30px;"),
            new Pair<>("Enormous", "-fx-font-size: 32px;"),
            new Pair<>("Colossal", "-fx-font-size: 36px;")
    };
    public static final Pair<String, String>[] fontWeights = new Pair[] {
            new Pair<>("Normal", "-fx-font-weight: normal;"),
            new Pair<>("Bold", "-fx-font-weight: bold;"),
            new Pair<>("Lighter", "-fx-font-weight: lighter;"),

    };
    //Media component
    public static final String Media_Video_icon = "/Default_video_icon.png";
    public static final String Media_music_icon = "/Default_music_icon.png";
    public static final String Media_music_disk_icon = "/Default_music_disk_icon.png";
    //Paragraph Component
    public static final String Paragraph_icon = "/Default_Paragraph_Icon.png";
    //Code component
    public static final String Cpp_Icon = "/Default_Cpp_Icon.png";
    public static final String Cpp_default_snippet = """
            #include <iostream>
            
            using namespace std;
            int main() {
                string name ;
                cout << "Enter your name: "<<endl;
                cin>>name;
                cout << "Hello, " << name << "!" << endl;
                return 0;
            }
            """;
}
