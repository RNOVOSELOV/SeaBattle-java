/**
 * Created by novoselov on 16.09.2015.
 */
public class SeaBattle {
    public static void main(String[] args) {
        Player[] players = new Player[2];
        for (int i = 0; i < players.length; i++) {
            Player temp = new Player();
            temp.setName();
            players[i] = temp;
            System.out.println(temp.getName());
        }

        Field field = new Field();
        field.tuneSettings();
        field.setShips();

        System.out.println("\nИгра началась:");
        Player player = players[0];
        boolean setNextPlayer = true;
        while (field.isNotGameOver()) {
            if (setNextPlayer) {
                player = players[0];
            } else {
                player = players[1];
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
