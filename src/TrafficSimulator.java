import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TrafficSimulator {
	
	public static void main(String[] args) throws FileNotFoundException{
		
		int HOI = 1;
		int iters = 1000;
		Highway highway = new Highway();
		
		Scanner in = new Scanner(new File("roads.csv"));
		while(in.hasNextLine()){
			String[] line = in.nextLine().split(",");
			if(line[0].equals("1")){
				highway.addSegment((int)((Double.parseDouble(line[2])-Double.parseDouble(line[1]))/15), Integer.parseInt(line[5]));
			}
		}
		
		//highway.populate();
		
		//highway.addProbe(3.75);
		//highway.addProbe(7.75);
		
		//highway.
		
		for(int i = 0; i < iters; i++){
			highway.simulate(1);
		}
		
	}

}
