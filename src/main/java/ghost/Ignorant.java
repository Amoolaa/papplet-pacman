package ghost;

import processing.core.PApplet;

import java.lang.Math;

/**
 * Object representing the Ignorant ghost type. Contains different sprite and instructions
 * for chase and scatter modes.
 * 
 * In chase mode, Ignorant targets the Waka if it is more than 8 grid spaces
 * away from the Waka, and otherwise targets the bottom left corner. 
 * It targets the top left corner of the application box in scatter mode.
 */
public class Ignorant extends Ghost{
  
  private Corner scatterTarget;

  /**
   * Creates an Ignorant object. Sets the scatter target to the bottom left corner
   * of the application box.
   * @param spawn spawnpoint of Ignorant
   * @param config JSON config parser containing map information
   */
  public Ignorant(Spawn spawn, Config config) {
    super(spawn, config);
    this.scatterTarget = Corner.BOTTOMLEFT;
  }

  /**
   * Loads Ignorant sprites.
   * @param app Application used to run game.
   */
  public void setup(PApplet app) {
    this.setNormalSprite(app, "ignorant.png");
    this.setCurrentSprite(app, "ignorant.png");
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
   * Sets predefined rules for Ignorant's target in chase mode. 
   * @param waka Waka object in the game.
   */
  public void chaseMode(Waka waka) {
    double distance = Math.hypot(waka.getX() - this.getX(), waka.getY() - this.getY());
    
    /* If distance to Waka is larger than 8 units, Ignorant should target the Waka. */
    if (distance > 8 * 16) {
      this.setXTarget(waka.getX());
      this.setYTarget(waka.getY());
      this.setColDisplacement(waka.getCol() - this.getCol());
      this.setRowDisplacement(waka.getRow() - this.getRow());
      this.setTargetCol(waka.getCol());
      this.setTargetRow(waka.getRow());
    
    /* Otherwise, Ignorant should target the bottom left corner. We need to apply an offset here - 
     * since in chase mode there is a centering offset applied, we need to decrease this to push
     * the x and y pixel position targets to the corner of the application box. */
    } else {
      this.setXTarget(this.scatterTarget.getX() - 22);
      this.setYTarget(this.scatterTarget.getY() - 22);
      this.setColDisplacement(this.scatterTarget.getCol() - this.getCol());
      this.setRowDisplacement(this.scatterTarget.getRow() - this.getRow());
      this.setTargetCol(this.scatterTarget.getCol());
      this.setTargetRow(this.scatterTarget.getRow());
    }
  }
}