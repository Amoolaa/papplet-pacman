package ghost;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * Represents all eatable objects that the Waka interacts with throughout
 * the game. Contains eating functionality used by the Waka, as well as
 * separate sprites to represent the eaten and non-eaten status of the cell.
 */
public abstract class Eatable extends Interactable {
  
  /* Determines if object has been eaten yet. */
  private boolean eaten;
  
  /* Contains non-eaten and eaten sprites for object. */
  private PImage[] sprites;

  /**
   * For invocation by subclass constructors. Since the object is not initially eaten, 
   * eaten is set to false, and sprites are initialised to null prior to setup().
   * @param id Character which corresponds to the cell type
   * @param imagefile name of file containg image
   * @param col Column position of cell
   * @param row Row position of cell
   * @param isSolid Whether the cell is solid or not
   */
  protected Eatable(char id, String imagefile, int col, int row, boolean isSolid) {
    super(id, imagefile, col, row, isSolid);
    this.eaten = false;
    this.sprites = new PImage[2];
    this.sprites[0] = null;
    this.sprites[1] = null;
  }

  /**
   * Sets images to the sprite array.
   * @param imageA first image to be set at index 0 in the array
   * @param imageB second image to be set at index 1 in the array
   */
  public void setSprites(PImage imageA, PImage imageB) {
    this.sprites[0] = imageA;
    this.sprites[1] = imageB;
  }

  /**
   * Returns whether object has been eaten by the Waka. 
   * @return if fruit is eaten
   */
  public boolean eaten() {
    return this.eaten;
  }

  /**
   * Sets the Eatable object as eaten. This also changes the sprite to the 
   * eaten sprite (in the default case, a blank image).
   */
  public void eat() {
    this.setImage(this.sprites[1]);
    this.eaten = true;
  }

  /**
   * Resets the fruit object to its initial state.
   */
  public void reset() {
    this.setImage(this.sprites[0]);
    this.eaten = false;
  }

  /**
   * Loads and initialises eatable sprites.
   * @param app Application used to run the game
   */
  public void setup(PApplet app) {
    this.setImage(app.loadImage(this.getFilename()));
    this.sprites[0] = this.getImage();
    this.sprites[1] = new PImage();
  }

  /**
   * Draws eatable images.
   * @param app Application used to run the game
   */
  public void draw(PApplet app) {
    app.image(this.getImage(), this.getX(), this.getY());
  }
}
