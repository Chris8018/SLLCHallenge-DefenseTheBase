package com.chris96.Model;

class Sprite {
    private String color;
    private char[] movePattern;
    private int xCoord;
    private int yCoord;
    private int length;
    private int width;
    private int[][] coords;
    private int speed;
    private int boardSize;
    private String id;
    private boolean active;
    private char nextStep;
    private int nextStepIndex;
    private int stepCount;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public char[] getMovePattern() {
        return movePattern;
    }

    public void setMovePattern(char[] movePattern) {
        this.movePattern = movePattern;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int[][] getCoords() {
        return coords;
    }

    public void setCoords(int[][] coords) {
        this.coords = coords;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public char getNextStep() {
        return nextStep;
    }

    public void setNextStep(char nextStep) {
        this.nextStep = nextStep;
    }

    public int getNextStepIndex() {
        return nextStepIndex;
    }

    public void setNextStepIndex(int nextStepIndex) {
        this.nextStepIndex = nextStepIndex;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public Sprite(String color, char[] movePattern, int xCoord, int yCoord, int length, int width, int boardSize, int id) {
        this.color = color;
        this.movePattern = movePattern;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.length = length;
        this.width = width;
        this.boardSize = boardSize;

        this.init(id);
    }

    private void init(int id) {
        this.speed = (int) (Math.random() * 3) + 1;
        this.stepCount = this.speed;
        this.nextStepIndex = 0;
        this.nextStep = movePattern[nextStepIndex];
        this.coords = new int[length][width];

        int type;

        switch (color) {
            case "blue":
                type = 2;
                this.id = "Enemy" + id;
                break;
            case "green":
                type = 3;
                this.id = "Ally" + id;
                break;
            default:
                type = 4;
                this.id = "Missile" + id;
        }

        for (int i = 0; i < this.length; i++)
            for (int j = 0; j < this.width; j++)
                this.coords[i][j] = type;

        this.active = true;
    }

    public void breakSprite (int y, int x) {
        this.coords[y][x] = 0;
        int sum = 0;
        for (int[] row : coords)
            for (int i : row)
                sum += i;

        if (sum == 0)
            this.destroy();
    }

    private void takeStep (char step) {
        switch (step) {
            case 'U':
                this.setyCoord(getyCoord() - 1);
                break;
            case 'D':
                this.setyCoord(getyCoord() + 1);
                break;
            case 'L':
                this.setxCoord(getxCoord() - 1);
                break;
            case 'R':
                this.setxCoord(getxCoord() + 1);
                break;
        }
        this.stepCount--;
    }

    public void move () {
        if (!isActive())
            return;

        takeStep(nextStep);

        if (stepCount == 0) {
            nextStepIndex++;
            stepCount = getSpeed();
        }

        nextStep = this.movePattern[nextStepIndex % movePattern.length];

        this.isInTheBoard();
    }

    /**
     * Sprite is in the board if any of its 4 corners are on the board
     */
    private void isInTheBoard() {
        int leftX = getxCoord();
        int upY = getyCoord();
        int rightX = leftX + getWidth();
        int downY = upY + getLength();

        if (this.isIntheBoard(leftX, rightX, upY, downY))
            destroy();
    }

    private boolean isIntheBoard(int leftX, int rightX, int upY, int downY) {
        return !((leftX > 0 && leftX < this.boardSize && upY > 0 && upY < this.boardSize) ||
                (rightX > 0 && rightX < this.boardSize && upY > 0 && upY < this.boardSize) ||
                (leftX > 0 && leftX < this.boardSize && downY > 0 && downY < this.boardSize) ||
                (rightX > 0 && rightX < this.boardSize && downY > 0 && downY < this.boardSize));
    }

    public void destroy() {
        this.active = false;
    }

    public boolean isAHole(int y, int x) {
        return this.coords[y][x] == 0;
    }

    public void printInfo() {
        System.out.println();
    }
}
