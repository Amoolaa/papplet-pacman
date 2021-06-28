package ghost;

/**
 * Represents the scatter mode exhibited by Ghosts. It is paired with the chase mode.
 */
public class Scatter extends Mode {
  /**
   * Creates a new Scatter mode object.
   * @param alternate string containing the alternate mode
   */
  public Scatter(String alternate) {
    super();
    this.setAlternateModeName(alternate);
    this.setName("scatter");
  }
}
