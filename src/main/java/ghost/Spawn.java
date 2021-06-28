package ghost;
/**
 * Represents all Waka and Ghost spawnpoints on the map. Used when resetting the Ghost
 * and Waka if a win/loss condition is met. 
 */
public class Spawn extends Cell {
  
  /**
   * Creates a Spawn object.
   * @param id Character which corresponds to the cell type
   * @param col Column position of cell
   * @param row Row position of cell
   * @param isSolid Whether the cell is solid or not
   */
  public Spawn(char id, int col, int row, boolean isSolid) {
    super(id, col, row, isSolid);
  }
}
