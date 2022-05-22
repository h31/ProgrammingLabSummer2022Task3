package terraIncognita.Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import terraIncognita.Controllers.BasicController;
import terraIncognita.Main;
import terraIncognita.Model.Desk.Desk;
import terraIncognita.Model.Desk.Labyrinth;
import terraIncognita.Model.Tiles.*;

import java.io.*;
import java.net.URL;
import java.util.Objects;

public class Utils {

    public static BasicController loadFXMLScene (URL resource) {
        Parent root;
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(resource));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BasicController controller = loader.getController();
        controller.setRuledScene(new Scene(root));
        return controller ;
    }

    public static Labyrinth genLabyrinthFromExistingSource(String source) {
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

        Labyrinth labyrinth = new Labyrinth(lineCount, lineLength);
        char[] chars = sb.toString().toCharArray();
        for(int y = 0; y < lineCount; y++) {
            for (int x = 0; x < lineLength; x++) {
                char ch = chars[y * lineLength + x];
                switch (ch) {
                    case ' ':
                        labyrinth.insertTile(new EmptyTile(), new Point(x, y));
                        break;
                    case '#':
                        labyrinth.insertTile(new WallTile(), new Point(x, y));
                        break;
                    case 'S':
                        labyrinth.insertTile(new StartTile(), new Point(x, y));
                        break;
                    case 'E':
                        labyrinth.insertTile(new EndTile(), new Point(x, y));
                        break;
                    case 'T':
                        labyrinth.insertTile(new TreasureTile(), new Point(x, y));
                        break;
                    default:
                        if (ch >= '0' && ch <='9') {
                            labyrinth.insertTile(new WormholeTile(ch), new Point(x, y));
                        } else {
                            throw new RuntimeException(new IllegalArgumentException(
                                    "Unexpected token " + ch + " in file " + source));
                        }
                }
            }
        }

        return labyrinth;
    }

    public static String genUrlOf(String file) {
        return new File(file).toURI().toString();
    }
}
