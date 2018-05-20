import java.util.*;
import java.util.concurrent.Semaphore;

public static class Taxi{

private static ArrayList <Person> requests;
private static int maxPeople;
private static Semaphore availableSeats;
private static int branches;
private static int currentStop;
private static boolean toHeadQuarters;
//  Accept requests/hails from people
//  Store them using (person number, where they are, where they are going)
//  Sort them by how close they are relative to where you are going
    public Taxi(int numPeople, int numBranches){
        availableSeats = new Semaphore(numPeople);
        branches = numBranches;
        currentStop =0;
        toHeadQuarters=false;
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
            availableSeats--;
        }
        else if(!toHeadQuarters){
            if(stop-newPerson.getCurrentStop<0){
                requests.add(((stop-newPerson.getCurrentStop())*-1),newPerson);
                availableSeats++;
            }
            else{
                requests.add(newPerson);
                availableSeats++;
            }
        } 
    } 

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