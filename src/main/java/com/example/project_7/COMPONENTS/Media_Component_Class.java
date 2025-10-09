package com.example.project_7.COMPONENTS;

public class Media_Component_Class extends Component_Base_Classes{
    private String mediaPath = "";
    private double size = 200;
    boolean isVideo = false;
    Media_Component_Class(String mediaPath,Double size,boolean isVideo){
        this.isVideo= isVideo;
        this.mediaPath = mediaPath;
        this.size = size;
    }
    public Media_Component export(){
        return new Media_Component(mediaPath,size,isVideo);
    }
    public String toString(){
        return (mediaPath+" "+size +" "+isVideo);
    }
}
