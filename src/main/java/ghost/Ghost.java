package ghost;

import processing.core.PImage;
import processing.core.PApplet;

import java.lang.Math;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

/**
 * Object representing the AI-controlled opposition to the Waka in the game. Contains 
 * methods controlling the behaviour of Ghosts towards any specified target (which changes
 * based on the type of ghost), as well as updating modes, and contains debug
 * mode functionality for the prescribed mode targets. The Ghost AI uses relative 
 * displacement in both cardinal directions to take whatever path to minimise distance to the
 * target. 
 */
public abstract class Ghost extends Player {

  /* Target column. */
  private int targetCol;

  /* Target row. */
  private int targetRow;

  /* Target alive. */
  private boolean alive;

  /* The normal mode (i.e. chase and scatter) sprite of the ghost. */
  private PImage normalSprite;
  
  /* The Ghost's current sprite.*/
  private PImage currentSprite;
  
  /* The horizontal displacement to the target (by columns of cells).*/
  private int colDisplacement;
  
  /* The vertical displacement to the target (by rows of cells). */
  private int rowDisplacement;
  
  /* List containing lengths (in seconds) of chase and scatter modes for Ghosts.*/
  private List<Integer> modeLengths;
  
  /* Arbitrary counters; used to control time sensitive actions in tick(). */
  private Map<String, Integer> counters;
  
  /* Used to represent the number of shifts done to the modeLengths list. */
  private int modeShifts;
  
  /* Representing whether debug line is toggled on or off. */
  private boolean debug;
  
  /* The horizontal pixel location of the target. */
  private int xTarget;
  
  /* The vertical pixel location of the target. */
  private int yTarget;
  
  /* The row occupied by the Waka. */
  private int wakaRow;
  
  /* The column occupied by the Waka. */
  private int wakaCol;
  
  /* List containing all chasers in the game. */
  private List<Ghost> chasers;

  /* The current mode the ghost is in. */
  private Mode currentMode;

  /* The length of the frightened mode. */
  private int frightenedLength;

  /* The length of the invisible mode. */
  private int invisLength;


  /**
   * For invocation by subclass constructors of Ambusher, Chaser, Ignorant and Whim.
   * @param spawn Spawnpoint of Ghost.
   * @param config Config parser for the game.
   */
  public Ghost(Spawn spawn, Config config) {
    super(spawn, config);
    this.normalSprite = null;
    this.currentSprite = null;
    this.colDisplacement = 0;
    this.rowDisplacement = 0;
    this.xTarget = 0;
    this.yTarget = 0;
    this.wakaCol = 0;
    this.wakaRow = 0;
    this.counters = new HashMap<String, Integer>();
    this.counters.put("scatter-chase", 1);
    this.counters.put("frightened", 1);
    this.counters.put("invisible", 1);
    this.frightenedLength = config.getFrightenedLength();
    this.invisLength = config.getInvisLength();
    this.modeShifts = 0;
    this.debug = false;

    /* Converts modeLengths from int[] to List<Integer>. */
    this.modeLengths = Arrays.stream(config.getModeLengths()).boxed().collect(Collectors.toList());
    this.chasers = null;
    this.currentMode = GameManager.MODES.get("scatter");
    this.alive = true;
  }
  
  /**
   * Returns the row the ghost is targeting.
   * @return the row the ghost is targeting
   */
  public int getTargetRow() {
    return this.targetRow;
  }

  /**
   * Sets the row the ghost is targeting.
   * @param row row the ghost is targeting
   */
  public void setTargetRow(int row) {
    this.targetRow = row;
  }

  /**
   * Returns the column the ghost is targeting.
   * @return the column the ghost is targeting
   */
  public int getTargetCol() {
    return this.targetCol;
  }

  /**
   * Sets the column the ghost is targeting.
   * @param col column the ghost is targeting
   */
  public void setTargetCol(int col) {
    this.targetCol = col;
  }

