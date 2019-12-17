package solver;

public class PicrossException extends Exception {

    public enum Type {
        DIMENSION,
        DECISION;

        private String description;

        static {
            DIMENSION.description = "";
            DECISION.description = "";
        }

        @Override
        public String toString() {
            return description;
        }
    }

    private Type type;

    public PicrossException(Type type) {
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
