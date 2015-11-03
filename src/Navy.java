import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by Роман on 03.11.2015.
 */
public class Navy {
    private ArrayList<Ship> ships;          // Массив корабрей, которые размещены на поле

    public Navy() {
        ships = new ArrayList<>();
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
        // Сортировка кораблей от максимального к минимальному в массиве
        // чтобы расставлять сначала наибольшие корабли
        Collections.sort(ships);
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
    // Комбинация по умолчанию, по классике: 4 палубы - 1 шт; 3 палубы - 2 шт; 2 палубы - 3 шт; 1 палуба - 4 шт
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

    public ArrayList<Ship> getShips() {
        return ships;
    }

    int getSumDecks() {
        int desc = 0;
        for (Ship ship : ships) {
            desc = desc + ship.getCurrentDeckCount();
        }
        return desc;
    }

    // Поиск корабля из флотилии по заданной координате
    Ship getShip(Point coordinate) {
        for (Ship ship : ships) {
            if (ship.isPlacedIn(coordinate))
                return ship;
        }
        return null;
    }

    // Проверка на потопление всех кораблей и окончание игры
    boolean navyHasNotSunk() {
        return (getSumDecks() != 0);
    }
}
