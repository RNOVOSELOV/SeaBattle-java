import java.util.Scanner;

/**
 * Created by novoselov on 16.09.2015.
 */
public class Player {
    String name;        // Имя игрока
    Scanner scanner;

    // Конструктор, получаем имя игрока
    Player () {
        scanner = new Scanner(System.in);
        System.out.print("Ваше имя: ");
        name = scanner.nextLine();
    }

    // Получаем от игрока координату для выстрела
    int getShoot () {

        int shoot;
        do {
            System.out.print(name + ", введите координату для выстрела [1-" + Field.SIZE + "]: ");
            if (scanner.hasNextInt()) {
                shoot = scanner.nextInt();
                if (shoot >=1 && shoot <= Field.SIZE)
                    break;
                else
                    System.out.println(name + " вы ввели неверную координату, повторите ввод пожалуйста.");
            }
            scanner.nextLine();
        } while (true);
        return shoot-1; // Передаем в формате координат
    }
}
