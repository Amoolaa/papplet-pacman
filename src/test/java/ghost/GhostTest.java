package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import processing.core.PApplet;

import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class GhostTest {

  @BeforeEach
  public void setup() {
    PrintWriter writer = null;
    try {
      writer = new PrintWriter("loop.json");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    writer.println("{");
    writer.println("    \"map\": \"loop.txt\",");
    writer.println("    \"lives\": 3,");
    writer.println("    \"speed\": 1,");
    writer.println("    \"frightenedLength\": 5,");
    writer.println("    \"invisLength\": 1,");
    writer.println("    \"modeLengths\": [");
    writer.println("        4,\n        4,\n        4,\n     ]\n}");
    writer.flush();
    writer.close();


    try {
      writer = new PrintWriter("waka_on_ghost.json");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    writer.println("{");
    writer.println("    \"map\": \"waka_on_ghost.txt\",");
    writer.println("    \"lives\": 3,");
    writer.println("    \"speed\": 1,");
    writer.println("    \"frightenedLength\": 10,");
    writer.println("    \"invisLength\": 1,");
    writer.println("    \"modeLengths\": [");
    writer.println("        1,\n        1,\n        5,\n        20\n    ]\n}");
    writer.flush();
    writer.close();

    try {
      writer = new PrintWriter("dead_end.json");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    writer.println("{");
    writer.println("    \"map\": \"dead_end.txt\",");
    writer.println("    \"lives\": 3,");
    writer.println("    \"speed\": 1,");
    writer.println("    \"frightenedLength\": 5,");
    writer.println("    \"invisLength\": 1,");
    writer.println("    \"modeLengths\": [");
    writer.println("        4,\n        4,\n        5,\n        20\n    ]\n}");
    writer.flush();
    writer.close();


    try {
      writer = new PrintWriter("loop.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    writer.print("0000000000000000000000000000\n0000000000000000000000000000\n" + 
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000061500000000000000000\n" +
            "000000002p200000000000000000\n0000000041300000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "6111150000000000000000000000\n2777720000000000000000000000\n" +
            "2711720000000000000000000000\n2c77720000000000000000000000\n" +
            "4111130000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n");
    writer.close();


    try {
      writer = new PrintWriter("waka_on_ghost.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    writer.print("0000000000000000000000000000\n0000000000000000000000000000\n" + 
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "6111111111111111111111111115\n2c77777777777777777777777p82\n" +
            "4111111111111111111111111113\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n");
    writer.close();

    try {
      writer = new PrintWriter("dead_end.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    writer.print("0000000000000000000000000000\n0000000000000000000000000000\n" + 
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000006150000000000000000\n0000000002720000000000000000\n" +
            "0000000613741500000000000000\n0000000277c77200000000000000\n" +
            "0000000415761300000000000000\n0000000002720000000000000000\n" +
            "0000000004130000006111500000\n000000000000000000277p200000\n" +
            "0000000000000000004111300000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n");
    writer.close();
  }

  @AfterEach
  public void takedown() {
    File win = new File("loop.json");
    win.delete();
    File winMap = new File("loop.txt");
    winMap.delete();

    File wakaOnGhost = new File("waka_on_ghost.json");
    wakaOnGhost.delete();
    File wakaOnGhostMap = new File("waka_on_ghost.txt");
    wakaOnGhostMap.delete();

    File deadEnd = new File("dead_end.json");
    deadEnd.delete();
    File deadEndMap = new File("dead_end.txt");
    deadEndMap.delete();
  }

  /* Testing basic getters and setters. */ 
  @Test
  public void gettersAndSettersTest() {
    App app = new App();
    GameManager game = new GameManager(app, "waka_on_ghost.json");
    Chaser chaser = (Chaser) game.getGhosts().get(0);

    /* Giving game one tick to initialise. */
    game.tick();  

    assertEquals(0, chaser.getTargetRow());
    chaser.setTargetRow(2);
    assertEquals(2, chaser.getTargetRow());

    assertEquals(0, chaser.getTargetCol());
    chaser.setTargetCol(10);
    assertEquals(10, chaser.getTargetCol());

    assertEquals(0, chaser.getXTarget());
    assertEquals(0, chaser.getYTarget());

    assertEquals(true, chaser.isAlive());
    chaser.setAlive(false);
    assertEquals(false, chaser.isAlive());

    chaser.toggleDebug();
    assertEquals(true, chaser.isDebug());

    chaser.toggleDebug();
    assertEquals(false, chaser.isDebug());

    chaser.setRowDisplacement(2);
    assertEquals(2, chaser.getRowDisplacement());

    chaser.setColDisplacement(10);
    assertEquals(10, chaser.getColDisplacement());

    chaser.move(30);
    assertEquals(Player.RIGHT, chaser.getCurrentMove());
  }

  /* Testing reset functionality. */
  @Test
  public void resetTest() {
    App app = new App();
    GameManager game = new GameManager(app, "waka_on_ghost.json");
    PApplet.runSketch(new String[]{"App"}, app);
    game.setup();

    app.settings();
    
    Chaser chaser = (Chaser) game.getGhosts().get(0);

    for (int i = 0; i < 16 * 5; i++) {
      game.tick();
    }

    assertEquals(6, chaser.getCol());
    chaser.setAlive(false);

    chaser.reset();

    assertEquals(Player.LEFT, chaser.getCurrentMove());
    assertEquals(chaser.getSpawn(), chaser.getCurrentCell());
    assertEquals(9, chaser.getXOffset());
    assertEquals(9, chaser.getYOffset());
    assertEquals(1, chaser.getCol());
    assertEquals(17, chaser.getRow());
    assertTrue(chaser.isAlive());
    assertEquals("scatter", chaser.getCurrentModeName());
    List<Integer> temp = new ArrayList<Integer>();
    temp.add(1);
    temp.add(1);
    temp.add(5);
    temp.add(20);

    assertEquals(temp, chaser.getModeLengths());
  }

  /* Testing collision with waka. */
  @Test
  public void wakaOnGhostTest() {
    App app = new App();
    GameManager game = new GameManager(app, "waka_on_ghost.json");
    PApplet.runSketch(new String[]{"App"}, app);
    game.setup();
    app.settings();
    Chaser chaser = (Chaser) game.getGhosts().get(0);
    Waka waka = game.getWaka();

    /* After 12 units worth of movement, Ghost and Waka should collide and reset. */
    for (int i = 0; i < 12*16; i++) {
      game.tick();
    }

    assertEquals(chaser.getCurrentCell(), chaser.getSpawn());
    assertEquals(waka.getCurrentCell(), waka.getSpawn());
    assertEquals(2, waka.getCurrentLives());
  }


  /* Testing dead-end behaviours. */
  @Test
  public void deadEndTest() {
    App app = new App();
    GameManager game = new GameManager(app, "dead_end.json");
    Chaser chaser = (Chaser) game.getGhosts().get(0);

    /* 4 seconds worth of frames before mode change. */
    for (int i = 0; i < 16 * 15; i++) {
      
      /* up dead end */
      if (i == 16 * 2) {
        assertEquals(10, chaser.getCol());
        assertEquals(21, chaser.getRow());
        assertEquals(Player.UP, chaser.getCurrentMove());
      }
      
      /* One frame after, should change direction. */
      if (i == 16 * 2 + 1) {
        assertEquals(Player.DOWN, chaser.getCurrentMove());
      }

      /* left dead end */
      if (i == 16 * 6) {
        assertEquals(8, chaser.getCol());
        assertEquals(23, chaser.getRow());
        assertEquals(Player.LEFT, chaser.getCurrentMove());
      }

      /* One frame after, should change direction. */
      if (i == 16 * 6 + 1) {
        assertEquals(Player.RIGHT, chaser.getCurrentMove());
      }
      game.tick();
    }

    /* Now, the mode has changed to scatter mode, and we can test other directions. */
    assertEquals("chase", chaser.getCurrentModeName());
    assertEquals(9, chaser.getCol());
    assertEquals(23, chaser.getRow());

    for (int i = 0; i < 16 * 15; i++) {
      /* Right dead end */
      if (i == 16 * 3) {
        assertEquals(12, chaser.getCol());
        assertEquals(23, chaser.getRow());
        assertEquals(Player.RIGHT, chaser.getCurrentMove());
      }

      if (i == 16 * 3 + 1) {
        assertEquals(Player.LEFT, chaser.getCurrentMove());
      }

      /* Down dead end */
      if (i == 16 * 7) {
        assertEquals(10, chaser.getCol());
        assertEquals(25, chaser.getRow());
        assertEquals(Player.DOWN, chaser.getCurrentMove());
      }

      if (i == 16 * 7 + 1) {
        assertEquals(Player.UP, chaser.getCurrentMove());
      }

      game.tick();
    }
  }

  /* Testing frightened behaviour when Waka collides with the ghost. */
  @Test
  public void frightenedWakaOnGhost() {
    App app = new App() {
      public void exit() {
        ;
      }
    };
    GameManager game = new GameManager(app, "waka_on_ghost.json");
    PApplet.runSketch(new String[]{"App"}, app);
    game.setup();
    app.settings();
    Chaser chaser = (Chaser) game.getGhosts().get(0);
    Waka waka = game.getWaka();

    /* We move left to collect the superfruit. */
    waka.queue(Player.RIGHT);
    
    for (int i = 0; i < 16 * 25; i++) {
      game.tick();
      /* After one units worth of movement, mode should change to frightened. */
      if (i == 16) {
        assertEquals("frightened", chaser.getCurrentModeName());
      }
    }

    /* One more tick to allow attributes to reset. */

    assertFalse(chaser.isAlive());
    app.dispose();
  }


  /* Testing when there is only one path available to the Ghost. */
  @Test
  public void onePath() {
    App app = new App();
    GameManager game = new GameManager(app, "loop.json");
    Chaser chaser = (Chaser) game.getGhosts().get(0);
    Waka waka = game.getWaka();

    /* The waka is trapped, and we can simply observe the chaser go around in a loop. */

    /* After 3 units worth of movement, chaser should have moved up and to the left. */
    for (int i = 0; i < 3 * 16; i++) {
      game.tick();
    }

    assertEquals(2, chaser.getCol());
    assertEquals(19, chaser.getRow());
    assertEquals(Player.RIGHT, chaser.getCurrentMove());

    /* After 3 units worth of movement, chaser should have moved right and downwards. */
    for (int i = 0; i < 3 * 16; i++) {
      game.tick();
    }

    assertEquals(4, chaser.getCol());
    assertEquals(20, chaser.getRow());
    assertEquals(Player.DOWN, chaser.getCurrentMove());

    /* After 3 units worth of movement, chaser should have moved down and to the left. */
    for (int i = 0; i < 3 * 16; i++) {
      game.tick();
    }

    assertEquals(2, chaser.getCol());
    assertEquals(21, chaser.getRow());
    assertEquals(Player.LEFT, chaser.getCurrentMove());

    /* After 3 units worth of movement, chaser should have moved left and upwards. */
    for (int i = 0; i < 3 * 16; i++) {
      game.tick();
    }

    assertEquals(1, chaser.getCol());
    assertEquals(19, chaser.getRow());
    assertEquals(Player.UP, chaser.getCurrentMove());


    /* We do the same thing, but now in the opposite direction. */
    chaser.move(Player.DOWN);

    for (int i = 0; i < 3 * 16; i++) {
      game.tick();
    }

    assertEquals(2, chaser.getCol());
    assertEquals(21, chaser.getRow());
    assertEquals(Player.RIGHT, chaser.getCurrentMove());

    for (int i = 0; i < 3 * 16; i++) {
      game.tick();
    }

    assertEquals(4, chaser.getCol());
    assertEquals(20, chaser.getRow());
    assertEquals(Player.UP, chaser.getCurrentMove());

    for (int i = 0; i < 3 * 16; i++) {
      game.tick();
    }
    
    assertEquals(2, chaser.getCol());
    assertEquals(19, chaser.getRow());
    assertEquals(Player.LEFT, chaser.getCurrentMove());
  }


  /* Testing what happens when we reach the end of modeLengths. It should return to scatter. */
  @Test
  public void modeLengthsTest() {
    App app = new App();
    GameManager game = new GameManager(app, "loop.json");
    Chaser chaser = (Chaser) game.getGhosts().get(0);
    Waka waka = game.getWaka();


    /* Simulating 12 seconds worth of the game */
    for (int i = 0; i < 60 * 12; i++) {
      game.tick();
    }

    /* We need one more tick to change the mode */
    game.tick();

    assertEquals("scatter", chaser.getCurrentModeName());
  }
}
