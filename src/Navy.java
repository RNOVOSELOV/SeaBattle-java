import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by Роман on 03.11.2015.
 */
public class Navy {
    private ArrayList<Ship> ships;          // Массив корабрей, которые размещены на поле

    public Navy() {
        ships = new ArrayList<>();
    }

    // Настройка игровой флотилии, можно создать любую комбинацию кораблей, однако колличесто палуб не должно превышать SIZE*2,
    // иначе программа выдаст предупреждение и закончит работу
    void formFleet(ArrayList s) {
        for (int i = 0; i < s.size(); i = i + 2) {
            ships.add(new Ship((int) s.get(i), (String) s.get(i + 1)));
        }
        // Сортировка кораблей от максимального к минимальному в массиве
        // чтобы расставлять сначала наибольшие корабли
        Collections.sort(ships);
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    int getSumDecks() {
        int desc = 0;
        for (Ship ship : ships) {
            desc = desc + ship.getCurrentDeckCount();
        }
        return desc;
    }

    // Поиск корабля из флотилии по заданной координате
    Ship getShip(Point coordinate) {
        for (Ship ship : ships) {
            if (ship.isPlacedIn(coordinate))
                return ship;
        }
        return null;
    }

    // Проверка на потопление всех кораблей и окончание игры
    boolean navyHasNotSunk() {
        return (getSumDecks() != 0);
    }
}
