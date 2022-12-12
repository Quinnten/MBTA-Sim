import java.util.*;
import java.lang.*;

public class Passenger extends Entity {

  private static HashMap<String, Passenger> passengers = new HashMap<>();
  private boolean traveling = true;
  private Station destination;
  private Station start;

  private Passenger(String name) { super(name); }

  public static Passenger make(String name) {
    if (passengers.containsKey(name)) {
      return passengers.get(name);
    } else {
      Passenger p = new Passenger(name);
      passengers.put(name, p);
      return p;
    }
  }

  public void endJourney() {
    traveling = false;
  }

  public boolean isTraveling() {
    return traveling;
  }

  public Station currDest() {
    return destination;
  }

  public void setDestination(Station s) {
    destination = s;
  }

  public void setStart(Station s) {
    start = s;
  }

  public Station startStation() {
    return start;
  }

}
