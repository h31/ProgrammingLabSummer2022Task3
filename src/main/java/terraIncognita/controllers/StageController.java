package terraIncognita.controllers;

import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import terraIncognita.utils.exceptions.SceneNotFoundException;
import terraIncognita.utils.Utils;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class StageController {

    private final Stage stage;
    private final Map<String, BasicController> controllers = new HashMap<String, BasicController>();

    public StageController(Stage primaryStage) {
        stage = primaryStage;
    }

    /**
     * Returns controller of a scene with name sceneName added into stage controller.
     * @param sceneName String
     * @return BasicController
     */
    public BasicController getControllerOf (String sceneName){
        if (!controllers.containsKey(sceneName)) {
            Utils.logErrorWithExit(new SceneNotFoundException("No Scene " + sceneName + " found"));
            return null;
        }
        return controllers.get(sceneName);
    }

    public void showScene() {
        stage.show();
    }

    public void loadScene(@NotNull URL fileName) {
        String sceneName = FilenameUtils.getBaseName(fileName.getPath());
        if (controllers.containsKey(sceneName)) {
            return;
        }
        controllers.put(sceneName, Utils.loadFXMLScene(fileName));
    }

    /**
     * Sets active scene to the given.
     * Invoke showScene() to actually make the scene appear.
     * @param sceneName - String
     */
    public void prepareScene(String sceneName) {
        if(!controllers.containsKey(sceneName)) {
            Utils.logErrorWithExit(new SceneNotFoundException("No Scene \"" + sceneName + "\" found"));
            return;
        }
        stage.setScene(controllers.get(sceneName).getRuledScene());
    }

}
