
public class Car {
	
	public static final int DEF_SPEED = 3;
	public static final int MAX_SPEED = 5;
	public static final double DECEL_PROB = 0.25;
	public static final double CHANGE_PROB = 0.5;
	public static final int CAR_WIDTH = 15;
	public static final double AUTO_PROB = .5;
	
	public int velocity;
	public boolean autonomous;
	public int distance;
	public int time;
	public boolean exiting;
	public int segment;
	
	public Car(int initVelocity, boolean autonomous, int segment){
		velocity = initVelocity;
		if(Math.random() < AUTO_PROB){
			this.autonomous = true;
		} else {
			this.autonomous = false;
		}
		distance = 0;
		time = 0;
		exiting = false;
		this.segment = segment;
	}
	
	public void updateVelocity(int followingDistance){
		velocity += 1;
		velocity = Math.min(Math.min(velocity, followingDistance), MAX_SPEED);
		if(velocity >= 2 && Math.random() < DECEL_PROB) velocity--;
		
		distance+=CAR_WIDTH;
		time+=1;
	}
	
	public void updateSegment(int newSegment, double exitProb){
		if(newSegment > segment){
			exiting = Math.random() < exitProb;
		}
		segment = newSegment;
	}

}
