package minesweeper;

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
    private final int numbersImages = 13;

    private final int cellSize = 15;
    private final int corveredForCell = 10;
    private final int markedForCell = 10;
    private final int emptyCell = 0;
    private final int mineCell = 9;
    private final int coveredMineCell = mineCell + corveredForCell;
    private final int markedMineCell = coveredMineCell + markedForCell;

    private final int nMines = 40;
    private final int nRows = 16;
    private final int nRols = 16;

    private final int drawMines = 9;
    private final int drawCovered = 10;
    private final int drawMarked = 11;
    private final int drawWrongMarked = 12;
    

    private final int boardWidth = nRols * cellSize + 1;
    private final int boardHeight = nRows * cellSize + 1;
    private int[] field;
    private int minesLeft;
    private int allCells;

    private boolean inGame;

    private Image[] img;

    
    private final JLabel statusbar;

    public Board(JLabel statusbar) {
        this.statusbar = statusbar;
        initBoard();
    }

   private void initBoard() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        img = new Image[numbersImages];
        for (int i = 0; i < numbersImages; i++) {
            var path = "C://Users//DELL//Documents//GitHub//Minesweeper//Minesweeper//src//resources//" + i + ".png";
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

        allCells = nRows * nRols;
        field = new int[allCells];

        for (int i = 0; i < allCells; i++) {
            field[i] = corveredForCell;
        }

        statusbar.setText(Integer.toString(minesLeft));

        int i = 0;
        while (i < nMines) {
            int position = (int) (allCells * random.nextDouble());
            if ((position < allCells)
                    && (field[position] != coveredMineCell)) {
                int current_col = position % nRols;
                field[position] = coveredMineCell;
                i++;
                if (current_col > 0) {
                    cell = position - 1 - nRols;
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
                    cell = position + nRols - 1;
                    if (cell < allCells) {
                        if (field[cell] != coveredMineCell) {
                            field[cell] += 1;
                        }
                    }
                }
                cell = position - nRols;
                if (cell >= 0) {
                    if (field[cell] != coveredMineCell) {
                        field[cell] += 1;
                    }
                }
                cell = position + nRols;
                if (cell < allCells) {
                    if (field[cell] != coveredMineCell) {
                        field[cell] += 1;
                    }
                }
                if (current_col < (nRols - 1)) {
                    cell = position - nRols + 1;
                    if (cell >= 0) {
                        if (field[cell] != coveredMineCell) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + nRols + 1;
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

        int current_col = j % nRols;
        int cell;

        if (current_col > 0) {
            cell = j - nRols - 1;
            if (cell >= 0) {
                if (field[cell] > mineCell) {
                    field[cell] -= corveredForCell;
                    if (field[cell] == emptyCell) {
                        find_empty_cells(cell);
                    }
                }
            }
            cell = j - 1;
            if (cell >= 0) {
                if (field[cell] > mineCell) {
                    field[cell] -= corveredForCell;
                    if (field[cell] == emptyCell) {
                        find_empty_cells(cell);
                    }
                }
            }
            cell = j + nRols - 1;
            if (cell < allCells) {
                if (field[cell] > mineCell) {
                    field[cell] -= corveredForCell;
                    if (field[cell] == emptyCell) {
                        find_empty_cells(cell);
                    }
                }
            }
        }
        cell = j - nRols;
        if (cell >= 0) {
            if (field[cell] > mineCell) {
                field[cell] -= corveredForCell;
                if (field[cell] == emptyCell) {
                    find_empty_cells(cell);
                }
            }
        }
        cell = j + nRols;
        if (cell < allCells) {
            if (field[cell] > mineCell) {
                field[cell] -= corveredForCell;
                if (field[cell] == emptyCell) {
                    find_empty_cells(cell);
                }
            }
        }
        if (current_col < (nRols - 1)) {
            cell = j - nRols + 1;
            if (cell >= 0) {
                if (field[cell] > mineCell) {
                    field[cell] -= corveredForCell;
                    if (field[cell] == emptyCell) {
                        find_empty_cells(cell);
                    }
                }
            }
            cell = j + nRols + 1;
            if (cell < allCells) {
                if (field[cell] > mineCell) {
                    field[cell] -= corveredForCell;
                    if (field[cell] == emptyCell) {
                        find_empty_cells(cell);
                    }
                }
            }
            cell = j + 1;
            if (cell < allCells) {
                if (field[cell] > mineCell) {
                    field[cell] -= corveredForCell;
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
            for (int j = 0; j < nRols; j++) {
                int cell = field[(i * nRols) + j];
                if (inGame && cell == mineCell) {
                    inGame = false;
                }
                if (!inGame) {
                    if (cell == coveredMineCell) {
                        cell = drawMines;
                    } else if (cell == markedMineCell) {
                        cell = drawMarked;
                    } else if (cell > coveredMineCell) {
                        cell = drawWrongMarked;
                    } else if (cell > mineCell) {
                        cell = drawCovered;
                    }
                } else {
                    if (cell > coveredMineCell) {
                        cell = drawMarked;
                    } else if (cell > mineCell) {
                        cell = drawCovered;
                        uncover++;
                    }
                }
                g.drawImage(img[cell], (j * cellSize),
                        (i * cellSize), this);
            }
        }
        if (uncover == 0 && inGame) {
            inGame = false;
            statusbar.setText("You won!");
        } else if (!inGame) {
            PlayMusic.playSound("C://Users//DELL//Documents//GitHub//Minesweeper//Minesweeper//src//sound//gameOver.wav");
            statusbar.setText("Game Over!");
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
            if ((x < nRols * cellSize) && (y < nRows * cellSize)) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (field[(cRow * nRols) + cCol] > mineCell) {
                        doRepaint = true;
                        if (field[(cRow * nRols) + cCol] <= coveredMineCell) {
                            if (minesLeft > 0) {
                                field[(cRow * nRols) + cCol] += markedForCell;
                                minesLeft--;
                                String msg = Integer.toString(minesLeft);
                                statusbar.setText(msg);
                            } else {
                                statusbar.setText("No marks left");
                            }
                        } else {
                            field[(cRow * nRols) + cCol] -= markedForCell;
                            minesLeft++;
                            String msg = Integer.toString(minesLeft);
                            statusbar.setText(msg);
                        }
                    }
                } else {
                    if (field[(cRow * nRols) + cCol] > coveredMineCell) {
                        return;
                    }
                    if ((field[(cRow * nRols) + cCol] > mineCell)
                            && (field[(cRow * nRols) + cCol] < markedMineCell)) {
                        field[(cRow * nRols) + cCol] -= corveredForCell;
                        doRepaint = true;
                        if (field[(cRow * nRols) + cCol] == mineCell) {
                            inGame = false;
                        }
                        if (field[(cRow * nRols) + cCol] == emptyCell) {
                            find_empty_cells((cRow * nRols) + cCol);
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
