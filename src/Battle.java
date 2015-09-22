/**
 * Created by novoselov on 16.09.2015.
 */
public class Battle {
    private Player[] players;
    private Field field;

    Battle() {
        players = new Player[2];
        field = new Field();
    }

    public void createPlayers() {
        for (int i = 0; i < players.length; i++) {
            Player temp = new Player();
            temp.setName();
            players[i] = temp;
        }
    }

    public void tuneField() {
        field.tuneSettings();
        field.setShips();
    }

    public void startGame() {
        System.out.println("\nИгра началась:");
        Player player = players[0];
        boolean changePlayers = true;
        while (field.isNotGameOver()) {
            if (changePlayers) {
                player = players[0];
            } else {
                player = players[1];
            }
            field.showField(false);
            int shoot = player.getShoot();
            if (field.doShoot(shoot))
                changePlayers = !changePlayers;
        }
        field.showField(false);
        System.out.println("Игра закончена. Победил игрок: " + player.getName());
    }
}
