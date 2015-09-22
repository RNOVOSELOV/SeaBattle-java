import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by novoselov on 16.09.2015.
 */
public class Field {
    static int SIZE = 20;                           // Размер поля (Добавить ввод размерности от пользователя)
    private char[] cells;
    private ArrayList<Ship> ships;                  // Массив корабрей, которые размещены на поле (Добавить ввод задаваемого пользователем колличества кораблей)

    Field() {
        cells = new char[SIZE];
        ships = new ArrayList<>();                  // Можно добавить любое количество кораблей, но пока вручную :-)
        init(cells);                                // инициализируем игровое поле
    }

    void tuneSettings() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Нажмите любую клавишу чтоб создать флот вручную [ВВОД - назначить корабли по умолчанию]: ");
        if (scanner.nextLine().isEmpty()) {
            addShipsByDefault();
        } else {
            do {
                int deck = getShipDeckCount();
                if (deck == 0) {
                    if (ships.size() == 0) {
                        System.out.println("Вы не содали ни одного корабля, создана флотилия по умолчанию");
                        addShipsByDefault();
                    }
                    break;
                } else {
                    addShip(deck);
                }
            } while (true);
        }
    }

    void addShip(int deck) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя корабля [ВВОД - назначить имя по умолчанию]: ");
        String name = scanner.nextLine();
        ships.add(new Ship(deck, name));
    }

    int getShipDeckCount() {
        int deck;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Введите палубность корабля [1-" + Field.SIZE + "; 0 - прекратить настройку кораблей]: ");
            if (scanner.hasNextInt()) {
                deck = scanner.nextInt();
                if (deck >= 0 && deck <= Field.SIZE) {
                    break;
                } else {
                    System.out.println("Вы ввели неверную размерность корабля, повторите ввод пожалуйста.");
                }
            }
            scanner.nextLine();
        } while (true);
        return deck;
    }

    void addShipsByDefault() {
        //первый параметр - палубность корабля, ограничена только размерностью поля
        ships.add(new Ship(3, "Линкор Тирпиц"));        // Корабль Трехпалубный
        ships.add(new Ship(2, "Крейсер Варяг"));        // Двухпалубный
        ships.add(new Ship(1, "Катер Неустрашимый"));   // Однопалубный
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
    Ship getShip(int coordinate) {
        for (Ship ship : ships) {
            if (ship.isPlacedIn(coordinate))
                return ship;
        }
        return null;
    }

    boolean doShoot(int shoot) {
        boolean changePlayer = true;
        switch (cells[shoot]) {
            case '.':
                System.out.println("Мимо!");
                cells[shoot] = '*';
                break;
            case '@':
            case '*':
                System.out.println("Кэп, сверь координаты, ты сюда уже стрелял!");
                break;
            case 'X':
                Ship s = getShip(shoot);
                s.setCrash();
                if (s.getDeck() != 0)
                    System.out.println(s.getName() + ": Ранен! Еще чуть чуть...");
                else
                    System.out.println(s.getName() + ": Цель ликвидирована!");
                cells[shoot] = '@';
                changePlayer = false;
                break;
            default:
                System.out.println("ERROR! Unrecognazed symbol!");
        }
        System.out.println("");
        return changePlayer;
    }

    boolean isNotGameOver() {
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
        Random random = new Random();
        for (Ship ship : ships) {
            setShip(ship, checkCells, random);      // Здесь расставляем кораблики
        }
        // Небольшая подсказка перед игрой :-)
        System.out.print("Чит: ");
        System.out.println(cells);
    }

    // Рекурсивная функция, которая расставляет кораблики
    void setShip(Ship s, char[] ch, Random random) {
        // Если временная позиция подходит, то
        int tempPosition = random.nextInt(SIZE - s.getCountIsNotPaddedDecks() + 1);
        for (int i = 0; i < s.getCountIsNotPaddedDecks(); i++) {
            if (ch[tempPosition + i] != '.') {
                setShip(s, ch, random);
                return;
            }
        }
        int[] tmpPositionArray = new int[s.getCountIsNotPaddedDecks()];
        tmpPositionArray[0] = tempPosition;
        for (int i = 0; i < s.getCountIsNotPaddedDecks(); i++) {
            cells[tempPosition + i] = 'X';
            ch[tempPosition + i] = 'X';
            tmpPositionArray[i] = tempPosition + i;
        }
        s.setPosition(tmpPositionArray);
        if (tempPosition != 0) {
            ch[tempPosition - 1] = 'X';
        }
        if (tempPosition + s.getCountIsNotPaddedDecks() < SIZE) {
            ch[tempPosition + s.getCountIsNotPaddedDecks()] = 'X';
        }
    }
}
