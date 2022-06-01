package terraIncognita.controllers;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import terraIncognita.App;
import terraIncognita.model.Game;
import terraIncognita.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class StartWindowController extends BasicController {
    @FXML
    private Label playerAmountLabel;
    @FXML
    private ComboBox<String> labyrinthComboBox;
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

    public String getLabyrinthName() {
        return App.LABYRINTHS_RELATIVE_DIR + labyrinthComboBox.getValue();
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
        List<String> filesName;
        try {
            filesName = Utils.loadSeparateLinesFrom(App.resourceLoader.getInputStreamOf(App.LABYRINTHS_RELATIVE_DIR + "labyrinths.list"));
        } catch (IOException e) {
            Utils.logErrorWithExit(e);
            return;
        }
        filesName.sort(Comparator.naturalOrder());
        labyrinthComboBox.setItems(FXCollections.observableList(filesName));
        labyrinthComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            startButtonDisabled.set(Objects.isNull(newVal));
            tryChangeStartBtnEnable();
        });

    }

    private void tryChangeStartBtnEnable() {
        if (startButtonDisabled.get()) {
            startBtnImage.setImage(new Image(
                    App.resourceLoader.getInputStreamOf(App.IMG_RELATIVE_DIR + START_IMAGE_NAME_DISABLED)
            ));
        } else {
            startBtnImage.setImage(new Image(
                    App.resourceLoader.getInputStreamOf(App.IMG_RELATIVE_DIR + START_IMAGE_NAME_ENABLED)
            ));
        }
    }

    private void checkIncDecBtnDisabled() {
        decPlayerAmountBtnDisabled.set(playerAmount.get() == Game.MIN_PLAYER_AMOUNT);
        incPlayerAmountBtnDisabled.set(playerAmount.get() == Game.MAX_PLAYER_AMOUNT);
    }

    @FXML
    private void btnDecPlayersClicked() {
        playerAmount.set(App.game.decPlayerAmount(1));
        checkIncDecBtnDisabled();
    }

    @FXML
    private void btnIncPlayersClicked() {
        playerAmount.set(App.game.incPlayerAmount(1));
        checkIncDecBtnDisabled();
    }

    @FXML
    private void btnStartGameClicked(){
            App.stageController.prepareScene(App.GAME_WINDOW_SCENE_NAME);
            App.stageController.getControllerOf(App.GAME_WINDOW_SCENE_NAME).setup();
            App.stageController.showScene();
    }
}
