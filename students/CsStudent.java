package students;

import bugs.Bug;
import building.Building;

//This class is a subclass of StudentBase, is a type of student, and contains a unique special
// attack implementation
public class CsStudent extends StudentBase implements Student {
  final int finAttack = 6;

  public CsStudent(int level) {
    super(6, 6, level);
  }

  //Method to perform a special attack, specific to the type of student; in this case the target
  // bug takes 4x more damage
  protected int specialAttack(Building building) {
    Bug[] bugs = building.getAllBugs();
    //Damage first bug with 4x the damage, if it exists
    if (bugs.length > 0) {
      bugs[0].damage((int) Math.round(finAttack * 4 * (Math.pow(getLevel(), 1.2f))));
      //return number of knowledge points
      return checkBug(bugs[0], building);
    } else {
      return 0;
    }
  }
}