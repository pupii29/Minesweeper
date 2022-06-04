

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Minesweeper extends JFrame {
    private JLabel statusbar;

    public Minesweeper() {
        initUI();
    }

    private void initUI() {
        statusbar = new JLabel("");
        add(statusbar, BorderLayout.SOUTH);
        add(new Board(statusbar));
        setResizable(false);
        pack();
        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            PlayMusic.playMusic("C://Users//DELL//Documents//GitHub//Minesweeper//Java-Minesweeper-Game-master//src//sound//theme.wav");
            var ex = new Minesweeper();
            ex.setVisible(true);
        });
    }
}
