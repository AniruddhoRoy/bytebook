package com.example.project_7.COMPONENTS;

import com.example.project_7.CONSTANTS;
import com.example.project_7.LIB;
import javafx.event.ActionEvent;


public class Base_Component {
    public String id = LIB.generate_Id(CONSTANTS.Component_id_lenght);

    void delete(ActionEvent e){
        LIB.DeleteComponent(this.id);
    }
}
