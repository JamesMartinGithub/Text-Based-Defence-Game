package students;

import bugs.Bug;
import building.Building;

//This class is a superclass for all different student types, and has implementations of methods
// shared by all of them. This was made to reduce code repetition between the student type classes.
// It is abstract and has abstract method specialAttack to ensure all student type classes contain
// an implementation for it, since it works differently for each student type
public abstract class StudentBase implements Student {
  private int attack;
  private int delay;
  private int level;
  private int attackCycle;

  public StudentBase(int attack, int delay, int level) {
    this.attack = attack;
    this.delay = delay;
    this.level = level;
    this.attackCycle = 0;
  }

  public int getLevel() {
    return level;
  }

  public int upgradeCost() {
    return (int) (100 * (Math.pow(2, level)));
  }

  public void upgrade() {
    level += 1;
  }

  //Method to attack the furthest forward bug in the building and return any gained knowledge
  // points. A special attack is performed every few game steps - the number specified by 'delay'
  public int defence(Building building) {
    int knowPoints = 0;
    if (attackCycle == delay - 1) {
      //If the attack is special call seperate method
      knowPoints += specialAttack(building);
    } else {
      //Otherwise damage the closest bug to the top floor
      Bug[] bugs = building.getAllBugs();
      if (bugs.length > 0) {
        //Do damage to the closest bug, relative to student attack power and level
        bugs[0].damage((int) Math.round(attack * (Math.pow(level, 1.2f))));
        knowPoints += checkBug(bugs[0], building);
      }
    }
    attackCycle = (attackCycle + 1) % delay;
    //return number of knowledge points
    return knowPoints;
  }

  //Abstract method must be defined in subclasses
  protected abstract int specialAttack(Building building);

  //Method to remove a bug if its hp reaches 0, and to return any gained knowledge points
  protected int checkBug(Bug bug, Building building) {
    int knowPoints = 0;
    if (bug.getCurrentHp() <= 0) {
      //Remove bug from building if its health reaches 0
      knowPoints += bug.getLevel() * 20;
      building.removeBug(bug);
    }
    return knowPoints;
  }
}