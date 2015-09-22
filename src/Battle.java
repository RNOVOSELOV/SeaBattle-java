/**
 * Created by novoselov on 16.09.2015.
 */
public class Battle {
    // Список игроков (на данный момент поддерживается два игрока)
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
            int shootX = player.getShoot('X');
            int shootY = player.getShoot('Y');
            if (field.doShoot(new Point(shootX, shootY)))
                changePlayers = !changePlayers;
        }
        field.showField(false);
        System.out.println("Игра закончена. Победил игрок: " + player.getName());
    }
}
