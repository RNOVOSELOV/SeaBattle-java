import java.util.Random;

/**
 * Created by novoselov on 16.09.2015.
 */
public class SeaButtle {
    public static void main(String[] args) {
        Player player = new Player();
        Field field = new Field();
        System.out.println("\nИгра началась:");
        while (field.isNotgameOver()) {
            field.showField();
            int shoot = player.getShoot();
            field.doShoot(shoot);
        }
        field.showField();
        System.out.println("Игра закончена.");
    }
}