  /**
   * Returns the current sprite loaded by the Ghost.
   * @return Ghost's current sprite
   */
  public PImage getCurrentSprite() {
    return this.currentSprite;
  }

  /**
   * Returns the x-pixel position target of the ghost.
   * @return x-pixel position target of the ghost
   */
  public int getXTarget() {
    return this.xTarget;
  }

  /**
   * Returns the y-pixel position target of the ghost.
   * @return y-pixel position target of the ghost
   */
  public int getYTarget() {
    return this.yTarget;
  }

  /**
   * Updates list containing Chasers.
   * @param game Central game control unit
   */
  public void updateChaserInfo(GameManager game) {
    this.chasers = game.getChasers();
  }

  /**
   * Returns list containing Chaser objects.
   * @return List containing Chasers
   */
  public List<Ghost> getChasers() {
    return this.chasers;
  }

  /**
   * Returns the name of the current mode of the ghost.
   * @return the name of the current mode of the ghost
   */
  public String getCurrentModeName() {
    return this.currentMode.getName();
  }

  /**
   * Updates row position of Waka with respect to multi-dimensional array of cells.
   * @param row row position of Waka
   */
  public void setWakaRow(int row) {
    this.wakaRow = row;
  }

  /**
   * Updates column position of Waka with respect to multi-dimensional array of cells.
   * @param col column position of Waka
   */
  public void setWakaCol(int col) {
    this.wakaCol = col;
  }


  /**
   * Sets the horizontal pixel position of the target.
   * @param x x-pixel position of target
   */
  public void setXTarget(int x) {
    this.xTarget = x;
  }

  /**
   * Sets the vertical pixel position of the target.
   * @param y y-pixel position of target
   */
  public void setYTarget(int y) {
    this.yTarget = y;
  }

  /**
   * Returns whether ghost is alive.
   * @return true if ghost is alive, false otherwise.
   */
  public boolean isAlive() {
    return this.alive;
  }

  /**
   * Sets the alive status of the ghost.
   * @param alive parameter representing if alive is to be set to true or false.
   */
  public void setAlive(boolean alive) {
    this.alive = alive;
  }

  /**
   * Sets the column displacement of Waka with respect to the column position
   * of the Ghost.
   * @param col column displacement to Waka
   */
  public void setColDisplacement(int col) {
    this.colDisplacement = col;
  }

  /**
   * Sets the row displacement of Waka with respect to the column position
   * of the Ghost.
   * @param row row displacement to Waka
   */
  public void setRowDisplacement(int row) {
    this.rowDisplacement = row;
  }

   /**
   * Returns the column displacement between the Ghost and its target.
   * @return column displacement
   */
  public int getColDisplacement() {
    return this.colDisplacement;
  }

  /**
   * Returns the row displacement between the Ghost and its target.
   * @return row displacement
   */
  public int getRowDisplacement() {
    return this.rowDisplacement;
  }

  /**
   * Returns the list containing mode lengths of scatter and chase for the ghost.
   * @return the list containing mode lengths of scatter and chase
   */
  public List<Integer> getModeLengths() {
    return this.modeLengths;
  }

  /**
   * Sets the normal sprite to a PImage object passed as parameter.
   * @param app Application used to run game
   * @param imagefile Name of file containing Ghost sprite
   */
  public void setNormalSprite(PApplet app, String imagefile) {
    this.normalSprite = app.loadImage(imagefile);
  }

  /**
   * Sets the current sprite to a PImage object passed as parameter.
   * @param app Application used to run game
   * @param imagefile Name of file containing Ghost sprite
   */
  public void setCurrentSprite(PApplet app, String imagefile) {
    this.currentSprite = app.loadImage(imagefile);
  }

  /**
   * Sets the name of the mode for the ghost.
   * @param modename the name of the mode to be set
   */
  public void setCurrentMode(String modename) {
    this.currentMode = GameManager.MODES.get(modename);
  }

