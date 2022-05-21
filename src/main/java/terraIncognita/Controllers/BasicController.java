package terraIncognita.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.Scene;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class BasicController implements Initializable {

    protected Scene ruledScene;

    @Override
    public abstract void initialize(URL location, ResourceBundle resources);

    public void setRuledScene(Scene scene) {
        ruledScene = scene;
    }
    public Scene getRuledScene() {
        return ruledScene;
    }

    public abstract void setup();
}
