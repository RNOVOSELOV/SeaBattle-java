/**
 * Created by novoselov on 22.09.2015.
 */
public class Point {
    private int x;
    private int y;

    Point() {
        this(0, 0);
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if (x < 0) {
            x = 0;
        } else if (x >= Field.SIZE) {
            x = Field.SIZE - 1;
        }
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (y < 0) {
            y = 0;
        } else if (y >= Field.SIZE) {
            y = Field.SIZE - 1;
        }
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        if (x != point.x) return false;
        return y == point.y;
    }
}
