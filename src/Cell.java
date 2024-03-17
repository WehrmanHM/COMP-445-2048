public class Cell {
    private final int x;
    private final int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Cell getNextCell(int size) {
        int newX = 0;
        int newY = 0;
        if (this.x < size-1) {
            newX = x+1;
        }
        if (this.y < size-1) {
            newY = y+1;
        }
        return new Cell(newX, newY);
    }
}
