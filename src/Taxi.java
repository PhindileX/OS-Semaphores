import java.util.*;
import java.util.concurrent.Semaphore;

public static class Taxi{

private static ArrayList <Person> requests;
private static ArrayList<Person> dropOffs;
private static int maxPeople;
private static Semaphore time;
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
    }
//while taxi is operating/Accepting hails:
    public static void hail(Person newPerson){
        if(toHeadQuarters){
            if(stop-newPerson.getCurrentStop>0){
            requests.add(stop-newPerson.getCurrentStop(),newPerson);
            }
            else{
                requests.add(newPerson);
            }
            
        }
        else if(!toHeadQuarters){
            if(stop-newPerson.getCurrentStop<0){
                requests.add(((stop-newPerson.getCurrentStop())*-1),newPerson);
            }
            else{
                requests.add(newPerson);
            }
        } 
    } 

    public static void run(){
//for every person in the sorted queue :
//  pick them up
//  add one minute to the time
//  if there is one person in the taxi
//      drop them off, check if there is someone who needs to be picked up while dropping of aperson.
//  Person can be picked up if they are in the dirrection of your drop off branch
// Change your  stop to current drop off or pick up stop
//if no one has requested:
//  stay in that stop until someone requests. 
//  change your current dirrectio to the first request.
    }

    public static String checkPersonStatus(Person person){
        if(dropOffs.contains(person)){return ("droppedOff");}
        else if(requests.contains(person)){return ("pickedUp");}
    }
}