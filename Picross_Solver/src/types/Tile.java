package types;

public class Tile {

    public enum State {
        FILLED,
        UNFILLED,
        AMBIGUOUS;

        private State opposite;

        static {
            FILLED.opposite = UNFILLED;
            UNFILLED.opposite = FILLED;
            AMBIGUOUS.opposite = AMBIGUOUS;
        }

        public State getOpposite() {
            return opposite;
        }
    }

    private State state;

    public Tile() {
        this.state = State.AMBIGUOUS;
    }

    public State getState() {
        return state;
    }

    public void setState(State change) {
        this.state = change;

    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Tile && this.getState() == ((Tile) other).getState())
            return true;
        return false;
    }
}