  /**
   * Toggles debug mode on or off. 
   */
  public void toggleDebug() {
    this.debug = !this.debug;
  }

  /**
   * Returns the status of debug mode.
   * @return true if in debug mode, false otherwise.
   */
  public boolean isDebug() {
    return this.debug;
  }

  /**
   * Updates and checks for multiple conditions every frame. Checks if Ghost collides with the
   * Waka, updates the target behaviour, updates the direction of the Ghost if at an intersection
   * and changes coordinates accordingly.
   */
  public void tick() {
    this.isOnWaka();
    this.updateMode();
    if (this.atIntersection() && (this.getXOffset() == 9 && this.getYOffset() == 9)) {
      this.action();
    } else if (this.atDeadEnd() && (this.getXOffset() == 9 && this.getYOffset() == 9)){ 
      this.deadEndAction();
    }
    this.updateCoords();
  }

  /**
   * Draws images and debug line if in debug mode.
   * @param app Application used to run game.
   */
  public void draw(PApplet app) {
    if (this.alive) {
      
      /* Sets colour of debug line to white. */
      app.stroke(255);

      /* Offset is required to draw the sprite in the correct position. */
      app.image(this.currentSprite, this.getX() + 8, this.getY() + 8);
    
      /* Draws debug line from the centre of the ghost sprite to the centre of the
      * target cell. */
      if (this.debug) {
        if (this.currentMode.getName().equals("chase")) {
          /* When we shift the x and y pixel positions to register as the centre of the ghost 
           * sprite in setup() we apply an offset of 8 pixels to draw them in their correct 
           * positions. The pixel position of images used is the top left. So, to get the necessary
           * offset to the centre of the Ghost, we offset by 8 to get to the top left corner and 
           * add half of the width and height of the Ghost sprites to draw to the centre. */
          app.line(this.getX() + 22, this.getY() + 22, this.xTarget + 22, this.yTarget + 22);
      
        /* If the ghost isn't in chase mode, it will be targeting a corner of the application box. 
         * We do not need to apply an offset because there is no initial offset done to the pixel
         * locations of the application box's corners. */
        } else {
          app.line(this.getX() + 22, this.getY() + 22, this.xTarget, this.yTarget);
        }
      }
    }
  }

