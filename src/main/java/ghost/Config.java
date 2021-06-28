package ghost;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.FileReader;
import java.io.Reader;

/**
 * JSON config parser. Parses config files and gives access to their information
 * for other objects. 
 */
public class Config {
  /* Name of file containg JSON config. */
  private String filename;
  
  /* Name of file containing map information. */
  private String mapfile;
  
  /* Number of lives for the Waka. */
  private int lives;
  
  /* Speed of Waka and Ghosts (pixels per frame) */
  private int speed;
  
  /* Array containing lengths (in seconds) of chase and scatter modes for Ghosts. */
  private int[] modeLengths;

  /* Length of invis mode. */
  private int invisLength;

  /* Length of frightened mode. */
  private int frightenedLength;

  /**
   * Creates a Config object.
   * @param filename Name of file containg JSON config.
   */
  public Config(String filename) {
    this.filename = filename;
    this.mapfile = null;
    this.lives = 0;
    this.speed = 0;
    this.modeLengths = null;
    this.frightenedLength = 0;
  }

  /**
   * Utilises JSON.simple to format JSON config information into variables for use.
   */
  public void formatter() {
    JSONParser parser = new JSONParser();
    JSONObject obj = null;
    Reader reader = null;

    try {
      reader = new FileReader(this.filename);
      obj = (JSONObject) parser.parse(reader);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }

    /* Numerical values must first be cast to long, and then recast to integer. */
    this.mapfile = (String) obj.get("map");
    this.lives = (int) ((long) obj.get("lives"));
    this.speed = (int) ((long) obj.get("speed"));
    this.frightenedLength = (int) ((long) obj.get("frightenedLength"));
    this.invisLength = (int) ((long) obj.get("invisLength"));
    
    JSONArray arr = (JSONArray) obj.get("modeLengths");
    int[] modeLengths = new int[arr.size()];
    for (int i = 0; i < arr.size(); i++) {
      modeLengths[i] = (int)((long) arr.get(i));
    }
    this.modeLengths = modeLengths;

    try {
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns length of invisible mode.
   * @return length of invis mode
   */
  public int getInvisLength() {
    return this.invisLength;
  }

  /**
   * Returns length of frightened mode.
   * @return length of frightened mode
   */
  public int getFrightenedLength() {
    return this.frightenedLength;
  }

  /**
   * Returns name of file containing map information.
   * @return filename of map file
   */
  public String getMap() {
    return this.mapfile;
  }

  /**
   * Returns number of lives for the Waka.
   * @return number of lives for Waka
   */
  public int getLives() {
    return this.lives;
  }

  /**
   * Returns speed (in pixels per frame) of Waka and Ghost objects.
   * @return speed of Waka and Ghosts
   */
  public int getSpeed() {
    return this.speed;
  }

  /**
   * Array containing lengths (in seconds) of chase and scatter modes for Ghosts.
   * @return array containing chase/scatter modelengths.
   */
  public int[] getModeLengths() {
    return this.modeLengths;
  }
}
