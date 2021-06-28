package ghost;

/**
 * An object which represents one 16x16 cell on the map and interacts with the
 * Waka and Ghost objects. 
 */
public abstract class Cell implements MapEntity {
  /* Character which corresponds to the cell type. */
  private char id;
  
  /* Column position of cell within multi-dimensional array. */
  private int col;
  
  /* Row position of cell within multi-dimensional array. */
  private int row;
  
  /* Determines if the cell is solid. */
  private boolean isSolid;

  /**
   * For invocation by subclass constructors.
   * @param id Character which corresponds to the cell type
   * @param col Column position of cell
   * @param row Row position of cell
   * @param isSolid Whether the cell is solid or not
   */
  public Cell(char id, int col, int row, boolean isSolid) {
    this.id = id;
    this.col = col;
    this.row = row;
    this.isSolid = isSolid;
  }

  /**
   * Returns the character identifier for the cell.
   * @return character identifier for cell type
   */
  public char getID() {
    return this.id;
  }

  /**
   * Returns the horizontal pixel position of the cell within application box.
   * @return x-value of pixel location of cell
   */
  public int getX() {
    return this.col * 16;
  }

  /**
   * Returns the vertical pixel position of the cell within application box.
   * @return y-value of pixel location of cell
   */
  public int getY() {
    return this.row * 16;
  }

  /**
   * Returns the row position of cell inside the multi-dimensional array;
   * @return row position of cell;
   */
  public int getRow() {
    return this.row;
  }

  /**
   * Returns the column position of cell inside the multi-dimensional array;
   * @return column position of cell;
   */
  public int getCol() {
    return this.col;
  }

  /**
   * Returns whether the cell is solid (more specifically, if it is involved in collision).
   * @return whether cell is solid
   */
  public boolean isSolid() {
    return this.isSolid;
  }
}
