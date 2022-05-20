package terraIncognita.Controllers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class BasicController implements Initializable {

    @Override
    public abstract void initialize(URL location, ResourceBundle resources);

    public abstract void setup();
}
