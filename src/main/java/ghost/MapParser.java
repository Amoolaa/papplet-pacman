package ghost;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import processing.core.PApplet;
import java.util.ArrayList;

/**
 * Map parser which formats a specified text file into a corresponding multi-dimensional
 * array of cells and performs other operations to do with the game map.
 */
public class MapParser {
  
  /* Multi-dimensional array containing cell information. */
  private Cell[][] cells;
  
  /* Name of file containing map information. */
  private String filename;
  
  /* Spawnpoint of Waka. */
  private Spawn wakaSpawn;
  
  /* Spawnpoints of Ghosts. */
  private ArrayList<Spawn> ghostSpawn;
  
  /* Total number of fruits on the map. */
  private int fruitCount;

  /**
   * Creates a map object. 
   * @param filename Name of file containing map information.
   */
  public MapParser(String filename) {
    this.filename = filename;
    this.wakaSpawn = null;
    this.ghostSpawn = new ArrayList<Spawn>();
    this.fruitCount = 0;
    this.cells = null;
  }

  /**
   * Returns the spawnpoint of the Waka object.
   * @return spawnpoint of Waka
   */
  public Spawn getWakaSpawn() {
    return this.wakaSpawn;
  }

  /**
   * Returns the list containing the spawnpoints of Ghost objects.
   * @return list containing spawnpoints of Ghosts
   */
  public ArrayList<Spawn> getGhostSpawn() {
    return this.ghostSpawn;
  }

  /**
   * Formats text file map information into a multi-dimensional array of cell objects.
   * @return multi-dimensional array of cells.
   */
  public Cell[][] formatter() {
    /* Opening file for reading. */
    File mapInput = new File(this.filename);
    Scanner scan = null;
    try {
      scan = new Scanner(mapInput);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
    /* Converting text file into multi-dimensional array of characters,
     * corresponding to cell types. */
    char[][] identifiers = new char[36][28];
    int i = 0;
    while (scan.hasNext()) {
      char[] row = scan.nextLine().toCharArray();
      identifiers[i] = row;
      i++;
    }

    /* Looping through each character, creating the particular cell-type object
     * and assigning it to the correct position in the multi-dimensional array
     * of cells. */
    Cell[][] cells = new Cell[36][28];
    for (i = 0; i < identifiers.length; i++) {
      for (int j = 0; j < identifiers[i].length; j++) {
        char id = identifiers[i][j];
        switch (id) {
          case '0':
            cells[i][j] = null;
            break;
          case '1':
            cells[i][j] = new Wall(id, "horizontal.png", j, i, true);
            break;
          case '2':
            cells[i][j] = new Wall(id, "vertical.png", j, i, true);
            break;
          case '3':
            cells[i][j] = new Wall(id, "upLeft.png", j, i, true);
            break;
          case '4':
            cells[i][j] = new Wall(id, "upRight.png", j, i, true);
            break;
          case '5':
            cells[i][j] = new Wall(id, "downLeft.png", j, i, true);
            break;
          case '6':
            cells[i][j] = new Wall(id, "downRight.png", j, i, true);
            break;
          case '7':
            cells[i][j] = new Fruit(id, "fruit.png", j, i, false);
            this.fruitCount++;
            break;
          case '8':
            cells[i][j] = new SuperFruit(id, "superfruit.png", j, i, false);
            this.fruitCount++;
            break;
          case 'p':
            cells[i][j] = new Spawn(id, j, i, false);
            this.wakaSpawn = (Spawn) cells[i][j];
            break;
          case 'a':
            cells[i][j] = new Spawn(id, j, i, false);
            this.ghostSpawn.add((Spawn) cells[i][j]);
            break;
          case 'c':
            cells[i][j] = new Spawn(id, j, i, false);
            this.ghostSpawn.add((Spawn) cells[i][j]);
            break;
          case 'i':
           cells[i][j] = new Spawn(id, j, i, false);
            this.ghostSpawn.add((Spawn) cells[i][j]);
            break;
          case 'w':
            cells[i][j] = new Spawn(id, j, i, false);
            this.ghostSpawn.add((Spawn) cells[i][j]);
            break;
          case 's':
            cells[i][j] = new SodaCan(id, "sodacan.png", "sodacaneaten.png", j, i, false);
            this.fruitCount++;
            break;
        }
      }
    }
    this.cells = cells;
    return cells;
  }

  /**
   * Returns the total fruit count of the map.
   * @return total fruit count of the map
   */
  public int getFruitCount() {
    return this.fruitCount;
  }


  
  /**
   * Resets map to initial conditions. 
   */
  public void reset() {
    for (int i = 0; i < this.cells.length; i++) {
      for (int j = 0; j < this.cells[i].length; j++) {
        if (this.cells[i][j] != null) {
          if (this.cells[i][j].getID() == '7' || this.cells[i][j].getID() == '8' ||
                  this.cells[i][j].getID() == 's') {
            ((Eatable)this.cells[i][j]).reset();
          }
        }
      }
    }
  }

  /**
   * Loads images for each cell. 
   * @param app application used to run the game
   */
  public void setup(PApplet app) {
    for (int i = 0; i < this.cells.length; i++) {
      for (int j = 0; j < this.cells[i].length; j++) {
        
        /* We first clear the case where we have null cells, since no image is loaded. */
        if (this.cells[i][j] == null) {
          continue;
        
        /* Then, there are only two other types of cells with visible sprites, which are
         * fruits and walls. */
        } else if (this.cells[i][j].getID() == '7') {
          ((Fruit)this.cells[i][j]).setup(app);
        } else if (this.cells[i][j].getID() == '8') {
          ((SuperFruit)this.cells[i][j]).setup(app);
        } else if (this.cells[i][j].getID() == 's') {
          ((SodaCan)this.cells[i][j]).setup(app);
        } else if (this.cells[i][j].isSolid()){
          ((Wall)this.cells[i][j]).setup(app);
        }
      }
    }
  }

  /**
   * Draws images for each cell.
   * @param app application used to run the game
   */
  public void draw(PApplet app) {
    for (int i = 0; i < this.cells.length; i++) {
      for (int j = 0; j < this.cells[i].length; j++) {     
        /* We first clear the case where we have null cells, since no image is loaded. */
        if (this.cells[i][j] == null) {
          continue;
        
        /* Then, there are only two other types of cells with visible sprites, which are
         * fruits and walls. */
        } else if (this.cells[i][j].isSolid() || this.cells[i][j].getID() == '7' || 
                this.cells[i][j].getID() == '8' || this.cells[i][j].getID() == 's') {
          Interactable cell = (Interactable) this.cells[i][j];
          cell.draw(app);
        }
      }
    }
  }
}
