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
		// UPDATE EACH CAR'S INTENTIONS, i.e. VELOCITY AND LANE CHANGING
		int columns = highway.size();
		for(int i=0; i<columns; i++){
			ArrayList<HighwayGridObject> column = highway.get(i);
			int lanes = column.size();
			for(int j=0; j<lanes; j++){
				HighwayGridObject spot = column.get(j);
				if(spot.hasCar){
					Car car = spot.car;
					int space=0;
					for(int k=1;k<=Car.MAX_SPEED;k++){
						if(!highway.get(i).get(j+k).hasCar && highway.get(i).get(j+k).openSpace){
							space ++;
						}
					if(j>0){
						int Lspace = 0;
						for(int k=1;k<=Car.MAX_SPEED;k++){
							if(!highway.get(i).get(j+k).hasCar && highway.get(i).get(j+k).openSpace){
								space ++;
							}
					}
					if()
					int Rspace = 0;
					for(int k=1;k<=Car.MAX_SPEED;k++){
						if(!highway.get(i).get(j+k).hasCar && highway.get(i).get(j+k).openSpace){
							space ++;
						}
					
					car.updateVelocity(space);
				}
				}
			}
		// MOVE EACH CAR BY VELOCITY OR CHANGE ITS LANE
			
		}
	}

}
