package com.minesweeper;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private static final int TILE_SIZE = 40;
    private static final int W = 400;
    private static final int H = 600;
    private static final int X = H / TILE_SIZE;
    private static final int Y = W / TILE_SIZE;
    private static Tile[][] grid = new Tile[X][Y];
    private static Scene scene;
    private static int safeTurns;

    public static int getTileSize() {
        return TILE_SIZE;
    }

    public static int getSafeTurns() {
        return safeTurns;
    }

    @Override
    public void start(Stage stage) {
        scene = new Scene(createContent());
        stage.setScene(scene);
        stage.show();
    }

    public static Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(H, W);
        safeTurns = 0;
        Tile.resetTurnsCount();

        for (int y = 0; y < Y; y++) {
            for (int x = 0; x < X; x++) {
                Tile tile = new Tile(x, y, Math.random() < 0.15);
                grid[x][y] = tile;
                root.getChildren().add(tile);
            }
        }

        for (int y = 0; y < Y; y++) {
            for (int x = 0; x < X; x++) {
                Tile tile = grid[x][y];

                if (tile.isHasBomb()) continue;

                safeTurns++;
                long bombs = getNeighbors(tile).stream().filter(Tile::isHasBomb).count();

                if (bombs > 0) tile.setText(String.valueOf(bombs));
            }
        }

        return root;
    }

    public static List<Tile> getNeighbors(Tile tile) {
        List<Tile> neighbors = new ArrayList<>();
        int[] points = {
                -1, -1,
                -1,  0,
                 0, -1,
                -1,  1,
                 1, -1,
                 0,  1,
                 1,  0,
                 1,  1,
        };

        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];

            int newX = tile.getX() + dx;
            int newY = tile.getY() + dy;

            if (newX >= 0 && newX < X && newY >= 0 && newY < Y) {
                neighbors.add(grid[newX][newY]);
            }
        }

        return neighbors;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void openTiles() {
        for (int y = 0; y < Y; y++) {
            for (int x = 0; x < X; x++) {
                grid[x][y].getText().setVisible(true);
            }
        }
    }

    public static void newWindow(boolean winner) {
        Stage stage = new Stage();
        Pane pane = new Pane();
        Button restart = new Button("Restart");
        Label label = new Label(winner ? "You win! Try again!" : "You loose! Try again!");

        restart.setTranslateX(55);
        restart.setTranslateY(40);
        label.setTranslateX(20);
        label.setTranslateY(10);

        restart.setOnAction(e -> {
            stage.close();
            scene.setRoot(createContent());
        });

        pane.getChildren().addAll(restart, label);
        stage.setTitle("Game Over");
        stage.setScene(new Scene(pane, 180, 80));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
