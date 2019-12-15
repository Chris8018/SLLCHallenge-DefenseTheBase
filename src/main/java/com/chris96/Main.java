package com.chris96;

import com.chris96.Model.*;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    // Answer from User David Akhihiero

    public static void main(String[] args) {
        int size, numOfMissiles;

        Scanner sc = new Scanner(System.in);
        System.out.println("Key\nB = Enemy\nG = Ally\n" + (char)254 + " = Missile\nA, B, C... are used to represent 10, 11, 12...\nOnly Time frames involving collision(s), are displayed in an attempt to reduce code runtime");

        try {
            System.out.print("Grid size: ");
            size = sc.nextInt();
            if (size < 6 || size > 19)
            {
                System.out.print(size + " is too " + (size < 6 ? "small" : "large") + ", default value is being used. Grid size is: ");
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            size = 16;
        }
        System.out.println(size);
        System.out.print("Number of missiles: ");

        try {
            numOfMissiles = sc.nextInt();
        } catch (NoSuchElementException e) {
            numOfMissiles = 5;
        }

        System.out.println(numOfMissiles);
        Board board = new Board(size, numOfMissiles);
        board.begin();
    }
}
