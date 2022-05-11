package minesweeper;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {

    private static JLabel statusbar;
    
    private int nCols;
    private int nRows;
    private int nMines;

    public Board(JLabel statusbar, int nRow, int nCol, int nMines) {
        this.statusbar = statusbar;
        this.nRows = nRows;
        this.nCols = nCol;
        this.nMines = nMines;
        initBoard();
    }

    public void initBoard() {

    }
}
