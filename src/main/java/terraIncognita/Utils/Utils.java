package terraIncognita.Utils;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import org.apache.commons.io.FilenameUtils;
import terraIncognita.App;
import terraIncognita.Controllers.BasicController;
import terraIncognita.Utils.Exceptions.ExceptionWrapper;
import terraIncognita.Utils.Exceptions.ExceptionsUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Utils {

    public static BasicController loadFXMLScene(URL resource) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(resource));
        try {
            root = loader.load();
        } catch (IOException e) {
            Utils.logErrorWithExit(e);
        }
        BasicController controller = loader.getController();
        controller.setRuledScene(new Scene(root));
        return controller ;
    }

    public static void logErrorWithExit(Exception err){
        if (TestUtils.isConsoleOutput()) {
            if (err instanceof RuntimeException) {
                throw (RuntimeException)err;
            }
            throw new ExceptionWrapper(err);
        }
        Throwable e = Objects.requireNonNullElse(err.getCause(), err);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(e.toString());
        alert.setHeaderText(e.getMessage());

        VBox content = new VBox();
        Label label = new Label("Stack trace:");

        //String stackTrace = App.getAppPath() + "\n" + ExceptionsUtils.getStackTrace(e);
        String stackTrace = ExceptionsUtils.getStackTrace(e);
        TextArea textArea = new TextArea();
        textArea.setText(stackTrace);

        content.getChildren().addAll(label, textArea);
        alert.getDialogPane().setContent(content);

        alert.showAndWait();

        Platform.exit();
    }

    public static List<String> loadSeparateLinesFrom(InputStream inputStream) throws IOException {
        List<String> res = new LinkedList<>();
        try(BufferedReader input = new BufferedReader(new InputStreamReader(inputStream))) {
            String labyrinthName = input.readLine();
            while(labyrinthName != null) {
                res.add(labyrinthName);
                labyrinthName = input.readLine();
            }
        } catch (IOException e){
            Utils.logErrorWithExit(e);
        }
        return res;
    }
}
