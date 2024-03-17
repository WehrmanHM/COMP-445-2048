import java.util.Random;

public class Player {
    private Random rng = new Random();

    public Board makeMove(Board input) {
        Move move = Move.values()[rng.nextInt(4)];
        return input.move(move);
    }
}
