package ghost;


/**
 * Enum representing corners of the application box. Used in the scatter modes
 * for Ghosts.
 */
public enum Corner implements MapEntity {
  /**
   * Top left corner of application box.
   */
  TOPLEFT (0, 0), 
  
  /**
   * Top right corner of application box.
   */
  TOPRIGHT (App.WIDTH, 0),

  /**
   * Bottom left corner of application box.
   */
  BOTTOMLEFT (0, App.HEIGHT),

  /**
   * Bottom right corner of application box.
   */
  BOTTOMRIGHT (App.WIDTH, App.HEIGHT);

  private final int x;
  private final int y;

  private Corner(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Returns x-coordinate of the Corner.
   * @return x-coordinate of Corner
   */
  public int getX() {
    return this.x;
  }

  /**
   * Returns y-coordinate of Corner.
   * @return y-coordinate of Corner
   */
  public int getY() {
    return this.y;
  }
  
  /**
   * Returns the column position of teh Corner.
   * @return column position of Corner
   */
  public int getCol() {
    if (this.x == 0) {
      return 0;
    } else {
      return (this.x / 16) - 1;
    }
  }

  /**
   * Returns the row position of teh Corner.
   * @return row position of Corner
   */
  public int getRow() {
    if (this.y == 0) {
      return 0;
    } else {
      return (this.y / 16) - 1;
    }
  }
}
