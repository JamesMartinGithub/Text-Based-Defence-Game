package students;

import building.Building;
import java.util.ArrayList;
import java.util.Random;

//This class relates student instances as a group, called a team. It has methods for management of
// students in the team, and for them to attack bugs
public class Team {
  private int knowledgePoints;
  private ArrayList<StudentBase> students;
  private int newStudentCost;

  //Constructor initialises students ArrayList
  public Team(int knowledgePoints) {
    this.knowledgePoints = knowledgePoints;
    this.newStudentCost = 100;
    this.students = new ArrayList<StudentBase>();
  }

  public int getKnowledgePoints() {
    return knowledgePoints;
  }

  public ArrayList<StudentBase> getStudents() {
    return students;
  }

  //Method to make all students in the team attack, and increase any gained knowledge points
  public void studentsAct(Building building) {
    int knowPoints = 0;
    for (StudentBase student : students) {
      knowPoints += student.defence(building);
    }
    knowledgePoints += knowPoints;
  }

  public int getNewStudentCost() {
    return newStudentCost;
  }

  //Method to recruit a new student of a randomly selected type
  public void recruitNewStudent() throws Exception {
    if (knowledgePoints < newStudentCost) {
      throw new Exception("Not enough knowledge points to recruit new student");
    } else {
      Random random = new Random();
      //Add new student to team based on value of random int
      switch (random.nextInt(4)) {
        case 0:
          students.add(new AiStudent(1));
          break;
        case 1:
          students.add(new CsStudent(1));
          break;
        case 2:
          students.add(new SeStudent(1));
          break;
        case 3:
          students.add(new CyberStudent(1));
          break;
        default:
          break;
      }
      //Reduce knowledge points accordingly
      knowledgePoints -= newStudentCost;
      newStudentCost += 10;
    }
  }

  //Method to increase level of a student if the team has enough knowledge points
  public void upgrade(StudentBase student) throws Exception {
    if (knowledgePoints < student.upgradeCost()) {
      throw new Exception("Not enough knowledge points to upgrade student");
    } else {
      student.upgrade();
      //Decrease knowledge points accordingly
      knowledgePoints -= student.upgradeCost();
    }
  }
}