package types;

public class Region {

    private int length;
    private Tile.State state;
    private boolean complete;

    public Region(int length, Tile.State state) {
        this.length = length;
        this.state = state;
        this.complete = false;
    }

    public int getLength() {
        return this.length;
    }

    public Tile.State getState() {
        return this.state;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
