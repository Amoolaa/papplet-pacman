package ghost;

/**
 * Represents all fruit objects that the Waka interacts with throughout
 * the game.
 */
public class Fruit extends Eatable {

  /**
   * Create a new fruit object. Since fruit is not initially eaten, eaten is set to false, and
   * sprites are initialised to null prior to setup().
   * @param id Character which corresponds to the cell type
   * @param imagefile name of file containg image
   * @param col Column position of cell
   * @param row Row position of cell
   * @param isSolid Whether the cell is solid or not
   */
  public Fruit(char id, String imagefile, int col, int row, boolean isSolid) {
    super(id, imagefile, col, row, isSolid);
  }
}