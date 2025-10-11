package com.example.project_7.COMPONENTS;

public class Paragraph_Component_Class extends Component_Base_Classes{
    String initialValue = "";
    double size;
    Paragraph_Component_Class(String initialValue,double size){
        this.initialValue = initialValue;
        this.size = size;
    }
    public Paragraph_Component export(){
        return new Paragraph_Component(this.initialValue,this.size);
    }
}
