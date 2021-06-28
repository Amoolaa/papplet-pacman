package ghost;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MapTest {
  
  private MapParser simple;

  @BeforeEach
  public void setup() {
    PrintWriter writer = null;
    try {
      writer = new PrintWriter("simple.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    writer.print("012345678aicws00000000000000\n0000000000000000000000000000\n" + 
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n");
    writer.close();
    this.simple = new MapParser("simple.txt");
  }

  @AfterEach
  public void takedown() {
    File simple = new File("simple.txt");
    simple.delete();
  }
  
  
  /* Testing constructor. */
  @Test
  public void constructorTest() {
    assertNotNull(new MapParser("simple.txt"));
  }

  /* Testing getters. */
  @Test
  public void gettersTest() {
    MapParser m = this.simple;
    assertNull(m.getWakaSpawn());
    assertEquals(new ArrayList<Ghost>(), m.getGhostSpawn());
    assertEquals(0, m.getFruitCount());
  }

  /* Testing formatter. */
  @Test
  public void formatterTest() {
    MapParser m = this.simple;
    Cell[][] cells = m.formatter();
    for (int i = 0; i < cells.length; i++) {
      if (i == 0) {  
        List<Character> c = Arrays.asList(cells[i]).stream().filter((cell) -> 
                (cell != null)).map((cell) -> cell.getID()).collect(Collectors.toList()); 
        
        List<Character> expected = new ArrayList<Character>();
        expected.addAll(Arrays.asList('1','2','3','4','5','6','7','8','a','i','c','w','s'));
        assertEquals(expected, c);
      } else {
        List<Character> c = Arrays.asList(cells[i]).stream().filter((cell) -> 
                (cell != null)).map((cell) -> cell.getID()).collect(Collectors.toList()); 
        assertEquals(0, c.size());
      }
    }
  }

  /* Testing reset. */
  @Test
  public void resetTest() {
    MapParser m = this.simple;
    Cell[][] cells = m.formatter();
    
    Fruit fruit = (Fruit)cells[0][7];
    SuperFruit superfruit = (SuperFruit)cells[0][8];
    SodaCan sodacan = (SodaCan)cells[0][13];
    
    assertFalse(fruit.eaten());
    assertFalse(superfruit.eaten());
    assertFalse(sodacan.eaten());

    fruit.eat();
    superfruit.eat();
    sodacan.eat();

    ((Fruit)cells[0][7]).reset();
    ((SuperFruit)cells[0][8]).reset();
    ((SodaCan)cells[0][13]).reset();

    assertFalse(fruit.eaten());
    assertFalse(superfruit.eaten());
    assertFalse(sodacan.eaten());
  }
}
