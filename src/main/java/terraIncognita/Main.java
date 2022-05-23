package terraIncognita;
import javafx.application.Application;
import javafx.stage.Stage;
import terraIncognita.Controllers.StageController;
import terraIncognita.Model.Game;
import terraIncognita.Utils.Exceptions.SceneNotFoundException;

import java.util.Timer;

public class Main extends Application {
    public static final String APP_DIR = System.getProperty("user.dir") + "/";
    public static final String RES_DIR = APP_DIR + "src/main/resources/";
    public static final String IMG_DIR = RES_DIR + "img/";
    public static final String TILES_IMG_DIR = IMG_DIR + "Tiles/";
    public static final String LABYRINTHS_DIR = RES_DIR + "labyrinths/";

    public static final String START_WINDOW_SCENE_NAME = "StartWindow";
    public static final String GAME_WINDOW_SCENE_NAME = "GameWindow";
    public static final String END_WINDOW_SCENE_NAME = "EndWindow";

    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;
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
        stageController.loadScene(getClass().getResource(START_WINDOW_SCENE_NAME + ".fxml"));
        stageController.loadScene(getClass().getResource(GAME_WINDOW_SCENE_NAME + ".fxml"));
        stageController.loadScene(getClass().getResource(END_WINDOW_SCENE_NAME + ".fxml"));
        stageController.prepareScene(START_WINDOW_SCENE_NAME);
        stageController.showScene();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        timer.cancel();
    }
}
