package ghost;

import processing.core.PApplet;

/** 
 * The PApplet application used to run the game. 
*/
public class App extends PApplet {

  /**
   * Width of application box in pixels.
   */
  public static final int WIDTH = 448;
  /**
   * Height of application box in pixels.
   */
  public static final int HEIGHT = 576;
  
  /* Central game control unit used to control larger sections of game logic, drawing,
   * setting up, and updating images in the application box. */
  private GameManager game;

  /**
   * Constructor assigns a new instance of GameManager used to control the game.
   */
  public App() {
    this.game = new GameManager(this, "config.json");
  }

  /**
   * Fixes application box dimensions.
   */
  public void settings() {
    size(WIDTH, HEIGHT);
  }

  /**
   * Sets fixed framerate and loads images.
   */
  public void setup() {
    frameRate(60);
    this.game.setup();  
  }

  /**
   * Draws images.
   */
  public void draw() { 
    background(0, 0, 0);
    this.game.draw();
  }

  /**
   * Takes in user input for the movement of Waka and/or toggle of the debug line.
   */
  public void keyPressed(){ 
    this.game.keyManager(keyCode, key);
  }
  
  /**
   * Starts the application.
   * @param args command line arguments
   */
  public static void main(String[] args) {  
    PApplet.main("ghost.App");
  }
}
