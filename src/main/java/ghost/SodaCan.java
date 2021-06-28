package ghost;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * Eatable object representing the sodacan debuff. When a Waka eats a SodaCan, the game enters 
 * Invisible mode. Ghosts should exhibit frightened behaviour (randomised movement) and sprites
 * should be difficult to see, appear to be wavy and (in general) act as a debuff for the player.
 */
public class SodaCan extends Eatable {
  
  /* Filename of the eaten sodacan image. */
  private String eatenImagefile;
  
  /**
   * Create a new SuperFruit object. Since fruit is not initially eaten, eaten is set to false, and
   * sprites are initialised to null prior to setup().
   * @param id Character which corresponds to the cell type
   * @param imagefile name of file containing non-eaten image
   * @param eatenImagefile name of file containing eaten image
   * @param col Column position of cell
   * @param row Row position of cell
   * @param isSolid Whether the cell is solid or not
   */
  public SodaCan(char id, String imagefile, String eatenImagefile, int col, int row, 
          boolean isSolid) {
    super(id, imagefile, col, row, isSolid);
    this.eatenImagefile = eatenImagefile;
  }

  /**
   * Returns the filename of the eaten sodacan image.
   * @return Filename of the eaten sodacan image
   */
  public String getEatenImagefile() {
    return this.eatenImagefile;
  }

  /**
   * Loads and initialises sodacan sprites.
   * @param app Application used to run the game
   */
  public void setup(PApplet app) {
    PImage noneaten = app.loadImage(this.getFilename());
    PImage eaten = app.loadImage(this.eatenImagefile);
    this.setImage(noneaten);
    this.setSprites(noneaten, eaten);
  }

  /**
   * Draws sodacan sprites.
   * @param app Application used to run the game
   */
  public void draw(PApplet app) {
    app.image(this.getImage(), this.getX(), this.getY());
  }
}
