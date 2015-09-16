import java.util.Scanner;

/**
 * Created by novoselov on 16.09.2015.
 */
public class Player {
    String name;
    Scanner scanner;
    Player () {
        scanner = new Scanner(System.in);
//        System.out.print("Ваше имя: ");
//        name = scanner.nextLine();
    }
    int getShoot () {

        int shoot;
        do {
            System.out.print(name + ", введите координату для выстрела: ");
            if (scanner.hasNextInt()) {
                shoot = scanner.nextInt();
                if (shoot >=0 && shoot < Field.SIZE)
                    break;
                else
                    System.out.println(name + " вы ввели неверную координату, повторите ввод пожалуйста.");
            }
            scanner.nextLine();
        } while (true);
        return shoot;
    }
}
