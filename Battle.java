import bugs.Bug;
import building.Building;
import java.util.ArrayList;
import java.util.HashMap;
import students.Student;
import students.StudentBase;
import students.Team;

//This class handles student management and outputs statistics each game step
public class Battle {
  private Team team;
  private Building building;
  private int initConstructionPoints;

  public Battle(Team team, Building building) {
    this.team = team;
    this.building = building;
    this.initConstructionPoints = building.getConstructionPoints();
  }

  //Recruits more students if the building is near destroyed or if less than 4 students are active
  //Otherwise, the student with the lowest level is upgraded
  public void manageTeam() {
    if (building.getConstructionPoints() < (initConstructionPoints * 0.2)) {
      //Recruit if construction points are less than 1/5th of initial value
      tryRecruit();
    } else {
      //Otherwise, upgrade or recruit depending on whether at least 4 students are already active
      ArrayList<StudentBase> students = team.getStudents();
      if (students.size() < 4) {
        //Try to recruit new student
        tryRecruit();
      } else {
        int lowestLevel = students.get(0).getLevel();
        int lowestIndex = 0;
        //Find student with the lowest level and try to upgrade them
        for (int i = 0; i < students.size(); i++) {
          if (lowestLevel > students.get(i).getLevel()) {
            lowestLevel = students.get(i).getLevel();
            lowestIndex = i;
          }
        }
        //Try to upgrade that student
        tryUpgrade(students.get(lowestIndex));
      }
    }
  }

  //Method to recruit a new student if the team has enough points, and to handle exceptions
  private void tryRecruit() {
    if (team.getKnowledgePoints() >= team.getNewStudentCost()) {
      try {
        team.recruitNewStudent();
      } catch (Exception exception) {
        System.out.println("Insufficient knowledge points");
      }
    }
  }

  //Method to upgrade a student if the team has enough points
  private void tryUpgrade(StudentBase student) {
    if (team.getKnowledgePoints() >= student.upgradeCost()) {
      try {
        team.upgrade(student);
      } catch(Exception exception) {
        System.out.println("Insufficient knowledge points");
      }
    }
  }

  //Method to simulate a turn in the game, the team is managed, bugs are moved and students defend
  public void step() {
    System.out.println("====");
    System.out.println("-Managing Team");
    manageTeam();
    System.out.println("-Bugs Moving");
    building.bugsMove();
    System.out.println("-Students Defending");
    team.studentsAct(building);
    System.out.println("====");
    outputStats();
  }

