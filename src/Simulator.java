import java.util.*;
import java.io.*;
public class Simulator{

    public static void main(String[] args){
        int noOfPeople;
        int noOfBranches;
        ArrayList<Person> peopleAndStops = new ArrayList<Person>();
        try{
            File file = new File("lib/input.txt");
            Scanner input = new Scanner(file);
            noOfPeople = Integer.parseInt(input.nextLine());
            noOfBranches = Integer.parseInt(input.nextLine());
            while(input.hasNext()){
               
                // System.out.println(input.nextLine());
                peopleAndStops.add(new Person(input.nextLine()));
            }
            Taxi.init(noOfPeople, noOfBranches);
            for(Person person:peopleAndStops){
                person.start();
            }
            Taxi.run();

            

        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
}