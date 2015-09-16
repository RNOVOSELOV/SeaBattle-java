import com.sun.glass.ui.Size;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by novoselov on 16.09.2015.
 */
public class Field {
    static int SIZE = 20;       // Размер поля (Добавить ввод размерности от пользователя)
    char[] cells;
    Ship[] ships;               // Массив корабрей, которые размещены на поле (Добавить ввод задаваемого пользователем колличества кораблей)
    Random random;

    Field() {
        cells = new char[SIZE];
        ships = new Ship[3];                            // Можно добавить любое количество кораблей, но пока вручную :-)
        ships[0] = new Ship(3, "Линкор Тирпиц");        // Корабль Трехпалубный первый параметр - палубность корабля, ограничена только размерностью поля
        ships[1] = new Ship(2, "Крейсер Варяг");        // Двухпалубный
        ships[2] = new Ship(1, "Катер Неустрашимый");   // Однопалубный

        random = new Random();
        init(cells);                // инициализируем игровое поле
        setShips();                 // Расставляем корабли из массива на поле
    }

    void init(char[] cl) {
        for (int i = 0; i < cl.length; i++) {
            cl[i] = '.';
        }
    }

    // Печать игрового поля
    void showField() {
        for (char cell : cells) {
            if (cell == 'X')
                System.out.print(".");
            else
                System.out.print(cell);
        }
        System.out.printf("\n");
    }

    int getSumDecks() {
        int desc = 0;
        for (Ship ship : ships) {
            desc = desc + ship.getDeck();
        }
        return desc;
    }

    // Поиск корабля по заданной координате
    Ship getShip(int coord) {
        for (Ship ship : ships) {
            for (int i = 0; i < ship.position.length; i++) {
                if (ship.position[i] == coord)
                    return ship;
            }
        }
        return null;
    }

    void doShoot(int shoot) {
        switch (cells[shoot]) {
            case '.':
                System.out.println("Мазила!");
                cells[shoot] = '*';
                break;
            case 'O':
            case '*':
                System.out.println("Кэп, сверь координаты, ты сюда уже стрелял!");
                break;
            case 'X':
                Ship s = getShip(shoot);
                s.setCrash();
                if (s.getDeck() != 0)
                    System.out.println(s.name + ": Ранен! Еще чуть чуть...");
                else
                    System.out.println(s.name + ": Цель ликвидирована!");
                cells[shoot] = 'O';
                break;
            default:
                System.out.println("ERROR! Unrecognazed symbol!");
        }
        System.out.println("");
    }

    boolean isNotgameOver() {
        return (getSumDecks() != 0);
    }   // Проверка на потопление всех кораблей

    // Расстановка кораблей из массива на игровом поле
    void setShips() {
        int decks = getSumDecks();
        // Не стал загоняться со сложными формулами, игра начнется только тогда, когда суммарное количество палуб в три раза меньше размерности поля
        // Очень пригодится, когда количество кораблей и их размерность будет вводиться пользователем
        if (decks > SIZE / 3) {
            System.out.println("Слишком много кораблей, заканчиваем играть, неинтересно!");
            System.out.println("Для продолжения игры необходимо увеличить размерность поля либо уменшить размерность (колличество) кораблей.");
            return;
        }
        // Массив необходим для проверки на соседство кораблей
        char[] checkCells = new char[SIZE];
        init(checkCells);
        for (Ship ship : ships) {
            setShip(ship, checkCells);      // Здесь расставляем кораблики
        }
        // Небольшая подсказка перед игрой :-)
        System.out.print("Чит: ");
        System.out.println(cells);
    }

    // Рекурсивная функция, которая расставляет кораблики
    void setShip(Ship s, char[] ch) {
        // Если временная позиция подходит, то
        int tempPosition = random.nextInt(SIZE - s.deckCount + 1);
        for (int i = 0; i < s.deckCount; i++) {
            if (ch[tempPosition + i] != '.') {
                setShip(s, ch);
                return;
            }
        }
        s.position[0] = tempPosition;
        for (int i = 0; i < s.deckCount; i++) {
            cells[tempPosition + i] = 'X';
            ch[tempPosition + i] = 'X';
            s.position[i] = tempPosition + i;
        }
        if (tempPosition != 0) {
            ch[tempPosition - 1] = 'X';
        }
        if (tempPosition + s.deckCount < SIZE) {
            ch[tempPosition + s.deckCount] = 'X';
        }
    }
}
