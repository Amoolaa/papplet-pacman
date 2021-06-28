package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import processing.core.PApplet;

import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;


public class WakaTest {

  @BeforeEach
  public void setup() {
    PrintWriter writer = null;
    try {
      writer = new PrintWriter("basic.json");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    writer.println("{");
    writer.println("    \"map\": \"simplemovement.txt\",");
    writer.println("    \"lives\": 3,");
    writer.println("    \"speed\": 1,");
    writer.println("    \"frightenedLength\": 10,");
    writer.println("    \"invisLength\": 20,");
    writer.println("    \"modeLengths\": [");
    writer.println("        5,\n        10,\n        15,\n        20\n    ]\n}");
    writer.flush();
    writer.close();

    try {
      writer = new PrintWriter("simplemovement.txt");
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
            "0000000274113741137200000000\n0000000277777p77777200000000\n" +
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
    File simpleMap = new File("simplemovement.txt");
    simpleMap.delete();
  }
  
  /* Testing constructor. */
  @Test
  public void constructorTest() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");
    assertNotNull(new Waka(game.getMapParser().getWakaSpawn(), game.getConfig()));
  }

  /* Testing basic movement of Waka at the start of the game. */
  @Test
  public void wakaMovementTest() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");

    Waka waka = game.getWaka();

    /* We check before and after the movement. */
    assertEquals(game.getMapParser().getWakaSpawn(), waka.getCurrentCell());
    assertEquals(Player.LEFT, waka.getCurrentMove());
    
    /* We call tick for 16 frames, the time it should take for the Waka
     * to move exactly one unit's worth of speed (specified in config). */
    for (int i = 0; i < 16; i++) {
      game.tick();
    }
    
    assertEquals(waka.getSpawn().getCol() - game.getConfig().getSpeed(), waka.getCurrentCell().getCol());
    assertEquals(9, waka.getXOffset());
    assertEquals(9, waka.getYOffset());
    assertEquals(Player.LEFT, waka.getCurrentMove());
  }

  /* Testing user input for the Waka's movement. */
  @Test
  public void wakaInputTest() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");
    
    Waka waka = game.getWaka();

    /* We let the Waka move in the default position for 16 frames,
     * and then check the response to user input in the opposite direction. */
    for (int i = 0; i < 16; i++) {
      game.tick();
    }

    int previousCol = waka.getCol();
    int previousRow = waka.getRow();

    /* We queue a move in the opposite direction, and wait 16 frames */
    waka.queue(Player.RIGHT);
    for (int i = 0; i < 16; i++) {
      game.tick();
    }

    /* Then, we check if the Waka has moved in that particular direction. */
    assertEquals(Player.RIGHT, waka.getCurrentMove());
    assertEquals(previousCol + game.getConfig().getSpeed(), waka.getCurrentCell().getCol());
    assertEquals(previousRow, waka.getCurrentCell().getRow());
    assertEquals(9, waka.getXOffset());
    assertEquals(9, waka.getYOffset());


