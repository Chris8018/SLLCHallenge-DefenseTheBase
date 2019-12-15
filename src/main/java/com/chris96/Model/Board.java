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
            System.out.print(getLetterNum(i++) + " ");
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

        // Enemy
        for (Enemy enemy : this.enemies) {
            if (!enemy.isActive())
                continue;

            int[][] coords = enemy.getCoords();

            int x = enemy.getxCoord();
            int y = enemy.getyCoord();
            int width = enemy.getWidth();
            int length = enemy.getLength();

            for (int i = y; i < y + length; i++) {
                if (i < 0 || i >= this.size)
                    continue;

                for (int j = x; j < x + width; j++) {
                    if (j < 0 || j >= this.size)
                        continue;

                    this.board[i][j] = coords[i - y][j - x];
                }
            }
        }

        // Ally
        for (Ally ally : this.allies) {
            if (!ally.isActive())
                continue;

            int[][] coords = ally.getCoords();

            int x = ally.getxCoord();
            int y = ally.getyCoord();
            int width = ally.getWidth();
            int length = ally.getLength();

            for (int i = y; i < y + length; i++) {
                if (i < 0 || i >= this.size)
                    continue;

                for (int j = x; j < x + width; j++) {
                    if (j < 0 || j >= this.size)
                        continue;

                    this.board[i][j] = coords[i - y][j - x];
                }
            }
        }

        // Missile
        for (Missile missile : this.missiles) {
            if (!missile.isActive())
                continue;

            int[][] coords = missile.getCoords();

            int x = missile.getxCoord();
            int y = missile.getyCoord();
            int width = missile.getWidth();
            int length = missile.getLength();

            for (int i = y; i < y + length; i++) {
                if (i < 0 || i >= this.size)
                    continue;

                for (int j = x; j < x + width; j++) {
                    if (j < 0 || j >= this.size)
                        continue;

                    this.board[i][j] = coords[i - y][j - x];
                }
            }
        }

    }

    private int checkCollision() {
        int collisionCount = 0;

        // Enemy | Ally
        for (Enemy enemy : this.enemies) {
            if (!enemy.isActive())
                continue;

            int eX = enemy.getxCoord();
            int eY = enemy.getyCoord();
            int eWidth = enemy.getWidth();
            int eLength = enemy.getLength();

            for (Ally ally : this.allies) {
                if (!ally.isActive())
                    continue;

                int aX = ally.getxCoord();
                int aY = ally.getyCoord();
                int aWidth = ally.getWidth();
                int aLength = ally.getLength();

                for (int i = aY; i < aY + aLength; i++) {
                    if (i < 0 || i >= this.size)
                        continue;

                    for (int j = aX; j < aX + aWidth; j++) {
                        if (j < 0 || j >= this.size)
                            continue;

                        if ((i >= eY && i < eY + eLength) && (j >= eX && j < eX + eWidth)) {
                            if (!enemy.isAHole(i - eY, j - eX) && !ally.isAHole(i - aY, j - aX)) {
                                System.out.println("Collision at:" + j + "," + i + " between an enemy and an ally");
                                ally.breakSprite(i - aY, j - aX);
                                enemy.breakSprite(i - eY, j - eX);

                                collisionCount++;
                            }
                        }
                    }
                }
            }
        }

        // Enemy | Missile
        for (Enemy enemy : this.enemies) {
            if (!enemy.isActive())
                continue;

            int eX = enemy.getxCoord();
            int eY = enemy.getyCoord();
            int eWidth = enemy.getWidth();
            int eLength = enemy.getLength();

            for (Missile missile : this.missiles) {
                if (!missile.isActive())
                    continue;

                int mX = missile.getxCoord();
                int mY = missile.getyCoord();
                int mWidth = missile.getWidth();
                int mLength = missile.getLength();

                for (int i = mY; i < mY + mLength; i++) {
                    if (i < 0 || i >= this.size)
                        continue;

                    for (int j = mX; j < mX + mWidth; j++) {
                        if (j < 0 || j >= this.size)
                            continue;

                        if ((i >= eY && i < eY + eLength) && (j >= eX && j < eX + eWidth)) {
                            if (!enemy.isAHole(i - eY, j - eX) && !missile.isAHole(i - mY, j - mX)) {
                                System.out.println("Collision at:" + j + "," + i + " between an enemy and a missile");
                                missile.breakSprite(i - mY, j - mX);
                                enemy.breakSprite(i - eY, j - eX);

                                collisionCount++;
                            }
                        }
                    }
                }
            }
        }

        for (Missile missile : this.missiles) {
            if (!missile.isActive())
                continue;

            int mX = missile.getxCoord();
            int mY = missile.getyCoord();
            int mWidth = missile.getWidth();
            int mLength = missile.getLength();

            for (Ally ally : this.allies) {
                if (!ally.isActive())
                    continue;

                int aX = ally.getxCoord();
                int aY = ally.getyCoord();
                int aWidth = ally.getWidth();
                int aLength = ally.getLength();

                for (int i = aY; i < aY + aLength; i++) {
                    if (i < 0 || i >= this.size)
                        continue;

                    for (int j = aX; j < aX + aWidth; j++) {
                        if (j < 0 || j >= this.size)
                            continue;

                        if ((i >= mY && i < mY + mLength) && (j >= mX && j < mX + mWidth)) {
                            if (!missile.isAHole(i - mY, j - mX) && !ally.isAHole(i - aY, j - aX)) {
                                System.out.println("Collision at:" + j + "," + i + " between a missile and an ally");
                                ally.breakSprite(i - aY, j - aX);
                                missile.breakSprite(i - mY, j - mX);

                                collisionCount++;
                            }
                        }
                    }
                }
            }
        }

        return collisionCount;
    }

    public void begin () {
        int enemiesActive = 0;
        for (Enemy enemy : enemies)
            if (enemy.isActive())
                enemiesActive++;

        int collisionCount = 1; // Set to 1 so initial state is drawn ???

        while (enemiesActive > 0) {
            placeSprites();

            if (collisionCount > 0)
                draw();

            for (int i = maxPossibleSpeed; i >= 1; i--) {
                final int i_const = i;
                this.enemies.forEach(enemy -> {
                    if (i_const - enemy.getSpeed() <= 0)
                        enemy.move();
                });
                this.allies.forEach(ally -> {
                    if (i_const - ally.getSpeed() <= 0)
                        ally.move();
                });
                this.missiles.forEach(missile -> {
                    if (i_const - missile.getSpeed() <= 0)
                        missile.move();
                });
                checkCollision();
            }

            enemiesActive = 0;
            for (Enemy enemy : enemies)
                if(enemy.isActive())
                    enemiesActive++;

            collisionCount = checkCollision();
        }

        placeSprites();
        draw();
    }
}
