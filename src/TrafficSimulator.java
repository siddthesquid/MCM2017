import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TrafficSimulator {
	
	public static void main(String[] args) throws FileNotFoundException{
		
		int HOI = 1;
		Highway highway = new Highway();
		
		Scanner in = new Scanner(new File("roads.csv"));
		while(in.hasNextLine()){
			String[] line = in.nextLine().split(",");
			highway.addSegment(feet, numLanes);
		}
		
	}

}
