/**
 * Created by novoselov on 16.09.2015.
 */
public class Battle {
    // На данный момент игра завязана на два игрока, поле общее, уничтожаемый флот тоже общий
    // Побеждает тот, кто уничтожил большее количество кораблей
    private Player[] players;
    private Field field;
    private Navy navy;
    private static Battle instance = null;

    private Battle() {
    }

    public static Battle getInstance() {
        if (instance == null)
            instance = new Battle();
        return instance;
    }

    // Настраиваем игроков
    public void createAndTunePlayers() {
        players = new Player[2];
        for (int i = 0; i < players.length; i++) {
            Player temp = new Player();
            temp.setName();
            players[i] = temp;
        }
    }

    // Настраиваем игровое поле и расставляем кораблики
    public boolean tuneField() {
        field = new Field();
        navy = new Navy();
        navy.formFleet();
        return field.setShips(navy);
    }

    // В БОЙ!
    public void startGame() {
        System.out.println("\nИгра началась:");
        Player player = players[0];
        boolean changePlayers = true;
        boolean cheat = false;
        while (navy.navyHasNotSunk()) {
            if (changePlayers) {
                player = players[0];
            } else {
                player = players[1];
            }
            field.showField(cheat);
            cheat = false;
            int shootX = player.getShoot('X');
            if (shootX < 0) {
                cheat = true;
                System.out.println("Чит: ");
                continue;
            }
            int shootY = player.getShoot('Y');
            if (shootY < 0) {
                cheat = true;
                System.out.println("Чит: ");
                continue;
            }
            if (doShoot(player, new Point(shootX, shootY))) {
                changePlayers = !changePlayers;
            }
        }
        field.showField(false);
        if (players[0].getDestroyedShipsCount() == players[1].getDestroyedShipsCount()) {
            System.out.println("Игра закончена. Победила дружба, игроки подбили одинаковое количество кораблей (" + player.getDestroyedShipsCount() + ")");
        } else if (players[0].getDestroyedShipsCount() > players[1].getDestroyedShipsCount()) {
            System.out.println("Игра закончена. Победил игрок: " + players[0].getName() + ". Количество уничтоженных кораблей - " + players[0].getDestroyedShipsCount());
        } else {
            System.out.println("Игра закончена. Победил игрок: " + players[1].getName() + ". Количество уничтоженных кораблей - " + players[1].getDestroyedShipsCount());
        }
    }

    boolean doShoot(Player player, Point shoot) {
        boolean shootIsFail = true;
        switch (field.getCellState(shoot)) {
            case FIRED_DECK:
                System.out.println("Тысяча чертей! Канонир, не трать снаряды, палуба уже подбита!");
                break;
            case FIRED_EMPTY_CELL:
                System.out.println("Тысяча чертей! Мы сюда уже стреляли, видимо наводчик пьян!");
                break;
            case NOT_FIRED_EMPTY_CELL:
                field.setCell(shoot, Field.CellState.FIRED_EMPTY_CELL);
                System.out.println("Мимо!");
                break;
            case NOT_FIRED_DECK:
                Ship s = navy.getShip(shoot);
                field.setCell(shoot, Field.CellState.FIRED_DECK);
                if ((s.getCurrentDeckCount() - 1) > 0) {
                    System.out.println("КАРАМБА! Есть попадание, еще чуть чуть: " + s.getName());
                } else {
                    System.out.println("СВИСТАТЬ ВСЕХ НАВЕРХ! Цель ликвидирована: " + s.getName());
                    field.paintFreePointsAroundShip(s);
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
}
