/**
 * Created by novoselov on 16.09.2015.
 */
public class Battle {
    private static Battle instance = null;
    private GameManager gameManager;

    private Battle() {
        gameManager = new GameManager();
    }

    public static Battle getInstance() {
        if (instance == null)
            instance = new Battle();
        return instance;
    }

    // Настраиваем игроков
    public void createPlayers() {
        gameManager.createAndTunePlayers();
    }

    // Настраиваем игровое поле и расставляем кораблики
    public boolean createNavy() {
        return gameManager.createNavyAndSetSheeps();
    }

    // В БОЙ!
    public void startGame() {
        gameManager.cheat ();//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        System.out.println("\nИгра началась:");
        while (gameManager.isNotGameOver()) {
            gameManager.currentPlayerShowField();
            gameManager.currentPlayShoot ();
        }
        gameManager.cheat(); // пОКАЗАТЬ ПОЛЯ ПОСЛЕ ИГРЫ
        gameManager.showResults ();
    }
}
