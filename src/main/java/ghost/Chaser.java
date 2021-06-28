package ghost;

import processing.core.PApplet;

/**
 * Object representing the Chaser ghost type. Contains different sprite and instructions
 * for chase and scatter modes.
 * 
 * Chaser targets the Waka's position in chase mode, and targets the top left corner
 * of the application box in scatter mode.
 */
public class Chaser extends Ghost{
  
  private Corner scatterTarget;
  
  /**
   * Creates a Chaser object. Sets the scatter target to the top left corner
   * of the application box.
   * @param spawn spawnpoint of Chaser
   * @param config JSON config parser containing map information
   */
  public Chaser(Spawn spawn, Config config) {
    super(spawn, config);
    this.scatterTarget = Corner.TOPLEFT;
  }

  /**
   * Loads Chaser sprites.
   * @param app Application used to run game.
   */
  public void setup(PApplet app) {
    this.setNormalSprite(app, "chaser.png");
    this.setCurrentSprite(app, "chaser.png");
    /* The x and y pixel positions of each image represent the top left corner. This is
     * not effective when representing all cardinal directions, so we align to the centre 
     * of the image. Since the dimensions of the sprites change based on direction, we
     * fix the offset relative to the current sprite dimensions. */
    this.setX(this.getX() - (this.getCurrentSprite().width)/2);
    this.setY(this.getY() - (this.getCurrentSprite().height)/2);
  }


  /**
   * Updates the location of the target if the Ghost is in chase mode
   * or scatter mode. Chasers target the Waka object in chase mode, and target
   * the top left corner of the application box in scatter mode.
   * @param waka Waka object in the game.
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
   * Sets predefined targets and displacements for Chaser's chase mode. 
   * @param waka Waka object in the game
   */
  public void chaseMode(Waka waka) {
    this.setXTarget(waka.getX());
    this.setYTarget(waka.getY());
    this.setColDisplacement(waka.getCol() - this.getCol());
    this.setRowDisplacement(waka.getRow() - this.getRow());
    this.setTargetCol(waka.getCol());
    this.setTargetRow(waka.getRow());
  }
}
