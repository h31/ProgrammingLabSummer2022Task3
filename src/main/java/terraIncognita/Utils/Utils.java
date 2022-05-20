package terraIncognita.Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Pair;
import terraIncognita.Controllers.BasicController;
import terraIncognita.Model.Desk;
import terraIncognita.Model.Tiles.*;

import java.io.*;
import java.net.URL;
import java.util.Objects;

public class Utils {

    public static class FXMLLoadResult {
        private Scene scene;
        private BasicController controller;
        public FXMLLoadResult(Scene scene, BasicController controller) {
            this.scene = scene;
            this.controller = controller;
        }

        public Scene getScene() {
            return scene;
        }

        public BasicController getController(){
            return controller;
        }
    }

    public static FXMLLoadResult loadFXMLScene (URL resource) {
        Parent root;
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(resource));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new FXMLLoadResult(new Scene(root), loader.getController()) ;
    }

    public static Desk genLabyrinthFromExistingSource(String source) {
        StringBuilder sb = new StringBuilder();
        int lineCount = 0;
        int lineLength = -1;
        try(BufferedReader input = new BufferedReader(new FileReader(source))) {
            String line = input.readLine();
            while(line != null) {
                lineCount++;
                if (lineLength == -1) {
                    lineLength = line.length();
                }
                sb.append(line);
                line = input.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Desk labyrinth = new Desk(lineCount, lineLength);
        char[] chars = sb.toString().toCharArray();
        for(int i = 0; i < lineCount; i++) {
            for (int j = 0; j < lineLength; j++) {
                char ch = chars[i * lineLength + j];
                switch (ch) {
                    case ' ':
                        labyrinth.insertTile(new EmptyTile(), j, i);
                        break;
                    case '#':
                        labyrinth.insertTile(new WallTile(), j, i);
                        break;
                    case 'S':
                        labyrinth.insertTile(new StartTile(), j, i);
                        break;
                    case 'E':
                        labyrinth.insertTile(new EndTile(), j, i);
                        break;
                    case 'T':
                        labyrinth.insertTile(new TreasureTile(), j, i);
                        break;
                    default:
                        if (ch >= '0' && ch <='9') {
                            labyrinth.insertTile(new WormholeTile(ch), j, i);
                        } else {
                            throw new RuntimeException(new IllegalArgumentException(
                                    "Unexpected token " + ch + " in file " + source));
                        }
                }
            }
        }

        return labyrinth;
    }
}
