/**
 * Created by novoselov on 16.09.2015.
 */
public class Battle {
    // Список игроков (на данный момент поддерживается два игрока)
    private Player[] players;
    private Field field;

    // Создаем игроков и игровое поле
    Battle() {
        players = new Player[2];
        field = new Field();
    }

    // Настраиваем игроков
    public void createPlayers() {
        for (int i = 0; i < players.length; i++) {
            Player temp = new Player();
            temp.setName();
            players[i] = temp;
        }
    }

    // Настраиваем игровое поле и расставляем кораблики
    public void tuneField() {
        field.tuneSettings();
        field.setShips();
    }

    // В БОЙ!
    public void startGame() {
        System.out.println("\nИгра началась:");
        Player player = players[0];
        boolean changePlayers = true;
        boolean cheat = false;
        while (field.isNotGameOver()) {
            if (changePlayers) {
                player = players[0];
            } else {
                player = players[1];
            }
            field.showField(cheat);
            cheat = false;
            int shootX = player.getShoot('X');
            if (shootX == 99) {
                cheat = true;
                System.out.println("Чит: ");
                continue;
            }
            int shootY = player.getShoot('Y');
            if (shootY == 99) {
                cheat = true;
                System.out.println("Чит: ");
                continue;
            }
            if (field.doShoot(new Point(shootX, shootY))) {
                changePlayers = !changePlayers;
            }
        }
        field.showField(false);
        System.out.println("Игра закончена. Победил игрок: " + player.getName());
    }
}
