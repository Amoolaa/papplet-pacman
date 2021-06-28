package ghost;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import processing.core.PApplet;

import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;

public class WhimTest {

  @BeforeEach
  public void setup() {
    PrintWriter writer = null;
    try {
      writer = new PrintWriter("basic.json");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    writer.println("{");
    writer.println("    \"map\": \"simple_whim.txt\",");
    writer.println("    \"lives\": 3,");
    writer.println("    \"speed\": 1,");
    writer.println("    \"frightenedLength\": 1,");
    writer.println("    \"invisLength\": 1,");
    writer.println("    \"modeLengths\": [");
    writer.println("        3,\n        5,\n        5,\n        20\n    ]\n}");
    writer.flush();
    writer.close();


    try {
      writer = new PrintWriter("trapped.json");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    writer.println("{");
    writer.println("    \"map\": \"trapped.txt\",");
    writer.println("    \"lives\": 3,");
    writer.println("    \"speed\": 1,");
    writer.println("    \"frightenedLength\": 1,");
    writer.println("    \"invisLength\": 1,");
    writer.println("    \"modeLengths\": [");
    writer.println("        4,\n        20,\n        4,\n        20\n    ]\n}");
    writer.flush();
    writer.close();

    try {
      writer = new PrintWriter("simple_whim.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    writer.print("0000000000000000000000000000\n0000000000000000000000000000\n" + 
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0611111111111111111111111500\n" +
            "0277777777777777777777777200\n0276111115761115761111157200\n" +
            "0272000002720002720000027200\n0272000613741113741500027200\n" +
            "0272000277777777777200027200\n0272000276115761157200027200\n" +
            "0272000272002720027200027200\n0272000272002720027200027200\n" +
            "0274111374113741137411137200\n0277777777777p77777777777200\n" +
            "0276111576115761157611157200\n0272000272002720027200027200\n" +
            "0272000272002720027200027200\n0274111374113741137411137200\n" +
            "02c777777777777777777w777200\n0411111111111111111111111300\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n");
    writer.close();
    
    try {
      writer = new PrintWriter("trapped.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    writer.print("0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000000000000000\n0000000000000000000000000000\n" +
            "0000000000000000611150000000\n000000000000000027w720000000\n" +
            "0000000000000000272720000000\n0000000000000000272720000000\n" +
            "0000000000006150272720000000\n0000000000002720272720000000\n" +
            "0000000000002720272720000000\n0000000000002720272720000000\n" +
            "0000000000063741572720000000\n0000006111137777272720000000\n" +
            "00000028777s7657272720000000\n0000004111157437272720000000\n" +
            "000000000002p777272720000000\n0000000000041111372720000000\n" + 
            "0000000000000000272720000000\n0000000000000000272720000000\n" +
            "0000000000000000272720000000\n0000000000000000272720000000\n" +
            "0000000000000000272720000000\n000000000000000027c720000000\n" +
            "0000000000000000411130000000\n0000000000000000000000000000\n" +
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
    File simpleMovement = new File("simple_whim.txt");
    simpleMovement.delete();
    
    File trapped = new File("trapped.json");
    trapped.delete();
    File simpleTrapped = new File("trapped.txt");
    simpleTrapped.delete();
  }

  /* Testing constructor. */
  @Test
  public void constructorTest() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");

    for (Spawn s : game.getMapParser().getGhostSpawn()) {
      if (s.getID() == 'w') {  
        assertNotNull(new Whim(s, game.getConfig()));
      }
    }
  }

  /* Testing updating Whim's target location */
  @Test
  public void updateTargetTest() {
    App app = new App();
    GameManager game = new GameManager(app, "basic.json");
    Whim whim = (Whim) game.getWhims().get(0);
    Chaser chaser = (Chaser) game.getChasers().get(0);
    Waka waka = game.getWaka();
    
    /* Whim's target the bottom right corner in scatter mode. */
    assertEquals("scatter", whim.getCurrentModeName());
    

    /* We must update first, otherwise the x, y targets are initialised to 0. */
    whim.updateTargetLocation(waka);

    assertEquals(App.WIDTH, whim.getXTarget());
    assertEquals(App.HEIGHT, whim.getYTarget());

    /* Whim should initially move left for 3 units, up for 5 units and 6 to the right as it
     * approaches the top right corner of the application box in scatter mode. */
    for (int i = 0; i < 16 * 14; i++) {
      
      /* After three seconds, Whim's mode should change. */
      if (i == 180) {
        assertEquals("chase", whim.getCurrentModeName());
      }

      /* We move the Waka upwards to better test the chase mode of Whim. */
      if (i == 16 * 5) {
        waka.queue(Player.UP);
      }

      game.tick();
    }

    /* Ambusher should move 5 units above its starting point, since it approaches its corner. */
    assertEquals(whim.getSpawn().getRow() - 5, whim.getCurrentCell().getRow());
    assertEquals(whim.getSpawn().getCol() + 3, whim.getCurrentCell().getCol());

    /* Then, we check if whim is correctly targeting in chase mode. We must verify the
     * position of the Chaser first. */

    assertEquals(2, chaser.getSpawn().getCol());
    assertEquals(22, chaser.getSpawn().getRow());
     
    /* Since the chaser has moved 14 units upwards towards its corner in this time period, 
     * we subtract 14 from its initial row position. */
    assertEquals(2, chaser.getCurrentCell().getCol());
    assertEquals(22 - 14, chaser.getCurrentCell().getRow());

    /* Additionally, Waka has moved to the left by 5 units and upwards by 5 units. */
    assertEquals(13, waka.getSpawn().getCol());
    assertEquals(17, waka.getSpawn().getRow());
    
    assertEquals(8, waka.getCurrentCell().getCol());
    assertEquals(12, waka.getCurrentCell().getRow());

    /* We can now test the target locations of Whim in chase mode. For horizontal and
     * vertical components of the Whim's target vector, we find the displacement between
     * the components of Waka (where we add 2 to its direction) and Whim, and then multiply it 
     * by 2. We know the position of waka and whim from above. */
    assertEquals(2 + (8-2)*2, whim.getTargetCol());
    
    /* i.e. we take the current row of Whim and add to it the displacement between two rows above
     * waka and whim's current row position. */
    assertEquals(8 + (10 - 8)*2, whim.getTargetRow());
  }

  /* Testing Whim's target vector when Waka is facing left. */
  @Test
  public void leftTargetTest() {
    App app = new App();
    GameManager game = new GameManager(app, "trapped.json");
    Whim whim = (Whim) game.getWhims().get(0);
    Chaser chaser = (Chaser) game.getChasers().get(0);
    Waka waka = game.getWaka();

    /* We wait four second's worth of frames to allow ghosts to turn to chase mode. */
    for (int i = 0; i < 16 * 15; i++) {
      game.tick();
    }

    assertEquals("chase", whim.getCurrentModeName());

    /* We check the coordinates of the chaser. We know that both ghosts must go
     * left and downwards, so we know its position after 15 units worth
     * of movement. */
    assertEquals(17, chaser.getCol());
    assertEquals(11, chaser.getRow());

    /* We also check Waka's coordinates. */
    assertEquals(12, waka.getCol());
    assertEquals(18, waka.getRow());

    whim.updateTargetLocation(waka);

    /* We now test Whim's direction vector. We find the displacement between
     * the components of Waka (where we add 2 to its direction) and Whim, and then multiply it 
     * by 2. We know the position of waka and whim from above. This was calculation was done
     * by hand using geometry.*/
    assertEquals(17+(12-2-17)*2, whim.getTargetCol());
    assertEquals(11+(18-11)*2, whim.getTargetRow());
  }

  /* Testing Whim's target vector when Waka is facing right. */
  @Test
  public void rightTargetTest() {
    App app = new App();
    GameManager game = new GameManager(app, "trapped.json");
    Whim whim = (Whim) game.getWhims().get(0);
    Chaser chaser = (Chaser) game.getChasers().get(0);
    Waka waka = game.getWaka();

    waka.queue(Player.RIGHT);

    /* We wait five second's worth of frames (since the waka
     * will hit the sodacan, making it invis for 1 second) to allow ghosts to turn to chase mode. */
    for (int i = 0; i < 16 * 15; i++) {
      game.tick();
    }

    assertEquals("chase", whim.getCurrentModeName());

    /* We check the coordinates of the chaser. We know that both ghosts must go
     * left and downwards, so we know its position after 15 units worth
     * of movement. */
    assertEquals(17, chaser.getCol());
    assertEquals(11, chaser.getRow());

    /* We also check Waka's coordinates. */
    assertEquals(15, waka.getCol());
    assertEquals(18, waka.getRow());

    whim.updateTargetLocation(waka);

    /* We now test Whim's direction vector. We find the displacement between
     * the components of Waka (where we add 2 to its direction) and Whim, and then multiply it 
     * by 2. We know the position of waka and whim from above. This calculation was done
     * by hand using geometry.*/
    assertEquals(17+(15+2-17)*2, whim.getTargetCol());
    assertEquals(11+(18-11)*2, whim.getTargetRow());
  }

  /* Testing Whim's target vector when Waka is facing up. */
  @Test
  public void upTest() {
    App app = new App();
    GameManager game = new GameManager(app, "trapped.json");
    Whim whim = (Whim) game.getWhims().get(0);
    Chaser chaser = (Chaser) game.getChasers().get(0);
    Waka waka = game.getWaka();

    waka.queue(Player.UP);

    /* We wait four second's worth of frames to allow ghosts to turn to chase mode. */
    for (int i = 0; i < 16 * 15; i++) {
      game.tick();
    }

    assertEquals("chase", whim.getCurrentModeName());

    /* We check the coordinates of the chaser. We know that both ghosts must go
     * left and downwards, so we know its position after 15 units worth
     * of movement. */
    assertEquals(17, chaser.getCol());
    assertEquals(11, chaser.getRow());

    /* We also check Waka's coordinates. */
    assertEquals(12, waka.getCol());
    assertEquals(15, waka.getRow());

    whim.updateTargetLocation(waka);

    /* We now test Whim's direction vector. We find the displacement between
     * the components of Waka (where we add 2 to its direction) and Whim, and then multiply it 
     * by 2. We know the position of waka and whim from above. This calculation was done
     * by hand using geometry.*/
    assertEquals(17+(12-17)*2, whim.getTargetCol());
    assertEquals(11+(15-2-11)*2, whim.getTargetRow());
  }

  /* Testing Whim's target vector when Waka is facing down. */
  @Test
  public void downTest() {
    App app = new App();
    GameManager game = new GameManager(app, "trapped.json");
    Whim whim = (Whim) game.getWhims().get(0);
    Chaser chaser = (Chaser) game.getChasers().get(0);
    Waka waka = game.getWaka();
    
    /* We first move up, and then immediately back down. */
    waka.queue(Player.UP);

    /* We wait four second's worth of frames to allow ghosts to turn to chase mode. */
    for (int i = 0; i < 16 * 15; i++) {
      if (i == 1) {
        waka.queue(Player.DOWN);
      }
      game.tick();
    }

    assertEquals("chase", whim.getCurrentModeName());

    /* We check the coordinates of the chaser. We know that both ghosts must go
     * left and downwards, so we know its position after 15 units worth
     * of movement. */
    assertEquals(17, chaser.getCol());
    assertEquals(11, chaser.getRow());

    /* We also check Waka's coordinates. */
    assertEquals(12, waka.getCol());
    assertEquals(18, waka.getRow());

    whim.updateTargetLocation(waka);

    /* We now test Whim's direction vector. We find the displacement between
     * the components of Waka (where we add 2 to its direction) and Whim, and then multiply it 
     * by 2. We know the position of waka and whim from above. This calculation was done
     * by hand using geometry.*/
    assertEquals(17+(12-17)*2, whim.getTargetCol());
    assertEquals(11+(18+2-11)*2, whim.getTargetRow());
  }

  /* Testing frightened and invis behaviours. */
  @Test
  public void frightenedInvisTest() {
    App app = new App() {
      public void exit() {
        ;
      }
    };
    GameManager game = new GameManager(app, "trapped.json");
    PApplet.runSketch(new String[]{"App"}, app);
    game.setup();
    app.settings();

    Whim whim = (Whim) game.getWhims().get(0);
    Chaser chaser = (Chaser) game.getChasers().get(0);
    Waka waka = game.getWaka();

    /* We first move up and to the left to eat the sodacan. */
    waka.queue(Player.UP);

    for (int i = 0; i < 16 * 3; i++) {
      game.tick();
      if (i == 16 * 1) {
        waka.queue(Player.LEFT);
      }
    }
    
    assertEquals("invisible", whim.getCurrentModeName());
    
    waka.queue(Player.LEFT);
    for (int i = 0; i < 4 * 16; i++) {
      game.tick();
    }

    assertEquals("frightened", whim.getCurrentModeName());

    for (int i = 0; i < 60; i++) {
      game.tick();
    }

    assertEquals("scatter", whim.getCurrentModeName());
    app.dispose();
  }
}
