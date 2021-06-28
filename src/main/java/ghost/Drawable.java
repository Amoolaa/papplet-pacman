package ghost;

import processing.core.PApplet;


/**
 * Interface representing when objects should be drawable. They should have associated sprites,
 * their sprites must be loaded into the game and then be drawn to the application box.
 */
public interface Drawable {
  
  /**
   * Draws images.
   * @param app Application used to run the game
   */
  public void draw(PApplet app);

  /**
   * Loads and initialises images.
   * @param app Application used to run the game
   */
  public void setup(PApplet app);
}
