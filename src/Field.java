import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by novoselov on 16.09.2015.
 */
public class Field {
    static int SIZE = 10;                           // Размер поля (Добавить ввод размерности от пользователя)
    private char[][] cells;
    private ArrayList<Ship> ships;                  // Массив корабрей, которые размещены на поле (Добавить ввод задаваемого пользователем колличества кораблей)

    Field() {
        cells = new char[SIZE][SIZE];
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
        ships.add(new Ship(4, "Авианосец \"Авраам Линкольн\""));
        ships.add(new Ship(3, "Ракетный крейсер \"Анцио\""));    // Корабль Трехпалубный
        ships.add(new Ship(3, "Ракетный крейсер \"Порт Роял\""));
        ships.add(new Ship(2, "Эсминец \"Дональд Кук\""));          // Двухпалубный
        ships.add(new Ship(2, "Эсминец \"Арли Берк\""));
        ships.add(new Ship(2, "Эсминец \"Спрюенс\""));
        ships.add(new Ship(1, "Катер \"Фридом\""));               // Однопалубный
        ships.add(new Ship(1, "Катер \"Форт Ворф\""));
        ships.add(new Ship(1, "Катер \"Индепенденс\""));
        ships.add(new Ship(1, "Катер \"Коронадо\""));
    }

    void init(char[][] cl) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cl[i][j] = '·';
            }
        }
    }

    // Печать игрового поля
    void showField(boolean cheats) {
        for (int i = 0; i < SIZE; i++) {
            for (char cell : cells[i]) {
                if (cell == 'O' && cheats == false)
                    System.out.print(" · ");
                else
                    System.out.print(" " + cell + " ");
            }
            System.out.printf("\n");
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
    Ship getShip(Point coordinate) {
        for (Ship ship : ships) {
            if (ship.isPlacedIn(coordinate))
                return ship;
        }
        return null;
    }

    //  Пустое поле - '·'
    //  Стоит корабль (для чита и при расстановке кораблей) - 'O'
    //  Подбита палуба - 'X'
    //  Стрелянное поле - '•'
    //  Поле рядом с кораблем, когда корабль уничтожен - '¤'
    //
    //


    boolean doShoot(int shoot) {
        boolean changePlayer = true;
/*
        switch (cells[shoot]) {
            case '·':
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
        */
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
        if (decks > SIZE*2) {
            System.out.println("Слишком много кораблей, заканчиваем играть, неинтересно!");
            System.out.println("Для продолжения игры необходимо увеличить размерность поля либо уменшить размерность (колличество) кораблей.");
            return;
        }
        // Массив необходим для проверки на соседство кораблей
        char[][] checkCells = new char[SIZE][SIZE];
        init(checkCells);
        Random random = new Random();
        for (Ship ship : ships) {
            findFreeSpaceForShip(ship, checkCells, random);      // Здесь расставляем кораблики
        }
        // Небольшая подсказка перед игрой :-)
        System.out.println("Чит: ");
        showField(true);
    }

    // Рекурсивная функция, которая расставляет кораблики
    void findFreeSpaceForShip(Ship s, char[][] ch, Random random) {
        // Определяем вертикальный или горизонтальный будет корабль
        boolean isHorizontal = random.nextBoolean();
        // Ищем опорную точку для головы корабля
        Point tempPoint;
        int firstCoordinate = random.nextInt(SIZE - s.getCountIsNotPaddedDecks() + 1);
        int secondCoordinate = random.nextInt(SIZE + 1);
        if (isHorizontal){
            tempPoint = new Point(firstCoordinate,secondCoordinate);
        } else {
            tempPoint = new Point(secondCoordinate,firstCoordinate);
        }
        int x = 0;
        int y = 0;
        for (int i = 0; i < s.getCountIsNotPaddedDecks(); i++) {
            if (ch[tempPoint.getX() + x][tempPoint.getY() + y] != '·') {
                findFreeSpaceForShip(s, ch, random);
                return;
            }
            if (isHorizontal) {
                x++;
            } else {
                y++;
            }
        }
        String srt = (isHorizontal==true)?" true":" false";
        System.out.println("x: " + tempPoint.getX() + " y: " + tempPoint.getY() + srt);
        setShipOnFreeSpace(s, ch, tempPoint, isHorizontal);
    }

    void setShipOnFreeSpace(Ship s, char[][] ch, Point tempPoint, boolean isHorizontal) {
        int x = 0;
        int y = 0;
        for (int i = 0; i < s.getCountIsNotPaddedDecks(); i++) {
            cells[tempPoint.getX() + x][tempPoint.getY() + y] = 'X';
            ch[tempPoint.getX() + x][tempPoint.getY() + y] = 'X';
            s.addPosition(tempPoint.getX() + x, tempPoint.getY() + y);
            if (isHorizontal) {
                x++;
            } else {
                y++;
            }
        }
        /*
        if (tempPosition != 0) {
            ch[tempPosition - 1] = 'X';
        }
        if (tempPosition + s.getCountIsNotPaddedDecks() < SIZE) {
            ch[tempPosition + s.getCountIsNotPaddedDecks()] = 'X';
        }
*/

        for (int i = 0; i < SIZE; i++) {
            for (char cell : ch[i]) {
                System.out.print(" " + cell + " ");
            }
            System.out.printf("\n");
        }
        System.out.printf("\n");
    }
}
