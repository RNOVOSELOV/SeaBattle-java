import java.util.Random;

/**
 * Created by Роман on 07.11.2015.
 */
public class Computer extends Player {
    protected static int count;

    static {
        count = 1;
    }

    public Computer() {
        destroyedShipsCount = 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName() {
        name = "Компьютер - " + count++;
    }

    @Override
    public void playerDestroyShip() {
        destroyedShipsCount++;
    }

    @Override
    public int getDestroyedShipsCount() {
        return destroyedShipsCount;
    }

    @Override
    public int getShoot(char ch) {
        Random rnd = new Random();
        rnd.nextInt();
        return rnd.nextInt(Field.SIZE);
    }

    @Override
    public void printMaps(char[][] opponent, boolean showOpponentShips, String message, String name) {
        return;
    }
}
