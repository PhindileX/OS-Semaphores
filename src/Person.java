import java.utils.*;

public class Person extends Thread{
    int id;
    ArrayList<int[]> stops;
    int currentStop;
//Accept all the hails that would be called by that person and the time they would spend at each brunch;
public Person(String schedule){
    currentStop=0;
    stops = new ArrayList<int[]>();
    String hails = schedules;
    hails.replace("(","");
    hails.replace(")", "");
    hails.replace(" ", "");
    String[] hail = hails.split(",");
    int id = (Integer)hail[0];
    for(int i=1; i<hail.length;i+=2){
        int[] a = {Integer.parseInt(hail[i]),Integer.parseInt(hail[i+1])};
        stops.add(a);
    }
    
    
}

public void run(){
//For each new bruch 
    for(int[] stop: stops){
        Taxi.hail(id, currentStop, stop[0]);
//  hail for taxi with your (current location, next location)
//  wait for taxi to pick you up
//  advance time by on minute
//  if dropped off
//      hail again.
    }
}
}