package ghost;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

public class GameManagerTest {  

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

    try {
      writer = new PrintWriter("map.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    writer.print("0000000000000000000000000000\n0000000000000000000000000000\n" + 
              "0000000000000000000000000000\n6111111111111561111111111115\n" +
              "2877777777777227777777777782\n2761157611157227611157611572\n" +
              "2720027200027227200027200272\n2741137411137437411137411372\n" +
              "2777777777777777777777777772\n2761157657611111157657611572\n" +
              "2741137227411561137227411372\n2777777227777227777227777772\n" +
              "2761157241157227611327611572\n2720027261137437411527200272\n" +
              "2720027227a7c7i7w77227200272\n2720027227611111157227200272\n" +
              "2741137437200000027437411372\n2777777s772000000277s7777772\n" +
              "4111157657200000027657611113\n0000027227411111137227200000\n" +
              "0000027227777p77777227200000\n0000027227611111157227200000\n" +
              "6111137437411561137437411115\n2777777777777227777777777772\n" +
              "2761157611157227611157611572\n2741527411137437411137261372\n" +
              "2777227777777777777777227772\n4157227657611111157657227613\n" +
              "6137437227411561137227437415\n2777777227777227777227777772\n" +
              "2761111341157227611341111572\n2741111111137437411111111372\n" +
              "2877777777777777777777777782\n4111111111111111111111111113\n" +
              "0000000000000000000000000000\n0000000000000000000000000000\n");
    writer.close();

    try {
      writer = new PrintWriter("easyloss.json");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    writer.println("{");
    writer.println("    \"map\": \"easyloss.txt\",");
    writer.println("    \"lives\": 3,");
    writer.println("    \"speed\": 1,");
    writer.println("    \"frightenedLength\": 10,");
    writer.println("    \"invisLength\": 1,");
    writer.println("    \"modeLengths\": [");
    writer.println("        1,\n        1,\n        5,\n        20\n    ]\n}");
    writer.flush();
    writer.close();

    
    try {
      writer = new PrintWriter("easyloss.txt");
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
      writer = new PrintWriter("easywin.json");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
    writer.println("{");
    writer.println("    \"map\": \"easywin.txt\",");
    writer.println("    \"lives\": 3,");
    writer.println("    \"speed\": 1,");
    writer.println("    \"frightenedLength\": 10,");
    writer.println("    \"invisLength\": 1,");
    writer.println("    \"modeLengths\": [");
    writer.println("        1,\n        1,\n        5,\n        20\n    ]\n}");
    writer.flush();
    writer.close();

    
    try {
      writer = new PrintWriter("easywin.txt");
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
            "6111111111111111111111111115\n27777777777777777777777777p2\n" +
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
  }

  @AfterEach
  public void takedown() {
    File basic = new File("basic.json");
    basic.delete();

    File easyWin = new File("easywin.json");
    easyWin.delete();
    File easyWinMap = new File("easywin.txt");
    easyWinMap.delete();

    File easyLoss= new File("easyloss.json");
    easyLoss.delete();
    File easyLossMap = new File("easyloss.txt");
    easyLossMap.delete();
  }

  /* Testing constructor. */
  @Test
  public void constructorTest() {
    App app = new App();
    assertNotNull(new GameManager(app, "basic.json"));
  }

  
  @Test
  public void getterTest() {
    App app = new App();
    GameManager g = new GameManager(app, "basic.json");
    assertNotNull(g.getCells());
    assertTrue(g.getFruitCount() == g.getMapParser().getFruitCount());
    List<Ghost> ghosts = new ArrayList<Ghost>();
    for (Spawn s : g.getMapParser().getGhostSpawn()) {
      if (s.getID() == 'c') {
        ghosts.add(new Chaser(s, g.getConfig()));
      } else if (s.getID() == 'a') {
        ghosts.add(new Ambusher(s, g.getConfig()));
      } else if (s.getID() == 'i') {
        ghosts.add(new Ignorant(s, g.getConfig()));
      } else if (s.getID() == 'w') {
        ghosts.add(new Whim(s, g.getConfig()));
      }
    }
    for (int i = 0; i < ghosts.size(); i++) {
      assertEquals(ghosts.get(i).getSpawn(), g.getGhosts().get(i).getSpawn());
    }
    assertEquals(g.getWaka().getSpawn(), g.getMapParser().getWakaSpawn());
  }

  /* Testing what happens when Waka eats all fruits. */
  @Test
  public void eatAllFruitsTest() {
    App app = new App() {
      public void exit() {
        ;
      }
    };
    GameManager game = new GameManager(app, "easywin.json");
    PApplet.runSketch(new String[]{"App"}, app);
    game.setup();
    app.settings();
    Waka waka = game.getWaka();

    /* Since there is no ghost, we can move to the right and collect all fruits. */
    for (int i = 0; i < 25 * 16; i++) {
      game.tick();
    }

    assertTrue(game.isGameWon());
    
    /* We wait for 10 seconds. */
    for (int i = 0; i < 10 * 60; i++) {
      game.tick();
    }

    assertFalse(game.isGameWon());
    assertEquals(waka.getSpawn(), waka.getCurrentCell());
    app.dispose();
  }

  /* Testing key manager. */ 
  @Test
  public void keyManagerTest() {
    App app = new App() {
      public void exit() {
        ;
      }
    };
    GameManager game = new GameManager(app, "easyloss.json");
    PApplet.runSketch(new String[]{"App"}, app);
    game.setup();
    app.settings();

    Chaser chaser = (Chaser) game.getGhosts().get(0);
    Waka waka = game.getWaka();

    assertEquals(Player.LEFT, waka.getCurrentMove());
    assertFalse(chaser.isDebug());

    for (int i = 0; i < 16; i++) {
      game.tick();
    }

    app.key = ' ';
    app.keyCode = 32;
    game.keyManager(app.keyCode, app.key);
    assertTrue(chaser.isDebug());

    app.key = App.CODED;
    app.keyCode = 39;
    game.keyManager(app.keyCode, app.key);

    game.tick();

    assertEquals(Player.RIGHT, waka.getCurrentMove());

    
    app.key = 'A';
    app.keyCode = 65;
    game.keyManager(app.keyCode, app.key);
    assertTrue(chaser.isDebug());
    app.dispose();
  }

  /* Testing game lost. */
  @Test
  public void gameLostTest() {
    App app = new App() {
      public void exit() {
        ;
      }
    };
    GameManager game = new GameManager(app, "easyloss.json");
    PApplet.runSketch(new String[]{"App"}, app);
    game.setup();
    app.settings();
    Waka waka = game.getWaka();

    /* Since there is a ghost, we wait the required time for the ghost to
     * collide with the waka and lose a life. */
    for (int i = 0; i < 12 * 16 * 3; i++) {
      if (i == 12 * 16) {
        assertEquals(2, waka.getCurrentLives());
      } else if (i == 12 * 16 * 2) {
        assertEquals(1, waka.getCurrentLives());
      } else if (i == 12 * 16 * 3) {
        assertEquals(0, waka.getCurrentLives());
      } 
      game.tick();
    }
    assertTrue(game.isGameOver());
    
    /* We wait for 10 seconds. */
    for (int i = 0; i < 10 * 60; i++) {
      game.tick();
    }

    assertFalse(game.isGameOver());
    app.dispose();
  }
}