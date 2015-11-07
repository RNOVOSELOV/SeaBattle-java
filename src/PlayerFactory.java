/**
 * Created by Роман on 07.11.2015.
 */
public class PlayerFactory {
    Player player;

    Player createPlayer(Player.INTELLIGENCE intelligence) {
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
        return player;
    }
}
