import java.util.*;

public class MoveEvent implements Event {
  public final Train t; public final Station s1, s2;
  public MoveEvent(Train t, Station s1, Station s2) {
    this.t = t; this.s1 = s1; this.s2 = s2;
  }
  public boolean equals(Object o) {
    if (o instanceof MoveEvent e) {
      return t.equals(e.t) && s1.equals(e.s1) && s2.equals(e.s2);
    }
    return false;
  }
  public int hashCode() {
    return Objects.hash(t, s1, s2);
  }
  public String toString() {
    return "Train " + t + " moves from " + s1 + " to " + s2;
  }
  public List<String> toStringList() {
    return List.of(t.toString(), s1.toString(), s2.toString());
  }



  public void replayAndCheck(MBTA mbta) {
    // Needs to make sure there isn't another train at the desired location
    if (s2.isOccupied()) {
      throw new UnsupportedOperationException("Train " + t.toString() + " is trying to go to station " + s1.toString() + " but it is occupied");
    }

    // Need to make sure the train is arriving at the right station and reverse if it needs to
    List<Station> stations = mbta.lines.get(t);

    int index = stations.indexOf(s1);

    //Looks at cases when the train is not at the edge of the line
    if (t.isMovingForward() && !stations.get(stations.size() - 1).equals(s2)) {
      if(!s2.equals(stations.get(index + 1))) {
        throw new UnsupportedOperationException(s2.toString() + " does not come after " + s1.toString() + " on the " + t.toString() + " line");
      } 
    } else if (!t.isMovingForward() && !stations.get(0).equals(s2)) {
      if(!s2.equals(stations.get(index - 1))) {
        throw new UnsupportedOperationException(s2.toString() + " does not come before " + s1.toString() + " on the " + t.toString() + " line");
      }
    } else if (t.isMovingForward() && stations.get(stations.size() - 1).equals(s2)) {
      if (s2.equals(stations.get(index + 1))) {
        t.setDirection(false);
      } else {
        throw new UnsupportedOperationException(s2.toString() + " does not come after " + s1.toString() + " on the " + t.toString() + " line");
      }
    } else if (!t.isMovingForward() && stations.get(0).equals(s2)) {
      if (s2.equals(stations.get(index - 1))) {
        t.setDirection(true);
      } else {
        throw new UnsupportedOperationException(s2.toString() + " does not come before " + s1.toString() + " on the " + t.toString() + " line");
      }
    }

    //Now that we know that the move is valid, update both stations.
    s1.setOccupied(false);
    s2.setOccupied(true);
    t.setStation(s2);
  }
}
