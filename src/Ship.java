import java.util.ArrayList;

/**
 * Created by novoselov on 16.09.2015.
 */
public class Ship {
    private String name;                    // Имя корабля
    private ArrayList<Point> positions;      // Координаты на которых располагается корабль
    private int countIsNotPaddedDecks;      // Колличество неподбитых палуб (в начале игры равно палубности корабля)

    // Конструктор по умолчанию
    Ship() {
        this(1);
    }

    // Конструктор, если не введено имя корябля (Имя корабля присваивается в формате: "Неизвестный (палуб: <<КОЛЛИЧЕСТВО ПАЛУБ>>)")
    Ship(int deckCount) {
        this(deckCount, "Неизвестный (палуб: " + deckCount + ")");
    }

    // Первый параметр - колличество палуб на корабле, ограничено только размерностью поля
    Ship(int deckCount, String name) {
        this.name = name;
        if (name.isEmpty())
            this.name = "Неизвестный (палуб: " + deckCount + ")";
        countIsNotPaddedDecks = deckCount;
        positions = new ArrayList<Point>();
    }

    public String getName() {
        return name;
    }

    public int getCountIsNotPaddedDecks() {
        return countIsNotPaddedDecks;
    }

    boolean isPlacedIn(Point coordinate) {
        for (Point i : positions) {
            if (i.equals(coordinate))
                return true;
        }
        return false;
    }

    // Колличество неразрушенных палуб
    int getDeck() {
        return countIsNotPaddedDecks;
    }

    public void addPosition(Point position) {
        this.positions.add(position);
    }

    public void addPosition(int x, int y) {
        this.positions.add(new Point(x,y));
    }

    // Корабль получил дамаг
    void setCrash() {
        if (countIsNotPaddedDecks > 0)
            countIsNotPaddedDecks--;
    }
}