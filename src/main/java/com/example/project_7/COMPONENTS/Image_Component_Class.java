package com.example.project_7.COMPONENTS;

import java.io.Serializable;

public class Image_Component_Class extends Component_Base_Classes implements Serializable {
    private String path= "";
    private double size;
    Image_Component_Class(String path, double size){
        this.size = size;
        this.path = path;
    }
    public Image_Component export(){
        return new Image_Component(path,size);
    }
    public String toString(){
        return (path+" "+size);
    }

}