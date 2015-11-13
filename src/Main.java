/**
 * Created by novoselov on 22.09.2015.
 */
public class Main {
    public static void main(String[] args) {
        GameManager game = GameManager.getInstance();
        game.createAndTunePlayers();                    // Создаем игроков
        if (game.createNavyAndSetShips()) {             // Настраиваем игровое поле
            game.startGame();                           // Начинаем игровой цикл
        }
    }
}
