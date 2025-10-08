package com.example.project_7;

import com.example.project_7.COMPONENTS.Component_Base_Classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Process_Class implements Serializable {
    private static final long serialVersionUID = 1L;

    String fileName;
    ArrayList<Component_Base_Classes> components = new ArrayList<>();
    void save(String path){
        FILEIO.writeProcess(this,path+"/"+this.fileName+CONSTANTS.Applicaiton_Extention);
    }
    static Process_Class read(String path ){
        Process_Class processClass = FILEIO.readProcess(path);
        return processClass;
    }
}
