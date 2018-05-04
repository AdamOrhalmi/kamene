import java.io.Serializable;
import java.util.Random;

public class Field implements Serializable {
    private final int rows;
    private final int columns;
    private int stonesNr;
    private Stone[][] stones;
    private Random random = new Random();


    public Field(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        setStones();
    }

    public Field() {
        this.rows = 4;
        this.columns = 4;
        setStones();
    }

    private void setStones() {
        this.stonesNr = getRows() * getColumns();

        stones = new Stone[getRows()][getColumns()];
        int stonesPlaced = 0;
        while (stonesPlaced != stonesNr) {

            int row = random.nextInt(this.getRows());
            int column = random.nextInt(this.getColumns());
            if (stones[row][column] == null) {
                stonesPlaced++;
                stones[row][column] = new Stone(stonesPlaced);
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getStonesNr() {
        return stonesNr;
    }

    public Stone getStone(int row, int column) {
        return stones[row][column];
    }


    public boolean isSolved() {
        int count = 1;
        for (int row = 0; row < getRows(); row++) {
            for (int column = 0; column < getColumns(); column++) {
                if (stones[row][column].getValue() != count) {
                    return false;
                }
                count++;
            }
        }
        return true;

    }

    public Coords getNullStonePos() {
        for (int row = 0; row < getRows(); row++) {
            for (int column = 0; column < getColumns(); column++) {
                if (stones[row][column].getValue() == stonesNr) {
                    return new Coords(row, column);
                }
            }
        }
        throw new RuntimeException("null stone not found.");
    }

    public boolean switchNullStoneWith(int rowChange, int columnChange) {
        Coords nullStonePos = getNullStonePos();
        int nullRow = nullStonePos.getRow();
        int nullColumn = nullStonePos.getColumn();

        int movedRow = nullRow + rowChange;
        int movedColumn = nullColumn + columnChange;
        if (movedRow < 0 || movedRow == getRows() || movedColumn == getColumns() || movedColumn < 0) {
            return false;

        }
        Stone movedStone = stones[movedRow][movedColumn];
        stones[movedRow][movedColumn] = stones[nullRow][nullColumn];
        stones[nullRow][nullColumn] = movedStone;
        return true;
    }

    public void solve() {
        int i = 1;
        for (int row = 0; row < getRows(); row++) {
            for (int column = 0; column < getColumns(); column++) {
                stones[row][column] = new Stone(i);
                i++;
            }
        }
    }


}
