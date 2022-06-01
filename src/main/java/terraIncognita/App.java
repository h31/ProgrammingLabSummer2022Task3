package terraIncognita;

import javafx.application.Application;
import javafx.stage.Stage;
import terraIncognita.controllers.StageController;
import terraIncognita.model.Game;
import terraIncognita.utils.ResourceLoader;
import terraIncognita.utils.Utils;

import java.util.Objects;
import java.util.Timer;

public class App extends Application {
    public static final String IMG_RELATIVE_DIR = "img/";
    public static final String TILES_IMG_RELATIVE_DIR = IMG_RELATIVE_DIR + "tiles/";
    public static final String LABYRINTHS_RELATIVE_DIR = "labyrinths/";

    public static final String START_WINDOW_SCENE_NAME = "StartWindow";
    public static final String GAME_WINDOW_SCENE_NAME = "GameWindow";
    public static final String END_WINDOW_SCENE_NAME = "EndWindow";

    public static final ResourceLoader resourceLoader = new ResourceLoader();

    public static final Timer timer = new Timer();

    public static Game game = new Game();
    public static StageController stageController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){

        primaryStage.setResizable(false);
        stageController = new StageController(primaryStage);
        try {
            stageController.loadScene(Objects.requireNonNull(getClass().getResource(START_WINDOW_SCENE_NAME + ".fxml")));
            stageController.loadScene(Objects.requireNonNull(getClass().getResource(GAME_WINDOW_SCENE_NAME + ".fxml")));
            stageController.loadScene(Objects.requireNonNull(getClass().getResource(END_WINDOW_SCENE_NAME + ".fxml")));
        } catch (NullPointerException e) {
            Utils.logErrorWithExit(e);
            return;
        }
        stageController.prepareScene(START_WINDOW_SCENE_NAME);
        stageController.getControllerOf(START_WINDOW_SCENE_NAME).setup();
        stageController.showScene();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        timer.cancel();
    }
}
