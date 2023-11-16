package students;

import bugs.Bug;
import building.Building;
import java.util.Random;

//This class is a subclass of StudentBase, is a type of student, and contains a unique special
// attack implementation
public class CyberStudent extends StudentBase implements Student {
  final int finAttack = 7;

  public CyberStudent(int level) {
    super(7, 8, level);
  }

  //Method to perform a special attack, specific to the type of student; in this case there's a
  // <= 70% chance of the first bug being killed, otherwise 2x the normal damage is done instead
  protected int specialAttack(Building building) {
    int knowPoints = 0;
    Bug[] bugs = building.getAllBugs();
    //Generate new random number between 1-100
    Random random = new Random();
    int randInt = random.nextInt(100) + 1;
    //Check if that number is below the probability of removal
    if (randInt <= Math.min(getLevel(), 50) + 20) {
      if (bugs.length > 0) {
        //If so, kill and remove first bug if it exists
        bugs[0].damage(bugs[0].getCurrentHp());
        knowPoints += checkBug(bugs[0], building);
      }
    } else {
      //If the probability check fails, do double damage
      bugs[0].damage((int) Math.round(finAttack * 2 * (Math.pow(getLevel(), 1.2f))));
      knowPoints += checkBug(bugs[0], building);
    }
    //return number of knowledge points
    return knowPoints;
  }
}