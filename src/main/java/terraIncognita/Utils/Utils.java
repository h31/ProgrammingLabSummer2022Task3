package terraIncognita.Utils;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import terraIncognita.Controllers.BasicController;
import terraIncognita.Utils.Exceptions.ExceptionsUtils;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

    public static BasicController loadFXMLScene(URL resource) {
        Parent root;
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(resource));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BasicController controller = loader.getController();
        controller.setRuledScene(new Scene(root));
        return controller ;
    }

    public static String genUrlOf(String file) {
        return new File(file).toURI().toString();
    }

    public static void logError(Exception e) {
        System.err.println(e.getMessage());
        for (var traceEl : e.getStackTrace()) {
            System.err.println(traceEl.toString());
        }
    }

    public static void logErrorWithExit(Exception e) {
        if (TestUtils.isConsoleOutput()) {
            TestUtils.pushExitException(e);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(e.toString());
        alert.setHeaderText(e.getMessage());

        VBox content = new VBox();
        Label label = new Label("Stack trace:");

        String stackTrace = ExceptionsUtils.getStackTrace(e);
        TextArea textArea = new TextArea();
        textArea.setText(stackTrace);

        content.getChildren().addAll(label, textArea);
        alert.getDialogPane().setContent(content);

        alert.showAndWait();

        Platform.exit();
    }

    public static List<File> loadFilesFrom(String labyrinthsDir) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(labyrinthsDir))) {
            return stream
                    .filter(path -> !Files.isDirectory(path))
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        }
    }
}
