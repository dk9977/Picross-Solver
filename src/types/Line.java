package types;

public abstract class Line {

    protected int index, length;
    protected Tile[] tiles;
    protected int[] regions;

    public Line(int index, int length, Tile[] tiles, int[] regions) {
        this.index = index;
        this.length = length;
        this.tiles = tiles;
        this.regions = regions;
    }

    public int getIndex() {
        return this.index;
    }

    public int getLength() {
        return this.length;
    }

    public Tile[] getTiles() {
        return this.tiles;
    }

    public Tile getTile(int i) {
        return this.tiles[i];
    }

    public int[] getRegions() {
        return this.regions;
    }
}
