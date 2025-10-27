package core;

public class Main {
    public static void main(String[] args) {
        Playroom playroom = new Playroom(1000.0f);
        MainMenu menu = new MainMenu(playroom);
        menu.display();
    }
}