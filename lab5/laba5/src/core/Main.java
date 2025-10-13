    package core;

    public class Main {
        public static void main(String[] args) {
            // 1. Створюємо "Отримувача" - нашу ігрову кімнату
            Playroom playroom = new Playroom();

            // 2. Створюємо "Викликача" - меню, і передаємо йому кімнату
            MainMenu menu = new MainMenu(playroom);

            // 3. Запускаємо меню
            menu.display();
        }
    }