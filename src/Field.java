import java.util.Random;

/**
 * Created by novoselov on 16.09.2015.
 */

public class Field {
    static final int SIZE = 10;             // Размер поля, пока хардкод (Добавить ввод размерности поля от пользователя, потом, когда научимся работать в графике)
    public char[][] cells;                 // Игровое поле
    public enum CellState {NOT_FIRED_CELL, FIRED_AND_EMPTY_CELL, NOT_FIRED_DECK, FIRED_DECK;}

    public Field() {
        cells = new char[SIZE][SIZE];
        init(cells);                                // инициализируем игровое поле
    }

    public char getCell(Point p) {
        return cells[p.getX()][p.getY()];
    }

    public void setCell(Point p, char ch) {
        cells[p.getX()][p.getY()] = ch;
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

    // Расстановка кораблей на игровом поле
    boolean setShips(Navy navy) {
        int decks = navy.getSumDecks();
        // Не стал загоняться со сложными формулами, игра начнется только тогда,
        // когда суммарное количество палуб меньше чем двойная размерность поля
        if (decks > SIZE * 2) {
            System.out.println("\nСлишком много кораблей, заканчиваем играть, неинтересно!");
            System.out.println("Игра начнется только тогда, когда суммарное количество палуб не больше, чем удвоенная размерность поля.");
            return false;
        }
        // Временный массив необходим для проверки на соседство кораблей
        // массив используется только на этапе расстановки кораблей
        char[][] checkCells = new char[SIZE][SIZE];
        init(checkCells);
        Random random = new Random();
        for (Ship ship : navy.getShips()) {
            findFreeSpaceForShip(ship, checkCells, random);      // Здесь расставляем кораблики, начиная от наибольшего к наименьшему
        }
        return true;
    }

    // Рекурсивная функция, которая ищет точку для головы корабля и проверяет уместится ли корабль
    // Если умещается то продолжает работу и размещает корабль на игровом поле,
    // если не умещается, то перезапускает себя и ищет другую опорную (головную) точку
    // Правило, что суммарное количество палуб меньше чем двойная размерность поля защищает от бесконейной рекурсии
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
    void setShipOnFreeSpace(Ship s, char[][] ch, Point shipHead, boolean isHorizontal) {
        int x = 0;
        int y = 0;
        s.setIsHorizontal(isHorizontal);
        for (int i = 0; i < s.getDeckCount(); i++) {
            cells[shipHead.getX() + x][shipHead.getY() + y] = 'O';
            ch[shipHead.getX() + x][shipHead.getY() + y] = 'O';
            s.addPosition(shipHead.getX() + x, shipHead.getY() + y);
            if (isHorizontal) {
                x++;
            } else {
                y++;
            }
        }
        paintFreePointsAroundShip(s, ch);
    }

    void paintFreePointsAroundShip(Ship s) {
        paintFreePointsAroundShip(s, cells);
    }

    // Поле рисует вокруг корябля "область несоприкосновения"
    // 1) при создании игрового поля необходима при расстановке, чтобы корабли не касались друг друга, манипулирует с временным массивом
    // 2) в процессе игры рисует вокруг подбитого корябля характерную область, куда не стоит стрелять игроку
    void paintFreePointsAroundShip(Ship s, char[][] ch) {
        int x = 0;
        int y = 0;
        Point shipHead = s.getShipHead();
        for (int i = 0; i < s.getDeckCount(); i++) {
            if (s.isHorizontal()) {
                if (shipHead.getY() != 0) {
                    if (ch[shipHead.getX() + x][shipHead.getY() + y - 1] == '·') {
                        ch[shipHead.getX() + x][shipHead.getY() + y - 1] = '•';
                    }
                }
                if (shipHead.getY() != SIZE - 1) {
                    if (ch[shipHead.getX() + x][shipHead.getY() + y + 1] == '·') {
                        ch[shipHead.getX() + x][shipHead.getY() + y + 1] = '•';
                    }
                }
            } else {
                if (shipHead.getX() != 0) {
                    if (ch[shipHead.getX() + x - 1][shipHead.getY() + y] == '·') {
                        ch[shipHead.getX() + x - 1][shipHead.getY() + y] = '•';
                    }
                }
                if (shipHead.getX() != SIZE - 1) {
                    if (ch[shipHead.getX() + x + 1][shipHead.getY() + y] == '·') {
                        ch[shipHead.getX() + x + 1][shipHead.getY() + y] = '•';
                    }
                }
            }
            if (s.isHorizontal()) {
                x++;
            } else {
                y++;
            }
        }
        if (s.isHorizontal()) {
            if (shipHead.getX() != 0) {
                if (ch[shipHead.getX() - 1][shipHead.getY()] == '·') {
                    ch[shipHead.getX() - 1][shipHead.getY()] = '•';
                }
                if (shipHead.getY() != 0) {
                    if (ch[shipHead.getX() - 1][shipHead.getY() - 1] == '·') {
                        ch[shipHead.getX() - 1][shipHead.getY() - 1] = '•';
                    }
                }
                if (shipHead.getY() != SIZE - 1) {
                    if (ch[shipHead.getX() - 1][shipHead.getY() + 1] == '·') {
                        ch[shipHead.getX() - 1][shipHead.getY() + 1] = '•';
                    }
                }
            }
            if (shipHead.getX() + s.getDeckCount() != SIZE) {
                if (ch[shipHead.getX() + s.getDeckCount()][shipHead.getY()] == '·') {
                    ch[shipHead.getX() + s.getDeckCount()][shipHead.getY()] = '•';
                }
                if (shipHead.getY() != 0) {
                    if (ch[shipHead.getX() + s.getDeckCount()][shipHead.getY() - 1] == '·') {
                        ch[shipHead.getX() + s.getDeckCount()][shipHead.getY() - 1] = '•';
                    }
                }
                if (shipHead.getY() != SIZE - 1) {
                    if (ch[shipHead.getX() + s.getDeckCount()][shipHead.getY() + 1] == '·') {
                        ch[shipHead.getX() + s.getDeckCount()][shipHead.getY() + 1] = '•';
                    }
                }
            }
        } else {
            if (shipHead.getY() != 0) {
                if (ch[shipHead.getX()][shipHead.getY() - 1] == '·') {
                    ch[shipHead.getX()][shipHead.getY() - 1] = '•';
                }
                if (shipHead.getX() != 0) {
                    if (ch[shipHead.getX() - 1][shipHead.getY() - 1] == '·') {
                        ch[shipHead.getX() - 1][shipHead.getY() - 1] = '•';
                    }
                }
                if (shipHead.getX() != SIZE - 1) {
                    if (ch[shipHead.getX() + 1][shipHead.getY() - 1] == '·') {
                        ch[shipHead.getX() + 1][shipHead.getY() - 1] = '•';
                    }
                }
            }
            if (shipHead.getY() + s.getDeckCount() != SIZE) {
                if (ch[shipHead.getX()][shipHead.getY() + s.getDeckCount()] == '·') {
                    ch[shipHead.getX()][shipHead.getY() + s.getDeckCount()] = '•';
                }
                if (shipHead.getX() != 0) {
                    if (ch[shipHead.getX() - 1][shipHead.getY() + s.getDeckCount()] == '·') {
                        ch[shipHead.getX() - 1][shipHead.getY() + s.getDeckCount()] = '•';
                    }
                }
                if (shipHead.getX() != SIZE - 1) {
                    if (ch[shipHead.getX() + 1][shipHead.getY() + s.getDeckCount()] == '·') {
                        ch[shipHead.getX() + 1][shipHead.getY() + s.getDeckCount()] = '•';
                    }
                }
            }
        }
    }
}
