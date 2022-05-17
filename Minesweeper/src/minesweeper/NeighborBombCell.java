package minesweeper;


public class NeighborBombCell extends Cell {
    private int bombCellCount;

    public NeighborBombCell() {
        super();
        this.cellType = CellType.NEIGHBOR;
    }

    @Override
    public CellType getCellType() {
        return this.cellType;
    }

    @Override
    public String getImageName() {
        return Integer.toString(bombCellCount);
    }

    @Override
    public void cellCount() {
        this.bombCellCount++;
    }
}
