/**
 * Created by novoselov on 16.09.2015.
 */
public abstract class Player {
    protected String name;                // Имя игрока
    protected int destroyedShipsCount;    // Количество уничтоженных игроком кораблей
    private Field myField;
    private Field opponentMap;

    public static enum INTELLIGENCE {HUMAN, COMPUTER;}

    public abstract String getName();

    // Устанавливаем имя игрока
    public abstract void setName();

    // Увеличить счетчик подбитых игроком кораблей
    public abstract void playerDestroyShip();

    public abstract int getDestroyedShipsCount();

    // Получаем от игрока координату для выстрела
    public abstract int getShoot(char ch);

    public void tuneFields() {
        myField = new Field();
        opponentMap = new Field();
    }

    public boolean setShips(Navy navy) {
        return myField.setShips(navy);
    }

    public Field.CellState getCellState(Point point) {
        return myField.getCellState(point);
    }

    public void setCell(Point point, Field.CellState cellState) {
        myField.setCell(point, cellState);
    }

    public void paintFreePointsAroundShip(Ship s) {
        myField.paintFreePointsAroundShip(s);
    }
}