import java.util.*;

public class AStar {

    public static List<Move> solve(Board initialBoard, int goalNum, int verbosity) {
        PriorityQueue<Board> openList = new PriorityQueue<>(Comparator.comparingInt(Board::getF).reversed());
        Set<Board> closedList = new HashSet<>();
        
        openList.add(initialBoard);
       
        
        while (!openList.isEmpty()) {
            
            Board currentBoard = openList.poll();
            if (verbosity == 0 || verbosity == 1){Main.printBoard(currentBoard);}

            if (currentBoard.isGoalState(goalNum)) {
                // Found the goal state, return the sequence of moves
                if (verbosity == 2){System.out.println("Max Score: "+currentBoard.getScore());}
                return currentBoard.getSolution();
            }
            // else if (currentBoard.emptyCells().size() == 0 && !currentBoard.canMerge()) {
            //     return null;   
            // }
            
            closedList.add(currentBoard);

            // Generate successor states by applying each possible move
            for (Move move : Move.values()) {
                Board successor = currentBoard.move(move);
                if (successor != null && !closedList.contains(successor)) {
                    openList.add(successor);
                }
            }
        }
        
        // If no solution is found
        return null;
    }
}
