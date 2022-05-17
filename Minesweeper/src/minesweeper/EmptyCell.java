package minesweeper;

public class EmptyCell extends Cell {
    public EmptyCell() {
        super();
        this.cellType = CellType.EMPTY;
    }

    public EmptyCell(String isCovered, String isMarked) {
        super(isCovered, isMarked);
        this.cellType = CellType.EMPTY;
    }


    @Override
    public CellType getCellType() {
        return this.cellType;
    }

    @Override
    public String getImageName() {
        return ImageName.EMPTY.toString();
    }

}
