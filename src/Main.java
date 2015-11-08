/**
 * Created by novoselov on 22.09.2015.
 */
public class Main {
    public static void main(String[] args) {
        Battle battle = Battle.getInstance();
        battle.createPlayers();             // Создаем игроков
        if (battle.createNavy()) {           // Настраиваем игровое поле
            battle.startGame();             // Начинаем игровой цикл
        }
    }
}
