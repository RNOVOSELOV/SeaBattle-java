import com.sun.glass.ui.Size;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by novoselov on 16.09.2015.
 */
public class Field {
    static int SIZE = 20;
    char[] cells;
    Ship[] ships;
    Random random;

    Field() {
        cells = new char[SIZE];
        ships = new Ship[3];
        ships[0] = new Ship(3, "Линкор Тирпиц");        // Корабль Трехпалубный
        ships[1] = new Ship(2, "Крейсер Варяг");        // Двухпалубный
        ships[2] = new Ship(1, "Катер Неустрашимый");   // Однопалубный

        random = new Random();
        init(cells);
        setShips();
    }

    void init(char[] cl) {
        for (int i = 0; i < cl.length; i++) {
            cl[i] = '.';
        }
    }

    void showField() {
        for (char cell : cells) {
            if (cell == 'X')
                System.out.print(".");
            else
                System.out.print(cell);
        }
        System.out.printf("\n");
    }

    int getSumDecks () {
        int desc = 0;
        for (Ship ship : ships) {
            desc = desc + ship.deckCount;
        }
        return desc;
    }

    Ship getShip (int coord)
    {
        for (Ship ship : ships) {
            for (int i = 0; i < ship.position.length; i++) {
                if (ship.position[i] == coord)
                    return  ship;
            }
        }
        return null;
    }

    void doShoot(int shoot) {
        switch (cells[shoot]) {
            case '.':
                System.out.println("Мазило!");
                cells[shoot] = '*';
                break;
            case 'O':
            case '*':
                System.out.println("Кэп, сверь координаты, ты сюда уже стрелял!");
                break;
            case 'X':
                Ship s = getShip(shoot);
                s.deckCount--;
                if (s.deckCount != 0)
                    System.out.println(s.name + ": Ранен! Еще чуть чуть...");
                else
                    System.out.println(s.name + ": Цель ликвидирована!");
                cells[shoot] = 'O';
                break;
            default:
                System.out.println("ERROR! Unrecognazed symbol!");
        }
        System.out.println("");
    }

    boolean isNotgameOver() {
        return (getSumDecks()!=0);
    }

    void setShips () {
        int decks = getSumDecks();
        if (decks > SIZE/3) {
            System.out.println("Слишком много кораблей, заканчиваем играть, неинтересно!");
            System.out.println("Для продолжения игры необходимо увеличить размерность поля либо уменшить размерность кораблей.");
            return;
        }
        char [] checkCells = new char[SIZE];
        init(checkCells);
        for (Ship ship : ships) {
            setShip(ship, checkCells);
        }
        System.out.print("Чит: ");
        System.out.println(cells);
    }

    void setShip(Ship s, char[] ch) {
        int tempPosition = random.nextInt(SIZE - s.deckCount + 1);
        for (int i = 0; i < s.deckCount; i++) {
            if (ch[tempPosition + i] != '.') {
                setShip(s, ch);
                return;
            }
        }
        s.position[0] = tempPosition;
        for (int i = 0; i < s.deckCount; i++) {
            cells[tempPosition+i] = 'X';
            ch[tempPosition+i] = 'X';
            s.position[i] = tempPosition+i;
        }
        if (tempPosition != 0) {
            ch[tempPosition-1] = 'X';
        }
        if (tempPosition + s.deckCount < SIZE){
            ch[tempPosition + s.deckCount] = 'X';
        }
    }
}
