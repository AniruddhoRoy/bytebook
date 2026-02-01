package com.example.project_7.COMPONENTS;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Image_Component_Class extends Component_Base_Classes implements Serializable {
    private double size;
    private String base64;
    Image_Component_Class(String base64, double size){
        this.size = size;
        this.base64 = base64;
    }
    public Image_Component export(){
        return new Image_Component(base64,size);
    }
    public String toString(){
        return (base64+" "+size);
    }

}