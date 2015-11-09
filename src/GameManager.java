import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Роман on 08.11.2015.
 */
public class GameManager {
    private Player[] players;
    private Player currentPlayer;
    private static GameManager instance = null;

    private GameManager() {
    }

    public static GameManager getInstance() {
        if (instance == null)
            instance = new GameManager();
        return instance;
    }

    public void createAndTunePlayers() {
        PlayerFactory pFactory = new PlayerFactory();
        players = new Player[2];
        players[0] = pFactory.createPlayer(Player.INTELLIGENCE.HUMAN);
        players[1] = pFactory.createPlayer(Player.INTELLIGENCE.COMPUTER);
        currentPlayer = players[0];
    }

    public boolean createNavyAndSetShips() {
        ArrayList ships = new ArrayList();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Нажмите любую клавишу чтоб создать флот вручную [ВВОД - назначить корабли по умолчанию]: ");
        if (scanner.nextLine().isEmpty()) {
            ships = addShipsByDefault();
        } else {
            do {
                int deck = getShipDeckCount();
                if (deck == 0) {
                    if (ships.size() == 0) {
                        System.out.println("Вы не содали ни одного корабля, создана флотилия по умолчанию");
                        ships = addShipsByDefault();
                    }
                    break;
                } else {
                    ships.add(deck);
                    ships.add(getShipName(deck));
                }
            } while (true);
        }
        players[0].createNavy(ships);
        players[1].createNavy(ships);
        return players[0].setShips() && players[1].setShips();
    }

    // Ручное добавление корабля во флотилию
    private String getShipName(int deck) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя корабля [ВВОД - назначить имя по умолчанию]: ");
        String name = "";
        name = scanner.nextLine();
        return name;
    }

    // Запрос количества палуб для создаваемого вручную корабля
    private int getShipDeckCount() {
        int deck;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Введите палубность корабля [1-" + Field.SIZE + "; 0 - прекратить настройку кораблей]: ");
            if (scanner.hasNextInt()) {
                deck = scanner.nextInt();
                if (deck >= 0 && deck <= Field.SIZE) {
                    break;
                } else {
                    System.out.println("Вы ввели неверную размерность корабля, он неумещается на поле, повторите ввод пожалуйста.");
                }
            }
            scanner.nextLine();
        } while (true);
        return deck;
    }

    // Автогенерация флотилии
    // Комбинация по умолчанию, по классике: 4 палубы - 1 шт; 3 палубы - 2 шт; 2 палубы - 3 шт; 1 палуба - 4 шт
    private ArrayList<java.io.Serializable> addShipsByDefault() {
        ArrayList<java.io.Serializable> s = new ArrayList<java.io.Serializable>();
        s.add(4);
        s.add("Авианосец \"Авраам Линкольн\"");
        s.add(3);
        s.add("Ракетный крейсер \"Анцио\"");
        s.add(3);
        s.add("Ракетный крейсер \"Порт Роял\"");
        s.add(2);
        s.add("Эсминец \"Дональд Кук\"");
        s.add(2);
        s.add("Эсминец \"Арли Берк\"");
        s.add(2);
        s.add("Эсминец \"Спрюенс\"");
        s.add(1);
        s.add("Катер \"Фридом\"");
        s.add(1);
        s.add("Катер \"Форт Ворф\"");
        s.add(1);
        s.add("Катер \"Индепенденс\"");
        s.add(1);
        s.add("Катер \"Коронадо\"");
        return s;
    }

    private void changeCurrentPlayer() {
        if (currentPlayer == players[0]) {
            currentPlayer = players[1];
        } else {
            currentPlayer = players[0];
        }
    }

    private Player getOpponent() {
        if (currentPlayer == players[0]) {
            return players[1];
        } else {
            return players[0];
        }
    }

    private boolean isNotGameOver() {
        return players[0].navyIsAlive() && players[1].navyIsAlive();
    }

    private void playersShowField(boolean showOpponentShips, String message) {
        currentPlayer.printMaps(getOpponent().myField.cells, showOpponentShips, message, getOpponent().getName());
    }

    private void currentPlayShoot() {
        int shootX = currentPlayer.getShoot('X');
        int shootY = currentPlayer.getShoot('Y');
        Point coordinate = new Point(shootX, shootY);
        System.out.println(currentPlayer.getName() + ": выстрел по точке с координатами " + coordinate.toString());
        if (getOpponent().doShoot(coordinate)) {
            changeCurrentPlayer();
        }
    }

    // В БОЙ!
    public void startGame() {
        playersShowField(true, "\n" + "Чит режим активирован.");
        System.out.println("\nИгра началась:");
        while (isNotGameOver()) {
            playersShowField(false, "Ход игрока: " + currentPlayer.getName());
            currentPlayShoot();
        }
        playersShowField(true, "\n" + "Результаты игры.");
        showResults();
    }

    private void showResults() {
        System.out.println("Игра закончена.");
        if (players[0].navyIsAlive()) {
            System.out.println("Победил игрок: " + players[0].getName());
        } else if (players[1].navyIsAlive()) {
            System.out.println("Победил игрок: " + players[1].getName());
        }
    }
}