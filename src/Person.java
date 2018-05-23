import java.util.concurrent.Semaphore;
import java.util.*;

public class Person extends Thread{
    public static int time;
    private int id;
    private ArrayList<int[]> stops;
    private int currentStop;
    private int nextStop;
    private boolean droppedOff,pickedUp, isNextStop;
    private Semaphore lock;
    
//Accept all the hails/stops that would be called by that person and the time they would spend at each brunch;
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

        // if(stops.size()>0){
            isNextStop =true;
        // }
        lock= new Semaphore(1,true);
    }

    public void run(){
    //For each new bruch 
        int currentStopNumber=0;
        while(isNextStop){
            for(int[] stop: stops){
                this.nextStop=stop[0];
                

            //  hail for taxi with yourself
                try{
                    lock.acquire();
                    Taxi.hail(this);
                    lock.release();
                } 
                catch(InterruptedException e){ e.printStackTrace();}
                try{
                    while(!pickedUp){
                        lock.acquire();
                        String pickUp = Taxi.checkPersonStatus(this);
                        if (pickUp.equals("pickedUp")){pickedUp=true;}
                        lock.release();
                    }
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                Taxi.advanceTime(1);
            //  if dropped off
            //      hail again.
                
                while(!droppedOff){
                    lock.tryAcquire();
                    String dropOff =Taxi.checkPersonStatus(this);
                    if (dropOff.equals("droppedOff")){droppedOff=true;}
                    lock.release();
                }
                try {
                    Thread.sleep(17*stop[1]);
                    Taxi.advanceTime(stop[1]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(stops.size()>1){
                    this.currentStop=stop[0];
                    currentStopNumber++;
                }
                if(currentStopNumber==stops.size()){
                    isNextStop=false;
                }
                else if(currentStopNumber<stops.size()){
                    isNextStop  =true;
                }
            }
            System.exit(0);
        }
        
    }

    public int getCurrentStop(){return this.currentStop;}
    public int getNextStop(){
        return this.nextStop;
    }
    public int getID(){return(this.id);}
   
}