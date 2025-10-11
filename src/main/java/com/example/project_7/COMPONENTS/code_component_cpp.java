package com.example.project_7.COMPONENTS;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.*;

public class code_component_cpp extends Application {
    private SplitPane splitPane;
    private CodeArea codeArea;
    private CodeArea terminal;
    private int initialLength;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);

        splitPane = new SplitPane();
//        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.setPrefSize(900, 500);

        loadCodeArea();
        loadTerminal();

        root.getChildren().add(splitPane);

        Scene scene = new Scene(root, 900, 500);
        stage.setScene(scene);
        stage.setTitle("Code Component Demo");
        stage.show();
    }

    /** Create and add the code editor area */
    private void loadCodeArea() {
        codeArea = new CodeArea();
        codeArea.setWrapText(true);
        codeArea.setStyle("-fx-font-size: 16px; -fx-font-family: 'Consolas';");
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.replaceText("""
            #include <iostream>
            
            using namespace std;
            int main() {
                string name ;
                cout << "Enter your name: "<<endl;
                cin>>name;
                cout << "Hello, " << name << "!" << endl;
                return 0;
            }
            """);

        VBox codeBox = new VBox(codeArea);
        VBox.setVgrow(codeArea, Priority.ALWAYS);

        splitPane.getItems().add(codeBox);
    }

    /** Create and add the terminal section */
    private void loadTerminal() {
        Button runButton = new Button("Run");

        terminal = new CodeArea("Byte book compiler v0.7 ==>");
        terminal.setWrapText(true);
//        terminal.setEditable(false);
        terminal.setStyle(
                "-fx-background-color: white;" +
                        "-fx-font-family: 'Consolas';" +
                        "-fx-highlight-fill: gray;" +
                        "-fx-highlight-text-fill: white;" +
                        "-fx-text-fill: blue;"
        );

        VBox.setVgrow(terminal, Priority.ALWAYS);
        VBox terminalContainer = new VBox(5, runButton, terminal);
        terminalContainer.setAlignment(Pos.CENTER);
        initialLength = terminal.getLength();
        terminal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.BACK_SPACE) {
                int caret = terminal.getCaretPosition();
                if (caret > initialLength) {
                    // Handles surrogate pairs correctly
                    String text = terminal.getText();
                    int prev = Character.offsetByCodePoints(text, caret, -1);
                    terminal.replaceText(prev, caret, "");
                }
                event.consume(); // Prevent default behavior
            }
        });
        terminal.caretPositionProperty().addListener((obs, oldPos, newPos) -> {
            try{
                if (newPos < initialLength) {
//                        codeArea.appendText(lastCharacter);
                    terminal.moveTo(initialLength);  // force caret to end of read-only content
                }
            }catch (Exception exception){

            }

        });
        runButton.setOnAction(e -> {
            terminal.clear();
            runCpp(codeArea.getText(),terminal);
        });

        splitPane.getItems().add(terminalContainer);
        splitPane.setDividerPositions(0.6); // top = 60%, bottom = 40%
    }
     void runCpp(String sourceCode, CodeArea terminal) {
         try {
             File cppFile = new File("temp.cpp");
             try (FileWriter writer = new FileWriter(cppFile)) {
                 writer.write(sourceCode);
             }

             appendText(terminal, "> Compiling...\n");
             Process compileProcess = new ProcessBuilder("g++", cppFile.getAbsolutePath(), "-o", "temp.exe")
                     .redirectErrorStream(true)
                     .start();

             String compileOutput = new String(compileProcess.getInputStream().readAllBytes());
             int compileExit = compileProcess.waitFor();

             if (compileExit != 0) {
                 appendText(terminal, "Compilation failed:\n" + compileOutput + "\n");
                 return;
             }

             appendText(terminal, "Compilation successful.\n> Running program...\n");
             Process runProcess = new ProcessBuilder("./temp.exe")
                     .redirectErrorStream(true)
                     .start();

             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(runProcess.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
             new Thread(() -> {
                 try {
                     int c;
                     while ((c = reader.read()) != -1) { // read char by char
                         char ch = (char) c;
                         appendText(terminal,String.valueOf(ch));
                     }
                 } catch (IOException ignored) {}
             }).start();
             terminal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                 if (event.getCode() == KeyCode.ENTER) {
                     event.consume(); // stop default newline
                     String text = terminal.getText(initialLength, terminal.getLength());
                     try {
                         appendText(terminal,"\n");
                         writer.write(text);
                         writer.newLine();
                         writer.flush();
                     }catch (IOException ex) {
//                         appendText(terminal, "\n[Input error]\n");
                     }

                 }
             });
             new Thread(() -> {
                 try {
                     runProcess.waitFor();
                     Platform.runLater(() -> appendText(terminal, "\n> Program finished.\n"));
                 } catch (InterruptedException ignored) {}
             }).start();
         }catch (Exception ex) {
             appendText(terminal, "Error: " + ex.getMessage() + "\n");
         }

     }
         private void appendText(CodeArea terminal, String text) {
             //this is used because code is running in other threads
             Platform.runLater(() ->{
                 terminal.appendText(text);
                 initialLength = terminal.getLength();
             });

         }
}
