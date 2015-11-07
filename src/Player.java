/**
 * Created by novoselov on 16.09.2015.
 */
public abstract class Player {
    protected String name;                // Имя игрока
    protected int destroyedShipsCount;    // Количество уничтоженных игроком кораблей

    public static enum INTELLIGENCE {HUMAN, COMPUTER;}

    public abstract String getName();

    // Устанавливаем имя игрока
    public abstract void setName();

    // Увеличить счетчик подбитых игроком кораблей
    public abstract void playerDestroyShip();

    public abstract int getDestroyedShipsCount();

    // Получаем от игрока координату для выстрела
    public abstract int getShoot(char ch);
}