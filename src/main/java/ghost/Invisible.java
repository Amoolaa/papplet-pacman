package ghost;

/**
 * Represents the invisible mode exhibited by Ghosts when a sodacan is eaten by a Waka.
 */
public class Invisible extends Mode {
  /**
   * Creates a new Invisible mode object.
   * @param imagefile string containing the associated sprite filename
   */
  public Invisible(String imagefile) {
    super(imagefile);
    this.setName("invisible");
  }
}
