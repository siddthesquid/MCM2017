import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TrafficSimulator {
	
	public static void main(String[] args) throws FileNotFoundException, InterruptedException{
		
		int HOI = 1;
		int iters = 1000;
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
				segments.add(new Segment(Double.parseDouble(line[1]),Double.parseDouble(line[2]),lanes,Integer.parseInt(line[3])));
			}
		}
		
		Highway highway = new Highway(segments);
//		highway.addSlowdown(100, 200, 1);
		
	    GUISimulator gs = new GUISimulator();
	    gs.init();
	    gs.updateHighway(highway, 0, 6000);
	    
		for(int i = 0; i < iters; i++){
			Thread.sleep(1000);
			highway.simulate();
			gs.updateHighway(highway, 0, 6000);
//			System.out.println(highway.numCars);
		}
		
	}

}
