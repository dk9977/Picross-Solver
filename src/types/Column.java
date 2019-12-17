package types;

public class Column extends Line {

    public Column(int index, int length, Tile[] tiles, int[] regNums) {
        super(index, length, tiles, regNums);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Column && this.index == ((Column) other).index)
            return true;
        return false;
    }
}
