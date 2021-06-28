package ghost;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.List;

import java.lang.Math;

/**
 * Object representing the user-controlled character in the game. Contains methods and attributes
 * to implement collision with Wall objects, alternation between open and closed sprites and 
 * interactions with Eatable objects.
 */
public class Waka extends Player {
  /* Represents the last move queued by the user. */
  private int queuedMove;
  
  /* Arbitrary counter; used to control time sensitive actions in tick(). */
  private int counter;
  
  /* The number of fruits eaten by the Waka. */
  private int fruitsEaten;
  
  /* The number of lives left remaining. */
  private int currentLives;
  
  /* Array containing the sprite corresponding to Waka's current direction and the closed sprite.*/
  private PImage[] currentSprites;
  
  /* The sprite to be drawn to the application box. */
  private PImage printedSprite;
  
  /* Array containing all of Waka's sprites. */
  private PImage[] sprites;

  /**
   * Creates a Waka object.
   * @param spawn Spawnpoint of Waka.
   * @param config Config parser for the game.
   */
  public Waka(Spawn spawn, Config config) {
    super(spawn, config);
    this.sprites = new PImage[5];
    this.currentSprites = new PImage[2];
    this.printedSprite = null;
    this.queuedMove = -1;
    this.counter = 0;
    this.fruitsEaten = 0;
    this.currentLives = config.getLives();
  }

  /**
   * Eats a fruit if Waka occupies the same cell has a non-eaten fruit. Updates the game mode if
   * an Eatable object with a corresponding mode is eaten.
   * @param ghosts List containing all Ghosts in the game
   */
  public void eat(List<Ghost> ghosts) {
    /* Waka eats the fruit when it is in the centre of a cell because it would otherwise appear to
     * eat the fruit before/after it has entered the cell. */
    if (this.getXOffset() == 9 || this.getYOffset() == 9) {
      if (this.getCurrentCell().getID() == '7') {
        if (!((Fruit)this.getCurrentCell()).eaten()) {
          
          /* Eat fruit, and increment the number of fruits eaten. */
          ((Fruit)this.getCurrentCell()).eat();
          this.fruitsEaten++;
        }
    } else if (this.getCurrentCell().getID() == '8') {
      if (!((SuperFruit)this.getCurrentCell()).eaten()) {
        ((SuperFruit)this.getCurrentCell()).eat();
        this.fruitsEaten++;
        
        /* We must update the modes of the Ghosts to frightened, since a superfruit was eaten. */
        for (Ghost g : ghosts) {
          g.setCurrentMode("frightened");
        }
      }
    } else if (this.getCurrentCell().getID() == 's') {
      if (!((SodaCan)this.getCurrentCell()).eaten()) {
        ((SodaCan)this.getCurrentCell()).eat();
        this.fruitsEaten++;
        /* We must update the modes of the Ghosts to invisible, since a superfruit was eaten. */
        for (Ghost g : ghosts) {
            g.setCurrentMode("invisible");
          }
        }
      }
    }
  }

  /**
   * Returns array containing the sprite corresponding to Waka's current direction,
   * and the closed sprite.
   * @return array contaning current active sprites
   */
  public PImage[] getCurrentSprite() {
    return this.currentSprites;
  }

  /**
   * Returns array containing all Waka sprites.
   * @return all Waka sprites
   */
  public PImage[] getAllSprites() {
    return this.sprites;
  }

  /**
   * Returns the number of lives left remaining for the Waka object.
   * @return number of lives left remaining
   */
  public int getCurrentLives() {
    return this.currentLives;
  }

  /**
   * Sets the number of lives for the Waka object.
   * @param lives number of lives to be set
   */
  public void setCurrentLives(int lives) {
    this.currentLives = lives;
  }

  /**
   * Returns the number of fruits eaten by the Waka.
   * @return number of fruits eaten
   */
  public int getFruitsEaten() {
    return this.fruitsEaten;
  }

  /**
   * Sets the number of fruits eaten by the Waka.
   * @param fruits Integer which fruitsEaten is to be set to.
   */
  public void setFruitsEaten(int fruits) {
    this.fruitsEaten = fruits;
  }

  /**
   * Switches between open and closed sprites every 8 frames. 
   */
  public void alternatingSprite() {
    this.counter += 1;
    if (this.counter == 8) {
      this.printedSprite = this.currentSprites[1];
    } else if (this.counter == 16) {
      this.counter = 0;
      this.printedSprite = this.currentSprites[0];
    }
  }

  /**
   * Updates and checks for multiple conditions every frame. Updates direction based on
   * queued move, stops movement if no satisfactory move is queued to pass an oncoming wall, 
   * eats fruit when possible, alternates sprites and updates coordinates based on the
   * following conditions.
   */
  public void tick() {
    /* We first change direction based on the queued move (if possible). */
    this.action(); 
    /* If the Waka's direction is facing an oncoming wall and no move was queued to change
     * its direction, the Waka stops at the oncoming wall. */
    if (this.getXOffset() == 9 && this.getYOffset() == 9) {
      if ((this.getCurrentMove() == LEFT && 
              (this.getCells()[this.getRow()][this.getCol() - 1]).isSolid()) || 
              (this.getCurrentMove() == RIGHT && 
              (this.getCells()[this.getRow()][this.getCol() + 1]).isSolid()) ||
              (this.getCurrentMove() == UP && 
              (this.getCells()[this.getRow() - 1][this.getCol()]).isSolid()) ||
              (this.getCurrentMove() == DOWN && 
              (this.getCells()[this.getRow() + 1][this.getCol()]).isSolid())) {
        return;
      }
    }
    this.alternatingSprite();
    this.updateCoords();
  }

