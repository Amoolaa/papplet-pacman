package ghost;

/**
 * Represents the frightened mode exhibited by Ghosts when a superfruit is eaten by a Waka.
 */
public class Frightened extends Mode {
  /**
   * Creates a new Frightened mode object.
   * @param imagefile string containing the associated sprite filename
   */
  public Frightened(String imagefile) {
    super(imagefile);
    this.setName("frightened");
  }
}
