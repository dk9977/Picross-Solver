package solver;

import types.Line;
import types.Region;

public abstract class LineManager extends Thread {

    protected Line line;
    protected int length;
    protected Region[] regions;
    protected boolean ready;

    public LineManager(Line line) {
        this.line = line;
        this.length = line.getLength();
        this.regions = line.getRegions();
        this.ready = true;
    }

    synchronized public void update() {
        ready = true;
    }
}
