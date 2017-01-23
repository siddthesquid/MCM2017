
public class HighwayExitGridObject extends HighwayGridObject {
	
	public double spawnRate;
	int segment;
	
	public HighwayExitGridObject(boolean openSpace, int segment, double spawnRate){
		super(openSpace);
		this.segment = segment;
		this.spawnRate = spawnRate;
	}
	
	public void updateSpawn(){
		if(Math.random() < spawnRate) addCar(new Car(Car.DEF_SPEED,false,segment));
	}

}
