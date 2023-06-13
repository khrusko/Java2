package hr.algebra.khruskoj2.controller;

import hr.algebra.khruskoj2.utils.ReflectionUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.*;

public class DocumentationController {

    @FXML
    private TextArea taDocumentation;

    public void initialize() {
        ReflectionUtils.generateDocumentation();
        String documentation = loadDocumentationFromFile();
        taDocumentation.setText(documentation);
    }

    private String loadDocumentationFromFile() {
        StringBuilder sb = new StringBuilder();
        try (InputStream is = new FileInputStream("Documentation.html");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Replace HTML line breaks with newline characters
                line = line.replaceAll("<br />", "\n");
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


}
