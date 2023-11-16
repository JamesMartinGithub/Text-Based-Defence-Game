package students;

import bugs.Bug;
import building.Building;

//This class is a subclass of StudentBase, is a type of student, and contains a unique special
// attack implementation
public class SeStudent extends StudentBase implements Student {

  public SeStudent(int level) {
    super(5, 6, level);
  }

  //Method to perform a special attack, specific to the type of student; in this case the first
  // 5 bugs are moved back 2 steps
  protected int specialAttack(Building building) {
    Bug[] bugs = building.getAllBugs();
    //First 5 bugs are slowed by 2 steps, only if they exist
    if (bugs.length > 0) {
      bugs[0].slowDown(2);
      if (bugs.length > 1) {
        bugs[1].slowDown(2);
        if (bugs.length > 2) {
          bugs[2].slowDown(2);
          if (bugs.length > 3) {
            bugs[3].slowDown(2);
            if (bugs.length > 4) {
              bugs[4].slowDown(2);
            }
          }
        }
      }
    }
    //return number of knowledge points (always 0 as no bugs are killed)
    return 0;
  }
}