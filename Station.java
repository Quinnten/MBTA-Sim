import java.util.*;
import java.lang.*;

public class Station extends Entity {

  // Boolean that indecates whether there is a train at the station
  private boolean occupied = false;

  // A list of all passengers at a station waiting to board.
  private ArrayList<Passenger> waitingPassengers = new ArrayList<>();

  // All stations that have been created
  private static HashMap<String, Station> activeStations = new HashMap<>();

  private Station(String name) { super(name); }

  public static Station make(String name) {
    if (activeStations.containsKey(name)) {
      return activeStations.get(name);
    } else {
      Station s = new Station(name);
      activeStations.put(name, s);
      return s;
    }
  }

  // A a passenger to the station
  public void addPassenger(Passenger p) {
    waitingPassengers.add(p);
  }

  // Remove a particular passenger from the list
  public void removePassenger(Passenger p) {
    waitingPassengers.remove(p);
  }

  //Return the entire list of passengers 
  public ArrayList<Passenger> getPassengers() {
    return waitingPassengers;
  }

  public boolean isOccupied() {
    return occupied;
  }

  public void setOccupied(boolean b) {
    occupied = b;
  }
}
