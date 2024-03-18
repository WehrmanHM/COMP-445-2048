import java.util.List;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++){
            runAStar(4096, 2);
        }
        
    }

    public static void runAStar(int goalNum, int verbosity){

        Board board = new Board(4);
        // Create the initial board state
        board = board.placeTile();

        // Call the A* solver to find the solution
        long startTime = System.nanoTime();
        List<Move> solution = AStar.solve(board, goalNum, verbosity);
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
    // Convert elapsed time from nanoseconds to milliseconds
        double elapsedTimeInSeconds = elapsedTime / 1_000_000_000.0;
        // Check if a solution is found
        if (solution != null) {
            // Print the solution
            if (verbosity == 1 || verbosity == 2){
            System.out.println("Solution:");
            System.out.println("Elapsed time: " + elapsedTimeInSeconds + " seconds");
            System.out.println(solution.size() +" moves \n \n ");
            }
            if (verbosity == 0){System.out.println(solution);}
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