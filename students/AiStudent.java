package students;

import bugs.Bug;
import building.Building;

//This class is a subclass of StudentBase, is a type of student, and contains a unique special
// attack implementation
public class AiStudent extends StudentBase implements Student {
  final int finAttack = 7;

  public AiStudent(int level) {
    super(7, 7, level);
  }

  //Method to perform a special attack, specific to the type of student; in this case the
  // first 3 bugs are damaged
  protected int specialAttack(Building building) {
    int knowPoints = 0;
    Bug[] bugs = building.getAllBugs();
    //Damage first 3 bugs if they exist
    if (bugs.length > 0) {
      bugs[0].damage((int) Math.round(finAttack * (Math.pow(getLevel(), 1.2f))));
      knowPoints += checkBug(bugs[0], building);
      if (bugs.length > 1) {
        bugs[1].damage((int) Math.round(finAttack * (Math.pow(getLevel(), 1.2f))));
        knowPoints += checkBug(bugs[1], building);
        if (bugs.length > 2) {
          bugs[2].damage((int) Math.round(finAttack * (Math.pow(getLevel(), 1.2f))));
          knowPoints += checkBug(bugs[2], building);
        }
      }
    }
    //return number of knowledge points
    return knowPoints;
  }
}