package ghost;

import processing.core.PApplet;
import java.util.Comparator;
import java.util.List;
import java.lang.Math;

/**
 * Object representing the Whim ghost type. Contains different sprite and instructions
 * for chase and scatter modes. 
 * 
 * In chase mode, Whim targets double the vector from the closest 
 * Chaser to 2 grid spaces ahead of Waka. In scatter mode, Whim targets the bottom right
 * corner of the application box.
 */
public class Whim extends Ghost {
   
  /* Corner targeted in scatter mode. */
  private Corner scatterTarget;
  
  /* Chaser that is used as target. */
  private Ghost targetChaser;

  /**
   * Creates a Whim object. Sets the scatter target to the bottom right corner
   * of the application box and initialises the Chaser target.
   * @param spawn spawnpoint of Whim
   * @param config JSON config parser containing map information
   */
  public Whim(Spawn spawn, Config config) {
    super(spawn, config);
    this.scatterTarget = Corner.BOTTOMRIGHT;
    this.targetChaser = null;
  }

  /**
   * Loads Whim sprites.
   * @param app Application used to run game.
   */
  public void setup(PApplet app) {
    this.setNormalSprite(app, "whim.png");
    this.setCurrentSprite(app, "whim.png");
    /* The x and y pixel positions of each image represent the top left corner. This is
     * not effective when representing all cardinal directions, so we align to the centre 
     * of the image. Since the dimensions of the sprites change based on direction, we
     * fix the offset relative to the current sprite dimensions. */
    this.setX(this.getX() - (this.getCurrentSprite().width)/2);
    this.setY(this.getY() - (this.getCurrentSprite().height)/2);
  }


  /**
   * Updates the location of the target if the Ghost is in chase mode
   * or scatter mode. Whims target double the vector from the closest chaser to two
   * spaces ahead of the Waka, based on its direction.
   * @param waka Waka object in the game
   */
  public void updateTargetLocation(Waka waka) {
    this.setWakaRow(waka.getRow());
    this.setWakaCol(waka.getCol());
    if (this.getCurrentModeName().equals("chase")) {
      this.chaseMode(waka);
    } else if (this.getCurrentModeName().equals("scatter")) {
      /* In this case, the scatter target becomes one predefined corner of the
       * application box. */
      this.scatterTarget(this.scatterTarget);
    } else if (this.getCurrentModeName().equals("frightened")) {
      this.randomTarget();
    
    /* Invis case. */
    } else {
      this.randomTarget();
    }
  }

