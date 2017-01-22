
public class Probe {
	
	int carsPassed;
	int time;
	
	public Probe(){
		carsPassed = 0;
		time = 0;
	}
	
	public void incCars(){
		carsPassed++;
	}
	
	public double getThroughput(){
		return carsPassed * 1.0/time;
	}
	
	

}