  //Method to output the current state of the game
  private void outputStats() {
    System.out.println("Current game state:");
    //Output no. of knowledge points
    System.out.println("  Knowledge points: " + team.getKnowledgePoints());
    //Output cost of recruiting new student
    System.out.println("  Recruiting Cost: " + team.getNewStudentCost());
    //Output current building construction points
    System.out.println("  Construction Points: " + building.getConstructionPoints());
    //Output the number of each type of student in the team, and each student's level
    System.out.println("  Active Students:");
    //    Hashmaps are initialised to store the student numbers and levels - HashMaps are used
    //    instead of ArrayLists since passing the student class names as keys to get data
    //    makes the code more readable than if arbitrary index-based retrieval was used where the
    //    index-class correlation would not be obvious
    HashMap<String, Integer> studentCount = new HashMap<String, Integer>();
    studentCount.put("AiStudent", 0);
    studentCount.put("CsStudent", 0);
    studentCount.put("CyberStudent", 0);
    studentCount.put("SeStudent", 0);
    HashMap<String, ArrayList<Integer>> studentLevels = new HashMap<String, ArrayList<Integer>>();
    studentLevels.put("AiStudent", new ArrayList<Integer>());
    studentLevels.put("CsStudent", new ArrayList<Integer>());
    studentLevels.put("CyberStudent", new ArrayList<Integer>());
    studentLevels.put("SeStudent", new ArrayList<Integer>());
    //    For each student in the team, the class-relevant hashmap entry is updated with the
    //    new total and the student's level
    for (StudentBase student : team.getStudents()) {
      String name = student.getClass().getName().split("[.]")[1];
      studentCount.replace(name, studentCount.get(name) + 1);
      ArrayList<Integer> tempArray = studentLevels.get(name);
      tempArray.add(student.getLevel());
      studentLevels.replace(name, tempArray);
    }
    //    The student data is output, by retrieving each hashmap data entry using the student class
    //    names as keys
    System.out.println("    -AiStudent x" + studentCount.get("AiStudent"));
    System.out.println("      -Levels: " + studentLevels.get("AiStudent").toString()
        .substring(1, studentLevels.get("AiStudent").toString().length() - 1));
    System.out.println("    -CsStudent x" + studentCount.get("CsStudent"));
    System.out.println("      -Levels: " + studentLevels.get("CsStudent").toString()
        .substring(1, studentLevels.get("CsStudent").toString().length() - 1));
    System.out.println("    -CyberStudent x" + studentCount.get("CyberStudent"));
    System.out.println("      -Levels: " + studentLevels.get("CyberStudent").toString()
        .substring(1, studentLevels.get("CyberStudent").toString().length() - 1));
    System.out.println("    -SeStudent x" + studentCount.get("SeStudent"));
    System.out.println("      -Levels: " + studentLevels.get("SeStudent").toString()
        .substring(1, studentLevels.get("SeStudent").toString().length() - 1));
    //Output the number of each type of bug in the building,
    // and each bug's distance to the top floor and hp
    System.out.println("  Active Bugs:");
    //    Hashmaps are initialised to store the bug numbers, distances and hps
    HashMap<String, Integer> bugCount = new HashMap<String, Integer>();
    bugCount.put("ConcurrentModificationBug", 0);
    bugCount.put("NoneTerminationBug", 0);
    bugCount.put("NullPointerBug", 0);
    HashMap<String, ArrayList<Integer>> bugDistances = new HashMap<String, ArrayList<Integer>>();
    bugDistances.put("ConcurrentModificationBug", new ArrayList<Integer>());
    bugDistances.put("NoneTerminationBug", new ArrayList<Integer>());
    bugDistances.put("NullPointerBug", new ArrayList<Integer>());
    HashMap<String, ArrayList<Integer>> bugHps = new HashMap<String, ArrayList<Integer>>();
    bugHps.put("ConcurrentModificationBug", new ArrayList<Integer>());
    bugHps.put("NoneTerminationBug", new ArrayList<Integer>());
    bugHps.put("NullPointerBug", new ArrayList<Integer>());
    //    For each bug in the building, the class-relevant hashmap entry is updated with the
    //    new total and the bug's distance and hp
    for (Bug bug : building.getAllBugs()) {
      String name = bug.getClass().getName().split("[.]")[1];
      bugCount.replace(name, bugCount.get(name) + 1);
      ArrayList<Integer> tempArray = bugDistances.get(name);
      tempArray.add(bug.getCurrentSteps()
          + ((building.getTopFloor() - bug.getCurrentFloor()) * bug.getBaseSteps()));
      bugDistances.replace(name, tempArray);
      tempArray = bugHps.get(name);
      tempArray.add(bug.getCurrentHp());
      bugHps.replace(name, tempArray);
    }
    //    The bug data is output, by retrieving each hashmap data entry using the bug class
    //    names as keys
    System.out.println("    -ConcurrentModificationBug x"
        + bugCount.get("ConcurrentModificationBug"));
    System.out.println("      -Steps left: "
        + bugDistances.get("ConcurrentModificationBug").toString()
        .substring(1, bugDistances.get("ConcurrentModificationBug").toString().length() - 1));
    System.out.println("      -Hp: " + bugHps.get("ConcurrentModificationBug").toString()
        .substring(1, bugHps.get("ConcurrentModificationBug").toString().length() - 1));
    System.out.println("    -NoneTerminationBug x" + bugCount.get("NoneTerminationBug"));
    System.out.println("      -Steps left: " + bugDistances.get("NoneTerminationBug").toString()
        .substring(1, bugDistances.get("NoneTerminationBug").toString().length() - 1));
    System.out.println("      -Hp: " + bugHps.get("NoneTerminationBug").toString()
        .substring(1, bugHps.get("NoneTerminationBug").toString().length() - 1));
    System.out.println("    -NullPointerBug x" + bugCount.get("NullPointerBug"));
    System.out.println("      -Steps left: " + bugDistances.get("NullPointerBug").toString()
        .substring(1, bugDistances.get("NullPointerBug").toString().length() - 1));
    System.out.println("      -Hp: " + bugHps.get("NullPointerBug").toString()
        .substring(1, bugHps.get("NullPointerBug").toString().length() - 1));
  }
}