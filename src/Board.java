import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Much of the code below is taken from https://www.baeldung.com/2048-java-solver
 */

public class Board {
    private int[][] board;
    private int score;
    private Cell spawnCell = new Cell(0, 0);

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
    public Board placeTile(Cell cell, int number) {
        // if the board is full return null so we can end the game

        Board result = new Board(this.board, this.score);
        result.board[cell.getX()][cell.getY()] = number;

        List<Cell> emptyCells = emptyCells();
        // if the board is full return null so we can end the game
        if (emptyCells.isEmpty()) {
            return null;
        } else if (emptyCells.contains(spawnCell)) {
            // spawn at spawnCell
        } else {
            boolean spawned = false;
            while (!spawned) {
                for (int x = spawnCell.getX(); x < board.length; x++) {
                    for (int y = spawnCell.getY(); y < board.length; y++) {
                        Cell tempCell = new Cell(x, y);
                        if (emptyCells.contains(tempCell)) {
                            spawnCell = tempCell;
                            spawned = true;
                            // spawn at tempCell
                        }
                    }
                }
                // if we went through the whole board and didn't spawn, loop back to (0,0) and try again
                if (!spawned) {
                    spawnCell = new Cell(0, 0);
                }
            }

        }
        return result;
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

}
