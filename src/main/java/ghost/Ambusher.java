package ghost;

import processing.core.PApplet;

/**
 * Object representing the Ambusher ghost type. Contains different sprite and instructions
 * for chase and scatter modes.
 * 
 * Chaser targets four grid spaces ahead of the Waka with regards to the direction the Waka
 * is facing, and targets the top right corner of the application box in scatter mode.
 */
public class Ambusher extends Ghost{
  
  private Corner scatterTarget;
  
  /**
   * Creates an Ambusher object. Sets the scatter target to the top right corner of the application
   * box.
   * @param spawn spawnpoint of Ambusher
   * @param config JSON config parser containing map information
   */
  public Ambusher(Spawn spawn, Config config) {
    super(spawn, config);
    this.scatterTarget = Corner.TOPRIGHT;
  }

  /**
   * Loads Ambusher sprites.
   * @param app Application used to run game.
   */
  public void setup(PApplet app) {
    this.setNormalSprite(app, "ambusher.png");
    this.setCurrentSprite(app, "ambusher.png");
    /* The x and y pixel positions of each image represent the top left corner. This is
     * not effective when representing all cardinal directions, so we align to the centre 
     * of the image. Since the dimensions of the sprites change based on direction, we
     * fix the offset relative to the current sprite dimensions. */
    this.setX(this.getX() - (this.getCurrentSprite().width)/2);
    this.setY(this.getY() - (this.getCurrentSprite().height)/2);
  }


  /**
   * Updates the location of the target if the Ghost is in chase mode
   * or scatter mode. Ambushers target four grid spaces ahead of the Waka object in chase mode, 
   * and target the top right corner of the application box in scatter mode.
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
   * Sets predefined rules for Ambusher's target in chase mode. 
   * @param waka Waka object in the game.
   */
  public void chaseMode(Waka waka) {
    /* We split into move-wise cases, since this affects the direction we increment the four
     * grid spaces. */
    if (waka.getCurrentMove() == LEFT) {
      /* If the Waka is moving left, we must subtract four units from the pixel count. We must
       * also bound the pixel location and offset it by the required amount, since xTarget
       * is used to draw the line and is offset by +22 in Ghost's draw() method. */
      this.setXTarget(Player.bounded(waka.getX() - 16 * 4 + 22, App.WIDTH, 0) - 22);
      
      /* yTarget is left as is, since we are not concerned with the Waka's y-position when
       * moving horizontally. */
      this.setYTarget(waka.getY());

      /* We subtract 4 units from the Waka's current column (since it is moving leftwards),
       * and find the horizontal displacement. This is also bounded between the columns of the
       * application box. */
      int targetCol = Player.bounded(waka.getCol() - 4, 27, 0);
      this.setColDisplacement(targetCol - this.getCol());
      this.setTargetCol(targetCol);
      
      /* Row displacement is left as is. */
      int targetRow = waka.getRow();
      this.setRowDisplacement(targetRow - this.getRow());
      this.setTargetRow(targetRow);

    } else if (waka.getCurrentMove() == RIGHT) {
      /* We do similar if the Waka moves rightwards. */
      this.setXTarget(Player.bounded(waka.getX() + 16 * 4 + 22, App.WIDTH, 0) - 22);
      this.setYTarget(waka.getY());

      int targetCol = Player.bounded(waka.getCol() + 4, 27, 0);
      this.setColDisplacement(targetCol - this.getCol());
      this.setTargetCol(targetCol);

      int targetRow = waka.getRow();
      this.setRowDisplacement(targetRow - this.getRow());
      this.setTargetRow(targetRow);
    
    /* For up and down, rather than altering horizontal attributes, we work with the vertical
     * attributes (y and row). */
    } else if (waka.getCurrentMove() == UP) {
      this.setXTarget(waka.getX());
      this.setYTarget(Player.bounded(waka.getY() - 16 * 4 + 22, App.HEIGHT, 0) - 22);

      int targetCol = waka.getCol();
      this.setColDisplacement(targetCol - this.getCol());
      this.setTargetCol(targetCol);
      
      int targetRow = Player.bounded(waka.getRow() - 4, 35, 0);
      this.setRowDisplacement(targetRow - this.getRow());
      this.setTargetRow(targetRow);

    } else if (waka.getCurrentMove() == DOWN) {
      this.setXTarget(waka.getX());
      this.setYTarget(Player.bounded(waka.getY() + 16 * 4 + 22, App.HEIGHT, 0) - 22);
      
      int targetCol = waka.getCol();
      this.setColDisplacement(targetCol- this.getCol());
      this.setTargetCol(targetCol);

      int targetRow = Player.bounded(waka.getRow() + 4, 35, 0);
      this.setRowDisplacement(targetRow - this.getRow());
      this.setTargetRow(targetRow);
    }
  }
}