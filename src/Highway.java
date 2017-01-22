import java.util.ArrayList;

public class Highway {
	public int maxLanes = 0;
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
			if(numLanes>Highway.maxLanes){
				Highway.maxLanes = numLanes;
			}
			highway.add(cellColumn);
		}
	}
	
	public void simulate(int steps){
		// UPDATE EACH CAR'S LANE, i.e. IMPLEMENT LANE SWITCHING
		int columns = highway.size();
		for(int i=0; i<columns; i++){
			ArrayList<HighwayGridObject> column = highway.get(i);
			for(int j=0; j<maxLanes;){
				HighwayGridObject spot = column.get(j);
				if(spot.hasCar){
					Car car = spot.car;
					int space = 0;
					int Lspace = 0;
					int Rspace = 0;
					for(int k=1;k<=Car.MAX_SPEED;k++){
						if(!highway.get(i+k).get(j).hasCar && highway.get(i+k).get(j).openSpace){
							space ++;
						}
						if(j<=maxLanes-1 && !highway.get(i).get(j+1).hasCar && highway.get(i).get(j+1).openSpace && !highway.get(i+k).get(j+1).hasCar && highway.get(i).get(j+1).openSpace){
							Rspace ++;
						}
						if(j>=1 && !highway.get(i).get(j-1).hasCar && highway.get(i).get(j-1).openSpace && !highway.get(i+k).get(j-1).hasCar && highway.get(i+k).get(j-1).openSpace){
							Lspace ++;
						}
					}
					boolean Rchange = false;
					int new_lane = j;
					if(Rspace>space && Math.random() < Car.CHANGE_PROB){
						Rchange = true;
						new_lane++;
						j++;
					}
					if(!Rchange && Lspace>space && Math.random() < Car.CHANGE_PROB){
						new_lane--;
					}
					spot.car = null;
					spot.hasCar = false;
					HighwayGridObject new_spot = column.get(new_lane);
					new_spot.car = car;
					new_spot.hasCar = true;
				}
				j++;
			}
		}
		// update each car's velocity and position
		for(int m=0; m<maxLanes; m++){
			for(int n=0; n<columns;){
				HighwayGridObject spot = highway.get(n).get(m);
				if(spot.hasCar){
					Car car = spot.car;
					int space = 0;
					for(int k=1;k<=Car.MAX_SPEED;k++){
						if(!highway.get(n+k).get(m).hasCar && highway.get(n+k).get(m).openSpace){
							space ++;
						}
					}
				car.updateVelocity(space);
				HighwayGridObject new_spot = highway.get(n+car.velocity).get(m);
				new_spot.car = car;
				new_spot.hasCar = true;
				spot.car = null;
				n = n+car.velocity+1;
				}
			}
		}
	}

}
