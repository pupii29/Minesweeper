package minesweeper;


public class NeighborBombCell extends Cell {

    private int bombCellCount;

    public NeighborBombCell() {
        super();
        this.cellType = CellType.NEIGHBOR;
    }


    public NeighborBombCell(String isCovered, String isMarked, int bombCellCount) {
        super(isCovered, isMarked);
        this.cellType = CellType.NEIGHBOR;
        this.bombCellCount = bombCellCount;
    }

    //want to call this function when initializing board, which automatically
    @Override
    public void cellCount() {
        this.bombCellCount++;
    }

    @Override
    public CellType getCellType() {
        return this.cellType;
    }

    @Override
    public String getImageName() {
        return Integer.toString(bombCellCount);
    }

}
