package solver;

import types.Line;
import types.Tile;

import java.util.ArrayList;

public class LineManager extends Thread {

    protected Line line;
    protected int length;
    protected int[] regions;
    protected boolean ready;
    protected ArrayList<Tile[]> opts;

    public LineManager(Line line) {
        this.line = line;
        this.length = line.getLength();
        this.regions = line.getRegions();
        this.ready = true;
        this.initOptions();
    }

    synchronized public void update() {
        ready = true;
    }

    private int gapTotal() {
        int reglen = 0;
        for (int r : regions) {
            reglen += r;
        }
        return this.length - reglen;
    }

    private int[] initGapList() {
        int[] gaps = new int[regions.length];
        gaps[0] = 0;
        for (int i = 1; i < regions.length; ++i) {
            gaps[i] = 1;
        }
        return gaps;
    }

    private void initOptions() {
        int[] gapList = initGapList();
        int gapSize = gapTotal();
        int maxGap = gapSize - gapList.length + 1;
        boolean gapMod;
        do {
            Tile[] opt = new Tile[this.length];
            int i = 0;
            for (int r = 0; r < regions.length; ++r) {
                for (int j = 0; j < gapList[r]; ++i, ++j) {
                    opt[i] = new Tile(Tile.State.UNFILLED);
                }
                for (int j = 0; j < regions[r]; ++i, ++j) {
                    opt[i] = new Tile(Tile.State.FILLED);
                }
            }
            for (; i < this.length; ++i) {
                opt[i] = new Tile(Tile.State.UNFILLED);
            }
            this.opts.add(opt);
            gapMod = false;
            for (int k = gapList.length - 1; k >= 0; --k) {
                if (gapList[k] < maxGap) {
                    ++gapList[k];
                    gapMod = true;
                    break;
                }
                gapList[k] = 1;
            }
        } while (gapMod);
    }
}
