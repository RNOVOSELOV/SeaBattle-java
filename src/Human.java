import java.util.Scanner;

/**
 * Created by novoselov on 16.09.2015.
 */
public class Human extends Player {
    protected static int count;

    static {
        count = 1;
    }

    public Human() {
        destroyedShipsCount = 0;
    }

    @Override
    public String getName() {
        return name;
    }

    // Устанавливаем имя игрока
    @Override
    public void setName() {
        Scanner scanner;
        scanner = new Scanner(System.in);
        System.out.print("Имя игрока " + count + " [ВВОД - назначить имя по умолчанию]: ");
        String n = scanner.nextLine();
        if (n.isEmpty()) {
            n = "Игрок - " + count;
        }
        name = n;
        count++;
    }

    // Увеличить счетчик подбитых игроком кораблей
    @Override
    public void playerDestroyShip() {
        destroyedShipsCount++;
    }

    @Override
    public int getDestroyedShipsCount() {
        return destroyedShipsCount;
    }

    @Override
    public Point getShoot() {
        int shootX = getShoot('X');
        int shootY = getShoot('Y');
        return new Point(shootX, shootY);
    }

    // Получаем от игрока координату для выстрела
    @Override
    public int getShoot(char ch) {
        Scanner scanner = new Scanner(System.in);
        int shoot;
        do {
            System.out.print(name + ", введите координату " + ch + " [1-" + Field.SIZE + "]: ");
            if (scanner.hasNextInt()) {
                shoot = scanner.nextInt();
                if (shoot >= 1 && shoot <= Field.SIZE) {
                    break;
                } else {
                    System.out.println(name + " вы ввели неверную координату, повторите ввод пожалуйста.");
                }
            }
            scanner.nextLine();
        } while (true);
        return shoot - 1; // Передаем в формате координат
    }

    @Override
    public void printMaps(char[][] opponent, boolean showOpponentShips, String message, String name) {
        System.out.println(message);
        if (!showOpponentShips) {
            System.out.println("Моя карта:\t\t\t\t\t\t\t\tКарта соперника:");
        } else {
            System.out.println(getName() + ":\t\t\t\t\t\t\t\t\t\t\t" + name + ":");
        }
        for (int j = 0; j < 2; j++) {
            System.out.print("  _|");
            for (int i = 1; i <= Field.SIZE; i++) {
                System.out.print("_" + i + "_");
            }
            System.out.print("\t\t");
        }
        System.out.println("");
        for (int i = 0; i < Field.SIZE; i++) {
            for (int j = 0; j < Field.SIZE; j++) {
                if (j == 0) {
                    System.out.printf("%2d |", i + 1);
                }
                System.out.print(" " + myField.cells[j][i] + " ");
                if (j == Field.SIZE - 1) {
                    System.out.print(" \t\t");
                }
            }
            for (int j = 0; j < Field.SIZE; j++) {
                if (j == 0) {
                    System.out.printf("%2d |", i + 1);
                }
                if (opponent[j][i] == 'O' && showOpponentShips == false) {
                    System.out.print(" · ");
                } else {
                    System.out.print(" " + opponent[j][i] + " ");
                }
            }
            System.out.printf("\n");
        }
        System.out.printf("\n");
    }
}


