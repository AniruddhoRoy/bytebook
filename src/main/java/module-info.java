module com.example.project_7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.web;
    requires org.fxmisc.richtext;
    requires org.fxmisc.flowless;
    requires javafx.media;
    requires reactfx;
    requires html2pdf;
    requires javafx.swing;
    requires javafx.graphics;
    requires org.jsoup;

    opens com.example.project_7 to javafx.fxml;
    exports com.example.project_7;
    exports com.example.project_7.COMPONENTS;
}