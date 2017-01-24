import java.util.ArrayList;

public class Highway {
	
	public static final int MAX_LANES = 5;
	public int numCars;
	
	public static int totalAcceleration;
	public int totalDistance;
	public int totalTime;
	public int maxCars;
	public double populationRate;
	
	
	public int maxLanes = 0;
	ArrayList<ArrayList<HighwayGridObject>> highway;
	ArrayList<HighwayExitGridObject> exitLane;
	ArrayList<Integer> segmentEnds;
	ArrayList<Double> dieRates;
	ArrayList<Integer> slowdowns;
	double initPopRate = -1;
	int policy;
	double autoProb;
	
	public Highway(ArrayList<Segment> segments, int policy, double popRate, double autoProb){
		this.autoProb = autoProb;
		populationRate = popRate;
		highway = new ArrayList<ArrayList<HighwayGridObject>>();
		exitLane = new ArrayList<HighwayExitGridObject>();
		segmentEnds = new ArrayList<Integer>();
		dieRates = new ArrayList<Double>();
		slowdowns = new ArrayList<Integer>();
		for(Segment segment: segments){
			if(initPopRate<0){
				initPopRate = getPopulateRate(segment.getFeet(), segment.getTrafficVolume());
			}
			addSegment(segment.getFeet(),segment.getLanes(),segment.getTrafficVolume());
		}
		this.policy = policy;
		maxCars = numCars;
	}
	
	public void addSlowdown(int from, int to, int speed){
		for(int i = from; i < to; i++){
			slowdowns.set(i, speed);
		}
	}
	
	private int getExitLength(int feet, int trafficVolume){
		return 20;
	}
	
	private double getSpawnRate(int feet, int trafficVolume){
		return 0;
//		if(feet/7%2==0) return .01;
//		else return 0;
	}
	
	private double getDieRate(int feet, int trafficVolume){
		return 0;
//		if(feet/7%2==0) return 0;
//		else return .25;
	}
	
	private double getPopulateRate(int feet, int trafficVolume){
		return populationRate;
//		return .4;
	}
	
	private int getSegment(int column){
		for(int i = 0; i < segmentEnds.size(); i++){
			if(column < segmentEnds.get(i)) return i;
		}
		return segmentEnds.get(segmentEnds.size()-1);
	}
	
	private void addSegment(int feet, int numLanes, int trafficVolume){
		
		int cells = feet/Car.CAR_WIDTH;
		int exitLength = Math.min(cells, getExitLength(feet,trafficVolume));
		
		for(int i = 0; i < cells; i++){
			// Generate Highway
			ArrayList<HighwayGridObject> cellColumn = new ArrayList<HighwayGridObject>();
			for(int j = 0; j < MAX_LANES; j++){
				if(j < MAX_LANES - numLanes){
					HighwayGridObject hgo = new HighwayGridObject(false);
					cellColumn.add(hgo);
				} else {
					HighwayGridObject hgo = new HighwayGridObject(true);
					if(Math.random() < getPopulateRate(feet,trafficVolume)) {
						hgo.addCar(new Car(Car.DEF_SPEED,autoProb,segmentEnds.size()));
						numCars++;
					}
					cellColumn.add(hgo);
				}
			}
			maxLanes = MAX_LANES;
			highway.add(cellColumn);
			dieRates.add(getDieRate(feet, trafficVolume));
			slowdowns.add(Car.MAX_SPEED);
			// Generate exit lane
			if(i < cells - exitLength){
				exitLane.add(new HighwayExitGridObject(false,segmentEnds.size(),0));
			} else if(i == cells - exitLength){
				exitLane.add(new HighwayExitGridObject(true,segmentEnds.size(), getSpawnRate(feet,trafficVolume)));
			} else {
				exitLane.add(new HighwayExitGridObject(true, segmentEnds.size(), 0));
			}
		}
		segmentEnds.add(highway.size());
		
	}
	
	private int getFollowingDistance(int column, int lane){

		if(lane<0) return 0;
		if(lane >= maxLanes || !highway.get(column).get(lane).openSpace) return 0;
		int space = 1;
		while(space <= Car.MAX_SPEED && space + column < highway.size() && !highway.get(column+space).get(lane).hasCar && highway.get(column+space).get(lane).openSpace) space++;
		return space-1;
	} 
	
	private int getFollowingVelocity(int column, int lane){
		int space = 1;
		while(space <= Car.MAX_SPEED && space + column < highway.size() && !highway.get(column+space).get(lane).hasCar && highway.get(column+space).get(lane).openSpace) space++;
		if(space + column < highway.size() && highway.get(column+space).get(lane).hasCar) {
			if(highway.get(column+space).get(lane).car.autonomous) return highway.get(column+space).get(lane).car.velocity;
			else return Math.max(highway.get(column+space).get(lane).car.velocity -1,0);
		}
		return 0;
	}
	
