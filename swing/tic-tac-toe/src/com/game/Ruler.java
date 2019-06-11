package com.game;

import javax.swing.*;

public class Ruler {
    private int turnsCount;
    private Player player1;
    private Player player2;
    private boolean[] grid;
    private boolean theEnd;
    private JPanel panel;

    Ruler(JPanel panel) {
        turnsCount = 0;
        grid = new boolean[9];
        player1 = new Player("Player1");
        player2 = new Player("Player2");
        theEnd = false;
        this.panel = panel;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public int getTurnsCount() {
        return turnsCount;
    }

    public void setTurnsCount(int turnsCount) {
        this.turnsCount = turnsCount;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void madeMove() {
        this.turnsCount++;
    }

    public boolean isBusy(int index) {
        return grid[index-1];
    }

    public void setGrid(int index) {
        this.grid[index-1] = true;
    }

    public boolean isTheEnd() {
        return theEnd;
    }

    public void setTheEnd() {
        this.theEnd = true;
    }

    public void getAction(JButton button, int index) {
        if (!isBusy(index) && !isTheEnd()) {
            if (turnsCount % 2 == 0) {
                button.setText("X");
                player1.setTurns(index);
                if (player1.checkForWin()) {
                    JOptionPane.showMessageDialog(panel, "Player 1 is the winner! Congratulations!");
                    setTheEnd();
                }
            } else {
                button.setText("O");
                player2.setTurns(index);
                if (player2.checkForWin()) {
                    JOptionPane.showMessageDialog(panel, "Player 2 is the winner! Congratulations!");
                    setTheEnd();
                }
            }

            setGrid(index);
            madeMove();
        }

        if (!isTheEnd() && checkForDraw()) {
            JOptionPane.showMessageDialog(panel, "It's a draw! Try again!");
            setTheEnd();
        }
    }

    private boolean checkForDraw() {
        return turnsCount == 9;
    }

    public void clearAll() {
        turnsCount = 0;
        grid = new boolean[9];
        player1 = new Player("Player1");
        player2 = new Player("Player2");
        theEnd = false;
    }
}
