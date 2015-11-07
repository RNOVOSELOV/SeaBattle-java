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

    // Получаем от игрока координату для выстрела
    @Override
    public int getShoot(char ch) {
        Scanner scanner = new Scanner(System.in);
        int shoot;
        do {
            System.out.print(name + ", введите координату " + ch + " [1-" + Field.SIZE + "; 0 - чит]: ");
            if (scanner.hasNextInt()) {
                shoot = scanner.nextInt();
                if (shoot >= 1 && shoot <= Field.SIZE) {
                    break;
                } else if (shoot == 0) {        // Игрок хочет почитить ;-)
                    break;
                } else {
                    System.out.println(name + " вы ввели неверную координату, повторите ввод пожалуйста.");
                }
            }
            scanner.nextLine();
        } while (true);
        return shoot - 1; // Передаем в формате координат
    }
}