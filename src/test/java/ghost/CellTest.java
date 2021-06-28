package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellTest {
  
  /* Testing constructor. */
  @Test
  public void constructorTest() {
    assertNotNull(new Spawn('1', 1, 1, true));
  }
  
  /* Testing getters. */
  @Test
  public void gettersTest() {
    Cell c = new Spawn('1', 2, 35, true);
    assertTrue(c.getID() == '1');
    assertTrue(c.getX() == 32);
    assertTrue(c.getY() == 560);
    assertTrue(c.getCol() == 2);
    assertTrue(c.getRow() == 35);
    assertTrue(c.isSolid());
  }
}
