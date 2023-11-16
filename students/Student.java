package students;

import building.Building;

//This interface sets out the required methods that student classes must implement
public interface Student {
  int getLevel();

  int upgradeCost();

  int defence(Building building);
}