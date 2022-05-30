package com.project.seabattle;

public class ShipType {
    public boolean isHorisontal;
    public int size;
    public int count;

    ShipType(boolean isHorisontal, int size, int count) {
        this.isHorisontal = isHorisontal;
        this.size = size;
        this.count = count;
    }

    public void changeRotation() {
        isHorisontal = !isHorisontal;
    }
}
