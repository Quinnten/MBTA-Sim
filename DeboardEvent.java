import java.util.*;

public class DeboardEvent implements Event {
  public final Passenger p; public final Train t; public final Station s;
  public DeboardEvent(Passenger p, Train t, Station s) {
    this.p = p; this.t = t; this.s = s;
  }
  public boolean equals(Object o) {
    if (o instanceof DeboardEvent e) {
      return p.equals(e.p) && t.equals(e.t) && s.equals(e.s);
    }
    return false;
  }
  public int hashCode() {
    return Objects.hash(p, t, s);
  }
  public String toString() {
    return "Passenger " + p + " deboards " + t + " at " + s;
  }
  public List<String> toStringList() {
    return List.of(p.toString(), t.toString(), s.toString());
  }



  public void replayAndCheck(MBTA mbta) {

    if (!t.getPassengers().contains(p)) {
      throw new UnsupportedOperationException("The passenger " + p.toString() + " is not located on the train " + t.toString());
    }

    if (!mbta.lines.containsKey(t) || !mbta.trips.containsKey(p)){
      throw new UnsupportedOperationException("The train or passenger does not exist");
    }
    //Basically just check that this station is the destination station 
    if (!p.currDest().equals(s)) {
      throw new UnsupportedOperationException(s.toString() + " is the wring destination for passenger " + p.toString());
    } 
    if (!t.currStation().equals(s)) {
      throw new UnsupportedOperationException("Can't deboard a passenger at a station they're not at");
    }

    //Then remove pasemger from tain
    t.removePassenger(p);
    s.addPassenger(p);
    //Add passenger to Station

    //Update destination or set the traveling boolean to false
    List<Station> journey = mbta.trips.get(p);
    if (journey.get(journey.size() - 1).equals(s)) {
      p.endJourney();
      p.setDestination(null);
      System.out.println(p.toString() + " has finished their journey and should no longer be allowed to board so help me God");
    } else {
      int index = journey.indexOf(s);
      p.setDestination(journey.get(index + 1));
    }
  }
}
