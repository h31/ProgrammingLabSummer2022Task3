package terraIncognita.Model.Desk;

import org.jetbrains.annotations.NotNull;
import terraIncognita.Model.Tiles.*;
import terraIncognita.Utils.Exceptions.MultipleUsageOfSingleTileException;
import terraIncognita.Utils.Exceptions.NoNeededTileException;
import terraIncognita.Utils.Point;
import terraIncognita.Utils.Utils;

import java.io.*;

public class Labyrinth extends Desk {

    private Point startPosition;
    private Point endPosition;
    private Point treasurePosition;
    private final Point[] wormholesPositions = new Point[10];
    private int wormholesAmount = 0;

    private Labyrinth(int vCount, int hCount) {
        super(vCount, hCount);
    }

    public static Labyrinth genLabyrinthFromExistingSource(InputStream source) {
        StringBuilder sb = new StringBuilder();
        int lineCount = 0;
        int lineLength = -1;
        try(BufferedReader input = new BufferedReader(new InputStreamReader(source))) {
            String line = input.readLine();
            while(line != null) {
                if (line.equals("")) {
                    line = input.readLine();
                    continue;
                }
                lineCount++;
                if (lineLength == -1) {
                    lineLength = line.length();
                }
                sb.append(line);
                line = input.readLine();
            }
        } catch (IOException e) {
            Utils.logErrorWithExit(e);
        }
        Labyrinth labyrinth = new Labyrinth(lineCount, lineLength);
        char[] chars = sb.toString().toCharArray();
        for (int y = 0; y < lineCount; y++) {
            for (int x = 0; x < lineLength; x++) {
                char ch = chars[y * lineLength + x];
                Tile tile = ('0' <= ch && ch <= '9') ? WormholeTile.getInstance(ch) : switch (ch) {
                    case ' ' -> EmptyTile.INSTANCE;
                    case '#' -> WallTile.INSTANCE;
                    case 'S' -> StartTile.INSTANCE;
                    case 'E' -> EndTile.INSTANCE;
                    case 'T' -> TreasureTile.INSTANCE;
                    default -> throw new IllegalArgumentException("Unexpected token " + ch + " in file " + source);
                };

                labyrinth.insertTile(tile, new Point(x, y));
            }
        }

        labyrinth.validate();
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

        if (tile instanceof StartTile) {
            if (startPosition != null) {
                Utils.logErrorWithExit(new MultipleUsageOfSingleTileException("Two or more StartTiles provided"));
            }
            startPosition = point;
        }
        if (tile instanceof EndTile) {
            if (endPosition != null) {
                Utils.logErrorWithExit(new MultipleUsageOfSingleTileException("Two or more EndTiles provided"));
                return;
            }
            endPosition = point;
        }

        if (tile instanceof TreasureTile) {
            if (treasurePosition != null) {
                Utils.logErrorWithExit(new MultipleUsageOfSingleTileException("Two or more Treasures provided"));
                return;
            }
            treasurePosition = point;
        }

        if (tile instanceof WormholeTile) {
            wormholesPositions[((WormholeTile)tile).getNumber()] = point;
            wormholesAmount++;
        }

    }

    public void validate() {
        if (treasurePosition == null || endPosition == null || startPosition == null) {
            Utils.logErrorWithExit(new NoNeededTileException("""
                    One of the following tiles not founded:
                    \r start
                    \r end
                    \r treasure.""")
            );
        }
    }

}