  /**
   * Loads images.
   * @param app Application used to run game.
   */
  public void setup(PApplet app) {
    /* Loading each sprite into the array. */
    this.sprites[0] = app.loadImage("playerClosed.png");
    this.sprites[1] = app.loadImage("playerUp.png");
    this.sprites[2] = app.loadImage("playerLeft.png");
    this.sprites[3] = app.loadImage("playerDown.png");
    this.sprites[4] = app.loadImage("playerRight.png");
    
    /* Setting the current sprites to alternate between (open and closed). */
    this.currentSprites[0] = this.sprites[2];
    this.currentSprites[1] = this.sprites[0];

    /* The x and y pixel positions of each image represent the top left corner. This is
     * not effective when representing all cardinal directions, so we align to the centre 
     * of the image. Since the dimensions of the sprites change based on direction, we
     * fix the offset relative to the current sprite dimensions. */
    this.setX(this.getX() - (this.currentSprites[0].width)/2);
    this.setY(this.getY() - (this.currentSprites[0].height)/2);
    this.printedSprite = this.currentSprites[0];
  }

  /**
   * Draws images.
   * @param app Application used to run game.
   */
  public void draw(PApplet app) {
    /* Offset is required to draw the sprite in the correct position. */
    app.image(this.printedSprite, this.getX() + 8, this.getY() + 8);
    
    app.stroke(255);

    /* Drawing the sprites representing the number of lives remaining. i * 28 is used as the
     * x-pixel position since horizontal-facing sprites are 26 pixels long, and the extra
     * 2 units provides aesthetic spacing. 34 * 16 is used as the y-pixel position since
     * the lives should be present in the second-last row from the bottom.*/
    for (int i = 0; i < this.currentLives; i++) {
      app.image(this.sprites[2], i * 28, 34 * 16);
    }
  }

  /**
   * Performs user-queued move if possible.
   */
  public void action() {
    /* If queued move is in the opposite direction to the current move, their difference
     * will always be 2. This type of move is permitted without taking into account
     * collision with neighbouring walls. */
    if (Math.abs(this.queuedMove - this.getCurrentMove()) == 2) {
      this.move(this.queuedMove);
    } else {
      /* Otherwise, for every other possible queued move we check if its possible to move
       * without colliding with a wall. */
      if ((this.queuedMove == RIGHT && 
              !this.getCells()[this.getRow()][this.getCol() + 1].isSolid() ||
              this.queuedMove == LEFT && 
              !this.getCells()[this.getRow()][this.getCol() - 1].isSolid() ||
              this.queuedMove == UP && 
              !this.getCells()[this.getRow() - 1][this.getCol()].isSolid() || 
              this.queuedMove == DOWN && 
              !this.getCells()[this.getRow() + 1][this.getCol()].isSolid()) &&
              this.getXOffset() == 9 && this.getYOffset() == 9) {
        this.move(this.queuedMove);
      }
    }
  }
  /**
   * Queues a user's input.
   * @param keyCode Integer corresponding to arrow key.
   */
  public void queue(int keyCode) {
    this.queuedMove = keyCode;
  }

  /**
   * Resets all values to their initial states.
   */
  public void reset() {
    /* Initial direction is left. */
    this.currentSprites[0] = this.sprites[2];
    this.printedSprite = this.currentSprites[0];
    this.setCurrentMove(LEFT);
    this.setXVel(-1 * this.getSpeed());
    this.setYVel(0);
    this.queuedMove = -1;

    /* Waka position is reset to its spawnpoint. */
    this.setCurrentCell(this.getSpawn());
    this.setX(this.getSpawn().getX() - (this.currentSprites[0].width)/2);
    this.setY(this.getSpawn().getY() - (this.currentSprites[0].height)/2);
    this.setCol(this.getSpawn().getCol());
    this.setRow(this.getSpawn().getRow());
    this.setXOffset(9);
    this.setYOffset(9);
  }

  /**
   * Changes velocities to correspond to the arrow key passed into the function.
   * @param keyCode Integer corresponding to arrow key.
   */
  public void move(int keyCode) {
    if (keyCode == LEFT) {
      this.setXVel(-1 * this.getSpeed());
      this.setYVel(0);
      this.currentSprites[0] = this.sprites[2];
      this.setCurrentMove(LEFT);
    } else if (keyCode == UP) {
      this.setXVel(0);
      this.setYVel(-1 * this.getSpeed());
      this.currentSprites[0] = this.sprites[1];
      this.setCurrentMove(UP);
    } else if (keyCode == RIGHT) {
      this.setXVel(1 * this.getSpeed());
      this.setYVel(0);
      this.currentSprites[0] = this.sprites[4];
      this.setCurrentMove(RIGHT);
    } else if (keyCode == DOWN) {
      this.setXVel(0);
      this.setYVel(1 * this.getSpeed()); 
      this.currentSprites[0] = this.sprites[3];
      this.setCurrentMove(DOWN);
    }
  }
}
