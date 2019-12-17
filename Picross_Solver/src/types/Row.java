package types;

public class Row extends Line {

    public Row(int index, int length, Tile[] tiles, int[] regNums) {
        super(index, length, tiles, regNums);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Row && this.index == ((Row) other).index)
            return true;
        return false;
    }
}
