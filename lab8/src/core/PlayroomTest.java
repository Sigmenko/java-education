package core; // Пакет

import core.toys.*; // Імпортуємо всі іграшки
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File; // Потрібен для тесту файлів
import java.nio.file.Path; // Потрібен для тесту файлів
import org.junit.jupiter.api.io.TempDir; // Потрібен для тесту файлів
import java.util.List; // Потрібен для перевірки списку

import static org.junit.jupiter.api.Assertions.*;

class PlayroomTest {

    private Playroom playroom;
    private Car car;
    private Doll doll;

    // Створює тимчасову папку для тестів з файлами
    @TempDir
    Path tempDir;
    private String testSaveFilePath; // Шлях до тестового файлу збереження

    @BeforeEach
    void setUp() {
        playroom = new Playroom(1000.0f);
        car = new Car("Феррарі", "Середня", 500.0f, "Червоний", 4);
        doll = new Doll("Барбі", "Маленька", 300.0f, "Рожевий", "Блонд");
        // Встановлюємо шлях до тестового файлу у тимчасовій папці
        testSaveFilePath = tempDir.resolve("test_playroom.ser").toString();
        playroom.setSaveFilePath(testSaveFilePath); // Використовуємо новий метод
    }

    // --- Тести для addToy() ---

    @Test
    void testAddToySuccess() {
        playroom.addToy(car);
        assertEquals(500.0f, playroom.getBudget(), 0.01, "Бюджет має зменшитись"); // 0.01 - похибка для float
        assertEquals(1, playroom.getToys().size(), "Має бути одна іграшка");
        assertTrue(playroom.getToys().contains(car), "Список має містити додану машинку");
    }

    @Test
    void testAddToyInsufficientBudget() {
        Playroom poorPlayroom = new Playroom(100.0f);
        poorPlayroom.addToy(car);
        assertEquals(100.0f, poorPlayroom.getBudget(), 0.01, "Бюджет не має змінитись");
        assertTrue(poorPlayroom.getToys().isEmpty(), "Список іграшок має бути порожнім");
    }

    // --- Тести для sortToys() ---

    @Test
    void testSortToysByPrice() {
        playroom.addToy(car);  // 500
        playroom.addToy(doll); // 300
        playroom.sortToys("1");
        List<Toy> sortedToys = playroom.getToys();
        assertEquals(doll.getName(), sortedToys.get(0).getName(), "Лялька (300) має бути першою");
        assertEquals(car.getName(), sortedToys.get(1).getName(), "Машинка (500) має бути другою");
    }

    @Test
    void testSortToysBySize() {
        playroom.addToy(car);  // Середня
        playroom.addToy(doll); // Маленька
        playroom.sortToys("2");
        List<Toy> sortedToys = playroom.getToys();
        assertEquals(doll.getName(), sortedToys.get(0).getName(), "Лялька (Маленька) має бути першою");
        assertEquals(car.getName(), sortedToys.get(1).getName(), "Машинка (Середня) має бути другою");
    }

    // --- Тести для removeToy() ---

    @Test
    void testRemoveToySuccess() {
        playroom.addToy(car);
        playroom.addToy(doll);
        float initialBudget = playroom.getBudget();
        playroom.removeToy("Феррарі");
        assertEquals(1, playroom.getToys().size(), "Має залишитись одна іграшка");
        assertEquals(initialBudget + car.getPrice(), playroom.getBudget(), 0.01, "Бюджет має збільшитись");
        assertFalse(playroom.getToys().stream().anyMatch(t -> t.getName().equals("Феррарі")), "Машинка має бути видалена");
    }

    @Test
    void testRemoveToyNotFound() {
        playroom.addToy(car);
        float initialBudget = playroom.getBudget();
        playroom.removeToy("Лялька");
        assertEquals(1, playroom.getToys().size(), "Список не має змінитись");
        assertEquals(initialBudget, playroom.getBudget(), 0.01, "Бюджет не має змінитись");
    }

    // --- Тести для findToysByPriceRange() ---
    // Ми можемо перевірити стан, а не вивід в консоль

    @Test
    void testFindToysInRange() {
        playroom.addToy(car); // 500
        playroom.addToy(doll); // 300
        // Викликаємо, але не перевіряємо вивід
        assertDoesNotThrow(() -> playroom.findToysByPriceRange(200.0f, 400.0f));
    }

    @Test
    void testFindToysNotInRange() {
        playroom.addToy(car);
        playroom.addToy(doll);
        assertDoesNotThrow(() -> playroom.findToysByPriceRange(600.0f, 800.0f));
    }

    // --- Тести для порожнього списку ---
    @Test
    void testSortToysEmptyList() {
        assertDoesNotThrow(() -> playroom.sortToys("1"));
    }

    @Test
    void testFindToysByPriceRangeEmptyList() {
        assertDoesNotThrow(() -> playroom.findToysByPriceRange(100f, 200f));
    }

    @Test
    void testRemoveToyEmptyList() {
        assertDoesNotThrow(() -> playroom.removeToy("Будь-яка назва"));
    }

    // --- Тест для невірного вибору сортування ---
    @Test
    void testSortToysInvalidChoice() {
        playroom.addToy(car);
        assertDoesNotThrow(() -> playroom.sortToys("3"));
        // Перевіримо, що порядок не змінився (потрібно додавати другу іграшку)
        playroom.addToy(doll);
        playroom.sortToys("3"); // Невірний вибір
        List<Toy> toys = playroom.getToys();
        assertEquals(car.getName(), toys.get(0).getName(), "Порядок не має змінитись при невірному виборі");
        assertEquals(doll.getName(), toys.get(1).getName());
    }

    // --- Тести для save/load ---
    @Test
    void testSaveAndLoadFromFile() {
        playroom.addToy(car);
        float budgetBeforeSave = playroom.getBudget();
        playroom.saveToFile(); // Зберігаємо у тимчасовий файл

        // Створюємо нову кімнату
        Playroom newPlayroom = new Playroom(0.0f);
        newPlayroom.setSaveFilePath(testSaveFilePath); // Вказуємо той самий файл
        newPlayroom.loadFromFile(); // Завантажуємо

        assertEquals(1, newPlayroom.getToys().size(), "Має завантажитись одна іграшка");
        assertEquals(budgetBeforeSave, newPlayroom.getBudget(), 0.01, "Бюджет має відновитись");
        assertEquals(car.getName(), newPlayroom.getToys().get(0).getName(), "Має завантажитись правильна іграшка");
    }

    @Test
    void testLoadFromFileNotFound() {
        // Arrange: Вказуємо шлях до файлу, якого точно немає
        playroom.setSaveFilePath(tempDir.resolve("non_existent_file.ser").toString());

        // Act & Assert
        assertDoesNotThrow(() -> playroom.loadFromFile());
        assertTrue(playroom.getToys().isEmpty(), "Список має залишитись порожнім");
    }
}