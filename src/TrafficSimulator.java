import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TrafficSimulator {
	
	
	public static void main(String[] args) throws FileNotFoundException, InterruptedException{
				
		int HOI = 1;
		int iters = 10000;
		int l = 0;
		int intervalMS = 200;
		double initPopRate = .3;
		double autoRate = .5;
		
		boolean firstDir = true;
		ArrayList<Segment> segments = new ArrayList<Segment>();
		
		Scanner in = new Scanner(new File("roads.csv"));
		in.nextLine();
		while(in.hasNextLine()){
			String[] line = in.nextLine().split(",");
			if(Integer.parseInt(line[0])==HOI){
				int lanes;
				if(firstDir){
					lanes = Integer.parseInt(line[5]);
				} else {
					lanes = Integer.parseInt(line[6]);
				}
				l = lanes;
				segments.add(new Segment(Double.parseDouble(line[1]),Double.parseDouble(line[2]),lanes,Integer.parseInt(line[3])));
			}
		}
		
		Highway highway = new Highway(segments,1,initPopRate,autoRate);
		
	    GUISimulator gs = new GUISimulator();
	    gs.init();
	    gs.updateHighway(highway, 0, 6000);
	    
	    int i;
		for(i = 0; i < iters && highway.numCars > 0; i++){
			Thread.sleep(intervalMS);
			highway.simulate();
			gs.updateHighway(highway, 0, 6000);
		}			
							
	}

}
