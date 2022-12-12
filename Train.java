import java.util.*;
import java.lang.*;

public class Train extends Entity {

  private boolean forward = true;
  private Station currStation;
  private ArrayList<Passenger> thoseOnBoard = new ArrayList<>();

  private static HashMap<String, Train> activeLines = new HashMap<>();

  private Train(String name) { super(name); }

  public static Train make(String name) {
    if (activeLines.containsKey(name)) {
      return activeLines.get(name);
    } else {
      Train t = new Train(name);
      activeLines.put(name, t);
      return t;
    }
  }

  public void addPassenger(Passenger p) {
    thoseOnBoard.add(p);
  }

  public void removePassenger(Passenger p) {
    thoseOnBoard.remove(p);
  }

  public ArrayList<Passenger> getPassengers(Passenger p) {
    return thoseOnBoard;
  }

  public Station currStation() {
    return currStation;
  }

  public void setStation(Station s) {
    currStation = s;
  }

  public boolean isMovingForward() {
    return forward;
  }

  public void setDirection(boolean b) {
    forward = b;
  }

}
