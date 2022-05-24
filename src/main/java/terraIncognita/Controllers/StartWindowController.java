package terraIncognita.Controllers;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;

import org.apache.commons.io.FilenameUtils;

import terraIncognita.Main;
import terraIncognita.Model.Game;
import terraIncognita.Utils.Utils;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class StartWindowController extends BasicController {
    @FXML
    private Label playerAmountLabel;
    @FXML
    private ComboBox<File> labyrinthComboBox;
    @FXML
    private Button startBtn;
    @FXML
    private ImageView startBtnImage;
    @FXML
    private Button decPlayerAmountBtn;
    @FXML
    private Button incPlayerAmountBtn;

    private final static String START_IMAGE_NAME_DISABLED = "startDisabled.png";
    private final static String START_IMAGE_NAME_ENABLED = "startEnabled.png";

    private final IntegerProperty playerAmount = new SimpleIntegerProperty(2);
    private final BooleanProperty startButtonDisabled = new SimpleBooleanProperty(true);
    private final BooleanProperty incPlayerAmountBtnDisabled = new SimpleBooleanProperty(false);
    private final BooleanProperty decPlayerAmountBtnDisabled = new SimpleBooleanProperty(true);

    public String getLabyrinthSource() {
        return labyrinthComboBox.getValue().getPath();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerAmountLabel.textProperty().bind(playerAmount.asString());
        startBtn.disableProperty().bind(startButtonDisabled);
        decPlayerAmountBtn.disableProperty().bind(decPlayerAmountBtnDisabled);
        incPlayerAmountBtn.disableProperty().bind(incPlayerAmountBtnDisabled);
    }

    @Override
    public void setup(Object... args) {
        var files = Utils.loadFilesFrom(Main.LABYRINTHS_DIR);
        labyrinthComboBox.setItems(FXCollections.observableList(files));
        labyrinthComboBox.setConverter(new StringConverter<File>() {
            @Override
            public String toString(File object) {
                return FilenameUtils.getBaseName(object.getPath());
            }

            @Override
            public File fromString(String string) {
                return null;
            }
        });
        labyrinthComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            startButtonDisabled.set(Objects.isNull(newVal));
            tryChangeStartBtnEnable();
        });

    }

    private void tryChangeStartBtnEnable() {
        if (startButtonDisabled.get()) {
            startBtnImage.setImage(new Image(
                    Utils.genUrlOf(Main.IMG_DIR + START_IMAGE_NAME_DISABLED)
            ));
        } else {
            startBtnImage.setImage(new Image(
                    Utils.genUrlOf(Main.IMG_DIR + START_IMAGE_NAME_ENABLED)
            ));
        }
    }

    private void checkIncDecBtnDisabled() {
        decPlayerAmountBtnDisabled.set(playerAmount.get() == Game.MIN_PLAYER_AMOUNT);
        incPlayerAmountBtnDisabled.set(playerAmount.get() == Game.MAX_PLAYER_AMOUNT);
    }

    @FXML
    private void btnDecPlayersClicked() {
        playerAmount.set(Main.game.decPlayerAmount(1));
        checkIncDecBtnDisabled();
    }

    @FXML
    private void btnIncPlayersClicked() {
        playerAmount.set(Main.game.incPlayerAmount(1));
        checkIncDecBtnDisabled();
    }

    @FXML
    private void btnStartGameClicked(){
            Main.stageController.prepareScene(Main.GAME_WINDOW_SCENE_NAME);
            Main.stageController.getControllerOf(Main.GAME_WINDOW_SCENE_NAME).setup();
            Main.stageController.showScene();
    }
}
