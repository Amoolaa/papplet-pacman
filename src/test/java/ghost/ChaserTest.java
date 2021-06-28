package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import processing.core.PApplet;

import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;

public class ChaserTest {

  @BeforeEach
  public void setup() {
    PrintWriter writer = null;
    try {
      writer = new PrintWriter("basic.json");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    writer.println("{");
    writer.println("    \"map\": \"simple_chaser.txt\",");
    writer.println("    \"lives\": 3,");
    writer.println("    \"speed\": 1,");
    writer.println("    \"frightenedLength\": 1,");
    writer.println("    \"invisLength\": 1,");
    writer.println("    \"modeLengths\": [");
    writer.println("        3,\n        5,\n        5,\n        20\n    ]\n}");
    writer.flush();
    writer.close();

    try {
      writer = new PrintWriter("simple_chaser.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    writer.print("0000000000000000000000000000\n0000000000000000000000000000\n" + 
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000611111111111500000000\n" +
            "000000028777777777s200000000\n0000000276115761157200000000\n" +
            "0000000272002720027200000000\n0000000272002720027200000000\n" +
            "0000000274113741137200000000\n0000000277777p7777c200000000\n" +
            "0000000276115761157200000000\n0000000272002720027200000000\n" +
            "0000000272002720027200000000\n0000000274113741137200000000\n" +
            "0000000277777777777200000000\n0000000411111111111300000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n");
    writer.close();
  }

  @AfterEach
  public void takedown() {
    File basic = new File("basic.json");
    basic.delete();
    File simpleMovement = new File("simple_chaser.txt");
    simpleMovement.delete();
  }

  /* Testing constructor. */
  @Test
  public void constructorTest() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");

    for (Spawn s : game.getMapParser().getGhostSpawn()) {
      assertNotNull(new Chaser(s, game.getConfig()));
    }
  }

  /* Testing updating Chaser's target location */
  @Test
  public void updateTargetTest() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");
    Chaser chaser = (Chaser) game.getGhosts().get(0);
    Waka waka = game.getWaka();
    assertEquals("scatter", chaser.getCurrentModeName());

    for (int i = 0; i < 16 * 5; i++) {
      game.tick();
    }
    assertEquals(waka.getSpawn(), chaser.getCurrentCell());

    chaser.updateTargetLocation(waka);

    assertEquals(0, chaser.getXTarget());
    assertEquals(0, chaser.getYTarget());

    /* Moving game past 3 seconds - the time it should switch to chase mode. */
    for (int i = 0; i < 100; i++) {
      game.tick();
    }

    chaser.updateTargetLocation(waka);

    assertEquals(waka.getX(), chaser.getXTarget());
    assertEquals(waka.getY(), chaser.getYTarget());
  }

  /* Testing frightened behaviour. */
  @Test
  public void frightenedTest() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");
    Chaser chaser = (Chaser) game.getGhosts().get(0);
    Waka waka = game.getWaka();
    assertEquals("scatter", chaser.getCurrentModeName());
    for (int i = 0; i < 16 * 5; i++) {
      game.tick();
    }

    waka.queue(Player.UP);
    for (int i = 0; i < 16 * 5; i++) {
      game.tick();
    }

    waka.queue(Player.DOWN);

    assertEquals(waka.getSpawn().getRow() - 5, chaser.getRow());
    assertEquals(waka.getSpawn().getCol(), chaser.getCol());

    assertEquals("frightened", chaser.getCurrentModeName());

    for (int i = 0; i < 60; i++) {
      game.tick();
    }
    
    assertEquals("scatter", chaser.getCurrentModeName());
  }

  /* Testing invis behaviour. */
  @Test
  public void invisTest() {
    App app = new App() {
      public void exit() {
        ;
      }
    };
    GameManager game = new GameManager(app, "basic.json");
    PApplet.runSketch(new String[]{"App"}, app);
    game.setup();
    app.settings();
    
    Chaser chaser = (Chaser) game.getGhosts().get(0);
    Waka waka = game.getWaka();
    assertEquals("scatter", chaser.getCurrentModeName());

    /* We move down, right and then up on the map so that we can get to the sodacan debuff. */
    waka.queue(Player.DOWN);
    for (int i = 0; i < 16 * 5; i++) {
      game.tick();
    }

    waka.queue(Player.RIGHT);
    for (int i = 0; i < 16 * 10; i++) {
      game.tick();
    }

    waka.queue(Player.UP);
    for (int i = 0; i < 16 * 10; i++) {
      game.tick();
    }

    /* Once the fruit has been eaten, it should be in invis mode. */
    assertEquals("invisible", chaser.getCurrentModeName());
    
    /* We test for what happens after invis runs out. */
    for (int i = 0; i < 60; i++) {
      game.tick();
    }

    assertEquals("chase", chaser.getCurrentModeName());
    app.dispose();
  }
}
