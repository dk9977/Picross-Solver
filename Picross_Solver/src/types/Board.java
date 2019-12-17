package types;

import solver.PicrossException;

public class Board {

    private int rLen, cLen;
    private Tile[][] grid;
    private Row[] rows;
    private Column[] cols;

    public Board(int rLen, int cLen, int[][] rRegs, int[][] cRegs) throws PicrossException {
        if (rRegs.length != rLen || cRegs.length != cLen) {
            throw new PicrossException(PicrossException.Type.DIMENSION);
        }
        this.rLen = rLen;
        this.cLen = cLen;
        this.grid = new Tile[rLen][cLen];
        this.rows = new Row[cLen];
        this.cols = new Column[rLen];
        for (int r = 0; r < cLen; ++r) {
            for (int c = 0; c < rLen; ++c) {
                this.grid[r][c] = new Tile();
            }
            this.rows[r] = new Row(r, rLen, this.grid[r], rRegs[r]);
        }
        for (int c = 0; c < rLen; ++c) {
            Tile[] list = new Tile[cLen];
            for (int r = 0; r < cLen; ++r) {
                list[r] = this.grid[r][c];
            }
            this.cols[c] = new Column(c, cLen, list, cRegs[c]);
        }
    }

    public int getRowLength() {
        return this.rLen;
    }

    public int getColumnLength() {
        return this.cLen;
    }

    public Tile getTile(int row, int col) {
        return this.grid[row][col];
    }

    public Row getRow(int row) {
        return this.rows[row];
    }

    public Column getCol(int col) {
        return this.cols[col];
    }

    /*
    public void setRow(int row, Tile[] tiles) throws PicrossException{
        if (tiles.length != this.rLen) {
            throw new PicrossException(PicrossException.Type.DIMENSION);
        }
        for (int col = 0; col < this.rLen; ++col) {
            if (!this.grid[row][col].equals(tiles[col])) {
                this.grid[row][col].setState(tiles[col].getState());
                this.cols[col].update();
            }
        }
    }
    */
}
