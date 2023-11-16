import bugs.Bug;
import bugs.ConcurrentModificationBug;
import bugs.NoneTerminationBug;
import bugs.NullPointerBug;
import building.Building;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import students.Team;

//This class runs the main game logic and reads bug wave information from files
public class EcsBuildingDefence {
  //ArrayList to store ArrayLists of bugs to be spawned, represented as strings
  private static ArrayList<ArrayList<String>> bugWaves;
  private static Building building;
  private static Team team;

  //Main method to instantiate and initialise building, team and battle objects
  //Bug waves are spawned and the battle is progressed using step()
  //parameters like the config filename passed as arguments
  public static void main(String[] args) {
    //0: top floor, 1: construction points, 2: filename, 3: knowledge points
    if (args.length == 4) {
      building = new Building(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
      team = new Team(Integer.parseInt(args[3]));
      Battle battle = new Battle(team, building);
      bugWaves = new ArrayList<ArrayList<String>>();
      //Read file and store bug information in ArrayList
      readFile(args[2]);
      int waveDelay = 8 * building.getTopFloor();
      int currentStep = 0;
      //Spawn first wave of bugs
      spawnWave(0);
      //While the building is not destroyed and bugs are still attacking
      while (building.getConstructionPoints() > 0 && building.getBugsLeft() > 0) {
        try {
          //Delay each program step to make the program output more human-readable
          Thread.sleep(200);
          if ((currentStep % waveDelay) == 0 && currentStep != 0
              && (currentStep / waveDelay) < bugWaves.size()) {
            //Spawn a new wave if the current step is a multiple of waveDelay,
            // and there are waves left to spawn
            spawnWave(currentStep / waveDelay);
          }
          //Progress battle by 1 step
          battle.step();
          currentStep += 1;
        } catch (InterruptedException exception) {
          System.out.println("Program interrupted");
        } catch (Exception exception2) {
          System.out.println("File is of the wrong format");
        }

      }
    } else {
      //If not enough arguments are passed, an error message is output
      System.out.println("insufficient arguments provided");
    }
  }

  //Method to parse file 1 line at a time, and add all bugs to bugWaves ArrayList, grouped by wave
  private static void readFile(String fileName) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(fileName));
      //Read first line in text file
      String readLine = reader.readLine();
      while (readLine != null) {
        //Split wave of bugs into seperate bug strings and store in bugWaves
        ArrayList<String> bugs =
            new ArrayList<String>(Arrays.asList(readLine.split(";")));
        bugWaves.add(bugs);
        //Get a line from the text file and store as a string
        readLine = reader.readLine();
      }
      reader.close();
    } catch (FileNotFoundException exception) {
      System.out.println("File cannot be found");
    } catch (IOException exception2) {
      System.out.println("File cannot be read");
    }
  }

  //Method to - for every bug-string in a wave - parse the string and instantiate/initialise the
  // bug and add it to the building
  private static void spawnWave(int index) {
    for (String bug : bugWaves.get(index)) {
      //Split string into seperate variables, used as parameters in bug initialisation
      String name = bug.split("\\(")[0];
      String type = bug.split("\\(")[1].substring(0, 3);
      try {
        int level = Integer.valueOf(bug.split(",")[1]);
        int steps = Integer.valueOf(bug.split(",")[2].replace(")", ""));
        Bug bugToAdd = null;
        //Create the type of bug corresponding to the provided initials
        switch (type) {
          case "CMB":
            bugToAdd = new ConcurrentModificationBug(name, level, steps);
            break;
          case "NTB":
            bugToAdd = new NoneTerminationBug(name, level, steps);
            break;
          case "NPB":
            bugToAdd = new NullPointerBug(name, level, steps);
            break;
          default:
            System.out.println("Wrong bug type format provided");
            break;
        }
        if (bugToAdd != null) {
          //Add new bug to building if it was parsed correctly
          building.addBug(bugToAdd);
        }
      } catch (NumberFormatException exception) {
        System.out.println("Wrong bug format provided");
      }
    }
  }
}