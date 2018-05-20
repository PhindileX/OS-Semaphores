import java.utils.*;

public class Person extends Thread{
    public static int time;
    int id;
    ArrayList<int[]> stops;
    int currentStop;
    boolean droppedOff,pickedUp;
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
        this.droppedOff=false;
        this.pickedUp = false;
    }

    public void run(){
    //For each new bruch 
        for(int[] stop: stops){
        //  hail for taxi with your (current location, next location)
            Taxi.hail(id, currentStop, stop[0]);  
            if(!pickedUp){
                Taxi.checkPersonStatus(this.id,stop[0]);
            }
            Thread.sleep(17);
        //  if dropped off
        //      hail again.
            while(!droppedOff){
                Taxi.checkPersonStatus(this.id,stop[0]);
            }
            Thread.sleep(17*stop[1]);
            this.currentStop=stop[0];
        }
        
    }

}