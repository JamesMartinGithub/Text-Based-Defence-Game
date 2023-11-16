package bugs;

//This class is a subclass of Bug, is a type of bug, and initialises with unique stats
public class ConcurrentModificationBug extends Bug {

  public ConcurrentModificationBug(String name, int level, int initialSteps) {
    super(name, 20, 4, level);
    setCurrentSteps(initialSteps);
  }
}