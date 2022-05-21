package terraIncognita;
import javafx.application.Application;
import javafx.stage.Stage;
import terraIncognita.Controllers.StageController;
import terraIncognita.Model.Game;
import terraIncognita.Utils.Exceptions.SceneNotFoundException;

public class Main extends Application {
    public static final String APP_DIR = System.getProperty("user.dir") + "/";
    public static final String RES_DIR = APP_DIR + "src/main/resources/";
    public static final String IMG_DIR = RES_DIR + "img/";
    public static final String TILES_IMG_DIR = IMG_DIR + "Tiles/";
    public static final String LABYRINTHS_DIR = RES_DIR + "labyrinths/";

    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 400;

    public static Game game = new Game();
    public static StageController stageController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws SceneNotFoundException {
        primaryStage.setResizable(false);
        stageController = new StageController(primaryStage);
        stageController.loadScene(getClass().getResource("StartWindow.fxml"));
        stageController.loadScene(getClass().getResource("GameWindow.fxml"));
        stageController.prepareScene("StartWindow");
        stageController.showScene();
    }
}
