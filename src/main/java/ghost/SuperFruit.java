package ghost;

/**
 * Eatable object representing the frightened buff. When a Waka eats a SuperFruit, the game enters
 * Frightened mode. When in frightened mode, Ghosts choose paths randomly, and so take on a random 
 * target. If a Waka collides with a ghost in frightened mode, the ghost is removed from the game
 * for that round. 
 */
public class SuperFruit extends Eatable {
  /**
   * Create a new SuperFruit object. Since fruit is not initially eaten, eaten is set to false, 
   * and sprites are initialised to null prior to setup().
   * @param id Character which corresponds to the cell type
   * @param imagefile name of file containg image
   * @param col Column position of cell
   * @param row Row position of cell
   * @param isSolid Whether the cell is solid or not
   */
  public SuperFruit(char id, String imagefile, int col, int row, boolean isSolid) {
    super(id, imagefile, col, row, isSolid);
  }
}
