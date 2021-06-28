package ghost;

import java.util.ArrayList;
import java.util.List;
import processing.core.PFont;
import processing.core.PApplet;
import java.util.HashMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Central game control unit used to control larger sections of game logic, drawing,
 * setting up, and updating images in the application box. Performs game-wide resets and
 * the associated screen if a win or loss condition is met, aggregates all 
 * draw(), setup(), and tick() methods and has other getters and setters to do with the 
 * state of the game.
 */
public class GameManager {
  /* Map parser. */
  private MapParser parser;
  
  /* Multi-dimensional array of cells. */
  private Cell[][] cells;
  
  /* Total fruit count of the map. */
  private int fruitCount;
  
  /* JSON config parser. */
  private Config config;
  
  /* Waka object in the game. */
  private Waka waka;
  
  /* List of Ghost objects in the game. */
  private ArrayList<Ghost> ghosts;
  
  /* Application used to run the game. */
  private App app;
  
  /* Total number of Waka's lives. */
  private int lives;
  
  /* Font for endgame scene. */
  private PFont font;
  
  /* Represents whether game has been lost. */
  private boolean gameOver;
  
  /* Represents whether game has been won. */
  private boolean gameWon;
  
  /* Arbitrary counter; used to control time sensitive actions in tick().*/
  private int counter;

  
  /**
   * Contains the Game modes and their corresponding names.
   */
  public static final Map<String, Mode> MODES;
  
  static {
    Map<String, Mode> modifiableModes = new HashMap<String, Mode>();
    modifiableModes.put("chase", new Chase("scatter"));
    modifiableModes.put("scatter", new Scatter("chase"));
    modifiableModes.put("frightened", new Frightened("frightened.png"));
    modifiableModes.put("invisible", new Invisible("invis.png"));
    MODES = Collections.unmodifiableMap(modifiableModes);
  }

  /**
   * Creates a GameManager object. 
   * @param app Application used to run the game.
   * @param configFilename Name of file containing config information.
   */
  public GameManager(App app, String configFilename) {
    /* Formatting JSON config information into variables for use. */
    this.config = new Config(configFilename);
    this.config.formatter();

    /* Initialising all map-related attributes. */
    this.parser = new MapParser(this.config.getMap());
    this.cells = this.parser.formatter();
    this.fruitCount = this.parser.getFruitCount();
    this.lives = config.getLives();
    this.waka = new Waka(this.parser.getWakaSpawn(), this.config);
    this.ghosts = new ArrayList<Ghost>();
    for (Spawn s : this.parser.getGhostSpawn()) {
      if (s.getID() == 'c') {
        this.ghosts.add(new Chaser(s, this.config));
      } else if (s.getID() == 'a') {
        this.ghosts.add(new Ambusher(s, this.config));
      } else if (s.getID() == 'i') {
        this.ghosts.add(new Ignorant(s, this.config));
      } else {
        this.ghosts.add(new Whim(s, this.config));
      }
    }
    this.font = null;
    this.gameOver = false;
    this.gameWon = false;
    this.counter = 0;
    this.app = app;
  }

  /**
   * Returns object containing map information for the game.
   * @return MapParser object.
   */
  public MapParser getMapParser() {
    return this.parser;
  }

  /**
   * Returns list containing Chasers.
   * @return list containing Chaser objects.
   */
  public List<Ghost> getChasers() {
    return this.ghosts.stream().filter(g -> (g.getSpawn().getID() == 'c')).
            collect(Collectors.toList());
  } 

  /**
   * Returns list containing Whims.
   * @return list containing Whim objects.
   */
  public List<Ghost> getWhims() {
    return this.ghosts.stream().filter(g -> (g.getSpawn().getID() == 'w')).
            collect(Collectors.toList());
  } 

  /**
   * Returns list containing Ambushers.
   * @return list containing Ambusher objects.
   */
  public List<Ghost> getAmbushers() {
    return this.ghosts.stream().filter(g -> (g.getSpawn().getID() == 'a')).
            collect(Collectors.toList());
  } 

  /**
   * Returns list containing Ignorants.
   * @return list containing Ignorant objects.
   */
  public List<Ghost> getIgnorant() {
    return this.ghosts.stream().filter(g -> (g.getSpawn().getID() == 'i')).
            collect(Collectors.toList());
  } 

  /**
   * Returns font used in the game.
   * @return font object
   */
  public PFont getFont() {
    return this.font;
  }

  /**
   * Returns JSON config parser object for the game.
   * @return JSON config parser object.
   */
  public Config getConfig() {
    return this.config;
  }

  /**
   * Loads images from all respective objects and loads endgame font.
   */
  public void setup() {
    this.parser.setup(this.app);
    this.waka.setup(this.app);
    if (this.ghosts.size() != 0) {
      for (Ghost g : this.ghosts) {
        g.setup(this.app);
      }
    }

    /* Loading modes */
    for (Mode m : MODES.values()) {
      m.setup(this.app);
    }

    /* Loading endgame font and aligning with the centre of the textbox. */
    this.font = this.app.createFont("PressStart2P-Regular.ttf", 24);
    this.app.textAlign(PApplet.CENTER, PApplet.CENTER);
    this.app.textFont(font);
  }

