/**
 * Created by novoselov on 22.09.2015.
 */
public class Main {
    public static void main(String[] args) {
        Battle battle = new Battle();
        battle.createPlayers();         // Создаем игроков
        battle.tuneField();             // Настраиваем игровое поле
        battle.startGame();             // Начинаем игровой цикл
    }
}