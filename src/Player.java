import java.util.ArrayList;

/**
 * Created by novoselov on 16.09.2015.
 */
public abstract class Player {
    protected String name;                // Имя игрока
    protected int destroyedShipsCount;    // Количество уничтоженных игроком кораблей
    public Navy navy;
    protected Field myField;

    public static enum INTELLIGENCE {HUMAN, COMPUTER;}
    public INTELLIGENCE intelligence;

    public abstract String getName();

    // Устанавливаем имя игрока
    public abstract void setName();

    // Увеличить счетчик подбитых игроком кораблей
    public abstract void playerDestroyShip();

    public abstract int getDestroyedShipsCount();

    // Получаем от игрока координату для выстрела
    public abstract Point getShoot();

    public void tuneFields() {
        myField = new Field();
    }

    public void createNavy(ArrayList ships) {
        navy = new Navy();
        navy.formFleet(ships);
    }

    public boolean setShips() {
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

    public boolean navyIsAlive() {
        return navy.navyHasNotSunk();
    }

    public boolean doShoot(Point shoot) {
        boolean shootIsFail = true;
        switch (getCellState(shoot)) {
            case FIRED_DECK:
                System.out.println("Тысяча чертей! Канонир, не трать снаряды, палуба уже подбита!");
                break;
            case FIRED_EMPTY_CELL:
                System.out.println("Тысяча чертей! Мы сюда уже стреляли, видимо наводчик пьян!");
                break;
            case NOT_FIRED_EMPTY_CELL:
                setCell(shoot, Field.CellState.FIRED_EMPTY_CELL);
                System.out.println("Мимо!");
                break;
            case NOT_FIRED_DECK:
                Ship s = navy.getShip(shoot);
                setCell(shoot, Field.CellState.FIRED_DECK);
                if ((s.getCurrentDeckCount() - 1) > 0) {
                    System.out.println("КАРАМБА! Есть попадание, еще чуть чуть: " + s.getName());
                } else {
                    System.out.println("СВИСТАТЬ ВСЕХ НАВЕРХ! Цель ликвидирована: " + s.getName());
                    paintFreePointsAroundShip(s);
                    playerDestroyShip();
                }
                s.setCrash();
                shootIsFail = false;
                break;
            default:
                System.out.println("ERROR! Unrecognazed symbol!");
        }
        System.out.println("");
        return shootIsFail;
    }

}