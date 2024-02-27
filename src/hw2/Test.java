package hw2;

public class Test {
    public static void main(String[] args) {
        FuzzballGame game = new FuzzballGame(3);
        System.out.println(game);
        game.strike(false);
        System.out.println(game); // one strike
        game.strike(false);
        System.out.println(game); // 0 strikes, one out, since it's a new batter
        game.strike(false);
        System.out.println(game); // one strike, one out
        game.strike(false);
        System.out.println(game); // 0 strikes, two outs, since it's a new batter
        game.strike(true); // batter is immediately out for swung strike
        System.out.println(game.isTopOfInning()); // should be false now
        System.out.println(game); // bottom of 1st inning, 0 outs

        game = new FuzzballGame(3);
        game.hit(15);
        System.out.println(game.runnerOnBase(1)); // true
        System.out.println(game.getBases()); // Xoo
        game.hit(15);
        System.out.println(game.getBases()); // XXo
        game.hit(15);
        System.out.println(game.getBases()); // XXo
        game.hit(15);
        System.out.println(game.getBases()); // XXX
        System.out.println(game.getTeam0Score()); // 1
        game.hit(150); System.out.println(game.getBases()); // oXX
        System.out.println(game.getTeam0Score()); // 3

    }
}
