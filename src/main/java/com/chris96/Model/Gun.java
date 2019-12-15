package com.chris96.Model;

public class Gun {
    private int ammoAmount;
    private final Missle ammoType;

    public int getAmmoAmount() {
        return ammoAmount;
    }

    public void setAmmoAmount(int ammoAmount) {
        this.ammoAmount = ammoAmount;
    }

    public Missle getAmmoType() {
        return ammoType;
    }

    public Gun(Missle ammoType, int ammoAmount)
    {
        this.ammoAmount = ammoAmount;
        this.ammoType = ammoType;
    }
}
