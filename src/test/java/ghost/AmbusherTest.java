package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import processing.core.PApplet;

import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;

public class AmbusherTest {

  @BeforeEach
  public void setup() {
    PrintWriter writer = null;
    try {
      writer = new PrintWriter("basic.json");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    writer.println("{");
    writer.println("    \"map\": \"simple_ambusher.txt\",");
    writer.println("    \"lives\": 3,");
    writer.println("    \"speed\": 1,");
    writer.println("    \"frightenedLength\": 1,");
    writer.println("    \"invisLength\": 1,");
    writer.println("    \"modeLengths\": [");
    writer.println("        3,\n        5,\n        5,\n        20\n    ]\n}");
    writer.flush();
    writer.close();

    try {
      writer = new PrintWriter("simple_ambusher.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    writer.print("0000000000000000000000000000\n0000000000000000000000000000\n" + 
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0061111111150000000000000000\n" +
            "0028777777720000000000000000\n0027611115720000000000000000\n" +
            "0027200002720000000000000000\n0027200613741111111500000000\n" +
            "0027200277777777777200000000\n0027200276115761157200000000\n" +
            "0027200272002720027200000000\n0027200272002720027200000000\n" +
            "0027411374113741137200000000\n002s777777777p77777200000000\n" +
            "0041111576115761157200000000\n0000000272002720027200000000\n" +
            "0000000272002720027200000000\n0611111374113741137200000000\n" +
            "027777777777777777a200000000\n0411111111111111111300000000\n" +
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
    File simpleMovement = new File("simple_ambusher.txt");
    simpleMovement.delete();
  }

  /* Testing constructor. */
  @Test
  public void constructorTest() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");

    for (Spawn s : game.getMapParser().getGhostSpawn()) {
      assertNotNull(new Ambusher(s, game.getConfig()));
    }
  }

  /* Testing updating Ambusher's target location */
  @Test
  public void updateTargetTest() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");
    Ambusher ambusher = (Ambusher) game.getGhosts().get(0);
    Waka waka = game.getWaka();
    assertEquals("scatter", ambusher.getCurrentModeName());


    for (int i = 0; i < 16 * 5; i++) {
      game.tick();
    }

    /* Ambusher should move 5 units above its starting point, since it approaches its corner. */
    assertEquals(waka.getSpawn().getRow(), ambusher.getCurrentCell().getRow());
    assertEquals(waka.getSpawn().getCol() + 5, ambusher.getCurrentCell().getCol());

    ambusher.updateTargetLocation(waka);

    assertEquals(App.WIDTH, ambusher.getXTarget());
    assertEquals(0, ambusher.getYTarget());

    /* The waka moves upwards. */
    waka.queue(Player.UP);

    /* Moving game past 3 seconds - the time it should switch to chase mode. */
    for (int i = 0; i < 100; i++) {
      game.tick();
    }

    ambusher.updateTargetLocation(waka);

    /* Should be targeting 4 units above waka. */
    assertEquals(waka.getX(), ambusher.getXTarget());
    assertEquals(waka.getY() - 16 * 4, ambusher.getYTarget());

    /* We test what happens when we move in other cardinal directions. */

    /* First, we test what happens when Waka moves right for a brief period of time. */
    waka.queue(Player.RIGHT);
    for (int i = 0; i < 8; i++) {
      game.tick();
    }
    /* Should be targeting 4 units to the right of waka. */
    assertEquals(waka.getX() + 16 * 4, ambusher.getXTarget());
    assertEquals(waka.getY(), ambusher.getYTarget());

    /* Then, we test what happens when Waka moves left for a brief period of time. */
    waka.queue(Player.LEFT);
    for (int i = 0; i < 8; i++) {
      game.tick();
    }
    /* Should be targeting 4 units to the left of waka. */
    assertEquals(waka.getX() - 16 * 4, ambusher.getXTarget());
    assertEquals(waka.getY(), ambusher.getYTarget());

    /* Then, we test what happens when Waka moves down for a brief period of time. */
    waka.queue(Player.DOWN);
    for (int i = 0; i < 8; i++) {
      game.tick();
    }

    /* Should be targeting 4 units down of waka. */
    assertEquals(waka.getX(), ambusher.getXTarget());
    assertEquals(waka.getY() + 16 * 4, ambusher.getYTarget());

    /* Additional test for invalid keys */
    waka.move(32);
    for (int i = 0; i < 8; i++) {
      game.tick();
    }

    /* Should still be targeting 4 units below waka. */
    assertEquals(waka.getX(), ambusher.getXTarget());
    assertEquals(waka.getY() + 16 * 4, ambusher.getYTarget());
  }

  /* Testing an edge case to see if Ambusher actually targets 4 spaces ahead and makes moves
   * at intersections to get there rather than 'appear' to target four spaces ahead, but in fact
   * move such that it is targeting the Waka. */
  @Test
  public void movementTest() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");
    Ambusher ambusher = (Ambusher) game.getGhosts().get(0);
    Waka waka = game.getWaka();
    
    /* We get the waka to move to a special position in the map by moving it 5 units to the left
     * and five units up. */
    for (int i = 0; i < 16 * 5; i++) {
      game.tick();
    }

    waka.queue(Player.UP);
    for (int i = 0; i < 16 * 5; i++) {
      game.tick();
    }

    /* We know (from previous tests) that the Ambusher will have moved 10 units above
     * its spawn location. So, we move it 10 units to the left, and see if it collides with
     * the Waka, rather than moving up and towards its target.*/
    for (int i = 0; i < 16 * 10; i++) {
      game.tick();
      /* After 3 seconds, it should change mode. We check sometime after 3 seconds.*/
      if (i == 19) {
        assertEquals("chase", ambusher.getCurrentModeName());
      }
    }

    /* The ambusher should head upwards along a path that is 2 units away from the waka, since
     * it should target the space that is above 4 spaces above waka. */
    assertEquals(waka.getCol() + 2, ambusher.getCurrentCell().getCol());
    assertEquals(waka.getRow() - 2, ambusher.getCurrentCell().getRow());
  }

