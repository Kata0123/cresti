import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame {
    private JButton[][] buttons = new JButton[3][3];
    private char currentPlayer = 'X';
    private JLabel statusLabel;
    private boolean gameOver = false;

    public TicTacToe() {
        setTitle("Крестики-нолики");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(400, 450);
        setLocationRelativeTo(null);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        boardPanel.setBackground(Color.WHITE);
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton btn = new JButton();
                btn.setFont(new Font("Arial", Font.BOLD, 60));
                btn.setFocusPainted(false);
                btn.setBackground(new Color(240, 240, 240));
                btn.addActionListener(new ButtonClickListener(row, col));
                buttons[row][col] = btn;
                boardPanel.add(btn);
            }
        }

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        statusLabel = new JLabel("Ход игрока X", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        JButton resetButton = new JButton("Новая игра");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 16));
        resetButton.addActionListener(e -> resetGame());

        bottomPanel.add(statusLabel, BorderLayout.CENTER);
        bottomPanel.add(resetButton, BorderLayout.EAST);

        add(boardPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameOver) {
                JOptionPane.showMessageDialog(TicTacToe.this, "Игра уже завершена. Начните новую.");
                return;
            }

            JButton btn = buttons[row][col];
            if (!btn.getText().isEmpty()) {
                JOptionPane.showMessageDialog(TicTacToe.this, "Эта клетка уже занята!");
                return;
            }

            btn.setText(String.valueOf(currentPlayer));
            btn.setEnabled(false);

            if (checkWinner()) {
                statusLabel.setText("Победил игрок " + currentPlayer + "!");
                gameOver = true;
                return;
            }

            if (isBoardFull()) {
                statusLabel.setText("Ничья!");
                gameOver = true;
                return;
            }

            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            statusLabel.setText("Ход игрока " + currentPlayer);
        }
    }

    private boolean checkWinner() {
        String symbol = String.valueOf(currentPlayer);

        for (int row = 0; row < 3; row++) {
            if (buttons[row][0].getText().equals(symbol) &&
                    buttons[row][1].getText().equals(symbol) &&
                    buttons[row][2].getText().equals(symbol)) {
                return true;
            }
        }

        for (int col = 0; col < 3; col++) {
            if (buttons[0][col].getText().equals(symbol) &&
                    buttons[1][col].getText().equals(symbol) &&
                    buttons[2][col].getText().equals(symbol)) {
                return true;
            }
        }

        if (buttons[0][0].getText().equals(symbol) &&
                buttons[1][1].getText().equals(symbol) &&
                buttons[2][2].getText().equals(symbol)) {
            return true;
        }

        if (buttons[0][2].getText().equals(symbol) &&
                buttons[1][1].getText().equals(symbol) &&
                buttons[2][0].getText().equals(symbol)) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setEnabled(true);
            }
        }
        currentPlayer = 'X';
        gameOver = false;
        statusLabel.setText("Ход игрока X");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}