package bugs;

//This class is a superclass of all bug type classes. It contains shared methods and basic
// getters/setters. It is a superclass to reduce code repetition in the bug classes
public class Bug {
  private String name;
  private int baseHp;
  private int baseSteps;
  private int level;
  private int currentHp;
  private int currentSteps;
  private int currentFloor;

  //Constructor sets currentHp based on bug's level and baseHp
  public Bug(String name, int baseHp, int baseSteps, int level) {
    this.name = name;
    this.baseHp = baseHp;
    this.baseSteps = baseSteps;
    this.level = level;
    this.currentHp = (int) Math.round(baseHp * (Math.pow(level, 1.5)));
    this.currentSteps = baseSteps;
    this.currentFloor = -1;
  }

  //Overload method allows the initial steps to be specified
  public Bug(String name, int baseHp, int baseSteps, int level, int initialSteps) {
    this.name = name;
    this.baseHp = baseHp;
    this.baseSteps = baseSteps;
    this.level = level;
    this.currentHp = (int) Math.round(baseHp * (Math.pow(level, 1.5)));
    this.currentSteps = initialSteps;
    this.currentFloor = -1;
  }

  public int getBaseSteps() {
    return baseSteps;
  }

  public int getLevel() {
    return level;
  }

  public int getCurrentHp() {
    return currentHp;
  }

  public int getCurrentSteps() {
    return currentSteps;
  }

  public int getCurrentFloor() {
    return currentFloor;
  }

  public void setCurrentSteps(int steps) {
    this.currentSteps = steps;
  }

  //Method to move the bug forward by 1 step
  public void move() {
    if (currentSteps > 0) {
      currentSteps -= 1;
    } else {
      //Move the bug up a floor is currentSteps is 0
      currentFloor += 1;
      currentSteps = baseSteps - 1;
    }
  }

  //Method to reduce the hp of the bug by a given amount, to a minimum of 0
  public void damage(int amount) {
    currentHp = Math.max(0, currentHp - amount);
  }

  //Method to increase the remaining steps of the bug by a given amount
  public void slowDown(int steps) {
    currentSteps += steps;
  }
}