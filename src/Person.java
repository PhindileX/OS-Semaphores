import java.utils.*;

public class Person extends Thread{
    public static int time;
    private int id;
    ArrayList<int[]> stops;
    private int currentStop;
    private int nextStop;
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
        nextStop = stops.get(1)[0];
    }

    public void run(){
    //For each new bruch 
        int currentStopNumber;
        for(int[] stop: stops){
            currentStopNumber++;
            this.currentStop=stop[0];
            this.nextStop=stops.get(currentStopNumber+1);
        //  hail for taxi with yourself
            Taxi.hail(this);  
            if(!pickedUp){
                Taxi.checkPersonStatus(this);
            }
            Thread.sleep(17);
        //  if dropped off
        //      hail again.
            while(!droppedOff){
                Taxi.checkPersonStatus(this);
            }
            Thread.sleep(17*stop[1]);
            

        }
        
    }

    public int getCurrentStop(){return this.currentStop;}
    public int getNextStop(){return this.nextStop;}
}