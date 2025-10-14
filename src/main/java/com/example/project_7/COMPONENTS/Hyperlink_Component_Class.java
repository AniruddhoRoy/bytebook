package com.example.project_7.COMPONENTS;

public class Hyperlink_Component_Class extends Component_Base_Classes{
    public String urlText;
    public String displayText;
    public Hyperlink_Component_Class(String displayText,String urlText)
    {
        this.displayText=displayText;
        this.urlText=urlText;
    }
    public Hyperlink_Component export()
    {
        return new Hyperlink_Component(displayText,urlText);
    }
}
