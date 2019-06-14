package com.minesweeper;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Tile extends StackPane {
    private int x;
    private int y;
    private boolean hasBomb;
    private Rectangle rectangle = new Rectangle(Main.getTileSize() - 2, Main.getTileSize() - 2);
    private Text text = new Text();
    private boolean isOpen;
    private static int turnsCount = 0;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHasBomb() {
        return hasBomb;
    }

    public static void resetTurnsCount() {
        turnsCount = 0;
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public Text getText() {
        return text;
    }

    public Tile(int x, int y, boolean hasBomb) {
        this.x = x;
        this.y = y;
        this.hasBomb = hasBomb;

        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.WHITE);
        text.setText(hasBomb ? "X" : "");
        text.setFill(hasBomb ? Color.RED : Color.BLACK);
        text.setFont(Font.font(30));
        text.setVisible(false);

        getChildren().addAll(rectangle, text);

        setTranslateX(x * Main.getTileSize());
        setTranslateY(y * Main.getTileSize());

        setOnMouseClicked(e -> open());
    }

    public void open() {
        if (isOpen) return;
        else if (hasBomb) {
            text.setVisible(true);
            rectangle.setFill(Color.YELLOW);
            Main.openTiles();
            Main.newWindow(false);
            return;
        }

        isOpen = true;
        text.setVisible(true);
        rectangle.setFill(Color.LIGHTBLUE);
        if (text.getText().isEmpty()) {
            Main.getNeighbors(this).forEach(Tile::open);
        }
        if (turnsCount == Main.getSafeTurns()) {
            text.setVisible(true);
            Main.openTiles();
            Main.newWindow(true);
        }
    }
}
