import java.util.ArrayList;

/**
 * Created by novoselov on 16.09.2015.
 */
public class Ship implements Comparable {
    private String name;                        // Имя корабля
    private boolean isHorizontal;
    private ArrayList<Point> positions;         // Координаты на которых располагается корабль
    private int countDesks;                     // Колличество палуб
    private int countIsNotPaddedDecks;          // Колличество неподбитых палуб (в начале игры равно палубности корабля)

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
        countDesks = deckCount;
        positions = new ArrayList<Point>();
    }

    public String getName() {
        return name;
    }

    // Возвращает ответ на запрос, располагается ли корабль на запрашиваемой координате
    boolean isPlacedIn(Point coordinate) {
        for (Point i : positions) {
            if (i.equals(coordinate))
                return true;
        }
        return false;
    }

    public int getCurrentDeckCount() {
        return countIsNotPaddedDecks;
    }

    public int getDeckCount() {
        return countDesks;
    }

    // Добавление в массив positions координаты на которой располагается корабль
    public void addPosition(Point position) {
        positions.add(position);
    }

    // Добавление в массив positions координаты на которой располагается корабль
    public void addPosition(int x, int y) {
        addPosition(new Point(x, y));
    }

    // Корабль получил дамаг
    void setCrash() {
        if (countIsNotPaddedDecks > 0)
            countIsNotPaddedDecks--;
    }

    // Получение расположения головной точки корабля (необходима при отрисовке области вокруг корабля, когда его подобьют)
    Point getShipHead() {
        return positions.get(0);
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void setIsHorizontal(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }

    // необходимо при сортировке списка кораблей
    // сортировка нужна для того чтобы когда пользователь создает сам флот,
    // сначала расставлять корабли с наибольшим колличеством палуб
    // и лишь затем с наименьшим
    @Override
    public int compareTo(Object o) throws ClassCastException {
        if (!(o instanceof Ship))
            throw new ClassCastException("A ship object expected.");
        return ((Ship) o).getDeckCount() - this.countDesks;
    }
}