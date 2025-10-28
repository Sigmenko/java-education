package core.toys; // Пакет має бути той самий

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; // Важливий імпорт!

class CarTest {

    @Test // Позначаємо метод як тест
    void testCarConstructorAndGetInfo() {
        // ARRANGE (Підготовка): Створюємо машинку
        Car testCar = new Car("Феррарі", "Середня", 500.0f, "Червоний", 4);

        // ACT (Дія): Отримуємо її опис
        String actualInfo = testCar.getInfo();
        String expectedInfo = "Назва: Феррарі, Розмір: Середня, Колір: Червоний, Ціна: 500.00 грн, Тип: Машинка, К-ть коліс: 4";

        // ASSERT (Перевірка): Порівнюємо отримане з очікуваним
        assertEquals(expectedInfo, actualInfo);

        // Додаткові перевірки базових полів
        assertEquals(500.0f, testCar.getPrice());
        assertEquals("Феррарі", testCar.getName());
        assertEquals("Середня", testCar.getSize());
    }
}