package minesweeper;

import java.awt.Dimension;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {
    private final int CELL_SIZE = 30;
    
    private int nCols;
    private int nRows;
    private int nMines;

    private int boardWidth;
    private int boardHeight;

    private boolean inGame;

    private static JLabel statusbar;
    private Map<String, Image> images;

    public Board(JLabel statusbar, int nRows, int nCols, int nMines) {
        this.statusbar = statusbar;
        this.nRows = nRows;
        this.nCols = nCols;
        this.nMines = nMines;

        boardHeight = nCols * CELL_SIZE + 1;
        boardWidth  = nRows * CELL_SIZE + 1;
        initBoard();
    }

    public void initBoard() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setImage();
        // addMouseListener(new MinesAdapter());
        newGame();
    }

    private void setImage() {
        images = new HashMap<>();

        for (int i = 1; i < 9; i++) {
            String path = "../resources/" + i + ".png";
            images.put(Integer.toString(i), (new ImageIcon(path)).getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
        }

        images.put("Bomb", (new ImageIcon("../resources/Bomb.png")).getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
        images.put("Covered", (new ImageIcon("../resources/Covered.png")).getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
        images.put("Empty", (new ImageIcon("../resources/Empty.png")).getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
        images.put("Marked", (new ImageIcon("../resources/Marked.png")).getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
        images.put("Wrongmarked", (new ImageIcon("../resources/Wrongmarked.png")).getImage().getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH));
    }

    private void newGame() {
        inGame = true;
        initField();
        setLocationBomb();
    }

    private void initField() {

    }

    private void setLocationBomb() {

    }
}
