package ghost;

import processing.core.PApplet;

/**
 * Abstract class for every moving player in the game. Contains mainly getters and setters
 * to do with map positions, as well as general functionality for moving between cells.
 */
public abstract class Player implements MapEntity, Drawable{
  /**
   * KeyCode representing left arrow key.
   */
  public static final int LEFT = 37;
  /**
   * KeyCode representing up arrow key.
   */
  public static final int UP = 38;
  /**
   * KeyCode representing right arrow key.
   */
  public static final int RIGHT = 39;
  /**
   * KeyCode representing down arrow key.
   */
  public static final int DOWN = 40;

  /* Horizontal pixel position of player. */
  private int x;
  
  /* Vertical pixel position of player.*/
  private int y;
  
  /* Column position of player.*/
  private int col;
  
  /* Row position of player.*/
  private int row;
  
  /* Horizontal pixel-wise offset for transitioning between 16x16 cells. */
  private int xOffset;
  
  /* Vertical pixel-wise offset for transitioning between 16x16 cells. */
  private int yOffset;
  
  /* Horizontal velocity (in pixels per frame). */
  private int xVel;
  
  /* Vertical velocity (in pixels per frame). */
  private int yVel;
  
  /* Speed of Player (in pixels per frame). */
  private int speed;

  /* Spawnpoint of Player. */
  private Spawn spawn;

  /* Current cell that Player occupies.*/
  private Cell currentCell;

  /* Represents the current direction Waka is moving.*/
  private int currentMove;
  
  /* Multi-dimensional array containing cell objects on the map. */
  private Cell[][] cells;
  
  /**
   * For invocation by subclass constructors. 
   * @param spawn Spawnpoint of Player.
   * @param config Config parser for the game.
   */
  protected Player(Spawn spawn, Config config) {
    this.speed = config.getSpeed();
    this.spawn = spawn;
    this.x = spawn.getX();
    this.y = spawn.getY();
    this.col = spawn.getCol();
    this.row = spawn.getRow();
    
    /* We set offsets to (9, 9) to represent the centre of 16x16 cells. */
    this.xOffset = 9;
    this.yOffset = 9;
    this.currentCell = spawn;
    
    /* Initial direction is left. */
    this.xVel = -1 * this.speed;
    this.yVel = 0;
    this.currentMove = LEFT;
  }

  /**
   * Bounds number between a certain range. 
   * This is used to represent pixel positions outside of the application box 
   * to a corresponding pixel position inside the box.
   * @param num number to be bounded
   * @param upper upper bound
   * @param lower lower bound
   * @return the lower bound if num is less than the lower bound, the upper bound if
   * is greater than the upper bound, or num otherwise. 
   */
  public static int bounded(int num, int upper, int lower) {
    if (num < lower) {
      return lower;
    } else if (num > upper) {
      return upper;
    } else {
      return num;
    }
  }

  /**
   * Sets the horizontal velocity of Player.
   * @param xVel horizontal velocity of Player
   */
  public void setXVel(int xVel) {
    this.xVel = xVel;
  }

  /**
   * Sets the vertical velocity of Player.
   * @param yVel vertical velocity of Player
   */
  public void setYVel(int yVel) {
    this.yVel = yVel;
  }

  /**
   * Returns spawnpoint object of Player.
   * @return spawnpoint object
   */
  public Spawn getSpawn() {
    return this.spawn;
  }

  /**
   * Returns the current direction of movement of the Player.
   * @return current direction of movement
   */
  public int getCurrentMove() {
    return this.currentMove;
  }

  /**
   * Returns the horizontal pixel position of the Player within application box.
   * @return x-value of pixel location of Player
   */
  public int getX() {
    return this.x;
  }

  /**
   * Sets the horizontal pixel position of the Player.
   * @param x x-pixel position
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * Returns the vertical pixel position of the Player within application box.
   * @return y-value of pixel location of Player
   */
  public int getY() {
    return this.y;
  }

  /**
   * Sets the vertical pixel position of the Player.
   * @param y y-pixel position
   */
  public void setY(int y) {
    this.y = y;
  }


  /**
   * Returns the pixels-per-frame speed of the player's movement.
   * @return pixels per frame of Player
   */
  public int getSpeed() {
    return this.speed;
  }

