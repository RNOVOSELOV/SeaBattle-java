import java.util.Scanner;

/**
 * Created by Роман on 07.11.2015.
 */
public class PlayerFactory {
    private Player player;

    public Player createPlayer() {
        Scanner scanner;
        Player.INTELLIGENCE intelligence;
        scanner = new Scanner(System.in);
        System.out.print("Игрок - человек [введите Y] или компьютер [введите любое значение]?");
        String n = scanner.nextLine();
        if (n.equals("Y") || n.equals("y")) {
            intelligence = Player.INTELLIGENCE.HUMAN;
        } else {
            intelligence = Player.INTELLIGENCE.COMPUTER;
        }
        return createPlayer(intelligence);
    }

    public Player createPlayer(Player.INTELLIGENCE intelligence) {
        switch (intelligence) {
            case COMPUTER:
                player = new Computer();
                break;
            case HUMAN:
            default:
                player = new Human();
                break;
        }
        player.setName();
        player.intelligence = intelligence;
        player.tuneFields();
        return player;
    }
}
