import com.sun.glass.ui.Size;

import java.util.Random;

/**
 * Created by novoselov on 16.09.2015.
 */
public class Field {
    static int SIZE = 20;
    char[] cells;
    Ship ship;
    Field () {
        cells = new char[SIZE];
        ship = new Ship();
        init();
        setShip(ship);
    }
    void init () {
        for (int i = 0; i < cells.length; i++) {
            cells [i] = '.';
        }
    }
    void showField () {
        for (char cell : cells) {
            if (cell == 'X')
                System.out.print(".");
            else
                System.out.print(cell);
        }
        System.out.printf("\n");
    }
    void doShoot (int shoot) {
        switch (cells[shoot]) {
            case '.':
                System.out.println("Мазило!");
                cells[shoot] = '*';
                break;
            case '*':
                System.out.println("Кэп, сверь координаты, ты сюда уже стрелял!");
                break;
            case 'X':
                System.out.println("Цель ликвидирована!");
                cells[shoot] = 'O';
                break;
            default:
                System.out.println("ERROR! Unrecognazed symbol!");
        }
    }

    boolean isNotgameOver() {
        return cells[ship.position] == 'X';
    }

    void setShip (Ship s) {
        Random random = new Random();
        s.position = random.nextInt(Field.SIZE);
        cells[s.position] = 'X';
        System.out.print("Подсказка: ");
        System.out.println(cells);
    }
}
