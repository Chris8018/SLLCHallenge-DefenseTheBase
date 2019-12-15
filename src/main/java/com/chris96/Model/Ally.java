package com.chris96.Model;

public class Ally extends Sprite{
    public Ally(int length, int width, int xCoord, int yCoord, char[] movePattern, int boardSize, int id) {
        super("green", movePattern, xCoord, yCoord, length, width, boardSize, id);
    }
}
