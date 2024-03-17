import java.util.List;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(4);
        Player player = new Player();
        board = board.placeTile();
        printBoard(board);
//        BacktrackingBeamSearch backtrack = new BacktrackingBeamSearch(board, 4, 5);
//        printBoard(backtrack.search());
        List<Board> successors = board.getSuccessors();
        for (Board succ : successors) {
            printBoard(succ);
        }
        // comment
//        do {
//            System.out.println("Player move");
//            System.out.println("=============");
//            board = player.makeMove(board);
//            printBoard(board);
//
////            System.out.println("Computer move");
////            System.out.println("===============");
////            board = board.placeTile();
////            printBoard(board);
//        } while (!board.emptyCells().isEmpty());
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
