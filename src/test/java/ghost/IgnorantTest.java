package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import processing.core.PApplet;

import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;


public class IgnorantTest {

  @BeforeEach
  public void setup() {
    PrintWriter writer = null;
    try {
      writer = new PrintWriter("basic.json");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    writer.println("{");
    writer.println("    \"map\": \"simple_ignorant.txt\",");
    writer.println("    \"lives\": 3,");
    writer.println("    \"speed\": 1,");
    writer.println("    \"frightenedLength\": 1,");
    writer.println("    \"invisLength\": 20,");
    writer.println("    \"modeLengths\": [");
    writer.println("        3,\n        5,\n        5,\n        20\n    ]\n}");
    writer.flush();
    writer.close();

    try {
      writer = new PrintWriter("simple_ignorant.txt");
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
            "0000000272002720027200000000\n0000000572002720027200000000\n" +
            "6111111374113741137200000000\n2777777777777p7777i200000000\n" +
            "4111111576115761157200000000\n0000000272002720027200000000\n" +
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
    File simpleMovement = new File("simple_ignorant.txt");
    simpleMovement.delete();
  }


  /* Testing constructor. */
  @Test
  public void constructorTest() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");

    for (Spawn s : game.getMapParser().getGhostSpawn()) {
      assertNotNull(new Ignorant(s, game.getConfig()));
    }
  }

  /* Testing updating Ignorant's target location */
  @Test
  public void updateTargetTest() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");
    Ignorant ignorant = (Ignorant) game.getGhosts().get(0);
    Waka waka = game.getWaka();
    assertEquals("scatter", ignorant.getCurrentModeName());

    for (int i = 0; i < 16 * 5; i++) {
      game.tick();
    }
    assertEquals(waka.getSpawn(), ignorant.getCurrentCell());

    ignorant.updateTargetLocation(waka);

    assertEquals(0, ignorant.getXTarget());
    assertEquals(App.HEIGHT, ignorant.getYTarget());

    /* Ignorant should move down towards its corner, since its still in scatter mode. */
    for (int i = 0; i < 16*5; i++) {
      game.tick();
    }

    ignorant.updateTargetLocation(waka);

    /* Ignorant should have moved left and then down to get to its target corner. */
    assertEquals(0, ignorant.getXTarget());
    assertEquals(App.HEIGHT, ignorant.getYTarget());
    assertEquals(waka.getSpawn().getRow() + 5, ignorant.getRow());
    assertEquals(waka.getSpawn().getCol(), ignorant.getCol());

    /* Now, after 20 frames, 3 seconds would have passed. */
    for (int i = 0; i < 20; i++) {
      game.tick();
    }
    
    /* One tick after 3 seconds would cause the Ignorant to change to 'chase' mode. */
    game.tick();

    ignorant.updateTargetLocation(waka);

    /* Since ignorant will be more than 8 units away from the Waka, it will target the Waka. */
    assertEquals(waka.getX(), ignorant.getXTarget());
    assertEquals(waka.getY(), ignorant.getYTarget());
    assertTrue(Math.hypot(waka.getX() - ignorant.getX(), waka.getY() - ignorant.getY()) > 8 * 16);

    /* In 5 units, the Ignorant must be less than 8 units away, and should target its corner. */
    for (int i = 0; i < 16 * 6; i++) {
      game.tick();
    }

    assertTrue(Math.hypot(waka.getX() - ignorant.getX(), waka.getY() - ignorant.getY()) < 8 * 16);

    /* Note: offsets applied here are intended, see Ignorant's chase method. These are not
     * used for anything other than drawing debug lines correctly when running the game, 
     * but can be used for testing purposes. */
    assertEquals(-22, ignorant.getXTarget());
    assertEquals(App.HEIGHT - 22, ignorant.getYTarget());
  }

  /* Testing frightened behaviour. */
  @Test
  public void frightenedTest() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");
    Ignorant ignorant = (Ignorant) game.getGhosts().get(0);
    Waka waka = game.getWaka();

    assertEquals("scatter", ignorant.getCurrentModeName());
    for (int i = 0; i < 16 * 5; i++) {
      game.tick();
    }

    waka.queue(Player.UP);
    for (int i = 0; i < 16 * 5; i++) {
      game.tick();
    }

    assertEquals(waka.getSpawn().getRow() + 5, ignorant.getRow());
    assertEquals(waka.getSpawn().getCol(), ignorant.getCol());

    game.tick();

    assertEquals("frightened", ignorant.getCurrentModeName());

    for (int i = 0; i < 60; i++) {
      game.tick();
    }
    
    assertEquals("scatter", ignorant.getCurrentModeName());
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
    
    Ignorant ignorant = (Ignorant) game.getGhosts().get(0);
    Waka waka = game.getWaka();
    assertEquals("scatter", ignorant.getCurrentModeName());

    /* We move left, up and right on the map so that we can get to the sodacan debuff. */
    for (int i = 0; i < 16 * 5; i++) {
      game.tick();
    }

    waka.queue(Player.UP);
    for (int i = 0; i < 16 * 5; i++) {
      game.tick();
    }

    waka.queue(Player.RIGHT);
    for (int i = 0; i < 16 * 10; i++) {
      game.tick();
    }

    /* At this point, the sodacan is eaten, and the mode should change accordingly. */
    assertEquals("invisible", ignorant.getCurrentModeName());
    app.dispose();
  }
}
