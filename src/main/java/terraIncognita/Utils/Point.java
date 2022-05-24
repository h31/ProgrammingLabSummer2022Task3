package terraIncognita.Utils;

public class Point {
    private final int _x, _y;

    public Point(int x, int y) {
        this._x = x;
        this._y = y;
    }

    public Point(Point point) {
        this._x = point.x();
        this._y = point.y();
    }

    public int x() {
        return _x;
    }
    public int y() {
        return _y;
    }
}
