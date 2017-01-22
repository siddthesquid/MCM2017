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
		// UPDATE EACH CAR'S LANE
		int columns = highway.size();
		for(int i=0; i<columns; i++){
			ArrayList<HighwayGridObject> column = highway.get(i);
			int lanes = column.size();
			for(int j=0; j<lanes){
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
						if(!highway.get(i).get(j+1).hasCar && highway.get(i).get(j+1).openSpace && !highway.get(i+k).get(j+1).hasCar && highway.get(i).get(j+1).openSpace){
							Rspace ++;
						}
						if(!highway.get(i).get(j-1).hasCar && highway.get(i).get(j-1).openSpace && !highway.get(i+k).get(j-1).hasCar && highway.get(i+k).get(j-1).openSpace){
							Lspace ++;
						}
					}
					if(Rspace>space && Math.random() < Car.CHANGE_PROB){
						car.Rchange = true;
						column.get(j+1)
						new_spot.car = car;
						spot.car = null;
						j++;
					}
					if(!car.Rchange && Lspace>space && Math.random() < Car.CHANGE_PROB){
						HighwayGridObject new_spot = column.get(j-1);
						new_spot.car = car;
						spot.car = null;
						car.Rchange = false;
					}
				}
				j++;
			}
		// update each car's velocity
		for(int j=0; j<lanes; j++){
			int lanes = highway.get(1).size();
			for(int i=0; i<columns){
				HighwayGridObject spot = highway.get(i).get(j);
				if(spot.hasCar){
					Car car = spot.car;
					int space = 0;
					int Lspace = 0;
					int Rspace = 0;
					for(int k=1;k<=Car.MAX_SPEED;k++){
						if(!highway.get(i+k).get(j).hasCar && highway.get(i+k).get(j).openSpace){
							space ++;
						}
					}
				car.updateVelocity(space);
				HighwayGridObject new_spot = highway.get(i+car.velocity).get(j);
				new_spot.car = car;
				spot.car = null;
				i = i+car.velocity;
				}
				}
			}
		}
	}

}
