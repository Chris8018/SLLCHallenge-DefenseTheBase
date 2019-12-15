package com.chris96.Model;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    private int[][] board;
    private ArrayList<Missile> missiles;
    private ArrayList<Enemy> enemies;
    private ArrayList<Ally> allies;
    private Random rand;
    private final int size;
    private int numOfPieces;
    private int numOfMissiles;
    private int maxSizeOfPieces;
    private ArrayList<Integer> restrictedXCoords;
    private int maxPossibleSpeed = 3;

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public ArrayList<Missile> getMissiles() {
        return missiles;
    }

    public void setMissiles(ArrayList<Missile> missiles) {
        this.missiles = missiles;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public ArrayList<Ally> getAllies() {
        return allies;
    }

    public void setAllies(ArrayList<Ally> allies) {
        this.allies = allies;
    }

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

    public int getSize() {
        return size;
    }

    public int getNumOfPieces() {
        return numOfPieces;
    }

    public void setNumOfPieces(int numOfPieces) {
        this.numOfPieces = numOfPieces;
    }

    public int getNumOfMissiles() {
        return numOfMissiles;
    }

    public void setNumOfMissiles(int numOfMissiles) {
        this.numOfMissiles = numOfMissiles;
    }

    public int getMaxSizeOfPieces() {
        return maxSizeOfPieces;
    }

    public void setMaxSizeOfPieces(int maxSizeOfPieces) {
        this.maxSizeOfPieces = maxSizeOfPieces;
    }

    public ArrayList<Integer> getRestrictedXCoords() {
        return restrictedXCoords;
    }

    public void setRestrictedXCoords(ArrayList<Integer> restrictedXCoords) {
        this.restrictedXCoords = restrictedXCoords;
    }

    public int getMaxPossibleSpeed() {
        return maxPossibleSpeed;
    }

    public void setMaxPossibleSpeed(int maxPossibleSpeed) {
        this.maxPossibleSpeed = maxPossibleSpeed;
    }

    public Board(int size, int numOfMissiles) {
        this.size = size;
        this.numOfMissiles = numOfMissiles;

        this.init();
    }

    private void init() {
        this.numOfPieces = size / 3;
        this.maxSizeOfPieces = size / 4;

        this.rand = new Random();
        this.board = new int[size][size];

        this.missiles = new ArrayList<>(numOfMissiles);
        this.restrictedXCoords = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.allies = new ArrayList<>();

        int enemyNum = rand.nextInt(this.numOfPieces - 1) + 1;
        int allyNum = this.numOfPieces - enemyNum;

        this.createEnemies(enemyNum);
        this.createAllies(allyNum);
        this.initMissles();
    }

    private void createEnemies(int enemyNum) {
        for (int i = 0; i < enemyNum; i++) {
            int length, width, x, y;
            length = rand.nextInt(this.maxSizeOfPieces) + 1;
            width = rand.nextInt(this.maxSizeOfPieces) + 1;

            x = rand.nextInt(this.size - 1 - width) + 1;
            y = rand.nextInt(this.size - 1 - length - this.maxSizeOfPieces) + 1;

            this.enemies.add(new Enemy(length, width, x, y, getRandomMovePattern(), this.size, i));
        }

        for (Enemy enemy : enemies)
            enemy.printInfo();
    }

    private void createAllies(int allyNum) {
        for (int i = 0; i < allyNum; i++) {
            int length, width, x, y;
            length = rand.nextInt(this.maxSizeOfPieces) + 1;
            width = rand.nextInt(this.maxSizeOfPieces) + 1;

            x = rand.nextInt(this.size - 1 - width) + 1;
            y = rand.nextInt(this.size - 1 - length - this.maxSizeOfPieces) + 1;

            this.allies.add(new Ally(length, width, x, y, new char[]{'D'}, this.size, i));
        }

        for (Ally ally : allies)
            ally.printInfo();
    }

    private void initMissles () {
        for (int i = 0; i < this.numOfMissiles; i++) {
            char[] movePattern;

            int x = rand.nextInt(this.size);

            int totalAlliesWidth = allies.stream().map(Sprite::getWidth).reduce(0, Integer::sum);

            if (this.numOfMissiles < (this.size - totalAlliesWidth))
                this.restrictedXCoords.add(x);

            Missile missile = new Missile(x, new char[]{'U'}, this.size, i);

            int speed = rand.nextInt(3) + 1;
            missile.setSpeed(speed);

            movePattern = new char[2 * (this.size -1) / speed];

            // WHY??
            int k = movePattern.length;
            for (int j = 0; j < k; j++)
                if (j < (k / 2))
                    movePattern[j] = 'U';
                else
                    movePattern[j] = 'D';

            missile.setMovePattern(movePattern);

            this.missiles.add(missile);
        }
    }

    private char[] getRandomMovePattern () {
        int numOfMoves = (int)(Math.random() * 4) + 1;

        char[] movePattern = new char[numOfMoves];

        for (int i = 0; i < numOfMoves; i++) {
            char move;

            int j = (int)(Math.random() * 4);
            switch (j) {
                case 0:
                    move = 'U';
                    break;
                case 1:
                    move = 'D';
                    break;
                case 2:
                    move = 'L';
                    break;
                default:
                    move = 'R';
            }
            movePattern[i] = move;
        }
        return movePattern;
    }

    public void draw () {
        System.out.print("  ");
        for (int i = 0; i < this.size; i++)
            System.out.print(getLetterNum(i) + " ");

        System.out.println();

        int i = 0;
        for (int[] row : board) {
            System.out.println(getLetterNum(i++) + " ");
            for (int element : row)
                System.out.print(getElement(element) + " ");
            System.out.println();
        }
    }

    private char getElement (int code) {
        switch (code) {
            case 0:
            case 1: return ' '; // Grid
            case 2: return 'B'; // Enemy
            case 3: return 'G'; // Ally
            case 4: return (char)254; // Missle
            default: return ' ';
        }
    }

    private char getLetterNum(int n) {
        if (n >= 0 && n <= 9)
            return (char) (n + 48);
        return (char) (n + 55);
    }

    private void placeSprites () {
        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++)
                this.board[i][j] = 1;

        for (Enemy enemy : this.enemies) {
            if (!enemy.isActive())
                continue;

            int[][] coords = enemy.getCoords();
            //
        }
    }

    private int checkCollision() {
        return 0;
    }

    public void begin () {
        //
    }
}