  /**
   * Returns the row position of Player with reference to the multi-dimensional array
   * containing Cells.
   * @return row position of Player
   */
  public int getRow() {
    return this.row;
  }

  /**
   * Sets row position of Player reference to the multi-dimensional array
   * containing Cells.
   * @param row row position
   */
  public void setRow(int row) {
    this.row = row;
  }

  /**
   * Returns the column position of Player with reference to the multi-dimensional array
   * containing Cells.
   * @return column position of Player
   */
  public int getCol() {
    return this.col;
  }

  /**
   * Sets the column position of Player with reference to the multi-dimensional array
   * containing Cells.
   * @param col column position
   */
  public void setCol(int col) {
    this.col = col;
  }

  /**
   * Returns the horizontal pixel offset of the centre of the Player sprite from 
   * the top-left corner of the cell it currently occupies.
   * @return horizontal pixel offset
   */
  public int getXOffset() {
    return this.xOffset;
  }

  /**
   * Sets the horizontal pixel offset of the centre of the Player sprite from 
   * the top-left corner of the cell it currently occupies.
   * @param offset pixel offset
   */
  public void setXOffset(int offset) {
    this.xOffset = offset;
  }
  /**
   * Returns the vertical pixel offset of the centre of the Player sprite from 
   * the top-left corner of the cell it currently occupies.
   * @return vertical pixel offset
   */
  public int getYOffset() {
    return this.yOffset;
  }

  /**
   * Sets the vertical pixel offset of the centre of the Player sprite from 
   * the top-left corner of the cell it currently occupies.
   * @param offset pixel offset
   */
  public void setYOffset(int offset) {
    this.yOffset = offset;
  }

  /**
   * Returns the cell object which the Player currently occupies.
   * @return current cell occupied by Player
   */
  public Cell getCurrentCell() {
    return this.currentCell;
  }

  /**
   * Sets Cells array. 
   * @param game central game control unit
   */
  public void setCells(GameManager game) {
    this.cells = game.getCells();
  }

  /**
   * Returns multi-dimensional array of cell objects from the map.
   * @return multi-dimensional array containing cell objects
   */
  public Cell[][] getCells() {
    return this.cells;
  }

  /**
   * Sets the current direction the Waka is moving.
   * @param keyCode current direction the Waka is moving
   */
  public void setCurrentMove(int keyCode) {
    this.currentMove = keyCode;
  }

  /**
   * Updates the current cell occupied by the Player object.
   * @param game central game control unit
   */
  public void updateCurrentCell(GameManager game) {
    this.currentCell = game.getCells()[this.getRow()][this.getCol()];
  }

  /**
   * Sets the current cell occupied by the Player object.
   * @param cell cell object which represents 16x16 tile within a larger map
   */
  public void setCurrentCell(Cell cell) {
    this.currentCell = cell;
  }


  /**
   * Updates coordinates based on velocities and offsets.
   */
  public void updateCoords() {
    /* Each pixel-wise position is incremented. */
    this.x += this.xVel;
    this.y += this.yVel;
    this.xOffset += this.xVel;
    this.yOffset += this.yVel;
    
    /* If offsets exceed the interval [1, 16], then the Player has exceeded the 
     * pixel boundaries for each cell. So, we must update the row and column position
     * of the Player within the cell array to reflect the Player's motion through
     * the map. */
    if (this.xOffset > 16) {
      this.xOffset = this.xOffset - 16;
      this.col += 1;
    } else if (this.xOffset < 1) {
      this.xOffset = 16 + this.xOffset;
      this.col -= 1;
    } else if (this.yOffset > 16) {
      this.yOffset = this.yOffset - 16;
      this.row += 1;
    } else if (this.yOffset < 1) {
      this.yOffset = 16 + this.yOffset;
      this.row -= 1;
    }
  }

  /**
   * Performs relevant checks and balances to do with collision,
   * movement, win and lose conditions every frame.
   */
  public abstract void tick();
  
  /**
   * Loads images.
   * @param app Application used to run game.
   */
  public abstract void setup(PApplet app);

  /**
   * Draws images.
   * @param app Application used to run game.
   */
  public abstract void draw(PApplet app);

  /**
   * Changes velocities to correspond to the arrow key passed into the function.
   * @param keyCode Integer corresponding to arrow key.
   */
  public abstract void move(int keyCode);
}
