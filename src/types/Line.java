package types;

public abstract class Line {

    protected int index, length;
    protected Tile[] tiles;
    protected Region[] regions;

    public Line(int index, int length, Tile[] tiles, int[] regNums) {
        this.index = index;
        this.length = length;
        this.tiles = tiles;
        int regs = 1 + regNums.length * 2;
        this.regions = new Region[regs];
        boolean fillMode = false;
        for (int i = 0; i < regs; ++i) {
            if (fillMode) {
                this.regions[i] = new Region(regNums[i / 2], Tile.State.FILLED);
            } else if (i == 0 || i == regs - 1) {
                this.regions[i] = new Region(0, Tile.State.UNFILLED);
            } else {
                this.regions[i] = new Region(1, Tile.State.UNFILLED);
            }
            fillMode = !fillMode;
        }
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

    public Region[] getRegions() {
        return regions;
    }
}
