package com.chris96.Model;

public class Missle {
    private final int power;
    private final int speed;

    public int getPower() {
        return power;
    }

    public int getSpeed() {
        return speed;
    }

    public Missle(int power, int speed) {
        this.power = power;
        this.speed = speed;
    }
}
