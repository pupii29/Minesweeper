package minesweeper;

public class BombCell extends Cell {
    public BombCell() {
        super();
        this.cellType = CellType.BOMB;
    }

    @Override
    public CellType getCellType() {
        return this.cellType;
    }

    @Override
    public String getImageName() {
        return ImageName.BOMB.toString();
    }
}