  /**
   * Sets predefined targets and displacements for Whim's chase mode. 
   * @param waka Waka object in the game
   */
  public void chaseMode(Waka waka) {
    /* Updates the closest Chaser to the Whim. */
    this.closestChaser(this.getChasers());

    /* We split into cases by the direction the Waka is facing. */
    if (waka.getCurrentMove() == LEFT) {
      /* We add horizontal and vertical components of the target vector to
       * the Chaser's coordinates. */
      int xTarget = this.targetChaser.getX() + 2*(waka.getX() - 2*16 - this.targetChaser.getX());
      int yTarget = this.targetChaser.getY() + 2*(waka.getY() - this.targetChaser.getY());

      /* We set the pixel positions for the target, applying the required offset to draw the line
       * relative to the Whim's centre and bounding by the edges of the application box. */
      this.setXTarget(Player.bounded(xTarget + 22, App.WIDTH, 0) - 22);
      this.setYTarget(Player.bounded(yTarget + 22, App.HEIGHT, 0) - 22);

      /* We find the target column and rows by using the same formula as used for the target
       * x and y pixel coordinates above, but using grid spaces rather than pixels. */
      int targetCol = this.targetChaser.getCol() + 
              2*(waka.getCol() - 2 - this.targetChaser.getCol());
      
      int targetRow = this.targetChaser.getRow() + 
              2*(waka.getRow() - this.targetChaser.getRow());
      
      this.setTargetCol(targetCol);
      this.setTargetRow(targetRow);
      /* We bound the displacements in both orientations between the dimensions of the application
       * box. Since displacement can be negative, our lower bounds are the same as the upper bound
       * but with the opposite sign. */
      this.setColDisplacement(Player.bounded(targetCol - this.getCol(), 35, -35));
      this.setRowDisplacement(Player.bounded(targetRow - this.getRow(), 27, -27));

    /* We repeat the process for all other cardinal directions. */
    } else if (waka.getCurrentMove() == RIGHT) {
      int xTarget = this.targetChaser.getX() + 2*(waka.getX() + 2*16 - this.targetChaser.getX());
      int yTarget = this.targetChaser.getY() + 2*(waka.getY() - this.targetChaser.getY());
      
      this.setXTarget(Player.bounded(xTarget + 22, App.WIDTH, 0) - 22);
      this.setYTarget(Player.bounded(yTarget + 22, App.HEIGHT, 0) - 22);

      int targetCol = this.targetChaser.getCol() + 
              2*(waka.getCol() + 2 - this.targetChaser.getCol());
      
      int targetRow = this.targetChaser.getRow() + 
              2*(waka.getRow() - this.targetChaser.getRow());

      this.setTargetCol(targetCol);
      this.setTargetRow(targetRow);
      
      this.setColDisplacement(Player.bounded(targetCol - this.getCol(), 35, -35));
      this.setRowDisplacement(Player.bounded(targetRow - this.getRow(), 27, -27));
    } else if (waka.getCurrentMove() == UP) {
      int xTarget = this.targetChaser.getX() + 2*(waka.getX() - this.targetChaser.getX());
      int yTarget = this.targetChaser.getY() + 2*(waka.getY() - 2*16 - this.targetChaser.getY());
      
      this.setXTarget(Player.bounded(xTarget + 22, App.WIDTH, 0) - 22);
      this.setYTarget(Player.bounded(yTarget + 22, App.HEIGHT, 0) - 22);

      int targetCol = this.targetChaser.getCol() + 
              2*(waka.getCol() - this.targetChaser.getCol());
      
      int targetRow = this.targetChaser.getRow() + 
              2*(waka.getRow() - 2 - this.targetChaser.getRow());
      
      this.setTargetCol(targetCol);
      this.setTargetRow(targetRow);
      
      this.setColDisplacement(Player.bounded(targetCol - this.getCol(), 35, -35));
      this.setRowDisplacement(Player.bounded(targetRow - this.getRow(), 27, -27));
    } else {
      int xTarget = this.targetChaser.getX() + 2*(waka.getX() - this.targetChaser.getX());
      int yTarget = this.targetChaser.getY() + 2*(waka.getY() + 2*16 - this.targetChaser.getY());
      
      this.setXTarget(Player.bounded(xTarget + 22, App.WIDTH, 0) - 22);
      this.setYTarget(Player.bounded(yTarget + 22, App.HEIGHT, 0) - 22);

      int targetCol = this.targetChaser.getCol() + 
              2*(waka.getCol() - this.targetChaser.getCol());
      
      int targetRow = this.targetChaser.getRow() + 
              2*(waka.getRow() + 2 - this.targetChaser.getRow());

      this.setTargetCol(targetCol);
      this.setTargetRow(targetRow);
      
      this.setColDisplacement(Player.bounded(targetCol - this.getCol(), 35, -35));
      this.setRowDisplacement(Player.bounded(targetRow - this.getRow(), 27, -27));
    }
  }

  /**
   * Sets the target chaser to the closest chaser by minimising straight line distance.
   * @param chasers List containing all chasers in the game
   */
  public void closestChaser(List<Ghost> chasers) {
    this.targetChaser = chasers.stream().min(
            Comparator.comparing(chaser -> 
            (Math.hypot(chaser.getX() - this.getX(), chaser.getY() - this.getY())))).get();
  }
}