  /**
   * Updates Ghost mode depending on their lengths as specified
   * in the config file, and applies their associated properties.
   */
  public void updateMode() {
    if (this.currentMode.getName().equals("chase") || 
            this.currentMode.getName().equals("scatter")) {
      
      /* If the number of shifts made to the modeLengths list is equal to the size, the 
       * list has shifted back to its original position. In this case, the first mode
       * should remain as scatter. */
      if (modeShifts == this.modeLengths.size()) {
        this.currentMode = GameManager.MODES.get("scatter");
      }

      /* If the counter is equal to the required number of frames per second, we can
       * change the mode of the Ghost. */
      if (this.counters.get("scatter-chase") == (this.modeLengths.get(0) * 60)) {
        this.currentMode = GameManager.MODES.get(this.currentMode.getAlternateModeName());
        /* First element of modeLengths is removed and pushed to the back of the queue. */
        this.modeLengths.add(this.modeLengths.remove(0));
        this.modeShifts++;

        /* Reset the counter to 1. */
        this.counters.put("scatter-chase", 1);

      /* Otherwise, we increment the respective counter. */
      } else {
        this.counters.put("scatter-chase", this.counters.get("scatter-chase") + 1);
      }
    } else if (this.currentMode.getName().equals("frightened")) {  
      
      /* Updates the sprite for frightened mode. */
      this.currentSprite = GameManager.MODES.get("frightened").getModeSprite();
      
      /* If the counter is equal to the required number of frames per second, we can
       * change the mode of the Ghost. */
      if (this.counters.get("frightened") == (this.frightenedLength * 60)) {
        this.counters.put("frightened", 1);
        this.counters.put("invisible", 1);
        this.currentSprite = this.normalSprite;
        
        /* Depending on the number of modeshifts done, we can determine
         * what the last mode was before frightened, and shift back to it, with
         * the remaining time added on. */
        if (this.modeShifts % 2 == 0) {
          this.currentMode = GameManager.MODES.get("scatter");
        } else {
          this.currentMode = GameManager.MODES.get("chase");
        }

      /* Oscillating between frightened and normal sprites when it gets close to
       * the end of the mode to let the player know when the mode is about to be finished. */
      } else if (this.counters.get("frightened") > ((this.frightenedLength - 2)* 60)) {
        if (this.counters.get("frightened") % 20 > 10) {
          this.currentSprite = this.normalSprite;
        } else {
          this.currentSprite = GameManager.MODES.get("frightened").getModeSprite();
        }
        this.counters.put("frightened", this.counters.get("frightened") + 1);
      
      /* Otherwise, we can add to the respective counter. */
      } else {
        this.counters.put("frightened", this.counters.get("frightened") + 1);
      }

    /* We do similar for invisible mode. */
    } else if (this.currentMode.getName().equals("invisible")) {
      
      this.currentSprite = GameManager.MODES.get("invisible").wavy(this.counters.get("invisible"));

      
      if (this.counters.get("invisible") == (this.invisLength * 60)) {
        this.counters.put("frightened", 1);
        this.counters.put("invisible", 1);
        this.currentSprite = this.normalSprite;

        if (this.modeShifts % 2 == 0) {
          this.currentMode = GameManager.MODES.get("scatter");
        } else {
          this.currentMode = GameManager.MODES.get("chase");
        }
      } else if (this.counters.get("invisible") > ((this.invisLength - 2)* 60)) {
        if (this.counters.get("invisible") % 20 > 10) {
          this.currentSprite = this.normalSprite;
        } else {
          this.currentSprite = GameManager.MODES.get("invisible").getModeSprite();
        }
        this.counters.put("invisible", this.counters.get("invisible") + 1);
      } else {
        this.counters.put("invisible", this.counters.get("invisible") + 1);
      }
    }
  }

