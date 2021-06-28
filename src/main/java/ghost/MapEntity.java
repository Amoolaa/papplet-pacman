package ghost;

/**
 * Used when an entity exists in some location on the application map. We should be
 * able to retrieve the pixel coordinates, as well as the cell coordinates, for the
 * object to exist on the board. Enables the extension to other non-cell or player related
 * entities.
 */
public interface MapEntity {
  
  /**
   * Returns the horizontal pixel position of the entity within the application box.
   * @return x-value of pixel location of entity
   */
  public int getX();

  /**
   * Returns the vertical pixel position of the entity within the application box.
   * @return y-value of pixel location of entity
   */
  public int getY();

  /**
   * Returns the column position of the entity with reference to the multi-dimensional array
   * containing Cells.
   * @return column position of entity
   */
  public int getCol();

  /**
   * Returns the row position of the entity with reference to the multi-dimensional array
   * containing Cells.
   * @return row position of entity
   */
  public int getRow();
}
