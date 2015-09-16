import com.sun.glass.ui.Size;

/**
 * Created by novoselov on 16.09.2015.
 */
public class Field {
    int SIZE = 10;
    char[] cells = new char[SIZE];
    Ship ship;
    void init () {
        for (int i = 0; i < cells.length; i++) {
            cells [i] = '.';
        }
    }
    void showField () {
        System.out.println(cells);
        for (char cell : cells) {
            if (cell == 'X')
                System.out.print(".");
            else
                System.out.print(cell);
        }
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
        ship = s;
        cells[s.position] = 'X';
    }
}
