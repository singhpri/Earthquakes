import java.util.*;
import edu.duke.*;

public class EarthQuakeClient {
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for(QuakeEntry qe:quakeData){
            if(qe.getMagnitude() > magMin){
                answer.add(qe);
            }
        }
        return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
    double distMax,
    Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for(QuakeEntry qe:quakeData){
            if(qe.getLocation().distanceTo(from) < distMax){
                answer.add(qe);
            }
        }
        return answer;
    }

    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        
        ArrayList<QuakeEntry> listBig = new ArrayList<QuakeEntry>();
        listBig = filterByMagnitude(list, 5.0);
        for(QuakeEntry qe:listBig){
            System.out.println(qe);
        }
        System.out.println("Found " + listBig.size() + " quakes that match that criteria");

    }

    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");

        // This location is Durham, NC
        //Location city = new Location(35.988, -78.907);
        
        // This location is Bridgeport, CA
        Location city =  new Location(38.17, -118.82);

        // TODO
        ArrayList<QuakeEntry> listNear = filterByDistanceFrom(list, 1000000, city);
        for(QuakeEntry qe:listNear){
        System.out.println(qe.getLocation().distanceTo(city)/1000 +" " + qe.getInfo());
    }
        System.out.println("Found " +listNear.size() +" quakes that match that criteria");
    }

    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }
    
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData, 
    double minDepth, double maxDepth){
        
        ArrayList<QuakeEntry> listDepth = new ArrayList<QuakeEntry>();
        
        for(QuakeEntry qe:quakeData){
            if(qe.getDepth() > minDepth && qe.getDepth() < maxDepth){
                listDepth.add(qe);
            }
        }
        return listDepth;
        
    }
    
    public void quakesOfDepth(){
        double minDepth = -10000.0;
        double maxDepth = -5000.0;
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for " + list.size() + " quakes");
        System.out.println("Find quakes with depth between " + minDepth + " and " + maxDepth);
        ArrayList<QuakeEntry> listDepth = filterByDepth(list, minDepth,maxDepth);
        for(QuakeEntry qe:listDepth){
        System.out.println(qe);
    }
        System.out.println("Found " + listDepth.size() + " quakes that match that criteria");
    }
    
    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData,
    String where, String phrase){
        
        ArrayList<QuakeEntry> nameList = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe:quakeData){
            String title = qe.getInfo();
            if(where.equals("start")){
            if(phrase.equals(title.substring(0,phrase.length()))){
               nameList.add(qe); 
            
            }
        }
            else if(where.equals("end")){
            if(phrase.equals(title.substring(title.length()-phrase.length(),title.length()))){
                nameList.add(qe);
            }
        }
            else if(where.equals("any")){
            for(int i=0;i<title.length()-phrase.length();i++){
                if(phrase.equals(title.substring(i,i+phrase.length()))){
                    nameList.add(qe);
                }
            }
        }
        }
        return nameList;
    }
    
    public void quakesByPhrase(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for " + list.size()+" quakes");
        String phrase = "Explosion";
        String where = "start";
        ArrayList<QuakeEntry> phraseList = filterByPhrase(list,where,phrase);
        for(QuakeEntry qe:phraseList){
            System.out.println(qe);
        }
        System.out.println("Found " + phraseList.size() + " quakes that match " +
        phrase+" at " +where);
    }
    
}
