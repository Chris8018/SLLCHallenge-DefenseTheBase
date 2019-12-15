package com.chris96.Model;

public class Missile extends Sprite{
    private int power;

    public int getPower() {
        return power;
    }

    public Missile(int xCoord, char[] movePattern, int boardSize, int id) {
        super("missile", movePattern, xCoord, boardSize - 1, 1, 1, boardSize, id);
        this.power = (int)(Math.random() * 5) + 1;
    }

    @Override
    public void breakSprite(int y, int x) {
        if (this.power == 0) {
            this.destroy();
            return;
        }
        this.power--;
    }

    @Override
    public void printInfo() {
        System.out.printf("I.D:%s\nXCoord:%d\nYCoord:%d\nLength:%d\nSpeed:%d\nPower:%d\nMovement Pattern:%s\n", getId(), getxCoord(), getyCoord(), getLength(), getWidth(), getSpeed(), getPower(), new String(getMovePattern()));
    }
}
