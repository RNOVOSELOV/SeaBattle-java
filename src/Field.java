import java.util.*;

/**
 * Created by novoselov on 16.09.2015.
 */

public class Field {
    static final int SIZE = 10;             // Размер поля, пока хардкод (Добавить ввод размерности от пользователя)
    private char[][] cells;                 // Игровое поле
    private ArrayList<Ship> ships;          // Массив корабрей, которые размещены на поле
    private static Field instance = null;

    private Field() {
        cells = new char[SIZE][SIZE];
        ships = new ArrayList<>();
        init(cells);                                // инициализируем игровое поле
    }

    public static Field getInstance() {
        if (instance == null)
            instance = new Field();
        return instance;
    }

    // Настройка игровой флотилии, можно создать любую комбинацию кораблей, однако колличесто палуб не должно превышать SIZE*2,
    // иначе программа выдаст предупреждение и закончит работу
    void formFleet() {
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

    // Ручное добавление корабля во флотилию
    void addShip(int deck) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя корабля [ВВОД - назначить имя по умолчанию]: ");
        String name = scanner.nextLine();
        ships.add(new Ship(deck, name));
    }

    // Запрос количества палуб для создаваемого вручную корабля
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
                    System.out.println("Вы ввели неверную размерность корабля, он неумещается на поле, повторите ввод пожалуйста.");
                }
            }
            scanner.nextLine();
        } while (true);
        return deck;
    }

    // Автогенерация флотилии
    // Комбинация по умолчанию: 4 палубы - 1 шт; 3 палубы - 2 шт; 2 палубы - 3 шт; 1 палуба - 4 шт
    void addShipsByDefault() {
        ships.add(new Ship(4, "Авианосец \"Авраам Линкольн\""));
        ships.add(new Ship(3, "Ракетный крейсер \"Анцио\""));
        ships.add(new Ship(3, "Ракетный крейсер \"Порт Роял\""));
        ships.add(new Ship(2, "Эсминец \"Дональд Кук\""));
        ships.add(new Ship(2, "Эсминец \"Арли Берк\""));
        ships.add(new Ship(2, "Эсминец \"Спрюенс\""));
        ships.add(new Ship(1, "Катер \"Фридом\""));
        ships.add(new Ship(1, "Катер \"Форт Ворф\""));
        ships.add(new Ship(1, "Катер \"Индепенденс\""));
        ships.add(new Ship(1, "Катер \"Коронадо\""));
    }

    // Инициализация игрового поля
    void init(char[][] cl) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cl[i][j] = '·';
            }
        }
    }

    // Печать игрового поля
    void showField(boolean cheats) {
        System.out.print("  _|");
        for (int i = 1; i <= SIZE; i++) {
            System.out.print("_" + i + "_");
        }
        System.out.println("");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (j == 0)
                    System.out.printf("%2d |", i + 1);
                if (cells[j][i] == 'O' && cheats == false)
                    System.out.print(" · ");
                else
                    System.out.print(" " + cells[j][i] + " ");
            }
            System.out.printf("\n");
        }
        System.out.printf("\n");
    }

    int getSumDecks() {
        int desc = 0;
        for (Ship ship : ships) {
            desc = desc + ship.getCurrentDeckCount();
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

    boolean doShoot(Player player, Point shoot) {
        boolean shootIsFail = true;
        switch (cells[shoot.getX()][shoot.getY()]) {
            case '·':
                System.out.println("Мимо!");
                cells[shoot.getX()][shoot.getY()] = '•';
                break;
            case 'X':
                System.out.println("Тысяча чертей! Канонир не трать снаряды, мы сюда уже стреляли!");
                break;
            case '•':
                System.out.println("Тысяча чертей! Видимо наводчик пьян, неоправданная трата боекомплекта!");
                break;
            case '∘':
                System.out.println("Тысяча чертей! Неоправданная трата боекомплекта!");
                break;
            case 'O':
                Ship s = getShip(shoot);
                cells[shoot.getX()][shoot.getY()] = 'X';
                if ((s.getCurrentDeckCount() - 1) > 0) {
                    System.out.println("КАРАМБА! Есть попадание, еще чуть чуть: " + s.getName());
                } else {
                    System.out.println("СВИСТАТЬ ВСЕХ НАВЕРХ! Цель ликвидирована: " + s.getName());
                    paintPointsAroundShip(s, cells, s.getShipHead(), s.isHorizontal());
                    player.playerDestroyShip();
                }
                s.setCrash();
                shootIsFail = false;
                break;
            default:
                System.out.println("ERROR! Unrecognazed symbol!");
        }
        System.out.println("");
        return shootIsFail;
    }

    boolean isNotGameOver() {
        return (getSumDecks() != 0);
    }   // Проверка на потопление всех кораблей

    // Расстановка кораблей из массива на игровом поле
    boolean setShips() {
        int decks = getSumDecks();
        // Не стал загоняться со сложными формулами, игра начнется только тогда,
        // когда суммарное количество палуб меньше чем двойная размерность поля
        if (decks > SIZE * 2) {
            System.out.println("\nСлишком много кораблей, заканчиваем играть, неинтересно!");
            System.out.println("Игра начнется только тогда, когда суммарное количество палуб не больше, чем удвоенная размерность поля.");
            return false;
        }

        // Сортировка кораблей от максимального до минимального в списке судов
        // чтобы расставлять сначала наибольший корабли
        Collections.sort(ships);

        // Временный массив необходим для проверки на соседство кораблей
        // массив используется только на этапе расстановки кораблей
        char[][] checkCells = new char[SIZE][SIZE];
        init(checkCells);
        Random random = new Random();
        for (Ship ship : ships) {
            findFreeSpaceForShip(ship, checkCells, random);      // Здесь расставляем кораблики по одному, начиная от наибольшего к наименьшему
        }
        return true;
    }

    // Рекурсивная функция, которая ищет точку для головы корабля и проверяет уместится ли корабль
    // Если умещается то продолжает работу и размещает корабль на игровом поле,
    // если не умещается, то перезапускает себя и ищет другую опорную (головную) точку
    void findFreeSpaceForShip(Ship s, char[][] ch, Random random) {
        // Определяем вертикальный или горизонтальный будет корабль
        boolean isHorizontal = random.nextBoolean();
        // Ищем опорную точку для головы корабля
        Point tempPoint;
        int firstCoordinate = random.nextInt(SIZE - s.getDeckCount() + 1);
        int secondCoordinate = random.nextInt(SIZE);
        if (isHorizontal) {
            tempPoint = new Point(firstCoordinate, secondCoordinate);
        } else {
            tempPoint = new Point(secondCoordinate, firstCoordinate);
        }
        int x = 0;
        int y = 0;
        for (int i = 0; i < s.getDeckCount(); i++) {
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
        // Головная точка найдена, размещаем корабль на поле
        setShipOnFreeSpace(s, ch, tempPoint, isHorizontal);
    }

    // Функция размещает корабль на игровом поле
    void setShipOnFreeSpace(Ship s, char[][] ch, Point tempPoint, boolean isHorizontal) {
        int x = 0;
        int y = 0;
        s.setIsHorizontal(isHorizontal);
        for (int i = 0; i < s.getDeckCount(); i++) {
            cells[tempPoint.getX() + x][tempPoint.getY() + y] = 'O';
            ch[tempPoint.getX() + x][tempPoint.getY() + y] = 'O';
            s.addPosition(tempPoint.getX() + x, tempPoint.getY() + y);
            if (isHorizontal) {
                x++;
            } else {
                y++;
            }
        }
        paintPointsAroundShip(s, ch, tempPoint, isHorizontal);
    }

    // Рисуем вокруг корябля "область несоприкосновения"
    // 1) при зоздании игрового поля необходима при расстановке, чтобы корабли не касались друг друга, манипулирует с временным массивом
    // 2) в процессе игры рисует вокруг подбитого корябля характерную область, куда не стоит стрелять игроку
    void paintPointsAroundShip(Ship s, char[][] ch, Point tempPoint, boolean isHorizontal) {
        int x = 0;
        int y = 0;
        for (int i = 0; i < s.getDeckCount(); i++) {
            if (isHorizontal) {
                if (tempPoint.getY() != 0) {
                    if (ch[tempPoint.getX() + x][tempPoint.getY() + y - 1] == '·') {
                        ch[tempPoint.getX() + x][tempPoint.getY() + y - 1] = '•';
                    }
                }
                if (tempPoint.getY() != SIZE - 1) {
                    if (ch[tempPoint.getX() + x][tempPoint.getY() + y + 1] == '·') {
                        ch[tempPoint.getX() + x][tempPoint.getY() + y + 1] = '•';
                    }
                }
            } else {
                if (tempPoint.getX() != 0) {
                    if (ch[tempPoint.getX() + x - 1][tempPoint.getY() + y] == '·') {
                        ch[tempPoint.getX() + x - 1][tempPoint.getY() + y] = '•';
                    }
                }
                if (tempPoint.getX() != SIZE - 1) {
                    if (ch[tempPoint.getX() + x + 1][tempPoint.getY() + y] == '·') {
                        ch[tempPoint.getX() + x + 1][tempPoint.getY() + y] = '•';
                    }
                }
            }
            if (isHorizontal) {
                x++;
            } else {
                y++;
            }
        }
        if (isHorizontal) {
            if (tempPoint.getX() != 0) {
                if (ch[tempPoint.getX() - 1][tempPoint.getY()] == '·') {
                    ch[tempPoint.getX() - 1][tempPoint.getY()] = '•';
                }
                if (tempPoint.getY() != 0) {
                    if (ch[tempPoint.getX() - 1][tempPoint.getY() - 1] == '·') {
                        ch[tempPoint.getX() - 1][tempPoint.getY() - 1] = '•';
                    }
                }
                if (tempPoint.getY() != SIZE - 1) {
                    if (ch[tempPoint.getX() - 1][tempPoint.getY() + 1] == '·') {
                        ch[tempPoint.getX() - 1][tempPoint.getY() + 1] = '•';
                    }
                }
            }
            if (tempPoint.getX() + s.getDeckCount() != SIZE) {
                if (ch[tempPoint.getX() + s.getDeckCount()][tempPoint.getY()] == '·') {
                    ch[tempPoint.getX() + s.getDeckCount()][tempPoint.getY()] = '•';
                }
                if (tempPoint.getY() != 0) {
                    if (ch[tempPoint.getX() + s.getDeckCount()][tempPoint.getY() - 1] == '·') {
                        ch[tempPoint.getX() + s.getDeckCount()][tempPoint.getY() - 1] = '•';
                    }
                }
                if (tempPoint.getY() != SIZE - 1) {
                    if (ch[tempPoint.getX() + s.getDeckCount()][tempPoint.getY() + 1] == '·') {
                        ch[tempPoint.getX() + s.getDeckCount()][tempPoint.getY() + 1] = '•';
                    }
                }
            }
        } else {
            if (tempPoint.getY() != 0) {
                if (ch[tempPoint.getX()][tempPoint.getY() - 1] == '·') {
                    ch[tempPoint.getX()][tempPoint.getY() - 1] = '•';
                }
                if (tempPoint.getX() != 0) {
                    if (ch[tempPoint.getX() - 1][tempPoint.getY() - 1] == '·') {
                        ch[tempPoint.getX() - 1][tempPoint.getY() - 1] = '•';
                    }
                }
                if (tempPoint.getX() != SIZE - 1) {
                    if (ch[tempPoint.getX() + 1][tempPoint.getY() - 1] == '·') {
                        ch[tempPoint.getX() + 1][tempPoint.getY() - 1] = '•';
                    }
                }
            }
            if (tempPoint.getY() + s.getDeckCount() != SIZE) {
                if (ch[tempPoint.getX()][tempPoint.getY() + s.getDeckCount()] == '·') {
                    ch[tempPoint.getX()][tempPoint.getY() + s.getDeckCount()] = '•';
                }
                if (tempPoint.getX() != 0) {
                    if (ch[tempPoint.getX() - 1][tempPoint.getY() + s.getDeckCount()] == '·') {
                        ch[tempPoint.getX() - 1][tempPoint.getY() + s.getDeckCount()] = '•';
                    }
                }
                if (tempPoint.getX() != SIZE - 1) {
                    if (ch[tempPoint.getX() + 1][tempPoint.getY() + s.getDeckCount()] == '·') {
                        ch[tempPoint.getX() + 1][tempPoint.getY() + s.getDeckCount()] = '•';
                    }
                }
            }
        }
    }
}
