package com.chris96;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int width, height, missileAmount;

        try {
            if (args.length > 0 && args.length <= 3)
            {
                width = Integer.parseInt(args[0]);
                height = Integer.parseInt(args[1]);
                missileAmount = Integer.parseInt(args[2]);
            } else {
                Scanner reader = new Scanner(System.in);

                width = getInput(reader, "base's Width");
                height = getInput(reader, "base's Height");
                missileAmount = getInput(reader, "missile amount");
            }
        } catch (Exception e) {
            System.out.println("Invalid input");
            e.printStackTrace();
            System.exit(1);
        }

    }

    private static int getInput(Scanner reader, String mess) {
        System.out.println("Enter " + mess + " : ");
        return reader.nextInt();
    }
}