    /* We check what happens if we input a non-arrow key. */
    waka.queue(32);
    assertEquals(Player.RIGHT, waka.getCurrentMove());
    for (int i = 0; i < 16; i++) {
      game.tick();
    }
    assertEquals(previousCol + 2 * game.getConfig().getSpeed(), waka.getCurrentCell().getCol());
  }

  /* Testing collision if no valid movement key is pressed and the Waka runs
   * into an oncoming wall. */
  @Test
  public void oncomingWallTest() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");
    Waka waka = game.getWaka();

    /* We let the Waka move in the default position for 16 * 5 frames,
     * which is the number required to hit the oncoming wall. */
    for (int i = 0; i < 16 * 5; i++) {
      game.tick();
    }

    /* Waka should be at a solid wall, so next cell to the left should be solid. */
    assertTrue(game.getCells()[waka.getRow()][waka.getCol() - 1].isSolid());
    assertFalse(waka.getCurrentCell().isSolid());
    assertEquals(9, waka.getXOffset());
    assertEquals(9, waka.getYOffset());

    int previousCol = waka.getCol();
    int previousRow = waka.getRow();

    /* We then check the behaviour of the Waka's movement after another
     * units' worth of movement (16 frames). It should be stationary. */
    assertTrue(game.getCells()[waka.getRow()][waka.getCol() - 1].isSolid());
    assertFalse(waka.getCurrentCell().isSolid());
    assertEquals(9, waka.getXOffset());
    assertEquals(9, waka.getYOffset());
    assertEquals(previousRow, waka.getCurrentCell().getRow());
    assertEquals(previousCol, waka.getCurrentCell().getCol());

    /* We check if a user-queued move towards the wall undesirably causes the Waka to move. */
    waka.queue(Player.LEFT);
    for (int i = 0; i < 16; i++) {
      game.tick();
    }
    assertEquals(previousRow, waka.getCurrentCell().getRow());
    assertEquals(previousCol, waka.getCurrentCell().getCol());
    assertTrue(game.getCells()[waka.getRow()][waka.getCol() - 1].isSolid());
    assertFalse(waka.getCurrentCell().isSolid());
    assertEquals(9, waka.getXOffset());
    assertEquals(9, waka.getYOffset());

    /* We then check if the Waka can still move from this stationary position. */
    waka.queue(Player.UP);
    for (int i = 0; i < 16; i++) {
      game.tick();
    }

    assertEquals(previousRow - 1, waka.getCurrentCell().getRow());
    assertEquals(previousCol, waka.getCurrentCell().getCol());
    assertFalse(waka.getCurrentCell().isSolid());
    assertEquals(9, waka.getXOffset());
    assertEquals(9, waka.getYOffset());
  }

  /* Testing the behaviour of user queued moves which are in the same direction
   * as Waka movement. */
  @Test
  public void sameDirectionMovementTest() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");
    Waka waka = game.getWaka();

    /* We let the Waka move in the default position for 16 frames. */
    for (int i = 0; i < 16; i++) {
      game.tick();
    }

    /* We queue a move in the same direction. This should have no effect on the movement. */
    waka.queue(Player.LEFT);

    int previousCol = waka.getCol();

    /* We check what happens after another units' worth of movement. */
    for (int i = 0; i < 16; i++) {
      game.tick();
    }

    assertEquals(previousCol - game.getConfig().getSpeed(), waka.getCurrentCell().getCol());
    assertEquals(9, waka.getXOffset());
    assertEquals(9, waka.getYOffset());
    assertEquals(Player.LEFT, waka.getCurrentMove());
  }

  /* Testing the behaviour of user queued moves which should result in a turn
   * at a valid intersection. */
  @Test
  public void queuedMoveTest() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");
    Waka waka = game.getWaka();

    for (int i = 0; i < 16; i++) {
      game.tick();
    }

    /* We straight away queue a move to go down. This should only work at a valid intersection. */
    waka.queue(Player.DOWN);

    /* We do the required number of ticks until the Waka reaches the intersection. */
    for (int i = 0; i < 16 * 5; i++) {
      if (waka.getXOffset() == 9 && waka.getYOffset() == 9 && 
              game.getCells()[waka.getRow()][waka.getCol() - 1].isSolid()) {
        break;
      }
      game.tick();
    }

    /* Direction should be left right up to the wall. 
     * It should not change direction up until this point.*/
    assertEquals(Player.LEFT, waka.getCurrentMove());
    int previousRow = waka.getRow();
    int previousCol = waka.getCol();

    /* Then, we do a units' worth of movement to see the Waka's response. */
    for (int i = 0; i < 16; i++) {
      game.tick();
    }

    /* The Waka should have moved downwards. */
    assertEquals(previousRow + game.getConfig().getSpeed(), waka.getCurrentCell().getRow());
    assertEquals(waka.getCurrentCell().getCol(),previousCol);
    assertEquals(9, waka.getXOffset());
    assertEquals(9, waka.getYOffset());
    assertEquals(Player.DOWN, waka.getCurrentMove());
  }

  /* Testing the Waka's behaviour around fruits. */
  @Test 
  public void fruitTest() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");
    Waka waka = game.getWaka();

    /* We move to the left, and check if the total fruit count increases. Fruit count
     * only increases when the Waka goes past more than one units' worth of moves (directly over it). */
    for (int i = 0; i < 16 * 5 + 1; i++) {
      game.tick();
    }
    assertEquals(5, waka.getFruitsEaten());

    /* We then move backwards, and check to see that the Waka does not re-eat the same fruits. */
    waka.queue(Player.RIGHT);
    for (int i = 0; i < 32; i++) {
      game.tick();
    }
    assertEquals(5, waka.getFruitsEaten());
  }

  /* Testing Waka's behaviour around collecting SuperFruits and SodaCans. */
  @Test
  public void collectibleTest() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");
    Waka waka = game.getWaka();
    
    /* We move left by 5 units. */
    for (int i = 0; i < 16 * 5; i++) {
      game.tick();
    }

    /* We then move up to collect our SuperFruit. */
    waka.queue(Player.UP);
    for (int i = 0; i < 16 * 5 + 1; i++) {
      game.tick();
    }

    assertEquals(10, waka.getFruitsEaten());

    /* We move down and back up again to see if the same superfruit is not double counted. */
    waka.queue(Player.DOWN);
    for (int i = 0; i < 17; i++) {
      game.tick();
    }

    waka.queue(Player.UP);
    for (int i = 0; i < 17; i++) {
      game.tick();
    }

    assertEquals(10, waka.getFruitsEaten());

    /* We then move to the right to eat the SodaCan. */
    waka.queue(Player.RIGHT);
    for (int i = 0; i < 16 * 11; i++) {
      game.tick();
    }

    assertEquals(20, waka.getFruitsEaten());
    
    /* We move down and back up again to see if the same superfruit is not double counted. We 
     * count an extra fruit here, just to see if any regular fruit eating behaviour has changed. */
    waka.queue(Player.DOWN);
    for (int i = 0; i < 17; i++) {
      game.tick();
    }

    waka.queue(Player.UP);
    for (int i = 0; i < 17; i++) {
      game.tick();
    }

    assertEquals(21, waka.getFruitsEaten());
  }


  /* Testing setters. */
  @Test
  public void settersTest() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");
    Waka waka = game.getWaka();

    assertEquals(0, waka.getFruitsEaten());
    waka.setFruitsEaten(42);
    assertEquals(42, waka.getFruitsEaten());

    assertEquals(game.getConfig().getLives(), waka.getCurrentLives());
    waka.setCurrentLives(9000);
    assertEquals(9000, waka.getCurrentLives());
  }

  /* Testing the Waka reset method. */
  @Test
  public void resetTest() {
    App app = new App() {
      public void exit() {
        ;
      }
    };
    GameManager game = new GameManager(app, "basic.json");
    PApplet.runSketch(new String[]{"App"}, app);
    game.setup();
    app.settings();
    

    Waka waka = game.getWaka();
    
    waka.queue(Player.DOWN);
    for (int i = 0; i < 16 * 6; i++) {
      game.tick();
    }

    waka.reset();

    assertEquals(Player.LEFT, waka.getCurrentMove());
    assertEquals(game.getMapParser().getWakaSpawn(), waka.getCurrentCell());
    assertEquals(5, waka.getFruitsEaten());
    app.dispose();
  }
}
