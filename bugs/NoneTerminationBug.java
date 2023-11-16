package bugs;

//This class is a subclass of Bug, is a type of bug, and initialises with unique stats
public class NoneTerminationBug extends Bug {

  public NoneTerminationBug(String name, int level, int initialSteps) {
    super(name, 200, 6, level);
    setCurrentSteps(initialSteps);
  }
}