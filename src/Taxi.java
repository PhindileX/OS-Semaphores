import java.sql.Time;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

public class Taxi {

private static CopyOnWriteArrayList <Person> requests;
private static CopyOnWriteArrayList<Person> dropOffs;
private static CopyOnWriteArrayList<Person> pickups;
private static int maxPeople;
private static long time;
private static Semaphore lock;
private static int branches;
private static int currentStop;
private static boolean toHeadQuarters;

//  Accept requests/hails from people
//  Store them using (person number, where they are, where they are going)
//  Sort them by how close they are relative to where you are going
    public static void init(int numPeople, int numBranches){
        lock = new Semaphore(1, true);
        maxPeople = numPeople;
        branches = numBranches;
        currentStop =0;
        toHeadQuarters=false;
        requests = new CopyOnWriteArrayList<Person>();
        dropOffs = new CopyOnWriteArrayList<Person>();
        pickups = new CopyOnWriteArrayList<Person>();
        time = System.currentTimeMillis();

    }
//while taxi is operating/Accepting hails:
    public static void hail(Person newPerson){
        requests.add(newPerson);
        System.out.println("branch: "+Taxi.getBranch()+" Person "+ newPerson.getID()+" hails");
    } 

    public static void run(){
    //for every person in the sorted queue :
    //Queue sorted by who to pick up first
    while(true){
            int highestDestination=branches;
            int lowestDestination;
            if(requests.isEmpty()){
                System.out.println("Requestst so far:");
                System.out.println(requests.toString());
            }
            else if(!requests.isEmpty()){
                for(Person person: requests){
                    //  if there is one person in the taxi        run();

                    //pick them up, check if there is someone who needs to be picked up while dropping of a person.
                    System.out.println("branch "+Taxi.getBranch()+": Taxi departs");
                    try{
                        lock.acquire();
                        if(requests.size()==1){
                            requests.remove(person);
                            System.out.println("branch: "+person.getCurrentStop()+" Person "+person.getID()+": requests "+person.getNextStop());
                            pickups.add(person);
                            Taxi.currentStop = person.getCurrentStop();
                            lowestDestination = person.getNextStop();
                            lock.release();

                        }
                        else if (person.getCurrentStop()<highestDestination){
                            if(person.getCurrentStop()>Taxi.currentStop){
                                Taxi.currentStop = person.getCurrentStop();
                                System.out.println("branch: "+person.getCurrentStop()+" Person "+person.getID()+": requests "+person.getNextStop());
                                pickups.add(person);
                                lock.release();
                            }
                            else{
                                Taxi.currentStop = person.getCurrentStop();
                                System.out.println("branch: "+person.getCurrentStop()+" Person "+person.getID()+": requests "+person.getNextStop());
                                pickups.add(Math.abs(Taxi.currentStop -person.getCurrentStop()),person);
                                lock.release();
                            }

                        }   
                        else{
                            if(person.getCurrentStop()>highestDestination){
                                System.out.println("branch: "+person.getCurrentStop()+" Person "+person.getID()+": requests "+person.getNextStop());
                                pickups.add(person);
                                Taxi.currentStop = person.getCurrentStop();
                                lock.release();
                            }
            
                        }
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }

                //  add one minute to the time
                    for(Person pickup:pickups){
                    //  Person can be picked up if they are in the dirrection of your drop off branch
                    dropOffs.add(pickup);
                    currentStop=pickup.getNextStop();
                    System.out.println("branch:"+Taxi.getBranch()+" Person "+ pickup.getID()+" disembarks");
                    // Change your  stop to current drop off or pick up stop
                    //if no one has requested:
                    //  stay in that stop until someone requests. 
                    //  change your current dirrectio to the first request.
                    }
                }
            }    
        }
        
    }

    public static String checkPersonStatus(Person person){
        
        if(dropOffs.contains(person)){return ("droppedOff");}
        else if(pickups.contains(person)){return ("pickedUp");}
        else{return ("PersonNotFound");}
    }

    public static synchronized void advanceTime(int t){
        try{
            lock.acquire();
            time+=t;
            lock.release();
        }
        catch(InterruptedException e){e.printStackTrace();}
    }
    public static synchronized CopyOnWriteArrayList<Person> getRequests(){
        return(requests);
    }

    public static synchronized void pickup(Person person){
        try{
            lock.acquire();
            pickups.add(person);
            lock.release();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    public static synchronized void dropOff(Person person){
        
    }
    public static int getBranch(){
        return (Taxi.currentStop);
    }
}