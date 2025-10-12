package com.example.project_7.COMPONENTS;

public class Code_Component_cpp_Class extends Component_Base_Classes{
    private double size;
    private String code;
    private String output;
    Code_Component_cpp_Class(double size,String code , String output){
        this.size = size;
        this.code = code;
        this.output = output;
    }
   public Code_Component_cpp export(){
        return new Code_Component_cpp(size,code,output);
    }
}
