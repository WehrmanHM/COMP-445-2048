import java.util.*;


/**
 * Much of the code below is taken from https://www.baeldung.com/2048-java-solver
 */

public class Board {
    private int[][] board;
    private int score;
    private Cell spawnCell = new Cell(0, 0);
    private Board parent; // Add parent field to keep track of the parent board
    private Move moveFromParent; // Add moveFromParent field to keep track of the move made from the parent board


    // default constructor, creates a size x size board
    public Board(int size) {
        board = new int[size][];
        score = 0;

        for (int x = 0; x < size; x++) {
            board[x] = new int[size];
            for (int y = 0; y < size; y++) {
                board[x][y] = 0;
            }
        }
    }

    // copy constructor that takes the current state of a board
    public Board(int[][] board, int score) {
        this.score = score;
        this.board = new int[board.length][];

        for (int x = 0; x < board.length; x++) {
            this.board[x] = Arrays.copyOf(board[x], board.length);
        }
    }

    // copies a board and places a new tile
    public Board placeTile() {

        Board result = new Board(this.board, this.score);
        List<Cell> emptyCells = emptyCells();
        // if the board is full return null so we can end the game
        if (emptyCells.isEmpty()) {
            return null;
        } else {
            Cell newSpawn = emptyCells.getFirst();
            result.board[newSpawn.getX()][newSpawn.getY()] = 2;
        }
//        if (!emptyCells.contains(spawnCell)) {
//            result.board[spawnCell.getX()][spawnCell.getY()] = 2;
//        } else {
//            for (int x = 0; x < board.length; x++) {
//                for (int y = 0; y < board.length; y++) {
//                    Cell tempCell = new Cell(x, y);
//                    if (!emptyCells.contains(tempCell)) {
//                        result.board[tempCell.getX()][tempCell.getY()] = 2;
//                    }
//                }
//            }
//        }
        return result;
    }

    private static int[][] transpose(int[][] input) {
        int[][] result = new int[input.length][];

        for (int x = 0; x < input.length; ++x) {
            result[x] = new int[input[0].length];
            for (int y = 0; y < input[0].length; ++y) {
                result[x][y] = input[y][x];
            }
        }

        return result;
    }

    private static int[][] reverse(int[][] input) {
        int[][] result = new int[input.length][];

        for (int x = 0; x < input.length; ++x) {
            result[x] = new int[input[0].length];
            for (int y = 0; y < input[0].length; ++y) {
                result[x][y] = input[x][input.length - y - 1];
            }
        }

        return result;
    }

    public Board move(Move move) {
        int newScore = 0;
        System.out.println(move.toString());
        // Clone the board
        int[][] tiles = new int[this.board.length][];
        for (int x = 0; x < this.board.length; ++x) {
            tiles[x] = Arrays.copyOf(this.board[x], this.board[x].length);
        }
        if (move == Move.LEFT || move == Move.RIGHT) {
            tiles = transpose(tiles);

        }
        if (move == Move.DOWN || move == Move.RIGHT) {
            tiles = reverse(tiles);
        }

        int[][] result = new int[tiles.length][];

        for (int x = 0; x < tiles.length; ++x) {
            LinkedList<Integer> thisRow = new LinkedList<>();
            for (int y = 0; y < tiles[0].length; ++y) {
                if (tiles[x][y] > 0) {
                    thisRow.add(tiles[x][y]);
                }
            }

            LinkedList<Integer> newRow = new LinkedList<>();
            while (thisRow.size() >= 2) {
                int first = thisRow.pop();
                int second = thisRow.peek();
                if (second == first) {
                    int newNumber = first * 2;
                    newRow.add(newNumber);
                    newScore += newNumber;
                    thisRow.pop();
                } else {
                    newRow.add(first);
                }
            }
            newRow.addAll(thisRow);
            result[x] = new int[tiles[0].length];
            for (int y = 0; y < tiles[0].length; ++y) {
                if (newRow.isEmpty()) {
                    result[x][y] = 0;
                } else {
                    result[x][y] = newRow.pop();
                }
            }
        }
        if (move == Move.DOWN || move == Move.RIGHT) {
            result = reverse(result);
        }
        if (move == Move.LEFT || move == Move.RIGHT) {
            result = transpose(result);
        }

        Board successor = new Board(result, this.score + newScore);
        successor.parent = this; // Set the current board as the parent
        successor.moveFromParent = move; // Set the move made from the parent
        return successor.placeTile();
    }



    public int getSize() {
        return board.length;
    }

    public int getScore() {
        return score;
    }

    public int getCell(Cell cell) {
        return board[cell.getX()][cell.getY()];
    }

    public boolean isEmpty(Cell cell) {
        return getCell(cell) == 0;
    }

    public List<Cell> emptyCells() {
        List<Cell> result = new ArrayList<>();
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                Cell cell = new Cell(x, y);
                if (isEmpty(cell)) {
                    result.add(cell);
                }
            }
        }
        return result;
    }

//    // returns the heuristic value of a board
//    public float getValue() {
//        int tileTotal = 0;
//        int numTiles = 0;
//        for (int[] ints : board) {
//            for (int y = 0; y < board.length; y++) {
//                if (ints[y] != 0) {
//                    tileTotal += ints[y];
//                    numTiles++;
//                }
//            }
//        }
//        return (float)tileTotal/numTiles + score;
//    }


    public List<Board> getSuccessors() {
        List<Board> successors = new ArrayList<>();
        for (Move move : Move.values()) {
            successors.add(this.move(move));
        }
        return successors;
    }
    // I Wrote these next couple  -Chris

    public Boolean isGoalState(int winNum){
        boolean isGoal = false;
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                //System.out.println(board[x][y]);
                if (board[x][y] == winNum) {
                    isGoal = true;
                    break;
                }
            }
        }
        System.out.println(isGoal);
		return isGoal;
	}

    public int getH() {
        int sum = 0;
        int count = 0;
        for (int[] row : board) {
            for (int value : row) {
                if (value != 0) {
                    sum += value;
                    count++;
                }
            }
        }
        return count == 0 ? 0 : sum / count;
    }

    public int getF() {
        int gCost = score;
        int hCost = getH();
        return gCost + hCost;
    }

    public List<Move> getSolution() {
        List<Move> solution = new ArrayList<>();
        Board currentBoard = this; // Start from the current board

        // Traverse back through the parent boards until reaching the initial state
        while (currentBoard.parent != null) {
            solution.add(0, currentBoard.moveFromParent); // Add the move to the beginning of the list
            currentBoard = currentBoard.parent; // Move to the parent board
        }

        return solution;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        Board tempBoard = (Board) obj;
        return (Arrays.deepEquals(tempBoard.board, this.board));
    }
}
