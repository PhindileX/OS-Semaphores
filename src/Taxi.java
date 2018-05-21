import java.sql.Time;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

public static class Taxi{

private static CopyOnWriteArrayList <Person> requests;
private static ArrayList<Person> dropOffs;
private static ArrayList<Person> pickups;
private static int maxPeople;
private static int time;
private static Semaphore lock;
private static int branches;
private static int currentStop;
private static boolean toHeadQuarters;

//  Accept requests/hails from people
//  Store them using (person number, where they are, where they are going)
//  Sort them by how close they are relative to where you are going
    public Taxi(int numPeople, int numBranches){
        availableSeats = new Semaphore(1, true);
        maxPeople = numPeople;
        branches = numBranches;
        currentStop =0;
        toHeadQuarters=false;
        requests = new ArrayList<Person>();
        dropOffs = new ArrayList<Person>();
        pickups = new ArrayList<Person>();
        time = System.currentTimeMillis();
    }
//while taxi is operating/Accepting hails:
    public static void hail(Person newPerson){
        requests.add(newPerson);
    } 

    public static void run(){
    //for every person in the sorted queue :
    //Queue sorted by who to pick up first
        int highestDestination=branches;
        int lowestDestination;
        for(Person person: requests){
            //  if there is one person in the taxi
            //drop them off, check if there is someone who needs to be picked up while dropping of aperson.
            lock.acquire();
            if(requests.isEmpty()){
                requests.remove(person);
                pickups.add(person);
                Taxi.currentStop = person.getCurrentStop();
                lowestDestination = person.getNextStop();
                lock.release();
            }
            else if (person.getCurrentStop()<highestDestination){
                if(person.getCurrentStop()>Taxi.currentStop){
                    Taxi.currentStop = person.getCurrentStop();
                    pickups.add(person);
                    lock.release();
                }
                else{
                    Taxi.currentStop = person.getCurrentStop();
                    pickups.add(Math.abs(Taxi.currentStop -person.getCurrentStop()),person);
                    lock.release();
                }

            }   
            else{
                if(person.getCurrentStop()>highestDestination){
                    pickups.add(person);
                    Taxi.currentStop = person.getCurrentStop();
                    lock.release();
                }
                
                
            }


        //  add one minute to the time
        
        //  Person can be picked up if they are in the dirrection of your drop off branch
        // Change your  stop to current drop off or pick up stop
        //if no one has requested:
        //  stay in that stop until someone requests. 
        //  change your current dirrectio to the first request.
        }
    
    }

    public static String checkPersonStatus(Person person){
        if(dropOffs.contains(person)){return ("droppedOff");}
        else if(requests.contains(person)){return ("pickedUp");}
    }

    public static synchronized void advanceTime(int t){
        lock.acquire();
        time+=t;
        lock.release();
    }
    public static synchronized ArrayList<Person> getRequests(){
        return(requests);
    }

    public static synchronized void pickup(Person person){
        lock.acquire();
        pickups.add(person);
        lock.release();
    }
    public static synchronized void dropOff(Person person){
        lock.acquire();
        pickups.remove(person);
        dropOffs.add(person);
        lock.release();
    }
}