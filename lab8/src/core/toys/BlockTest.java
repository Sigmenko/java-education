package core.toys; // Пакет має бути той самий

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    @Test
    void testBlockConstructorAndGetInfo() {
        // ARRANGE: Створюємо кубик
        Block testBlock = new Block("Дерев'яний", "Маленький", 50.0f, "Натуральний", "Куб");

        // ACT: Отримуємо опис
        String actualInfo = testBlock.getInfo();
        String expectedInfo = "Назва: Дерев'яний, Розмір: Маленький, Колір: Натуральний, Ціна: 50.00 грн, Тип: Кубик, Форма: Куб";

        // ASSERT: Перевіряємо
        assertEquals(expectedInfo, actualInfo);
        assertEquals(50.0f, testBlock.getPrice());
        assertEquals("Дерев'яний", testBlock.getName());
    }
}