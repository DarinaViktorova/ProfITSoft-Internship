package my.space;

import my.space.ui.ConsoleUI;

public class Main {

    public static void main(String[] args) {
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.run();
        consoleUI.closeScanner();
    }
}
