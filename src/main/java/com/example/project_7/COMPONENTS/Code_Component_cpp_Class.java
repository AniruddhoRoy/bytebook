package com.example.project_7.COMPONENTS;

import com.example.project_7.CONSTANTS;
import com.example.project_7.CONSTANTS.Language;

public class Code_Component_cpp_Class extends Component_Base_Classes{
    private double size;
    private String code;
    Language language;
    Code_Component_cpp_Class(double size, String code, Language language){
        this.size = size;
        this.code = code;
        this.language = language;
    }
   public Code_Component_cpp export(){
        return new Code_Component_cpp(size,code,language);
    }
}
