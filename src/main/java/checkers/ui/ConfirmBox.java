package checkers.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;


import static checkers.logic.Logic.getStepsStack;
import static checkers.ui.ContentCreator.*;
import static checkers.ui.changeContent.changingTurn;

public class ConfirmBox {



    public static boolean confirmation(String headerText, String message) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.initOwner(getWindow());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            boardPainter();
            changingTurn();
            getStepsStack().clear();
            return true;
        } else {
            alert.close();
            return false;
        }


    }
}
