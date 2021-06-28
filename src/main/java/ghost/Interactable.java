package ghost;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * Represents all cells which interact with players throughout the game. 
 */
public abstract class Interactable extends Cell implements Drawable {
  /* Name of file containing image. */
  private String imagefile;
  
  /* Image object representing cell in the application window. */
  private PImage image;

  /**
   * For invocation by subclass constructors. Note that image is initially set to null here prior 
   * to being initialised in setup().
   * @param id Character which corresponds to the cell type
   * @param imagefile name of file containg image
   * @param col Column position of cell
   * @param row Row position of cell
   * @param isSolid Whether the cell is solid or not
   */
  protected Interactable(char id, String imagefile, int col, int row, boolean isSolid) {
    super(id, col, row, isSolid);
    this.imagefile = imagefile;
    this.image = null;
  }

  /**
   * Returns PImage object representing the image of the cell in the application box.
   * @return image object representing cell
   */
  public PImage getImage() {
    return this.image;
  }

  /**
   * Sets PImage object representing the image of the cell.
   * @param image PImage object representing cell
   */
  public void setImage(PImage image) {
    this.image = image;
  }

  /**
   * Returns name of file containing image.
   * @return filename of image
   */
  public String getFilename() {
    return this.imagefile;
  }

  /**
   * Draws interactable sprites.
   * @param app Application used to run the game
   */
  public void draw(PApplet app) {
    app.image(this.getImage(), this.getX(), this.getY());
  }
}
