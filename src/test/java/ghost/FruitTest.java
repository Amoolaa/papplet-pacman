package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FruitTest {
  /* Testing eating. */
  @Test
  public void eatingTest() {
    Fruit f = new Fruit('7', "fruit.png", 7, 5, false);
    assertFalse(f.eaten());
    f.eat();
    assertTrue(f.eaten());
    f.reset();
    assertFalse(f.eaten());
  }
}
