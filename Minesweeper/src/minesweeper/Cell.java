package minesweeper;

public class Cell {
    protected boolean isCovered = true;
    protected CellType cellType = CellType.EMPTY;
    protected boolean isMarked = false;

    public Cell() {}

    public void cellCount() {}

    public void flipUp() {
        this.isCovered = false;
    }

    public String getImageName() {
        return ImageName.COVERED.toString();
    }

    public CellType getCellType() {
        return this.cellType;
    }

    public boolean isCoveredCell() {
        return this.isCovered;
    }

    public boolean isMarkedCell() {
        return this.isMarked;
    }

    public void changeWhetherMarked() {
        this.isMarked = !isMarked;
    }
}