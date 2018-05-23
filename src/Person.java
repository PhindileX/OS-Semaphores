import java.util.concurrent.Semaphore;
import java.util.*;

public class Person extends Thread{
    public static int time;
    private int id;
    private ArrayList<int[]> stops;
    private int currentStop;
    private int nextStop;
    private boolean droppedOff,pickedUp;
    private Semaphore queue;
//Accept all the hails that would be called by that person and the time they would spend at each brunch;
    public Person(String schedule){
        currentStop=0;
        stops = new ArrayList<int[]>();
        String hails = schedule;
        hails=hails.replaceFirst(" ", ",");
        hails=hails.replace("(","");
        hails=hails.replace(")", "");
        hails=hails.replace(" ", "");
        String[] hail = hails.split(",");
        System.out.println(hails);
        id = Integer.valueOf(hail[0]);
        for(int i=1; i<hail.length;i+=2){
            int[] a = {Integer.parseInt(hail[i]),Integer.parseInt(hail[i+1])};
            stops.add(a);
        }
        this.droppedOff=false;
            // Thread.sleep(17);
        this.pickedUp = false;
        if(stops.size()>2){
        nextStop = stops.get(1)[0];
        }
        queue= new Semaphore(1,true);
    }

    public void run(){
    //For each new bruch 
        int currentStopNumber=0;
        for(int[] stop: stops){
            currentStopNumber++;
            this.currentStop=stop[0];
            if(stops.size()>1){
            this.nextStop=stops.get(currentStopNumber+1)[0];}
        //  hail for taxi with yourself
            Taxi.hail(this); 
            while(!pickedUp){
               String pickUp = Taxi.checkPersonStatus(this);
               if (pickUp.equals("pickedUp")){pickedUp=true;}
            }
            Taxi.advanceTime(1);
        //  if dropped off
        //      hail again.
            while(!droppedOff){
                String dropOff =Taxi.checkPersonStatus(this);
                if (dropOff.equals("droppedOff")){droppedOff=true;}
            }
            try {
                Thread.sleep(17*stop[1]);
                Taxi.advanceTime(stop[1]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            

        }
        
    }

    public int getCurrentStop(){return this.currentStop;}
    public int getNextStop(){
        return this.nextStop;
    }
    public int getID(){return(this.id);}
   
}