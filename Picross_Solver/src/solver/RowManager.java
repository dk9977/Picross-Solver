package solver;

import types.Region;
import types.Row;
import types.Tile;

import java.util.ArrayList;

public class RowManager extends LineManager {

    public RowManager(Row row) {
        super(row);
    }

    /**
     *
     * @param list
     * @param firstSpace
     * @param regLength
     * @return
     * @throws PicrossException
     */
    private int nextAvailableSpace(Tile[] list, int firstSpace, int regLength) throws PicrossException {
        int lineLength = list.length;
        startDecider:
        for (int start = firstSpace; start + regLength <= lineLength;) {
            for (int i = start; i < start + regLength; ++i) {
                if (list[i].getState() == Tile.State.UNFILLED) {
                    start = i + 1;
                    continue startDecider;
                }
            }
            while (list[start + regLength].getState() == Tile.State.FILLED) {
                ++start;
            }
            return start;
        }
        throw new PicrossException(PicrossException.Type.DECISION);
    }

    /**
     *
     * @param list
     * @param firstSpace
     * @param regLength
     * @param state
     */
    private void fillTiles(Tile[] list, int firstSpace, int regLength, Tile.State state) {
        for (int i = firstSpace; i < firstSpace + regLength; ++i) {
            list[i].setState(state);
        }
    }

    /**
     *
     * @param regions
     * @return
     */
    private int minSize(Region[] regions) {
        int size = 0;
        for (Region region : regions) {
            size += region.getLength();
        }
        return size;
    }

    /**
     *
     * @param regions
     * @param regIndex
     * @param step
     * @param start
     * @return
     * @throws PicrossException
     */
    private ArrayList<Tile[]> validOrientations(Region[] regions, int regIndex, Tile[] step, int start) throws PicrossException {
        ArrayList<Tile[]> orientations = new ArrayList<>();
        if (regIndex >= regions.length) {
            orientations.add(step);
            return orientations;
        }
        Region region = regions[regIndex];
        int regLength = region.getLength();
        if (region.getState() == Tile.State.FILLED) {
            try {
                for (int i = start; i + regLength <= step.length; ++i) {
                    int nextIndex = nextAvailableSpace(step, start, regLength);
                    Tile[] next = step.clone();
                    fillTiles(next, nextIndex, regLength, Tile.State.FILLED);
                    orientations.addAll(validOrientations(regions, regIndex + 1, next, nextIndex + regLength));
                }
            }
            catch (PicrossException pex) {
                if (orientations.isEmpty()) {
                    throw new PicrossException(PicrossException.Type.DECISION);
                }
            }
        }
        else {
            try {
                for (int i = 0; start + i <= step.length; ++i) {
                    int nextIndex = nextAvailableSpace(step, start, regLength + i);
                    Tile[] next = step.clone();
                    fillTiles(next, nextIndex, regLength, Tile.State.UNFILLED);
                    orientations.addAll(validOrientations(regions, regIndex + 1, next, nextIndex + regLength));
                }
            }
            catch (PicrossException pex) {}
        }
        return orientations;
    }

    /**
     * Determines the states of some tiles based on a new datum.
     * @return a list of tiles with their known states
     * @throws PicrossException when info fails and the regions don't fit
     */
    private Tile[] knownInfo() throws PicrossException {
        Tile[] list = this.line.getTiles();
        int[] regStarts = new int[this.regions.length];
        int lineIndex = 0;
        for (int regIndex = 0; regIndex < this.regions.length; ++regIndex) {
            Region region = this.regions[regIndex];
            Tile.State state = region.getState();
            int regEnd = lineIndex + region.getLength();
            boolean setting = false;
            for (; lineIndex < regEnd; ++lineIndex) {
                if (regEnd > this.length) {
                    throw new PicrossException(PicrossException.Type.DECISION);
                }
                Tile tile = list[lineIndex];
                if (state == tile.getState()) {
                    if (!setting) {
                        setting = true;
                        regStarts[regIndex] = lineIndex;
                    }
                }
                else if (state.getOpposite() == tile.getState()) {
                    setting = false;
                    regEnd = lineIndex + 1;
                }
            }
            for (int i = regStarts[regIndex]; i < regEnd; ++i) {
                list[i].setState(state);
            }
        }
        // TODO this only gets the first, it needs to find ALL possibilities for usefulness
        return list;
    }

    @Override
    public void run() {

    }
}
