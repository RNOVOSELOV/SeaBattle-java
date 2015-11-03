/**
 * Created by novoselov on 22.09.2015.
 */
public class Main {
    public static void main(String[] args) {
        Battle battle = Battle.getInstance();
        battle.tuneGamePlay();              // Настройка игрового процесса
        battle.createPlayers();             // Создаем игроков
        if (battle.tuneField()) {           // Настраиваем игровое поле
            battle.startGame();             // Начинаем игровой цикл
        }
    }
}
