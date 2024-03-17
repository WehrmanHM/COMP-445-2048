import java.util.List;

public class Main {
    public static void main(String[] args) {
        runGameAStar(16);
        //runGameRandom();
    }

    public static void runGameAStar(int goal){
        Board board = new Board(4);
        for (int i = 0; i < 2; i++) {
            board.placeTile();
        }
        // Call the A* solver to find the solution
        List<Move> solution = AStar.solve(board, goal);
        // Check if a solution is found
        if (solution != null) {
            // Print the solution
            System.out.println("Solution:");
            for (Move move : solution) {
                System.out.println("Player move");
            System.out.println("=============");
            board = board.move(move);
            printBoard(board);

            System.out.println("Computer move");
            System.out.println("===============");
            board = board.placeTile();
            printBoard(board);
            }
        } else {
            System.out.println("No solution found.");
        }
    }

    public static void runGameRandom(){
        Board board = new Board(4);
        Player player = new Player();
        for (int i = 0; i < 2; i++) {
            board.placeTile();
        }
        do {
            System.out.println("Player move");
            System.out.println("=============");
            board = player.makeMove(board);
            printBoard(board);

            System.out.println("Computer move");
            System.out.println("===============");
            board = board.placeTile();
            printBoard(board);
        } while (!board.emptyCells().isEmpty());   
    }

    public static void printBoard(Board board) {
        StringBuilder topLines = new StringBuilder();
        StringBuilder midLines = new StringBuilder();
        for (int x = 0; x < board.getSize(); ++x) {
            topLines.append("+--------");
            midLines.append("|        ");
        }
        topLines.append("+");
        midLines.append("|");

        for (int y = 0; y < board.getSize(); ++y) {
            System.out.println(topLines);
            System.out.println(midLines);
            for (int x = 0; x < board.getSize(); ++x) {
                Cell cell = new Cell(x, y);
                System.out.print("|");
                if (board.isEmpty(cell)) {
                    System.out.print("        ");
                } else {
                    StringBuilder output = new StringBuilder(Integer.toString(board.getCell(cell)));
                    while (output.length() < 8) {
                        output.append(" ");
                        if (output.length() < 8) {
                            output.insert(0, " ");
                        }
                    }
                    System.out.print(output);
                }
            }
            System.out.println("|");
            System.out.println(midLines);
        }
        System.out.println(topLines);
        System.out.println("Score: " + board.getScore());
    }
}
