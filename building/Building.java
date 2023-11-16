package building;

import bugs.Bug;
import java.util.ArrayList;
import java.util.Arrays;

//This class contains and manages a list of bugs, and contains attribute constructionPoints to
// track the health of the building
public class Building {
  private int constructionPoints;
  private int topFloor;
  private ArrayList<Bug> bugs;

  //Constructor initialises bugs ArrayList
  public Building(int topFloor, int constructionPoints) {
    this.topFloor = topFloor;
    this.constructionPoints = constructionPoints;
    this.bugs = new ArrayList<Bug>();
  }

  public int getTopFloor() {
    return topFloor;
  }

  public int getConstructionPoints() {
    return constructionPoints;
  }

  //Getter method to return an Array of all living bugs in the building (floor 0 or above)
  public Bug[] getAllBugs() {
    ArrayList<Bug> aliveBugs = new ArrayList<Bug>();
    for (Bug bug : bugs) {
      if (bug.getCurrentHp() > 0 && bug.getCurrentFloor() != -1) {
        aliveBugs.add(bug);
      }
    }
    return sortBugs(aliveBugs);
  }

  //Method to add a bug to the building, not allowing duplicates
  public int addBug(Bug bug) {
    if (!bugs.isEmpty() && bugs.contains(bug)) {
      return -1;
    } else {
      bugs.add(bug);
      return bugs.size();
    }
  }

  public void removeBug(Bug bug) {
    bugs.remove(bug);
  }

  //Method to move all bugs closer to the top floor, and damage the building if any reach it
  public void bugsMove() {
    ArrayList<Bug> bugsToRemove = new ArrayList<Bug>();
    for (int i = 0; i < bugs.size(); i++) {
      if (constructionPoints <= 0) {
        break;
      } else {
        //Move each bug in bugs ArrayList
        bugs.get(i).move();
        if (bugs.get(i).getCurrentFloor() >= topFloor) {
          //Damage building if bug reached the top floor, the damage amount depending on the bug
          // type
          switch (bugs.get(i).getClass().getName()) {
            case "bugs.ConcurrentModificationBug":
              constructionPoints = Math.max(0, constructionPoints - 2);
              break;
            case "bugs.NoneTerminationBug":
              constructionPoints = Math.max(0, constructionPoints - 4);
              break;
            case "bugs.NullPointerBug":
              constructionPoints = Math.max(0, constructionPoints - 1);
              break;
            default:
              break;
          }
          bugsToRemove.add(bugs.get(i));
        }
      }
    }
    //Remove bugs that reached the top floor
    for (Bug bug : bugsToRemove) {
      removeBug(bug);
    }
  }

  //Method to sort the bugs ArrayList by distance to the top floor, using bubble sort
  private Bug[] sortBugs(ArrayList<Bug> bugList) {
    //CopyOf with given class acts as type cast; acceptable since buglist only ever contains bugs
    Bug[] bugArray = Arrays.copyOf(bugList.toArray(), bugList.size(), Bug[].class);
    boolean swapFlag = true;
    //Repeat cycles over bugs list, until no swaps occur
    while (swapFlag) {
      swapFlag = false;
      for (int i = 1; i < bugArray.length; i++) {
        //Encodes two bugs positions to compare them, such that floor is the primary decider
        // (step is tens column, floor is hundreds column)
        int bug1Pos = (bugArray[i].getCurrentFloor() * 100) + (100 - bugArray[i].getCurrentSteps());
        int bug0Pos = (bugArray[i - 1].getCurrentFloor() * 100)
            + (100 - bugArray[i - 1].getCurrentSteps());
        if (bug1Pos > bug0Pos) {
          //Swap bug positions if bug1 is closer than bug0
          Bug tempBug = bugArray[i - 1];
          bugArray[i - 1] = bugArray[i];
          bugArray[i] = tempBug;
          swapFlag = true;
        }
      }
    }
    return bugArray;
  }

  //Method to return the number of alive bugs in/about to enter the building
  public int getBugsLeft() {
    int count = 0;
    for (Bug bug : bugs) {
      if (bug.getCurrentHp() > 0) {
        count += 1;
      }
    }
    return count;
  }
}