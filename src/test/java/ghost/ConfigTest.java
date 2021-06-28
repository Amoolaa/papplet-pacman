package ghost;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;


public class ConfigTest {
  
  @BeforeEach
  public void setup() {
    PrintWriter writer = null;
    try {
      writer = new PrintWriter("basic.json");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    writer.println("{");
    writer.println("    \"map\": \"map.txt\",");
    writer.println("    \"lives\": 3,");
    writer.println("    \"speed\": 1,");
    writer.println("    \"frightenedLength\": 10,");
    writer.println("    \"invisLength\": 20,");
    writer.println("    \"modeLengths\": [");
    writer.println("        5,\n        10,\n        15,\n        20\n    ]\n}");
    writer.flush();
    writer.close();
  }

  @AfterEach
  public void takedown() {
    File basic = new File("basic.json");
    basic.delete();
  }

  /* Testing constructor. */
  @Test
  public void constructorTest() {
   assertNotNull(new Config("basic.json"));
  }

  /* Testing formatter. */
  @Test
  public void formatterTest() {
    Config c = new Config("basic.json");
    c.formatter();
    assertEquals("map.txt", c.getMap());
    assertEquals(3, c.getLives());
    assertEquals(1, c.getSpeed());
    assertEquals(10, c.getFrightenedLength());
    assertEquals(20, c.getInvisLength());
    assertEquals(10, c.getFrightenedLength());
    assertArrayEquals(new int[]{5, 10, 15, 20}, c.getModeLengths());
  }
}
