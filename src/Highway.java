import java.util.ArrayList;

public class Highway {
	
	ArrayList<ArrayList<HighwayGridObject>> highway;
	
	public Highway(){
		highway = new ArrayList<ArrayList<HighwayGridObject>>();
	}
	
	public void addSegment(int feet, int numLanes){
		int cells = feet/15;
		for(int i = 0; i < cells; i++){
			ArrayList<HighwayGridObject> cellColumn = new ArrayList<HighwayGridObject>();
			for(int j = 0; j < numLanes; j++){
				cellColumn.add(new HighwayGridObject(true));
			}
			highway.add(cellColumn);
		}
	}
	
	public void simulate(int steps){
		
	}

}
