package core.toys;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DollTest {

    @Test
    void testDollConstructorAndGetInfo() {
        // ARRANGE (Підготовка): Створюємо ляльку
        Doll testDoll = new Doll("Барбі", "Маленька", 300.0f, "Рожевий", "Блонд");

        // ACT (Дія): Отримуємо її опис
        String actualInfo = testDoll.getInfo();
        String expectedInfo = "Назва: Барбі, Розмір: Маленька, Колір: Рожевий, Ціна: 300.00 грн, Тип: Лялька, Колір волосся: Блонд";

        // ASSERT (Перевірка): Порівнюємо отримане з очікуваним
        assertEquals(expectedInfo, actualInfo);

        // Додаткові перевірки
        assertEquals(300.0f, testDoll.getPrice());
        assertEquals("Барбі", testDoll.getName());
    }
}