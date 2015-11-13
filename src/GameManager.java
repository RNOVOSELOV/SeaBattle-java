/**
 * Created by Роман on 08.11.2015.
 */
public class GameManager {
    private Player[] players;
    private Player currentPlayer;
    private static GameManager instance = null;

    private GameManager() {
    }

    public void createAndTunePlayers() {
        PlayerFactory pFactory = new PlayerFactory();
        players = new Player[2];
        players[0] = pFactory.createPlayer();
        players[1] = pFactory.createPlayer();
        currentPlayer = players[0];
    }

    public static GameManager getInstance() {
        if (instance == null)
            instance = new GameManager();
        return instance;
    }

    public boolean createNavyAndSetShips() {
        FleetConfigurator fc = FleetConfigurator.getFleetConfigurator();
        players[0].createNavy(fc.configureFleet());
        players[1].createNavy(fc.configureFleet());
        return players[0].setShips() && players[1].setShips();
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
        if (showOpponentShips == false && currentPlayer.intelligence == Player.INTELLIGENCE.COMPUTER) {
            return;
        }
        System.out.println(message);
        if (!showOpponentShips) {
            System.out.println("Моя карта:\t\t\t\t\t\t\t\tКарта соперника:");
        } else {
            System.out.println(currentPlayer.getName() + ":\t\t\t\t\t\t\t\t\t\t" + getOpponent().getName() + ":");
        }
        for (int j = 0; j < 2; j++) {
            System.out.print("  _|");
            for (int i = 1; i <= Field.SIZE; i++) {
                System.out.print("_" + i + "_");
            }
            System.out.print("\t\t");
        }
        System.out.println("");
        for (int i = 0; i < Field.SIZE; i++) {
            for (int j = 0; j < Field.SIZE; j++) {
                if (j == 0) {
                    System.out.printf("%2d |", i + 1);
                }
                System.out.print(" " + currentPlayer.myField.cells[j][i] + " ");
                if (j == Field.SIZE - 1) {
                    System.out.print(" \t\t");
                }
            }
            for (int j = 0; j < Field.SIZE; j++) {
                if (j == 0) {
                    System.out.printf("%2d |", i + 1);
                }
                if (getOpponent().myField.cells[j][i] == 'O' && showOpponentShips == false) {
                    System.out.print(" · ");
                } else {
                    System.out.print(" " + getOpponent().myField.cells[j][i] + " ");
                }
            }
            System.out.printf("\n");
        }
        System.out.printf("\n");
    }

    private void currentPlayShoot() {
        Point coordinate;
        if (currentPlayer.intelligence == Player.INTELLIGENCE.COMPUTER) {
            do {
                coordinate = currentPlayer.getShoot();
            }
            while (!(getOpponent().getCellState(coordinate) == Field.CellState.NOT_FIRED_EMPTY_CELL || getOpponent().getCellState(coordinate) == Field.CellState.NOT_FIRED_DECK));
        } else {
            coordinate = currentPlayer.getShoot();
        }
        System.out.println(currentPlayer.getName() + ": выстрел по точке с координатами " + coordinate.toString());
        if (getOpponent().doShoot(coordinate)) {
            changeCurrentPlayer();
        } else {
            if (currentPlayer.intelligence == Player.INTELLIGENCE.COMPUTER) {
                // Условие необходимо для реализации логики компьютера на добивание корабля, если удалось подбить палубу
                // Если промазали то пропускаем условие
                // Если подбитый корабль однопалубный, то тоже пропускаем условие
                // Если корабль ранен, то добавить в массив Computer.currentFiredShipCoordinates кординату подбитой палубы
                // очистить массив Computer.currentFiredShipCoordinates, если корабль уничтожен (чтобы компьютер искал новый корабль)
            }
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
        currentPlayer = players[0];
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