	private int getFollowingDistanceExiting(int column){
		if(!exitLane.get(column).openSpace) return 0;
		int space = 1;
		while(space <= Car.MAX_SPEED && space + column < exitLane.size() && !exitLane.get(column+space).hasCar && exitLane.get(column+space).openSpace) space++;
		return space-1;
	} 
	
	private void removeCar(Car c){
		numCars--;
		totalDistance += c.distance;
		totalTime += c.time;
	}

	private void simChangeLanes(){
		int columns = highway.size();
		for(int i=0; i<columns-1; i++){
			ArrayList<HighwayGridObject> column = highway.get(i);
			for(int j=0; j<maxLanes;j++){
				HighwayGridObject spot = column.get(j);
				if(spot.hasCar){
					Car car = spot.deleteCar();
					int space = getFollowingDistance(i,j);
					int Lspace = 0;
					if(j > 0 && !column.get(j-1).hasCar) Lspace = getFollowingDistance(i,j-1);
					int Rspace = 0;
					if(j < maxLanes -1 && !column.get(j+1).hasCar) Rspace = getFollowingDistance(i,j+1);
					if(j == maxLanes -1 && !exitLane.get(i).hasCar && car.exiting){
						Rspace = getFollowingDistanceExiting(i);
						space = Math.min(segmentEnds.get(car.segment)-i-2, space);
					}
					if(car.exiting){
						space = Math.max(0, space-1);
					}
					int new_lane = j;
					double rand = Math.random();
					double changeRProb = Car.CHANGE_PROB;
					double changeLProb = Car.CHANGE_PROB;
					if(car.exiting) {
						changeRProb = 1;
						changeLProb = 0;
					}
					if(Rspace>space && rand < changeRProb){
						new_lane++;
						j++;
					} else if(Lspace>space && rand < changeLProb){
						new_lane--;
					}
					if(new_lane == maxLanes){
						exitLane.get(i).addCar(car);
					} else {
						column.get(new_lane).addCar(car);
					}
				}
			}
			HighwayGridObject spot = exitLane.get(i);
			if(spot.hasCar && !spot.car.exiting){
				Car car = spot.deleteCar();
				int space = getFollowingDistanceExiting(i);
				int Lspace = 0;
				if(!column.get(maxLanes-1).hasCar) Lspace = getFollowingDistance(i,maxLanes-1);
				double rand = Math.random();
				if(Lspace>space){
					column.get(maxLanes-1).addCar(car);
				} else {
					exitLane.get(i).addCar(car);
				}
			}
		}
	}
	
	private void simNasch(){
		int columns = highway.size();
		for(int m=0; m<maxLanes; m++){
			for(int n=columns - 1; n>=0;n--){
				HighwayGridObject spot = highway.get(n).get(m);
				if(spot.hasCar){
					Car car = spot.car;
					int space = getFollowingDistance(n, m);
					if(car.autonomous){
						car.updateVelocity(Math.min(slowdowns.get(n),space),getFollowingVelocity(n,m));
					} else {
						car.updateVelocity(Math.min(slowdowns.get(n),space));
					}
				}
			}
		}
		
		for(int m=0; m<maxLanes; m++){
			for(int n=columns - 1; n>=0;n--){
				HighwayGridObject spot = highway.get(n).get(m);
				if(spot.hasCar){
					Car car = spot.deleteCar();
					int newX = n + car.velocity;
					if(!(newX >= columns - 2)){
						HighwayGridObject new_spot = highway.get(newX).get(m);
						new_spot.addCar(car);
						int newSeg = getSegment(newX);
						car.updateSegment(newSeg, dieRates.get(newSeg));
					} else {
						removeCar(car);
					}
				}
			}
		}
		
		for(int n = 0; n < columns; n++){
			HighwayExitGridObject spot = exitLane.get(n);
			if(spot.hasCar){
				Car car = spot.deleteCar();
				int space = getFollowingDistanceExiting(n);
				car.updateVelocity(Math.min(slowdowns.get(n),space));
				int newX = n + car.velocity;
				if(!(newX >= columns - 2 || (car.exiting && getSegment(newX+2) > car.segment))){
					HighwayGridObject new_spot = exitLane.get(newX);
					new_spot.addCar(car);
					int newSeg = getSegment(newX);
					car.updateSegment(newSeg, dieRates.get(newSeg));
				} else {
					removeCar(car);
				}
				n = newX;
			}
		}
		
		for(int n = 0; n < columns; n++){
			if(exitLane.get(n).openSpace && !exitLane.get(n).hasCar){
				exitLane.get(n).updateSpawn(autoProb);
			}
		}
		
//		for(int m = 0; m < maxLanes; m++){
//			HighwayGridObject hgo = highway.get(0).get(m);
//			if(hgo.openSpace && Math.random()<initPopRate){
//				Car newCar = new Car(Car.DEF_SPEED,false,-1);
//				newCar.updateSegment(0, dieRates.get(0));
//				hgo.addCar(newCar);
//				numCars++;
//			}
//		}
		
	}
	
	public void simulate(){
		simChangeLanes();
		simNasch();
	}

}
