package main;

import main.Graf;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException 
    {
        Graf g = new Graf("src/NewYork.hrn");
        g.printInfo();
        g.algoritmus();
    }
}
