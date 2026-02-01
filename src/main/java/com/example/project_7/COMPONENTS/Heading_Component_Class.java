package com.example.project_7.COMPONENTS;

public class Heading_Component_Class extends Component_Base_Classes{
    String heading_text = "";
    String style_background_color = "-fx-background-color: lightyellow;" ;
    String style_font_weight = "-fx-font-weight: bold;";
    String style_font_Size = "-fx-font-size: 20px;";
    String style_font_family = "-fx-font-family: 'Arial';";
    String style_font_color = "-fx-text-fill: darkblue;";
    String style_font_alignment;
    public Heading_Component_Class(
            String heading_text,
            String style_background_color,
            String style_font_weight,
            String style_font_Size,
            String style_font_family,
            String style_font_color,
            String style_font_alignment
    ) {
        this.heading_text = heading_text;
        this.style_background_color = style_background_color;
        this.style_font_weight = style_font_weight;
        this.style_font_Size = style_font_Size;
        this.style_font_family = style_font_family;
        this.style_font_color = style_font_color;
        this.style_font_alignment = style_font_alignment;
    }
    @Override
    public String toString() {
        return "Heading_Component_Class {" +
                "heading_text='" + heading_text + '\'' +
                ", style_background_color='" + style_background_color + '\'' +
                ", style_font_weight='" + style_font_weight + '\'' +
                ", style_font_Size='" + style_font_Size + '\'' +
                ", style_font_family='" + style_font_family + '\'' +
                ", style_font_color='" + style_font_color + '\'' +
                ", alignment='" + style_font_alignment + '\'' +
                '}';
    }

    public Heading_Component export(){
        return new Heading_Component(
                heading_text,
                style_background_color,
                style_font_weight,
                style_font_Size,
                style_font_family,
                style_font_color,
                style_font_alignment
        );
    }
}
