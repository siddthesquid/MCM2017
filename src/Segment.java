
public class Segment {
	
	double startMile;
	double endMile;
	int lanes;
	int trafficVolume;
	
	public Segment(double startMile, double endMile, int lanes, int trafficVolume){
		this.startMile = startMile;
		this.endMile = endMile;
		this.lanes = lanes;
		this.trafficVolume = trafficVolume;
	}
	
	public int getFeet(){
		return (int)((endMile - startMile) * 5280);
	}
	
	public int getLanes(){
		return lanes;
	}
	
	public int getTrafficVolume(){
		return trafficVolume;
	}

}
