package terraIncognita.Controllers;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import terraIncognita.Main;
import terraIncognita.Model.MovementDirection;
import terraIncognita.Utils.Exceptions.SceneNotFoundException;

import java.net.URL;
import java.util.ResourceBundle;

public class StartWindowController extends BasicController {
    public Label playerAmountLabel;

    private IntegerProperty playerAmount = new SimpleIntegerProperty(2);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerAmountLabel.textProperty().bind(playerAmount.asString());
    }

    @Override
    public void setup() {

    }

    public void btnDecPlayersClicked(ActionEvent actionEvent) {
        //TODO - add different type of player amount bounding
        playerAmount.set(Main.game.decPlayerAmount(1));
    }

    public void btnIncPlayersClicked(ActionEvent actionEvent) {
        //TODO - add different type of player amount bounding
        playerAmount.set(Main.game.incPlayerAmount(1));
    }

    public void btnStartGameClicked(ActionEvent actionEvent) throws SceneNotFoundException {
        Main.stageController.prepareScene("GameWindow");
        Main.stageController.showScene();
        Main.stageController.getControllerOf("GameWindow").setup();
    }
}
