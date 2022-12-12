import java.util.*;
import java.io.*;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;
import com.google.gson.*;

public class MBTA {

  public HashMap<Train, List<Station>> lines = new HashMap<>();
  public HashMap<Passenger, List<Station>> trips = new HashMap<>();

  // Creates an initially empty simulation
  public MBTA() { }

  // Adds a new transit line with given name and stations
  public void addLine(String name, List<String> stations) {
    Train t = Train.make(name);

    if (lines.containsKey(t)) {
      throw new UnsupportedOperationException("A " + name + " line already exists");
    }

    ArrayList<Station> stationList = new ArrayList<>();
    for (String s : stations) {
      stationList.add(Station.make(s));
    }

    //Initialize the train's current station to the beginning of the simulation
    t.setStation(stationList.get(0));

    //Initialize the station so it knows that it's occupied
    stationList.get(0).setOccupied(true);

    System.out.println(t.toString() + " starts at " + stationList.get(0).toString());

    lines.put(t, stationList);
  }

  // Adds a new planned journey to the simulation
  public void addJourney(String name, List<String> stations) {
    Passenger p = Passenger.make(name);

    if (trips.containsKey(p)) {
      throw new UnsupportedOperationException("A passeneger with the name " + name + " already exists");
    }

    ArrayList<Station> stationList = new ArrayList<>();

    for (String s : stations) {
      stationList.add(Station.make(s));
    }
    // Iniialize next stop for passenger
    p.setDestination(stationList.get(1));
    p.setStart(stationList.get(0));


    //Add passenger to station waitlist
    stationList.get(0).addPassenger(p);
    trips.put(p, stationList);
  }

  // Return normally if initial simulation conditions are satisfied, otherwise
  // raises an exception
  public void checkStart() {
    System.out.println("WE HAVE ENTERED THE METHOD!!!");

    for (Map.Entry<Passenger, List<Station>> mapElement : trips.entrySet()) {
          Passenger p = mapElement.getKey();
          List<Station> values = mapElement.getValue();

          // Check to see if every passenger is at the last station in there respective station list
          if(p.currDest() != values.get(1)) {
            throw new UnsupportedOperationException("Passenger " + p.toString() + " is going to wrong station " + p.currDest().toString());
          } 

          if(p.startStation() != values.get(0)) {
            throw new UnsupportedOperationException("Passenger " + p.toString() + " started at wrong station " + p.currDest().toString());
          }
        }
    System.out.println("WE ARE HERE!!!");
    for (Map.Entry<Train, List<Station>> mapElement : lines.entrySet()) {
          Train t = mapElement.getKey();
          List<Station> values = mapElement.getValue();

          //Make sure every train is at the beginning of the line
          if(t.currStation() != values.get(0)) {
            throw new UnsupportedOperationException("Train " + t.toString() + " started at wrong station " + t.currStation().toString());
          }

          //check that the starting station is true and the rest are false
          
        }

       System.out.println("WE ARE ABOUT TO LEAVE THE METHOD!!!");
  }

  // Return normally if final simulation conditions are satisfied, otherwise
  // raises an exception
  public void checkEnd() {
    for (Map.Entry<Passenger, List<Station>> mapElement : trips.entrySet()) {
          Passenger p = mapElement.getKey();
          List<Station> values = mapElement.getValue();

          // Check to see if every passenger is at the last station in there respective station list
          if(p.currDest() != null) {
            throw new UnsupportedOperationException("Passenger " + p.toString() + " ended at wrong station " + p.currDest().toString());
          }
        }
  }

  // reset to an empty simulation
  public void reset() {
    HashMap<Train, List<Station>> l = new HashMap<>();
    lines = l;
    HashMap<Passenger, List<Station>> t = new HashMap<>();
    trips = t;
  }

  // adds simulation configuration from a file
  public void loadConfig(String filename) {
      try {
        File myObj = new File(filename);
        Scanner myReader = new Scanner(myObj);
      
        String data = " ";
        while (myReader.hasNextLine()) {
          data = data.concat(myReader.nextLine());
        }

        Gson gson = new Gson();
        Converter c = gson.fromJson(data, Converter.class);

        for (Map.Entry<String, List<String>> mapElement : c.lines.entrySet()) {
          String key = mapElement.getKey();
          List<String> values = mapElement.getValue();
          if (lines.containsKey(key)) {
            throw new UnsupportedOperationException("Can not add two lines with the same name");
          }
          addLine(key, values);
        }

        for (Map.Entry<String, List<String>> mapElement : c.trips.entrySet()) {
          String key = mapElement.getKey();
          List<String> values = mapElement.getValue();
          if (trips.containsKey(key)) {
            throw new UnsupportedOperationException("Can not add two passemngers with the same name");
          }
          addJourney(key, values);
        }
        myReader.close();

      } catch(Exception e) {
          e.printStackTrace();
      }
  }
}
