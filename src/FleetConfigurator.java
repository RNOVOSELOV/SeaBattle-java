import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Роман on 14.11.2015.
 */
public class FleetConfigurator {
    private ArrayList ships;
    private static FleetConfigurator instance = null;

    private FleetConfigurator() {
        ships = new ArrayList();
    }

    public static FleetConfigurator getFleetConfigurator() {
        if (instance == null) {
            instance = new FleetConfigurator();
        }
        return instance;
    }

    public ArrayList configureFleet() {
        if (ships.size() == 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Нажмите любую клавишу чтоб создать флот вручную [ВВОД - назначить корабли по умолчанию]: ");
            if (scanner.nextLine().isEmpty()) {
                ships = addShipsByDefault();
            } else {
                do {
                    int deck = getShipDeckCount();
                    if (deck == 0) {
                        if (ships.size() == 0) {
                            System.out.println("Вы не содали ни одного корабля, создана флотилия по умолчанию");
                            ships = addShipsByDefault();
                        }
                        break;
                    } else {
                        ships.add(deck);
                        ships.add(getShipName(deck));
                    }
                } while (true);
            }
        }
        return ships;
    }

    // Ручное добавление корабля во флотилию
    private String getShipName(int deck) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя корабля [ВВОД - назначить имя по умолчанию]: ");
        String name = "";
        name = scanner.nextLine();
        return name;
    }

    // Запрос количества палуб для создаваемого вручную корабля
    private int getShipDeckCount() {
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
    private ArrayList<java.io.Serializable> addShipsByDefault() {
        ArrayList<java.io.Serializable> s = new ArrayList<java.io.Serializable>();
        s.add(4);
        s.add("Авианосец \"Авраам Линкольн\"");
        s.add(3);
        s.add("Ракетный крейсер \"Анцио\"");
        s.add(3);
        s.add("Ракетный крейсер \"Порт Роял\"");
        s.add(2);
        s.add("Эсминец \"Дональд Кук\"");
        s.add(2);
        s.add("Эсминец \"Арли Берк\"");
        s.add(2);
        s.add("Эсминец \"Спрюенс\"");
        s.add(1);
        s.add("Катер \"Фридом\"");
        s.add(1);
        s.add("Катер \"Форт Ворф\"");
        s.add(1);
        s.add("Катер \"Индепенденс\"");
        s.add(1);
        s.add("Катер \"Коронадо\"");
        return s;
    }
}
