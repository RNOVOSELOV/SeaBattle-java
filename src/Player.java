import java.util.Scanner;

/**
 * Created by novoselov on 16.09.2015.
 */
public class Player {
    private static int count;
    private String name;                // Имя игрока
    private int destroyedShipsCount;    // Количество уничтоженных игроком кораблей
    public enum PlayerGamePlay {COMPUTER, HUMAN;}
    private PlayerGamePlay gamePlay;

    Player() {
        destroyedShipsCount = 0;
        count++;
    }

    void setGamePlay (PlayerGamePlay gp) {
        gamePlay = gp;
    }

    public String getName() {
        return name;
    }

    // Устанавливаем имя игрока
    public void setName() {
        Scanner scanner;
        scanner = new Scanner(System.in);
        System.out.print("Имя игрока " + count + " [ВВОД - назначить имя по умолчанию]: ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            name = "Игрок - " + count;
        }
        this.name = name;
    }

    // Увеличить счетчик подбитых игроком кораблей
    public void playerDestroyShip() {
        destroyedShipsCount++;
    }

    public int getDestroyedShipsCount() {
        return destroyedShipsCount;
    }

    // Получаем от игрока координату для выстрела
    int getShoot(char ch) {
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