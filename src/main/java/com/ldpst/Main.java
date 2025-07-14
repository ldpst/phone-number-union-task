package com.ldpst;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Add absolute path to input file");
            System.exit(0);
        }
        String filepath = args[0].trim();

    }
}
