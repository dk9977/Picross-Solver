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
     * Finds the next spot where the specified region fits.
     * @param list the list of tiles to fit the region into
     * @param firstSpace the first potential space for the region
     * @param regLength the length of teh region
     * @return the lowest available space equal to or greater than firstSpace
     * @throws PicrossException when a spot can't be found
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
     * Mark the tiles in a range with a certain state.
     * @param list the list of tiles
     * @param firstSpace the first space to mark
     * @param regLength the length of the region to mark
     * @param state the state to mark the region as
     */
    private void fillTiles(Tile[] list, int firstSpace, int regLength, Tile.State state) {
        for (int i = firstSpace; i < firstSpace + regLength; ++i) {
            list[i].setState(state);
        }
    }

    /**
     * Finds the minimum number of tiles required from a region to the end.
     * @param regions the list of regions
     * @param firstReg the first region to measure
     * @return the minimum number of tiles needed for the region range
     */
    private int minSize(Region[] regions, int firstReg) {
        int size = 0;
        for (int i = firstReg; i < regions.length; ++i) {
            size += regions[i].getLength();
        }
        return size;
    }

    /**
     * Recursively find all of the valid orientations of tiles in the line.
     * @param regions the list of regions
     * @param regIndex the region to work on
     * @param step the current line in this step of the recursion
     * @param start the index to start at in the line
     * @return the valid orientations of tiles in the line
     * @throws PicrossException when there is no valid orientation
     */
    private ArrayList<Tile[]> validOrientations(Region[] regions, int regIndex, Tile[] step, int start) throws PicrossException {
        ArrayList<Tile[]> orientations = new ArrayList<>();
        if (regIndex >= regions.length) {
            orientations.add(step);
            return orientations;
        }
        Region region = regions[regIndex];
        int regLength = region.getLength();
        int minAfter = minSize(regions, regIndex);
        if (region.getState() == Tile.State.FILLED) {
            try {
                for (int i = start; i + regLength + minAfter <= step.length; ++i) {
                    int nextIndex = nextAvailableSpace(step, i, regLength);
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
                for (int i = 0; start + i + minAfter <= step.length; ++i) {
                    int nextIndex = nextAvailableSpace(step, start, regLength + i);
                    Tile[] next = step.clone();
                    fillTiles(next, nextIndex, regLength + i, Tile.State.UNFILLED);
                    orientations.addAll(validOrientations(regions, regIndex + 1, next, nextIndex + regLength + i));
                }
            }
            catch (PicrossException pex) {
                if (regLength > 0) {
                    throw new PicrossException(PicrossException.Type.DECISION);
                }
            }
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
        ArrayList<Tile[]> orientations = validOrientations(this.regions, 0, list, 0);
        if (orientations.isEmpty()) {
            throw new PicrossException(PicrossException.Type.DECISION);
        }
        for (Tile[] orientation : orientations) {
            for (int i = 0; i < list.length; ++i) {
                if (list[i].getState() == Tile.State.AMBIGUOUS) {
                    list[i].setState(orientation[i].getState());
                }
                else if (list[i].getState() != orientation[i].getState()) {
                    list[i].setState(Tile.State.AMBIGUOUS);
                }
            }
        }
        return list;
    }

    @Override
    public void run() {
        // TODO
    }
}
