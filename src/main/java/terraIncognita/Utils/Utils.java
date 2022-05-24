package terraIncognita.Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import terraIncognita.Controllers.BasicController;
import terraIncognita.Main;
import terraIncognita.Model.Desk.Desk;
import terraIncognita.Model.Desk.Labyrinth;
import terraIncognita.Model.Tiles.*;

import java.io.*;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Utils {

    public static BasicController loadFXMLScene (URL resource) {
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

    public static List<File> loadFilesFrom(String labyrinthsDir) {
        return (List<File>) FileUtils.listFiles(new File(labyrinthsDir), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
    }
}
