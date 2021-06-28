package ghost;

import processing.core.PApplet;

/**
 * Represents all wall objects which are involved in the
 * collision mechanics of Waka and Ghosts.
 */
public class Wall extends Interactable {
  /**
   * Creates a Wall object. 
   * @param id Character which corresponds to the cell type
   * @param imagefile name of file containg image
   * @param col Column position of cell
   * @param row Row position of cell
   * @param isSolid Whether the cell is solid or not
   */
  public Wall(char id, String imagefile, int col, int row, boolean isSolid) {
    super(id, imagefile, col, row, isSolid);
  }

  /**
   * Loads and initialises wall sprites.
   * @param app Application used to run the game
   */
  public void setup(PApplet app) {
    this.setImage(app.loadImage(this.getFilename()));
  }
}
