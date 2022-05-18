package minesweeper;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {
    private final int cellSize = 15;
   
    private int nCols = 16;
    private int nRows = 16;
    private int nMines = 40;
    private int minesLeft;

    private int boardWidth = nCols*cellSize + 1;
    private int boardHeight = nRows*cellSize + 1;

    private boolean inGame;
    private static Cell[][] field; // 2D array to represent game board

    private static JLabel statusbar;
    private Map<String, Image> images;

    public Board(JLabel statusbar) {
        this.statusbar = statusbar;
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
            images.put(Integer.toString(i), (new ImageIcon(path)).getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
        }

        images.put("Bomb", (new ImageIcon("../resources/Bomb.png")).getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
        images.put("Covered", (new ImageIcon("../resources/Covered.png")).getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
        images.put("Empty", (new ImageIcon("../resources/Empty.png")).getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
        images.put("Marked", (new ImageIcon("../resources/Marked.png")).getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
        images.put("Wrongmarked", (new ImageIcon("../resources/Wrongmarked.png")).getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
    }

    private void newGame() {
        inGame = true;
        initField();
        setLocationBomb();
    }

    private void initField() {
        field = new Cell[nRows][nCols];
        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                field[row][col] = new EmptyCell();
            }
        }
    }

    private void setLocationBomb() {
        minesLeft = nMines;
        statusbar.setText("Flags Left: " + Integer.toString(minesLeft));

        int i = 0;
        while (i < nMines) {
            Random random = new Random();

            int positionX = random.nextInt(nCols);
            int positionY = random.nextInt(nRows);

            // Randomly place the bomb cell
            if (field[positionX][positionY].getCellType() != CellType.BOMB) {
                field[positionX][positionY] = new BombCell();
                // Sets up neighbor cells 
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if ((dx != 0 || dy != 0) 
                        && positionX + dx < nCols && positionX + dx >= 0
                        && positionY + dy < nRows && positionY + dy >= 0) {
                            CellType typeOfCell = field[positionX + dx][positionY + dy].getCellType();
                            if (typeOfCell != CellType.BOMB) {
                                if (typeOfCell != CellType.NEIGHBOR) {
                                    NeighborBombCell neighbor = new NeighborBombCell();
                                    neighbor.cellCount();
                                    field[positionX + dx][positionY + dy] = neighbor;
                                } else {
                                    field[positionX + dx][positionY + dy].cellCount();
                                }
                            }
                        }
                    }
                }
                i++;
            }
        }
    }

    /**
     * Checks this for all neighbors
     * @param x
     * @param y
     * @author Truong Dang Khoa
     */
    public void find_empty_cells(int x, int y) {
        field[x][y].flipUp();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) { // set bounds
                if ((dx != 0 || dy != 0) 
                && x + dx < nCols && x + dx >= 0
                && y + dy < nRows && y + dy >= 0) {
                    CellType typeOfCell = field[x + dx][y + dy].getCellType();
                    if (typeOfCell == CellType.EMPTY 
                    && field[x + dx][y + dy].isCoveredCell() 
                    && !field[x + dx][y + dy].isMarkedCell()) {
                        find_empty_cells(x + dx, y + dy);
                    }
                }
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        int uncover = 0;
        
        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                Cell cell = field[row][col];
                String imageName = cell.getImageName();
                
                if (inGame && cell.getCellType() == CellType.BOMB && !cell.isCoveredCell()) {
                    inGame = false;
                }
                
                if (!inGame) {  
                    if (cell.getCellType() == CellType.BOMB && !cell.isMarkedCell()) {
                        cell.flipUp();
                        imageName = ImageName.BOMB.toString(); 
                    } else if (cell.isCoveredCell() && cell.getCellType() == CellType.BOMB && cell.isMarkedCell()) {
                        imageName = ImageName.MARKED.toString(); 
                    } else if (cell.isCoveredCell() && cell.getCellType() != CellType.BOMB && cell.isMarkedCell()) {
                        imageName = ImageName.WRONGMARKED.toString(); 
                    } else if (cell.isCoveredCell()) { 
                        imageName = ImageName.COVERED.toString();
                    }

                } else { 
                    if (cell.isMarkedCell()) { 
                        imageName = ImageName.MARKED.toString();
                    } else if (cell.isCoveredCell()) { 
                        imageName = ImageName.COVERED.toString();
                       uncover++;
                    }
                }
                g.drawImage(images.get(imageName), (col * cellSize), (row * cellSize), this);
            }
        }
        if (uncover == 0 && inGame) {

            inGame = false;
            statusbar.setText("You won!");

        } else if (!inGame) {
            statusbar.setText("Game Over!");
        }
    }    
}
