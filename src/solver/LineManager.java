package solver;

import types.Line;
import types.Region;
import types.Tile;

import java.util.ArrayList;

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

    private int gapTotal() {
        int reglen = 0;
        for (Region r : regions) {
            reglen += r.getLength();
        }
        return this.length - reglen;
    }

    private int[] initGapList() {
        int[] gaps = new int[this.length];
        gaps[0] = 0;
        for (int i = 1; i < this.length; ++i) {
            gaps[i] = 1;
        }
        return gaps;
    }

    private int sumList(int[] list) {
        int sum = 0;
        for (int n : list) {
            sum += n;
        }
        return sum;
    }

    private ArrayList<Tile[]> initOptions() {
        ArrayList<Tile[]> opts = new ArrayList<>();
        int[] gapList = initGapList();
        int gapSize = gapTotal();
        do {
            Tile[] opt = new Tile[this.length];
            int i = 0;
            for (int r = 0; r < regions.length; ++r) {
                for (int j = 0; j < gapList[r]; ++i, ++j) {
                    opt[i] = new Tile(Tile.State.UNFILLED);
                }
                for (int j = 0; j < regions[r].getLength(); ++i, ++j) {
                    opt[i] = new Tile(Tile.State.FILLED);
                }
            }
            for (; i < this.length; ++i) {
                opt[i] = new Tile(Tile.State.UNFILLED);
            }
            opts.add(opt);
            // TODO update gapList
        } while (sumList(gapList) < gapSize);
        return opts;
    }
}
