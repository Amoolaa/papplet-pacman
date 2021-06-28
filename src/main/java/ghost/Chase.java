package ghost;

/**
 * Represents the chase mode exhibited by Ghosts. It is paired with the 
 * scatter mode.
 */
public class Chase extends Mode {
  /**
   * Creates a new Chase mode object.
   * @param alternate string containing the alternate mode
   */
  public Chase(String alternate) {
    super();
    this.setAlternateModeName(alternate);
    this.setName("chase");
  }
}
