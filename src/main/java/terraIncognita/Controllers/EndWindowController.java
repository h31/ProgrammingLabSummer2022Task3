package terraIncognita.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EndWindowController extends BasicController{
    @FXML
    private Label winnerLabel;

    private StringProperty winnerName = new SimpleStringProperty("");
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        winnerLabel.textProperty().bind(winnerName);
    }

    @Override
    public void setup(Object... args) {
        if (args[0].getClass() == String.class) {
            winnerName.set(args[0] + " WINNER!");
        }
    }



}
