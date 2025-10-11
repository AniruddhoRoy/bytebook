package com.example.project_7.COMPONENTS;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.*;

public class Code_Component_Delete extends Application {
    private SplitPane splitPane;
    private CodeArea codeArea;
    private TextArea terminal;

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
                string name = "Radha Krishna";
                cout << "Enter your name: "<<endl;
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

        terminal = new TextArea();
        terminal.setWrapText(true);
        terminal.setEditable(true);
        terminal.setStyle(
                "-fx-control-inner-background: black;" +
                        "-fx-font-family: 'Consolas';" +
                        "-fx-highlight-fill: gray;" +
                        "-fx-highlight-text-fill: black;" +
                        "-fx-text-fill: white;"
        );

        VBox.setVgrow(terminal, Priority.ALWAYS);

        VBox terminalContainer = new VBox(5, runButton, terminal);
        terminalContainer.setAlignment(Pos.CENTER);

        runButton.setOnAction(e -> {
            terminal.clear();
            new Thread(() -> runCpp(codeArea.getText(), terminal)).start();
        });

        splitPane.getItems().add(terminalContainer);
        splitPane.setDividerPositions(0.6); // top = 60%, bottom = 40%
    }

    /** Compile and run the C++ code */
    private void runCpp(String sourceCode, TextArea terminal) {
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

            // Read output in background thread
            new Thread(() -> {
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        appendText(terminal, line + "\n");
                    }
                } catch (IOException ignored) {}
            }).start();

            // Allow user to send input on Enter
            terminal.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case ENTER -> {
                        try {
                            String[] lines = terminal.getText().split("\n");
                            String lastLine = lines[lines.length - 1];
                            writer.write(lastLine);
                            writer.newLine();
                            writer.flush();
                        } catch (IOException ex) {
                            appendText(terminal, "\n[Input error]\n");
                        }
                    }
                }
            });

            runProcess.waitFor();
            appendText(terminal, "\n> Program finished.\n");

        } catch (Exception ex) {
            appendText(terminal, "Error: " + ex.getMessage() + "\n");
        }
    }
    /** Thread-safe UI updates */
    private void appendText(TextArea terminal, String text) {
        //this is used because code is running in other threads
        Platform.runLater(() -> terminal.appendText(text));
    }
}
