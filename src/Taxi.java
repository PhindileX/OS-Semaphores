import java.util.*;


public class Taxi extends Thread{
    
    private int requests;
    private int stop;
    private boolean toHeadQuarters;         //Keeps track if taxi is going to heard quarters or to the furthest branch
    ArrayList<Person> passangers;
    ArrayList<Person> dropOff;

    public Taxi(int maxPassangers){
        requests = maxPassangers;
        toHeadQuarters=false;
        stop=0;
    }

    public void request(Person p){
        // passangers.add(p);
        if(toHeadQuarters){
            if(stop-p.getCurrentStop>0){
            passangers.add(stop-p.getCurrentStop(),p);
            }
            else{
                passangers.add(p);
            }
            requests--;
        }
        else if(!toHeadQuarters){
            if(stop-p.getCurrentStop<0){
                passangers.add(((stop-p.getCurrentStop())*-1),p);
                requests++;
            }
            else{
                passangers.add(p);
                requests++;
            }
        }
        
        
    }

    public void run(){
        for (Person person: passangers){
            if(person.getDeparture()<this.stop && toHeadQuarters){
                dropOff.add(person);
                passangers.remove(person);
                signal();

            }
            else if(){

            }
        }
    }
}