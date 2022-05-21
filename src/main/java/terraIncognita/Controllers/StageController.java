package terraIncognita.Controllers;

import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import terraIncognita.Utils.Exceptions.SceneNotFoundException;
import terraIncognita.Utils.Utils;

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
     * @throws SceneNotFoundException if no Scene with given name was found.
     */
    public BasicController getControllerOf (String sceneName) throws SceneNotFoundException {
        if (!controllers.containsKey(sceneName)) {
            throw new SceneNotFoundException("No Scene " + sceneName + " found");
        }
        return controllers.get(sceneName);
    }

    public void showScene() {
        stage.show();
    }

    public void loadScene(URL fileName) {
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
     * @throws SceneNotFoundException if no scene with name sceneName was found.
     */
    public void prepareScene(String sceneName) throws SceneNotFoundException {
        if(!controllers.containsKey(sceneName)) {
            throw new SceneNotFoundException("No Scene " + sceneName + " found");
        }
        stage.setScene(controllers.get(sceneName).getRuledScene());
    }

}