  /* Testing if Ambusher switches modes at the correct time. */
  @Test
  public void modeSwitch() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");
    Ambusher ambusher = (Ambusher) game.getGhosts().get(0);
    Waka waka = game.getWaka();

    for (int i = 0; i < 16 * 5; i++) {
      game.tick();
    }
    waka.queue(Player.UP);
    
    for (int i = 0; i < 16 * 5; i++) {
      game.tick();
    }


    for (int i = 0; i < 20; i++) {
      game.tick();
    }

    assertEquals("chase", ambusher.getCurrentModeName());
    for (int i = 0; i < 5 * 60; i++) {
      game.tick();
    }
    assertEquals("scatter", ambusher.getCurrentModeName());
  }

  /* Testing frightened and invis behaviours. */
  @Test
  public void frightenedInvisTest() {
    App app = new App() {
      public void exit() {
        ;
      }
    };
    GameManager game = new GameManager(app, "basic.json");
    PApplet.runSketch(new String[]{"App"}, app);
    game.setup();
    app.settings();
    Ambusher ambusher = (Ambusher) game.getGhosts().get(0);
    Waka waka = game.getWaka();

    /* We first check what happens when the Waka eats the sodacan. */
    assertEquals("scatter", ambusher.getCurrentModeName());
    for (int i = 0; i < 16 * 10; i++) {
      game.tick();
    }

    assertEquals("invisible", ambusher.getCurrentModeName());

    waka.queue(Player.UP);
    for (int i = 0; i < 10 * 16; i++) {
      game.tick();

      /* After a second, mode should revert back to scatter. */
      if (i == 60) {
        assertEquals("scatter", ambusher.getCurrentModeName());
      }

      /* Twenty frames after revert to scatter mode, it should change to chase. */
      if (i == 80) {
        assertEquals("chase", ambusher.getCurrentModeName());
      }
    }
    
    assertEquals("frightened", ambusher.getCurrentModeName());
    
    /* After a second, mode should revert back to chase. */
    for (int i = 0; i < 60; i++) {
      game.tick();
    }

    assertEquals("chase", ambusher.getCurrentModeName());
    app.dispose();
  }

  /* Testing if target location is bounded by the application boxes' dimensions. */
  @Test
  public void boundTest() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");
    Ambusher ambusher = (Ambusher) game.getGhosts().get(0);
    Waka waka = game.getWaka();

    /* We move 5 units to the left, 5 units down, and then to the left of the 1-way corridor. */
    for (int i = 0; i < 5 * 16; i++) {
      game.tick();
    }

    waka.queue(Player.DOWN);
    for (int i = 0; i < 5 * 16; i++) {
      game.tick();
    }

    waka.queue(Player.LEFT);
    for (int i = 0; i < 6 * 16; i++) {
      game.tick();
    }

    assertEquals("chase", ambusher.getCurrentModeName());

    assertEquals(2, waka.getCol());

    /* Column displacement should be 0 (as opposed to -2), since waka is facing left and 
     * is 2 units away from the edge of the application box. */
    assertEquals(0, ambusher.getTargetCol());
    assertEquals(waka.getRow(), ambusher.getTargetRow());
  }
}
