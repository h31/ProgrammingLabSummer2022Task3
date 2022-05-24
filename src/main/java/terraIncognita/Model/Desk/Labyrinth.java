package terraIncognita.Model.Desk;

import org.jetbrains.annotations.NotNull;
import terraIncognita.Model.Tiles.*;
import terraIncognita.Utils.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class Labyrinth extends Desk {

    private Point startPosition;
    private Point endPosition;
    private final Point[] wormholesPositions = new Point[10];
    private int wormholesAmount = 0;

    private Labyrinth(int vCount, int hCount) {
        super(vCount, hCount, false);
    }

    public static Labyrinth genLabyrinthFromExistingSource(String source) {
        int lineCount = 0;
        int lineLength = -1;
        try(BufferedReader input = new BufferedReader(new FileReader(source))) {
            String line = input.readLine();
            while(line != null) {
                lineCount++;
                if (lineLength == -1) {
                    lineLength = line.length();
                }
                line = input.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Labyrinth labyrinth = new Labyrinth(lineCount, lineLength);
        try(BufferedReader input = new BufferedReader(new FileReader(source))) {
            int ch = input.read();
            int y = 0;
            int x = 0;
            while (ch != -1) {
                if (ch == '\n') {
                    ch = input.read();
                    continue;
                }
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
                            labyrinth.insertTile(new WormholeTile((char)ch), new Point(x, y));
                        } else {
                            throw new RuntimeException(new IllegalArgumentException(
                                    "Unexpected token " + (char)ch + " in file " + source));
                        }
                }
                x = (x + 1) % lineLength;
                if (x == 0) {
                    y++;
                }
                ch = input.read();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return labyrinth;
    }

    public Point getStartPosition() {
        return startPosition;
    }

    public int getHorizontalSize() {
        return this.hCount;
    }

    public int getVerticalSize() {
        return this.vCount;
    }

    public Point getNextWormholePosition(int number) {
        if (number > wormholesAmount) {
            throw new IndexOutOfBoundsException(
                    String.format("Wormhole with index %d was provided, but only %d of them available", number, wormholesAmount)
            );
        }
        return wormholesPositions[(number + 1) % wormholesAmount];
    }

    @Override
    public void insertTile(@NotNull Tile tile, @NotNull Point point) {
        super.insertTile(tile, point);

        if (tile.getClass() == StartTile.class) {
            if (startPosition != null) {
                throw new IllegalArgumentException("Two or more StartTiles provided");
            }
            startPosition = point;
        }
        if (tile.getClass() == EndTile.class) {
            if (endPosition != null) {
                throw new IllegalArgumentException("Two or more EndTiles provided");
            }
            endPosition = point;
        }

        if (tile.getClass() == WormholeTile.class) {
            wormholesPositions[((WormholeTile)tile).getNumber()] = point;
            wormholesAmount++;
        }

    }
}