  /**
   * Prints "YOU WIN" for 10 seconds once the user has won the game.
   */
  public void gameWon() {
    if (this.counter == 10 * 60) {
      this.counter = 0;
      this.gameWon = false;
    } else {
      this.app.fill(0);
      this.app.rect(-1, -1, App.WIDTH + 1, App.HEIGHT + 1);
      this.app.fill(255);
      this.app.text("YOU WIN", (App.WIDTH)/2, (App.HEIGHT)/2);
      this.counter++;
    }
  }


  /**
   * Prints "YOU LOSE" for 10 seconds when the user has lost the game.
   */
  public void gameOver() {
    if (this.counter == 10 * 60) {
      this.counter = 0;
      this.gameOver = false;
    } else {
      this.app.fill(0);
      this.app.rect(-1, -1, App.WIDTH + 1, App.HEIGHT + 1);
      this.app.fill(255);
      this.app.text("GAME OVER", (App.WIDTH)/2, (App.HEIGHT)/2);
      this.counter++;
    }
  }
  /**
   * Checks for win/loss conditions and updates information accordingly. Checks if Waka and 
   * Ghost collide, if the Waka is out of lives or if all the fruits have been eaten from the map.
   */
  public void winLossConditions() {
    this.anyGhostOnWaka();
    this.outOfLives();
    this.allFruitsEaten();
  }

  public void keyManager(int keyCode, char key) {
    if (key == App.CODED) {
      this.getWaka().queue(keyCode);
    } else {
      if (key == ' ') {
        for (Ghost g : this.getGhosts()) {
          g.toggleDebug();
        }
      }
    }
  }

  /**
   * Updates and checks for win, loss, or ordinary gameplay conditions every frame. This is
   * the central tick() method, allowing objects to obtain information from each other and 
   * update their behaviours accordingly. 
   */
  public void tick() {
    /* If game is lost */
    if (this.gameOver) {
      this.gameOver();

    /* If game is won */
    } else if (this.gameWon) {
      this.gameWon();
    
    /* Otherwise, update information for regular gameplay conditions, and check if these
     * result in win/loss conditions. */
    } else {
      waka.setCells(this);
      waka.updateCurrentCell(this);
      waka.eat(this.ghosts);
      waka.tick();
      for (Ghost g : this.ghosts) {
        g.setCells(this);
        g.updateCurrentCell(this);
        g.updateChaserInfo(this);
        g.updateTargetLocation(waka);
        g.tick();
      }

      this.winLossConditions();
    }
  }

  /**
   * Draws all corresponding images for win, loss or normal gameplay conditions.
   */
   public void draw() {
    this.parser.draw(this.app);
    this.waka.draw(this.app);
    for (Ghost g : this.ghosts) {
      g.draw(this.app);
    }
    this.tick();
  }
  
  /**
   * Returns multi-dimensional array of cells corresponding to the map.
   * @return multi-dimensional array of cells
   */
  public Cell[][] getCells() {
    return this.cells;
  }

  /**
   * Returns total fruit count of the map.
   * @return total fruit count of the map
   */
  public int getFruitCount() {
    return this.fruitCount;
  }
  
  /**
   * Returns Waka object in the game.
   * @return Waka object
   */
  public Waka getWaka() {
    return this.waka;
  }

  /**
   * Returns lit of Ghost objects in the game.
   * @return list of Ghost objects
   */
  public ArrayList<Ghost> getGhosts() {
    return this.ghosts;
  }

  /**
   * Checks if Waka is out of lives. If so, the game is lost, and is subsequently reset.
   */
  public void outOfLives() {
    if (this.waka.getCurrentLives() == 0) {
      this.resetAll();
      this.gameOver = true;
    }
  }

  /**
   * Checks if Waka collides with a Ghost. If so, Waka's lives decrease by 1, and the
   * Ghost and Waka objects are reset.
   */
  public void anyGhostOnWaka() {
    for (Ghost g : this.ghosts) {
      if (g.isOnWaka() && g.isAlive()) {
        if (g.getCurrentModeName().equals("frightened")) {
          g.setAlive(false);
        } else {
          this.waka.setCurrentLives(this.waka.getCurrentLives() - 1);
          this.wakaGhostReset();
          return;
        }
      }
    }
  }

  public boolean isGameWon() {
    return this.gameWon;
  }

  public boolean isGameOver() {
    return this.gameOver;
  }


  /**
   * Checks if all fruits are eaten. If so, the game is won, and is subsequently reset.
   */
  public void allFruitsEaten() {
    if (this.waka.getFruitsEaten() == this.fruitCount) {
      this.resetAll();
      this.gameWon = true;
    }
  }

  /**
   * Resets all game attributes. Used for resetting when games are won or lost.
   */
  public void resetAll() {
    this.parser.reset();
    this.wakaGhostReset();
    this.waka.setCurrentLives(this.lives);
    this.waka.setFruitsEaten(0);
  }

  /**
   * Resets only Waka and Ghosts. Used for resetting in the middle of games.
   */
  public void wakaGhostReset() {
    this.waka.reset();
    for (Ghost g : this.ghosts) {
      g.reset();
    }
  }
}
