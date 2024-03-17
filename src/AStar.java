import java.util.*;

public class AStar {

    public static List<Move> solve(Board initialBoard, int goalNum) {
        PriorityQueue<Board> openList = new PriorityQueue<>(Comparator.comparingInt(Board::getF));
        Set<Board> closedList = new HashSet<>();
        
        openList.add(initialBoard);
        
        while (!openList.isEmpty()) {
            Board currentBoard = openList.poll();
            
            if (currentBoard.isGoalState(goalNum)) {
                // Found the goal state, return the sequence of moves
                return currentBoard.getSolution();
            }
            
            closedList.add(currentBoard);
            openList.addAll(currentBoard.getSuccessors(currentBoard));
        }
        
        // If no solution is found
        return null;
    }
}
