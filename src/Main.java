import java.util.List;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(4);
        Player player = new Player();
        board = board.placeTile();
        board = board.placeTile();
        for (int i = 75; i <= 200; i++) {
            try {
                BacktrackingBeamSearch backtrack = new BacktrackingBeamSearch(board, 16384, i);
                System.out.println("Score at beam width " + i + ": " + backtrack.search().getScore());
                board = new Board(4);
                board = board.placeTile().placeTile();
            } catch (NullPointerException n) {
                //System.out.println(n.getMessage());
                System.out.println("Search failed for beam width " + i);
            }
            }
        }
//        List<Board> successors = board.getSuccessors();
//        for (Board succ : successors) {
//            printBoard(succ);
//        }

//        do {
//            System.out.println("Player move");
//            System.out.println("=============");
//            board = player.makeMove(board);
//            printBoard(board);
////
////            System.out.println("Computer move");
////            System.out.println("===============");
////            board = board.placeTile();
////            printBoard(board);
//        } while (!board.emptyCells().isEmpty());
//  }
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
