/**
 * Created by novoselov on 16.09.2015.
 */
public class Ship {
    private String name;                    // Имя корабля
    private int[] position;                         // Координаты на которых располагается корабль
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
        position = new int[deckCount];
    }

    public String getName() {
        return name;
    }

    public int getCountIsNotPaddedDecks() {
        return countIsNotPaddedDecks;
    }

    boolean isPlacedIn(int coordinate) {
        for (int i : position) {
            if (i == coordinate)
                return true;
        }
        return false;
    }

    // Колличество неразрушенных палуб
    int getDeck() {
        return countIsNotPaddedDecks;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    // Корабль получил дамаг
    void setCrash() {
        if (countIsNotPaddedDecks > 0)
            countIsNotPaddedDecks--;
    }
}