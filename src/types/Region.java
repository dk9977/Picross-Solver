package types;

public class Region {

    private int length;
    private boolean complete;

    public Region(int length) {
        this.length = length;
        this.complete = false;
    }

    public int getLength() {
        return this.length;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
