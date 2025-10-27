package core.toys; // Пакет має бути той самий

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BallTest {

    @Test
    void testBallConstructorAndGetInfo() {
        // ARRANGE: Створюємо м'яч
        Ball testBall = new Ball("Футбольний", "Середній", 150.0f, "Білий", 22.0f);

        // ACT: Отримуємо опис
        String actualInfo = testBall.getInfo();
        String expectedInfo = "Назва: Футбольний, Розмір: Середній, Колір: Білий, Ціна: 150.00 грн, Тип: М'яч, Діаметр: 22.0 см";

        // ASSERT: Перевіряємо
        assertEquals(expectedInfo, actualInfo);
        assertEquals(150.0f, testBall.getPrice());
        assertEquals("Футбольний", testBall.getName());
    }
}