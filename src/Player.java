import java.util.Scanner;

/**
 * Created by novoselov on 16.09.2015.
 */
public class Player {
    String name;
    int getShoot () {
        Scanner scanner = new Scanner(System.in);
        int shoot;
        do {
            if (scanner.hasNextInt()) {
                shoot = scanner.nextInt();
                break;
            }
            scanner.nextLine();
        } while (true);
        System.out.println("Ваш выстрел: " + shoot);
        return shoot;
    }
}
