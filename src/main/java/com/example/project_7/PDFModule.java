package com.example.project_7;

import com.itextpdf.html2pdf.HtmlConverter;

import java.io.FileOutputStream;

public class PDFModule {
    public static void main(String[] args) throws Exception {
        String html = """
                <html>
                    <head>
                        <style>
                            body {
                                font-family: 'Arial';
                                margin: 30px;
                            }
                            h1 {
                                color: #2E86C1;
                                text-align: center;
                            }
                            p {
                                color: #555;
                                font-size: 14px;
                                line-height: 1.5;
                            }
                            .highlight {
                                background-color: yellow;
                                font-weight: bold;
                            }
                            img {
                                width: 200px;
                                display: block;
                                margin: 20px auto;
                            }
                        </style>
                    </head>
                    <body>
                        <h1>Export from JavaFX</h1>
                        <p>This is <span class='highlight'>styled with CSS</span> and exported as PDF.</p>
                        <img src='https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png' />
                    </body>
                </html>
                """;

        HtmlConverter.convertToPdf(html, new FileOutputStream("styled_with_css.pdf"));
        System.out.println("✅ PDF created: styled_with_css.pdf");
    }
}
