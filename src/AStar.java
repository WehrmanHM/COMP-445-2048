import java.util.*;

public class AStar {

    public static List<Move> solve(Board initialBoard, int goalNum) {
        PriorityQueue<Board> openList = new PriorityQueue<>(Comparator.comparingInt(Board::getF));
        Set<Board> closedList = new HashSet<>();
        
        openList.add(initialBoard);
       
        
        while (!openList.isEmpty()) {
            
            Board currentBoard = openList.poll();
            Main.printBoard(currentBoard);

            if (currentBoard.isGoalState(goalNum)) {
                // Found the goal state, return the sequence of moves
                return currentBoard.getSolution();
            }
            
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
