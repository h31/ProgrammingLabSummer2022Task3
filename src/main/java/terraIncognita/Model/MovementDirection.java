package terraIncognita.Model;

import terraIncognita.Utils.Point;

public enum MovementDirection {
    Up {
        @Override
        public Point move(Point point) {
            return new Point(point.x(), point.y() - 1);
        }
    },
    Down {
        @Override
        public Point move(Point point) {
            return new Point(point.x(), point.y() + 1);
        }
    },
    Left {
        @Override
        public Point move(Point point) {
            return new Point(point.x() - 1, point.y());
        }
    },
    Right {
        @Override
        public Point move(Point point) {
            return new Point(point.x() + 1, point.y());
        }
    };

    public abstract Point move(Point point);
}
