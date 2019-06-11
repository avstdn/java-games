package com.game;

public class Player {
    private String name;
    private boolean[] turns;

    Player(String name) {
        this.name = name;
        turns = new boolean[9];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean[] getTurns() {
        return turns;
    }

    public void setTurns(int index) {
        this.turns[index-1] = true;
    }

    public boolean checkForWin() {
        return (turns[0] && turns[1] && turns[2]) || (turns[3] && turns[4] && turns[5]) || (turns[6] && turns[7] && turns[8]) ||
                (turns[0] && turns[3] && turns[6]) || (turns[1] && turns[4] && turns[7]) || (turns[2] && turns[5] && turns[8]) ||
                (turns[0] && turns[4] && turns[8]) || (turns[2] && turns[4] && turns[6]);
    }
}
