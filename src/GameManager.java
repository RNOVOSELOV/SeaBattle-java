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

    public boolean createNavyAndSetSheeps() {
        players[0].createNavy();
        players[1].createNavy();
        return players[0].setShips() && players[1].setShips();
    }

    private void changeCurrentPlayer() {
        if (currentPlayer == players[0]) {
            currentPlayer = players[1];
        } else {
            currentPlayer = players[0];
        }
    }

    private boolean isNotGameOver() {
        return players[0].navyIsAlive() && players[1].navyIsAlive();
    }

    private void currentPlayerShowField() {
        boolean cheat = false;
        currentPlayer.printMaps(cheat);
    }

    private void cheat() {
        players[0].printMaps(true);
        players[1].printMaps(true);
    }

    private void currentPlayShoot() {
        int shootX = currentPlayer.getShoot('X');
        int shootY = currentPlayer.getShoot('Y');
        if (doShoot(currentPlayer, new Point(shootX, shootY))) {
            changeCurrentPlayer();
        }
    }

    // В БОЙ!
    public void startGame() {
        cheat ();//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 /*
        System.out.println("\nИгра началась:");
        while (isNotGameOver()) {
            currentPlayerShowField();
            currentPlayShoot ();
        }

        cheat(); // пОКАЗАТЬ ПОЛЯ ПОСЛЕ ИГРЫ
        showResults ();
        */
    }

    private boolean doShoot(Player player, Point shoot) {
        boolean shootIsFail = true;
        System.out.println(player.getName() + ": выстрел по точке с координатами " + shoot.toString());
        switch (player.getCellState(shoot)) {
            case FIRED_DECK:
                System.out.println("Тысяча чертей! Канонир, не трать снаряды, палуба уже подбита!");
                break;
            case FIRED_EMPTY_CELL:
                System.out.println("Тысяча чертей! Мы сюда уже стреляли, видимо наводчик пьян!");
                break;
            case NOT_FIRED_EMPTY_CELL:
                player.setCell(shoot, Field.CellState.FIRED_EMPTY_CELL);
                System.out.println("Мимо!");
                break;
            case NOT_FIRED_DECK:
                Ship s = currentPlayer.navy.getShip(shoot);
                player.setCell(shoot, Field.CellState.FIRED_DECK);
                if ((s.getCurrentDeckCount() - 1) > 0) {
                    System.out.println("КАРАМБА! Есть попадание, еще чуть чуть: " + s.getName());
                } else {
                    System.out.println("СВИСТАТЬ ВСЕХ НАВЕРХ! Цель ликвидирована: " + s.getName());
                    player.paintFreePointsAroundShip(s);
                    player.playerDestroyShip();
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

    private void showResults() {
        if (players[0].getDestroyedShipsCount() == players[1].getDestroyedShipsCount()) {
            System.out.println("Игра закончена. Победила дружба, игроки подбили одинаковое количество кораблей (" + currentPlayer.getDestroyedShipsCount() + ")");
        } else if (players[0].getDestroyedShipsCount() > players[1].getDestroyedShipsCount()) {
            System.out.println("Игра закончена. Победил игрок: " + players[0].getName() + ". Количество уничтоженных кораблей - " + players[0].getDestroyedShipsCount());
        } else {
            System.out.println("Игра закончена. Победил игрок: " + players[1].getName() + ". Количество уничтоженных кораблей - " + players[1].getDestroyedShipsCount());
        }
    }
}
