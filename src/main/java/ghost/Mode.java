package ghost;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * Abstract class representing the different modes the game can take. Modes contain
 * information to do with their sprites and/or behaviours.
 */
public abstract class Mode {
  
  /* Associated sprite for the mode. */
  private PImage modeSprite;
  
  /* Filename for the associated sprite. */
  private String imagefile;

  /* If modes have a paired relationship, this is the name of the alternate mode. */
  private String alternateModeName;
  
  /* Name of the mode. */
  private String name;

  /**
   * For invocation by subclass constructors. 
   * @param imagefile Filename for the associated sprite
   */
  protected Mode(String imagefile) {
    this.imagefile = imagefile;
    this.modeSprite = null;
    this.alternateModeName = null;
    this.name = null;
  }

  /**
   * For invocation by subclass constructors.
   * Used when there is no associated sprite to the mode.
   */
  protected Mode() {
    this.imagefile = null;
    this.modeSprite = new PImage();
    this.alternateModeName = null;
    this.name = null;
  }

  /**
   * Loads associated sprites, if they exist.
   * @param app Application used to run the game.
   */
  public void setup(PApplet app) {
    if (this.imagefile != null) {
      this.modeSprite = app.loadImage(this.imagefile);
    }
  }

  /**
   * Sets the name of the mode.
   * @param name Name of the mode
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the name of the mode.
   * @return Name of the mode
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets the name of the alternate mode (if they have a paired relationship)
   * @param modeName Alternate mode name
   */
  public void setAlternateModeName(String modeName) {
    this.alternateModeName = modeName;
  }

  /**
   * Returns the name of the alternate mode (if they have a paired relationship)
   * @return Alternate mode name
   */
  public String getAlternateModeName() {
    return this.alternateModeName;
  }

  /**
   * Returns the mode's associated sprite.
   * @return Associated sprite for the mode
   */
  public PImage getModeSprite() {
    return this.modeSprite;
  }


  /**
   * Emulates a wavy effect for the associated sprite by resizing the image. 
   * @param counter generic parameter for the cosine function to determine the resizing
   * @return resized image
   */
  public PImage wavy(int counter) {
    PImage temp = this.getModeSprite();

    /* We use the cosine function to produce the oscillatory nature of resizing sprites,
     * so that it appears to look wavy. The range of the function below is between 18 and 28,
     * so that sprites oscillate only a little amount between their original size and their
     * 'squashed' form. */
    temp.resize(this.getModeSprite().width, (int) (18 + 10 * Math.pow(Math.cos(counter/6), 2)));
    return temp;
  }
}
