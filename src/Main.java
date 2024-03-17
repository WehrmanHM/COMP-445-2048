import java.util.List;

public class Main {
    public static void main(String[] args) {
        runAStar(16);
    }

    public static void runAStar(int goalNum){

        Board board = new Board(4);
        // Create the initial board state
        board = board.placeTile();
        printBoard(board);

        // Call the A* solver to find the solution
        List<Move> solution = AStar.solve(board, goalNum);

        // Check if a solution is found
        if (solution != null) {
            // Print the solution
            System.out.println("Solution:");
            for (Move move : solution) {
                board =board.move(move);
                printBoard(board);
            }
        } else {
            System.out.println("No solution found.");
        }
        
        // List<Board> successors = board.getSuccessors();
        // for (Board succ : successors) {
        //     printBoard(succ);
        // }
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