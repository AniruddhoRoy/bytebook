package com.example.project_7;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Custome_codeArea extends CodeArea {
    public Custome_codeArea(){
        this.setWrapText(true);
        this.autosize();
        this.setParagraphGraphicFactory(LineNumberFactory.get(this));
        this.textProperty().addListener((obs, oldText, newText) -> {
            this.setStyleSpans(0, computeHighlighting(newText));
        });
//        this.getScene().getStylesheets().add(getClass().getResource("/CSS/codeArea.css").toExternalForm());

        sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.getStylesheets().add(
                        getClass().getResource("/CSS/codeArea.css").toExternalForm()
                );
            }
        });
    }
    private static final String[] KEYWORDS = new String[] {
            "abstract","assert","boolean","break","byte","case","catch","char","class",
            "const","continue","default","do","double","else","enum","extends","final",
            "finally","float","for","goto","if","implements","import","instanceof",
            "int","interface","long","native","new","package","private","protected",
            "public","return","short","static","strictfp","super","switch","synchronized",
            "this","throw","throws","transient","try","void","volatile","while"
    };

    private static final String KEYWORD_PATTERN =
            "\\b(" + String.join("|", KEYWORDS) + ")\\b";

    private static final String COMMENT_PATTERN =
            "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    private static final String STRING_PATTERN =
            "\"([^\"\\\\]|\\\\.)*\"";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
    );
    private static StyleSpans<Collection<String>> computeHighlighting(String text) {

        Matcher matcher = PATTERN.matcher(text);
        int lastEnd = 0;

        StyleSpansBuilder<Collection<String>> spansBuilder =
                new StyleSpansBuilder<>();

        while (matcher.find()) {

            String style =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("COMMENT") != null ? "comment" :
                                    matcher.group("STRING") != null ? "string" :
                                            null;

            spansBuilder.add(java.util.Collections.emptyList(),
                    matcher.start() - lastEnd);

            spansBuilder.add(java.util.Collections.singleton(style),
                    matcher.end() - matcher.start());

            lastEnd = matcher.end();
        }

        spansBuilder.add(java.util.Collections.emptyList(),
                text.length() - lastEnd);

        return spansBuilder.create();
    }
}
