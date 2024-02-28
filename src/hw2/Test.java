package hw2;

public class Test {
    public static void main(String[] args) {
        FuzzballGame game = new FuzzballGame(3);
        game = new FuzzballGame(3);
        game.ball();
        System.out.println(game.getBallCount()); // 1
        game.ball();
        System.out.println(game.getBallCount()); // 2
        game.ball();
        System.out.println(game.getBallCount()); // 3
        game.strike(true); // out!
        System.out.println(game.getBallCount()); // 0, since it's a new batter

        game = new FuzzballGame(3);
        game.hit(225); // a triple
        System.out.println(game.getBases()); // ooX
        game.ball();
        game.ball();
        game.ball();
        game.ball();
        System.out.println(game.getBallCount()); // 4
        game.ball(); // a walk
        System.out.println(game.getBases()); // XoX

    }
}
