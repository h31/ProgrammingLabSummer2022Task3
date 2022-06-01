package terraIncognita.model;

import terraIncognita.utils.Point;

public enum MovementDirection {
    UP {
        @Override
        public Point move(Point point) {
            return new Point(point.x, point.y - 1);
        }
    },
    DOWN {
        @Override
        public Point move(Point point) {
            return new Point(point.x, point.y + 1);
        }
    },
    LEFT {
        @Override
        public Point move(Point point) {
            return new Point(point.x - 1, point.y);
        }
    },
    RIGHT {
        @Override
        public Point move(Point point) {
            return new Point(point.x + 1, point.y);
        }
    };

    public abstract Point move(Point point);
}
