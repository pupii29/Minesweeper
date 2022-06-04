

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.EventQueue;

public class Minesweeper extends JFrame {
    private JLabel statusbar;

    public Minesweeper() {
        initUI();
    }

    public void initUI() {
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
            var ex = new Minesweeper();
            ex.setVisible(true);
        });
        PlayMusic.playMusic("C://Users//DELL//Documents//GitHub//Minesweeper//Java-Minesweeper-Game-master//src//sound//theme.wav");
    }
}
