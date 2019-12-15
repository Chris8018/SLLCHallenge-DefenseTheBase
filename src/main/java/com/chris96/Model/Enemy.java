package com.chris96.Model;

public class Enemy extends Sprite {

    public Enemy(int length, int width, int xCoord, int yCoord, char[] movePattern, int boardSize, int id) {
        super("blue", movePattern, xCoord, yCoord, length, width, boardSize, id);
    }
}
