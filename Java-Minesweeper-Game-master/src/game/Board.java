
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {
    private final int numberImages = 13;
    private final int cellSize = 15;

    private final int coverForCell = 10;
    private final int markForCell = 10;
    private final int emptyCell = 0;
    private final int mineCell = 9;
    private final int coveredMineCell = mineCell + coverForCell;
    private final int markedMineCell = coveredMineCell + markForCell;

    private final int drawMine = 9;
    private final int drawCover = 10;
    private final int drawMark = 11;
    private final int drawWrongMark = 12;

    private final int nMines = 40;
    private final int nRows = 16;
    private final int nCols = 16;

    private final int boardWidth = nCols * cellSize + 1;
    private final int boardHeight = nRows * cellSize + 1;

    private int[] field;
    private boolean inGame;
    private int minesLeft;
    private Image[] img;

    private int allCells;
    private final JLabel statusbar;

    public Board(JLabel statusbar) {
        this.statusbar = statusbar;
        initBoard();
    }

    private void initBoard() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));

        img = new Image[numberImages];
        for (int i = 0; i < numberImages; i++) {
            var path = "C://Users//DELL//Documents//GitHub//Minesweeper//Java-Minesweeper-Game-master//src//resources//" + i + ".png";
            img[i] = (new ImageIcon(path)).getImage();
        }
        addMouseListener(new MinesAdapter());
        newGame();
    }

    private void newGame() {
        int cell;

        var random = new Random();
        inGame = true;
        minesLeft = nMines;
        allCells = nRows * nCols;
        field = new int[allCells];

        for (int i = 0; i < allCells; i++) {
            field[i] = coverForCell;
        }

        statusbar.setText(Integer.toString(minesLeft));

        int i = 0;
        while (i < nMines) {
            int position = (int) (allCells * random.nextDouble());

            if ((position < allCells)
                    && (field[position] != coveredMineCell)) {
                int current_col = position % nCols;
                field[position] = coveredMineCell;
                i++;

                if (current_col > 0) {
                    cell = position - 1 - nCols;
                    if (cell >= 0) {
                        if (field[cell] != coveredMineCell) {
                            field[cell] += 1;
                        }
                    }
                    cell = position - 1;
                    if (cell >= 0) {
                        if (field[cell] != coveredMineCell) {
                            field[cell] += 1;
                        }
                    }

                    cell = position + nCols - 1;
                    if (cell < allCells) {
                        if (field[cell] != coveredMineCell) {
                            field[cell] += 1;
                        }
                    }
                }

                cell = position - nCols;
                if (cell >= 0) {
                    if (field[cell] != coveredMineCell) {
                        field[cell] += 1;
                    }
                }

                cell = position + nCols;
                if (cell < allCells) {
                    if (field[cell] != coveredMineCell) {
                        field[cell] += 1;
                    }
                }

                if (current_col < (nCols - 1)) {
                    cell = position - nCols + 1;
                    if (cell >= 0) {
                        if (field[cell] != coveredMineCell) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + nCols + 1;
                    if (cell < allCells) {
                        if (field[cell] != coveredMineCell) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + 1;
                    if (cell < allCells) {
                        if (field[cell] != coveredMineCell) {
                            field[cell] += 1;
                        }
                    }
                }
            }
        }
    }

    private void find_empty_cells(int j) {

        int current_col = j % nCols;
        int cell;

        if (current_col > 0) {
            cell = j - nCols - 1;
            if (cell >= 0) {
                if (field[cell] > mineCell) {
                    field[cell] -= coverForCell;
                    if (field[cell] == emptyCell) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j - 1;
            if (cell >= 0) {
                if (field[cell] > mineCell) {
                    field[cell] -= coverForCell;
                    if (field[cell] == emptyCell) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + nCols - 1;
            if (cell < allCells) {
                if (field[cell] > mineCell) {
                    field[cell] -= coverForCell;
                    if (field[cell] == emptyCell) {
                        find_empty_cells(cell);
                    }
                }
            }
        }

        cell = j - nCols;
        if (cell >= 0) {
            if (field[cell] > mineCell) {
                field[cell] -= coverForCell;
                if (field[cell] == emptyCell) {
                    find_empty_cells(cell);
                }
            }
        }

        cell = j + nCols;
        if (cell < allCells) {
            if (field[cell] > mineCell) {
                field[cell] -= coverForCell;
                if (field[cell] == emptyCell) {
                    find_empty_cells(cell);
                }
            }
        }

        if (current_col < (nCols - 1)) {
            cell = j - nCols + 1;
            if (cell >= 0) {
                if (field[cell] > mineCell) {
                    field[cell] -= coverForCell;
                    if (field[cell] == emptyCell) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + nCols + 1;
            if (cell < allCells) {
                if (field[cell] > mineCell) {
                    field[cell] -= coverForCell;
                    if (field[cell] == emptyCell) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + 1;
            if (cell < allCells) {
                if (field[cell] > mineCell) {
                    field[cell] -= coverForCell;
                    if (field[cell] == emptyCell) {
                        find_empty_cells(cell);
                    }
                }
            }
        }

    }

    @Override
    public void paintComponent(Graphics g) {

        int uncover = 0;

        for (int i = 0; i < nRows; i++) {

            for (int j = 0; j < nCols; j++) {

                int cell = field[(i * nCols) + j];

                if (inGame && cell == mineCell) {

                    inGame = false;
                }

                if (!inGame) {

                    if (cell == coveredMineCell) {
                        cell = drawMine;
                    } else if (cell == markedMineCell) {
                        cell = drawMark;
                    } else if (cell > coveredMineCell) {
                        cell = drawWrongMark;
                    } else if (cell > mineCell) {
                        cell = drawCover;
                    }

                } else {

                    if (cell > coveredMineCell) {
                        cell = drawMark;
                    } else if (cell > mineCell) {
                        cell = drawCover;
                        uncover++;
                    }
                }

                g.drawImage(img[cell], (j * cellSize),
                        (i * cellSize), this);
            }
        }

        if (uncover == 0 && inGame) {

            inGame = false;
            statusbar.setText("You win!!!");
            PlayMusic.playSound("C://Users//DELL//Documents//GitHub//Minesweeper//Java-Minesweeper-Game-master//src//sound//victory.wav");
        } else if (!inGame) {
            PlayMusic.playSound("C://Users//DELL//Documents//GitHub//Minesweeper//Java-Minesweeper-Game-master//src//sound//gameover.wav");
            statusbar.setText("Game over!");
        }
    }

    private class MinesAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();

            int cCol = x / cellSize;
            int cRow = y / cellSize;

            boolean doRepaint = false;

            if (!inGame) {

                newGame();
                repaint();
            }

            if ((x < nCols * cellSize) && (y < nRows * cellSize)) {

                if (e.getButton() == MouseEvent.BUTTON3) {

                    if (field[(cRow * nCols) + cCol] > mineCell) {

                        doRepaint = true;

                        if (field[(cRow * nCols) + cCol] <= coveredMineCell) {

                            if (minesLeft > 0) {
                                field[(cRow * nCols) + cCol] += markForCell;
                                minesLeft--;
                                String msg = Integer.toString(minesLeft);
                                statusbar.setText(msg);
                            } else {
                                PlayMusic.playSound("C://Users//DELL//Documents//GitHub//Minesweeper//Java-Minesweeper-Game-master//src//sound//errorr.wav");
                                statusbar.setText("No marks left");
                            }
                        } else {

                            field[(cRow * nCols) + cCol] -= markForCell;
                            minesLeft++;
                            String msg = Integer.toString(minesLeft);
                            statusbar.setText(msg);
                        }
                    }

                } else {

                    if (field[(cRow * nCols) + cCol] > coveredMineCell) {

                        return;
                    }

                    if ((field[(cRow * nCols) + cCol] > mineCell)
                            && (field[(cRow * nCols) + cCol] < markedMineCell)) {

                        field[(cRow * nCols) + cCol] -= coverForCell;
                        doRepaint = true;

                        if (field[(cRow * nCols) + cCol] == mineCell) {
                            inGame = false;
                        }

                        if (field[(cRow * nCols) + cCol] == emptyCell) {
                            find_empty_cells((cRow * nCols) + cCol);
                        }
                    }
                }

                if (doRepaint) {
                    repaint();
                }
            }
        }
    }
}