  /**
   * Checks if the Waka is on the same cell as the Ghost.
   * @return true if they occupy the same tile, and false otherwise.
   */
  public boolean isOnWaka() {
    if ((this.wakaRow == this.getRow()) && (this.wakaCol == this.getCol())) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Resets all values to their initial states.
   */
  public void reset() {
    /* Initial direction is left. */
    this.setXVel(-1 * this.getSpeed());
    this.setYVel(0);
    this.setCurrentMove(LEFT);

    /* Ghost position is reset to its spawnpoint. */
    this.setX(this.getSpawn().getX() - (this.normalSprite.width)/2);
    this.setY(this.getSpawn().getY() - (this.normalSprite.height)/2);
    this.setCol(this.getSpawn().getCol());
    this.setRow(this.getSpawn().getRow());
    this.setCurrentCell(this.getSpawn());
    this.setXOffset(9);
    this.setYOffset(9);

    /* Shifting the modeLengths list back to its original position. */
    Collections.rotate(this.modeLengths, this.modeShifts);
    this.currentMode = GameManager.MODES.get("scatter");
    this.currentSprite = this.normalSprite;
    this.modeShifts = 0;
    for (String s : this.counters.keySet()) {
      this.counters.put(s, 0);
    }
    this.alive = true;
  }

  /**
   * Sets the x and y pixel position targets, the column displacements and target column and row
   * when the ghost is in scatter mode.
   * @param corner the scatter target corner for the ghost.
   */
  public void scatterTarget(Corner corner) {
    this.setXTarget(corner.getX());
    this.setYTarget(corner.getY());
    this.setColDisplacement(corner.getCol() - this.getCol());
    this.setRowDisplacement(corner.getRow() - this.getRow());
    this.setTargetCol(corner.getCol());
    this.setTargetRow(corner.getRow());
  }

  /**
   * Sets random x and y pixel position targets, which are used to set the row and column
   * dislacements used to control the directions which the ghost travels in.
   */
  public void randomTarget() {
    Random r = new Random();
    int xRand = r.nextInt(App.WIDTH + 1);
    int yRand = r.nextInt(App.HEIGHT + 1);
    this.setXTarget(xRand);
    this.setYTarget(yRand);
    this.setColDisplacement(xRand/16 - this.getCol());
    this.setRowDisplacement(yRand/16 - this.getRow());
  }

  /**
   * Determines the movement direction the ghost should take if at a dead end.
   */
  public void deadEndAction() {
    if (this.getCurrentMove() == LEFT) {
      this.move(RIGHT);
    } else if (this.getCurrentMove() == RIGHT) {
      this.move(LEFT);
    } else if (this.getCurrentMove() == UP) {
      this.move(DOWN);
    } else {
      this.move(UP);
    }
  }

  /**
   * Detects if the ghost is at a dead end. 
   * @return true if at a dead end, and false otherwise
   */
  public boolean atDeadEnd() {
    if (this.getCurrentMove() == LEFT && 
            this.getCells()[this.getRow() + 1][this.getCol()].isSolid() &&
            this.getCells()[this.getRow() - 1][this.getCol()].isSolid() &&
            this.getCells()[this.getRow()][this.getCol() - 1].isSolid()) {
      
      return true;
    } else if (this.getCurrentMove() == RIGHT && 
            this.getCells()[this.getRow() + 1][this.getCol()].isSolid() &&
            this.getCells()[this.getRow() - 1][this.getCol()].isSolid() &&
            this.getCells()[this.getRow()][this.getCol() + 1].isSolid()) {
      
      return true;
    } else if (this.getCurrentMove() == UP && 
            this.getCells()[this.getRow()][this.getCol() + 1].isSolid() &&
            this.getCells()[this.getRow()][this.getCol() - 1].isSolid() &&
            this.getCells()[this.getRow() - 1][this.getCol()].isSolid()) {

      return true;
    } else if (this.getCurrentMove() == DOWN && 
            this.getCells()[this.getRow()][this.getCol() + 1].isSolid() &&
            this.getCells()[this.getRow()][this.getCol() - 1].isSolid() &&
            this.getCells()[this.getRow() + 1][this.getCol()].isSolid()) {
      
      return true;
    } else {
      return false;
    }
  }

  /**
   * Checks if Ghost is at an intersection.
   * @return true if the Ghost is at an intersection, and false otherwise.
   */
  public boolean atIntersection() {
    /* If moving horizontally and there are no walls above and below the Ghost,
     * it is at an intersection. */
    if ((this.getCurrentMove() == RIGHT || this.getCurrentMove() == LEFT) &&
            (!this.getCells()[this.getRow() + 1][this.getCol()].isSolid() ||
            !this.getCells()[this.getRow() - 1][this.getCol()].isSolid())) {
      return true;
    /* If moving vertically and there are no walls to the right and left of the Ghost,
     * it is at an intersection. */
    } else if ((this.getCurrentMove() == UP || this.getCurrentMove() == DOWN) &&
            (!this.getCells()[this.getRow()][this.getCol() + 1].isSolid() ||
            !this.getCells()[this.getRow()][this.getCol() - 1].isSolid())) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Changes the direction of the Ghost at an intersection when there is only
   * one possible path present.
   */
  public void onePath() {
    if (!this.getCells()[this.getRow() + 1][this.getCol()].isSolid() && 
            this.getCurrentMove() != UP) {
      this.move(DOWN);
    } else if (!this.getCells()[this.getRow() - 1][this.getCol()].isSolid() && 
            this.getCurrentMove() != DOWN) {
      this.move(UP);
    } else if (!this.getCells()[this.getRow()][this.getCol() - 1].isSolid() && 
            this.getCurrentMove() != RIGHT) {
      this.move(LEFT);
    } else if (!this.getCells()[this.getRow()][this.getCol() + 1].isSolid() && 
            this.getCurrentMove() != LEFT) {
      this.move(RIGHT);
    } 
  }
  
  /**
   * Performs AI-based movement towards the target location (if possible). Uses the row and column
   * displacements of the Ghost to its target to determine the move that would minimise the 
   * distance to the target.
   */
  public void action() {    
    /* We get the signs of horizontal and vertical displacement. */
    int xSign = (int) Math.signum(this.colDisplacement);
    int ySign = (int) Math.signum(this.rowDisplacement);

    /* It is only possible to change directions when in the middle of a cell. */
    if (this.getXOffset() == 9 && this.getYOffset() == 9) {
      
      /* We prioritise horizontal movement, since x-displacement is larger than
       * y-displacement. */
       if (Math.abs(this.colDisplacement) >= Math.abs(this.rowDisplacement)) {
        
        /* If the cell in the direction towards the target is free, xSign is not 
         * equal to zero and the current direction would not be opposite to the direction
         * towards the target (to prevent changing directions opposite to the previous direction),
         * we can move towards the target. */
        if (!this.getCells()[this.getRow()][this.getCol() + xSign].isSolid() && 
                xSign != 0 && this.getCurrentMove() != (-1) * xSign + 38) {
          this.move(38 + xSign);

        /* If we can't move in the direction that would minimise the x-displacement, we check
         * if we can move towards the target in the y-direction using the same logic as above. */
        } else if (!this.getCells()[this.getRow() + ySign][this.getCol()].isSolid() && 
                ySign != 0 && this.getCurrentMove() != (-1) * ySign + 39) {
          this.move(39 + ySign);

        /* If none of the above options persist, it must mean that there is only one move 
         * that is possible which is not towards the target, so we call onePath(). */
        } else {
          this.onePath();
        }
      
      /* We repeat the same logic if the vertical displacement is larger than the horizontal
       * displacement. */
      } else if (Math.abs(this.rowDisplacement) > Math.abs(this.colDisplacement)) {
        if (!this.getCells()[this.getRow() + ySign][this.getCol()].isSolid() && 
                ySign != 0 && this.getCurrentMove() != ((-1) * ySign + 39)) {
          this.move(39 + ySign);
        } else if (!this.getCells()[this.getRow()][this.getCol() + xSign].isSolid() && 
                xSign != 0 && this.getCurrentMove() != ((-1) * xSign + 38)) {
          this.move(38 + xSign);
        } else {
          this.onePath();
        }
      /* Note that we do not need a separate equals case, since if xDisplacement and yDisplacement
       * were to be equal, there is still no 'best' order to consider moves based on 
       * directions. */
      }
    }
  }

  /**
   * Changes velocities to correspond to the arrow key passed into the function.
   * @param keyCode Integer corresponding to arrow key.
   */
  public void move(int keyCode) {
    if (keyCode == LEFT) {
      this.setXVel(-1 * this.getSpeed());
      this.setYVel(0);
      this.setCurrentMove(LEFT);
    } else if (keyCode == UP) {
      this.setXVel(0);
      this.setYVel(-1 * this.getSpeed());
      this.setCurrentMove(UP);
    } else if (keyCode == RIGHT) {
      this.setXVel(1 * this.getSpeed());
      this.setYVel(0);
      this.setCurrentMove(RIGHT);
    } else if (keyCode == DOWN) {
      this.setXVel(0);
      this.setYVel(1 * this.getSpeed()); 
      this.setCurrentMove(DOWN);
    }
  }

  /**
   * Updates the location of the target if the Ghost is in chase mode
   * or scatter mode.
   * @param waka Waka object in the game.
   */
  public abstract void updateTargetLocation(Waka waka);

  /**
   * Sets predefined targets and displacements for Ghost's chase mode. 
   * @param waka Waka object in the game
   */
  public abstract void chaseMode(Waka waka);
}
