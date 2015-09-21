import java.util.Scanner;

/**
 * Created by novoselov on 16.09.2015.
 */
public class SeaBattle {
    public static void main(String[] args) {
        Player [] players = new Player[2];
        for (int i = 0; i < players.length; i++) {
            Player player = new Player();
            player.setName();
            players [i] = player;
            System.out.println(player.getName());
        }
        boolean setNextPlayer = true;
        Field field = new Field();

        System.out.println("\nИгра началась:");
        Player player = players[0];
        while (field.isNotGameOver()) {
            if (setNextPlayer) {
                player = players[0];
            } else {
                player = players [1];
            }
            field.showField();
            int shoot = player.getShoot();
            if (field.doShoot(shoot))
                setNextPlayer = !setNextPlayer;
        }
        field.showField();
        System.out.println("Игра закончена. Победил игрок: " + player.getName());
    }
}
