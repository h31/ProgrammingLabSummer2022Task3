package terraIncognita.model.tiles;

public abstract class Tile {
    private String imageFileName;

    public String getImageFileName() {
        return imageFileName;
    }
    protected void setImageFileName(String name) {
        imageFileName = name;
    }
}
