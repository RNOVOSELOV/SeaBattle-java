import java.util.Random;

/**
 * Created by novoselov on 16.09.2015.
 */
public class Ship {
    int[] position;     // Координаты корабля
    int deckCount;      // Колличество палуб
    String name;        // Имя корабля

    // Конструктор по умолчанию
    Ship() {
        this(1);
    }

    // Конструктор, если не введено имя корябля (Имя корабля присваивается в формате: "Неизвестный(палуб: <<КОЛЛИЧЕСТВО ПАЛУБ>>)")
    Ship(int deckCount) {
        this(deckCount, "Неизвестный (палуб: " + deckCount + ")");
    }

    Ship(int deckCount, String name) {
        this.name = name;
        this.deckCount = deckCount;
        position = new int[deckCount];
    }

    // Колличество неразрушенных палуб
    int getDeck () {
        return deckCount;
    }

    // Корабль получил дамаг
    void getCrash () {
        if (deckCount > 0)
        deckCount--;
    }
}
